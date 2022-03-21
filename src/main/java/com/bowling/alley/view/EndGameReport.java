package main.java.com.bowling.alley.view;
/**
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.java.com.bowling.alley.model.Bowler;
import main.java.com.bowling.alley.model.Party;

public class EndGameReport implements ActionListener, ListSelectionListener
{
	private JFrame win;
	private JButton printButton, finished;
	private JList<String> memberList;
	private Vector<String> retVal;

	private int result;
	private String selectedMember;

	public EndGameReport( String partyName, Party party )
	{
		result =0;
		retVal = new Vector<>();

		initWindow(partyName);

		// Member Panel
		JPanel partyPanel = createPartyPanel(party);

		// Button Panel
		JPanel buttonPanel = createButtonPanel();

		createMainPanel(partyPanel, buttonPanel);
	}

	private void initWindow(String partyName)
	{
		win = new JFrame("End Game Report for " + partyName + "?" );
		win.getContentPane().setLayout(new BorderLayout());
		((JPanel) win.getContentPane()).setOpaque(false);
	}

	@SuppressWarnings("deprecation")
	private void createMainPanel(JPanel partyPanel, JPanel buttonPanel)
	{
		// Clean up main panel
		JPanel colPanel = new JPanel();
		colPanel.setLayout(new GridLayout( 1, 2 ));
		colPanel.add(partyPanel);
		colPanel.add(buttonPanel);

		win.getContentPane().add("Center", colPanel);

		win.pack();

		// Center Window on Screen
		Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
		win.setLocation(
				((screenSize.width) / 2) - ((win.getSize().width) / 2),
				((screenSize.height) / 2) - ((win.getSize().height) / 2));
		win.show();
	}

	private JPanel createPartyPanel(Party party)
	{
		// Member Panel
		JPanel partyPanel = new JPanel();
		partyPanel.setLayout(new FlowLayout());
		partyPanel.setBorder(new TitledBorder("Party Members"));

		Vector<String> myVector = new Vector<>();
		Iterator<Bowler> iter = party.getMembers().iterator();

		while (iter.hasNext())
		{
			myVector.add( ((Bowler)iter.next()).getNick() );
		}

		memberList = new JList<>(myVector);
		memberList.setFixedCellWidth(120);
		memberList.setVisibleRowCount(5);
		memberList.addListSelectionListener(this);

		JScrollPane partyPane = new JScrollPane(memberList);
		partyPanel.add(partyPane);
		partyPanel.add(memberList);

		return partyPanel;
	}

	private JPanel createButtonPanel()
	{
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 1));

		printButton = createButton("Print Report");
		finished = createButton("Finished");

		buttonPanel.add(printButton);
		buttonPanel.add(finished);

		return buttonPanel;
	}

	private JButton createButton(String title)
	{
		JButton button = new JButton(title);
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		button.addActionListener(this);
		panel.add(button);
		return button;
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(printButton))
		{
			//Add selected to the vector.
			retVal.add(selectedMember);
		}

		if (e.getSource().equals(finished))
		{
			win.hide();
			result = 1;
		}

	}

	@SuppressWarnings("rawtypes")
	public void valueChanged(ListSelectionEvent e)
	{
		selectedMember = ((String) ((JList) e.getSource()).getSelectedValue());
	}

	public Vector<String> getResult()
	{
		while ( result == 0 )
		{
			try
			{
				Thread.sleep(10);
			}
			catch ( InterruptedException e )
			{
				System.err.println( "Interrupted" );
			}
		}

		return retVal;
	}

	@SuppressWarnings("deprecation")
	public void destroy()
	{
		win.hide();
	}


}

