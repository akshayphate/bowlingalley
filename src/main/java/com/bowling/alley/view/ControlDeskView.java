package com.bowling.alley.view;
/* ControlDeskView.java
 *
 *  Version:
 *			$Id$
 *
 *  Revisions:
 * 		$Log$
 *
 */

/**
 * Class for representation of the control desk
 *
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;


import com.bowling.alley.event.ControlDeskEvent;
import com.bowling.alley.observer.ControlDeskObserver;
import com.bowling.alley.publisher.ControlDesk;
import com.bowling.alley.publisher.Lane;


public class ControlDeskView implements ActionListener, ControlDeskObserver
{

	private JButton addParty, finished, assign, queries;
	private JFrame win;
	private JList<String> partyList;

	/** The maximum  number of members in a party */
	private int maxMembers;

	private ControlDesk controlDesk;

	/**
	 * Displays a GUI representation of the ControlDesk
	 *
	 */

	public ControlDeskView(ControlDesk controlDesk, int maxMembers)
	{
		this.controlDesk = controlDesk;
		this.maxMembers = maxMembers;
		int numLanes = controlDesk.getNumLanes();
		int numFrames = controlDesk.getNumFrames();
		boolean additionalThrow = controlDesk.getAdditionalThrow();


		initWindow();

		// Controls Panel
		JPanel controlsPanel;
		if(!additionalThrow){
			controlsPanel = createControlsPanel();
		}
		else{
			controlsPanel = new JPanel();
		}

		// Lane Status Panel
		JPanel laneStatusPanel = createLaneStatusPanel(numLanes, numFrames);

		// Party Queue Panel
		JPanel partyPanel ;
		if(!additionalThrow){
			partyPanel = createPartyPanel();
		}
		else{
			partyPanel = new JPanel();
		}


		// Clean up main panel
		createMainPanel(controlsPanel, laneStatusPanel, partyPanel);
	}

	private void initWindow()
	{
		win = new JFrame("Control Desk");
		win.getContentPane().setLayout(new BorderLayout());
		((JPanel) win.getContentPane()).setOpaque(false);
	}

	@SuppressWarnings("deprecation")
	private void createMainPanel(JPanel controlsPanel, JPanel laneStatusPanel, JPanel partyPanel)
	{
		JPanel colPanel = new JPanel();
		colPanel.setLayout(new BorderLayout());
		colPanel.add(controlsPanel, "East");
		colPanel.add(laneStatusPanel, "Center");
		colPanel.add(partyPanel, "West");

		win.getContentPane().add("Center", colPanel);
		win.pack();

		/* Close program when this window closes */
		win.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// Center Window on Screen
		Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
		win.setLocation(
				((screenSize.width) / 2) - ((win.getSize().width) / 2),
				((screenSize.height) / 2) - ((win.getSize().height) / 2)
		);
		win.show();
	}

	private JPanel createControlsPanel()
	{
		JPanel controlsPanel = new JPanel();
		controlsPanel.setLayout(new GridLayout(3, 1));
		controlsPanel.setBorder(new TitledBorder("Controls"));

		addParty = createButton("Add Party", controlsPanel);
		queries = createButton("Queries", controlsPanel);
		finished = createButton("Finished", controlsPanel);

		return controlsPanel;
	}

	private JPanel createLaneStatusPanel(int numOfLanes, int numOfFrames)
	{
		JPanel laneStatusPanel = new JPanel();
		laneStatusPanel.setLayout(new GridLayout(numOfLanes, 1));
		laneStatusPanel.setBorder(new TitledBorder("Lane Status"));

		HashSet<Lane> lanes = controlDesk.getLanes();
		Iterator<Lane> it = lanes.iterator();
		int laneCount=0;

		while (it.hasNext())
		{
			Lane curLane = it.next();
			LaneStatusView laneStat = new LaneStatusView(curLane,(laneCount+1), numOfFrames);
			curLane.subscribe(laneStat);
			curLane.getPinsetter().subscribe(laneStat);
			JPanel lanePanel = laneStat.showLane();
			lanePanel.setBorder(new TitledBorder("Lane" + ++laneCount ));
			laneStatusPanel.add(lanePanel);
		}

		return laneStatusPanel;
	}

	private JPanel createPartyPanel()
	{
		JPanel partyPanel = new JPanel();
		partyPanel.setLayout(new FlowLayout());
		partyPanel.setBorder(new TitledBorder("Party Queue"));

		Vector<String> empty = new Vector<>();
		empty.add("(Empty)");

		partyList = new JList<>(empty);
		partyList.setFixedCellWidth(120);
		partyList.setVisibleRowCount(10);
		JScrollPane partyPane = new JScrollPane(partyList);
		partyPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		partyPanel.add(partyPane);

		return partyPanel;
	}

	private JButton createButton(String title, JPanel parent)
	{
		JButton button = new JButton(title);
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		button.addActionListener(this);
		panel.add(button);
		parent.add(panel);
		return button;
	}

	/**
	 * Handler for actionEvents
	 *
	 * @param e	the ActionEvent that triggered the handler
	 *
	 */

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(addParty))
		{
			try {
				new AddPartyView(this, maxMembers);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		else if (e.getSource().equals(assign))
		{
			controlDesk.assignLane();
		}
		else if(e.getSource().equals(queries))
		{
			System.out.println("Queries");
			try {
				new QueriesView();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		else if (e.getSource().equals(finished))
		{
			win.hide();
			System.exit(0);
		}
	}

	/**
	 * Receive a new party from andPartyView.
	 *
	 * @param addPartyView	the AddPartyView that is providing a new party
	 *
	 */

	public void updateAddParty(AddPartyView addPartyView)
	{
		controlDesk.addPartyQueue(addPartyView.getParty());
	}

	/**
	 * Receive a broadcast from a ControlDesk
	 *
	 * @param ce	the ControlDeskEvent that triggered the handler
	 *
	 */

	public void receiveControlDeskEvent(ControlDeskEvent ce)
	{
		partyList.setListData(ce.getPartyQueue());
	}
}
