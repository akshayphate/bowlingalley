package com.bowlingalley.view;/* com.bowlingalley.view.AddPartyView.java
 *
 *  Version:
 * 		 $Id$
 * 
 *  Revisions:
 * 		$Log: com.bowlingalley.view.AddPartyView.java,v $
 * 		Revision 1.7  2003/02/20 02:05:53  ???
 * 		Fixed addPatron so that duplicates won't be created.
 * 		
 * 		Revision 1.6  2003/02/09 20:52:46  ???
 * 		Added comments.
 * 		
 * 		Revision 1.5  2003/02/02 17:42:09  ???
 * 		Made updates to migrate to observer model.
 * 		
 * 		Revision 1.4  2003/02/02 16:29:52  ???
 * 		Added ControlDeskEvent and ControlDeskObserver. Updated Queue to allow access to Vector so that contents could be viewed without destroying. Implemented observer model for most of ControlDesk.
 * 		
 * 
 */

/**
 * Class for GUI components need to add a party
 *
 */

import com.bowlingalley.BowlerFile;
import com.bowlingalley.ControlDeskView;
import com.bowlingalley.NewPatronView;
import com.bowlingalley.model.Bowler;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.util.*;

/**
 * Constructor for GUI used to Add Parties to the waiting party queue.
 *  
 */

public class AddPartyView implements ActionListener, ListSelectionListener {

	private int maxSize;

	private JFrame win;
	private JButton addPatron, newPatron, remPatron, finished;
	private JList partyList, allBowlers;
	private Vector party, bowlerdb;
	private Integer lock;

	private ControlDeskView controlDesk;

	private String selectedNick, selectedMember;

	public AddPartyView(ControlDeskView controlDesk, int max) {

		this.controlDesk = controlDesk;
		maxSize = max;

		win = new JFrame("Add Party");
		win.getContentPane().setLayout(new BorderLayout());
		((JPanel) win.getContentPane()).setOpaque(false);

		JPanel colPanel = new JPanel();
		colPanel.setLayout(new GridLayout(1, 3));

		// Party Panel
		createPartyPanel(colPanel);

		// Bowler Database
		createBowlerPanel(colPanel);

		// Button Panel
		createButtonPanel(colPanel);

		// Center Window on Screen
		displayWindow(colPanel);

	}

	private void displayWindow(JPanel colPanel) {
		win.getContentPane().add("Center", colPanel);

		win.pack();
		Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
		win.setLocation(
			((screenSize.width) / 2) - ((win.getSize().width) / 2),
			((screenSize.height) / 2) - ((win.getSize().height) / 2));
		win.show();
	}

	private void createButtonPanel(JPanel parentPanel) {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 1));
		// Insets buttonMargin = new Insets(4, 4, 4, 4);

		addPatron = createButton("Add to Party", buttonPanel);
		remPatron = createButton("Remove Member", buttonPanel);
		newPatron = createButton("New Patron", buttonPanel);
		finished  = createButton("Finished", buttonPanel);

		parentPanel.add(buttonPanel);
	}

	private JButton createButton(String buttonName, JPanel parentPanel) {
		JButton button = new JButton(buttonName);
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		button.addActionListener(this);
		panel.add(button);
		parentPanel.add(panel);

		return button;
	}

	private void createBowlerPanel(JPanel parentPanel) {
		JPanel bowlerPanel = new JPanel();
		bowlerPanel.setLayout(new FlowLayout());
		bowlerPanel.setBorder(new TitledBorder("Bowler Database"));

		try {
			bowlerdb = new Vector(BowlerFile.getBowlers());
		} catch (Exception e) {
			System.err.println("File Error");
			bowlerdb = new Vector();
		}
		allBowlers = new JList(bowlerdb);
		allBowlers.setVisibleRowCount(8);
		allBowlers.setFixedCellWidth(120);
		JScrollPane bowlerPane = new JScrollPane(allBowlers);
		bowlerPane.setVerticalScrollBarPolicy(
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		allBowlers.addListSelectionListener(this);
		bowlerPanel.add(bowlerPane);
		parentPanel.add(bowlerPane);
	}

	private void createPartyPanel(JPanel parentPanel) {
		JPanel partyPanel = new JPanel();
		partyPanel.setLayout(new FlowLayout());
		partyPanel.setBorder(new TitledBorder("Your Party"));

		party = new Vector();
		Vector empty = new Vector();
		empty.add("(Empty)");

		partyList = new JList(empty);
		partyList.setFixedCellWidth(120);
		partyList.setVisibleRowCount(5);
		partyList.addListSelectionListener(this);
		JScrollPane partyPane = new JScrollPane(partyList);
		//        partyPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		partyPanel.add(partyPane);
		parentPanel.add(partyPanel);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(addPatron)) {
			handleAddPatronEvent();
		}
		if (e.getSource().equals(remPatron)) {
			handleRemovePatronEvent();
		}
		if (e.getSource().equals(newPatron)) {
			handleNewPatronEvent();
		}
		if (e.getSource().equals(finished)) {
			handleFinishedEvent();
		}

	}

	private void handleFinishedEvent() {
		if ( party != null && party.size() > 0) {
			controlDesk.updateAddParty( this );
		}
		win.hide();
	}

	private void handleNewPatronEvent() {
		NewPatronView newPatron = new NewPatronView( this );
	}

	private void handleRemovePatronEvent() {
		if (selectedMember != null) {
			party.removeElement(selectedMember);
			partyList.setListData(party);
		}
	}

	private void handleAddPatronEvent() {
		if (selectedNick != null && party.size() < maxSize) {
			if (party.contains(selectedNick)) {
				System.err.println("Member already in Party");
			} else {
				party.add(selectedNick);
				partyList.setListData(party);
			}
		}
	}

	/**
 * Handler for List actions
 * @param e the ListActionEvent that triggered the handler
 */

	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource().equals(allBowlers)) {
			selectedNick =
				((String) ((JList) e.getSource()).getSelectedValue());
		}
		if (e.getSource().equals(partyList)) {
			selectedMember =
				((String) ((JList) e.getSource()).getSelectedValue());
		}
	}

/**
 * Accessor for com.bowlingalley.model.Party
 */

	public Vector getNames() {
		return party;
	}

/**
 * Called by NewPatronView to notify com.bowlingalley.view.AddPartyView to update
 * 
 * @param newPatron the NewPatronView that called this method
 */

	public void updateNewPatron(NewPatronView newPatron) {
		try {
			Bowler checkBowler = BowlerFile.getBowlerInfo( newPatron.getNick() );
			if ( checkBowler == null ) {
				BowlerFile.putBowlerInfo(
					newPatron.getNick(),
					newPatron.getFull(),
					newPatron.getEmail());
				bowlerdb = new Vector(BowlerFile.getBowlers());
				allBowlers.setListData(bowlerdb);
				party.add(newPatron.getNick());
				partyList.setListData(party);
			} else {
				System.err.println( "A com.bowlingalley.model.Bowler with that name already exists." );
			}
		} catch (Exception e2) {
			System.err.println("File I/O Error");
		}
	}

/**
 * Accessor for com.bowlingalley.model.Party
 */

	public Vector getParty() {
		return party;
	}

}
