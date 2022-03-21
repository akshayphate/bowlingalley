package main.java.com.bowling.alley.view;
/* AddPartyView.java
 *
 *  Version
 *  $Id$
 *
 *  Revisions:
 * 		$Log: NewPatronView.java,v $
 * 		Revision 1.3  2003/02/02 16:29:52  ???
 * 		Added ControlDeskEvent and ControlDeskObserver. Updated Queue to allow access to Vector so that contents could be viewed without destroying. Implemented observer model for most of ControlDesk.
 *
 *
 */

/**
 * Class for GUI components need to add a patron
 *
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import main.java.com.bowling.alley.view.AddPartyView;

public class NewPatronView implements ActionListener
{
	private JFrame win;
	private JButton abort, finished;
	private JTextField nickField, fullField, emailField;
	private String nick, full, email;

	private boolean done;
	private AddPartyView addParty;

	public NewPatronView(AddPartyView v)
	{
		addParty=v;
		done = false;

		initWindow();

		// Patron Panel
		JPanel patronPanel = createPatronPanel();

		// Button Panel
		JPanel buttonPanel = createButtonPanel();

		// Clean up main panel
		createMainPanel(patronPanel, buttonPanel);
	}

	private void initWindow()
	{
		win = new JFrame("Add Patron");
		win.getContentPane().setLayout(new BorderLayout());
		((JPanel) win.getContentPane()).setOpaque(false);
	}

	@SuppressWarnings("deprecation")
	private void createMainPanel(JPanel patronPanel, JPanel buttonPanel)
	{
		JPanel colPanel = new JPanel();
		colPanel.setLayout(new BorderLayout());
		colPanel.add(patronPanel, "Center");
		colPanel.add(buttonPanel, "East");

		win.getContentPane().add("Center", colPanel);

		win.pack();

		// Center Window on Screen
		Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
		win.setLocation(
				((screenSize.width) / 2) - ((win.getSize().width) / 2),
				((screenSize.height) / 2) - ((win.getSize().height) / 2));
		win.show();
	}

	private JPanel createPatronPanel()
	{
		JPanel patronPanel = new JPanel();
		patronPanel.setLayout(new GridLayout(3, 1));
		patronPanel.setBorder(new TitledBorder("Your Info"));

		nickField = createField("Nick Name", patronPanel);
		fullField = createField("Full Name", patronPanel);
		emailField = createField("E-Mail", patronPanel);

		return patronPanel;
	}

	private JPanel createButtonPanel()
	{
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 1));

		finished = createButton("Add Patron", buttonPanel);
		abort = createButton("Abort", buttonPanel);

		return buttonPanel;
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

	private JTextField createField(String title, JPanel parent)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JLabel label = new JLabel(title);
		JTextField field = new JTextField("", 15);
		panel.add(label);
		panel.add(field);
		parent.add(panel);
		return field;
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(abort))
		{
			done = true;
			win.hide();
		}

		if (e.getSource().equals(finished))
		{
			nick = nickField.getText();
			full = fullField.getText();
			email = emailField.getText();
			done = true;
			addParty.updateNewPatron( this );
			win.hide();
		}
	}

	public boolean done()
	{
		return done;
	}

	public String getNick()
	{
		return nick;
	}

	public String getFull()
	{
		return full;
	}

	public String getEmail()
	{
		return email;
	}
}
