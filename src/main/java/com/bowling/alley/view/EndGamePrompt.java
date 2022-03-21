package com.bowling.alley.view;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EndGamePrompt implements ActionListener
{

	private JFrame win;
	private JButton yesButton, noButton;

	private int result;

	public EndGamePrompt(String partyName)
	{
		result =0;
		initWindow(partyName);

		// Label Panel
		JPanel labelPanel = createLabelPanel(partyName);

		// Button Panel
		JPanel buttonPanel = createButtonPanel();

		// Clean up main panel
		createMainPanel(buttonPanel, labelPanel);
	}

	private void initWindow(String partyName)
	{
		win = new JFrame("Another Game for " + partyName + "?" );
		win.getContentPane().setLayout(new BorderLayout());
		((JPanel) win.getContentPane()).setOpaque(false);
	}

	@SuppressWarnings("deprecation")
	private void createMainPanel(JPanel buttonPanel, JPanel labelPanel)
	{
		JPanel colPanel = new JPanel();
		colPanel.setLayout(new GridLayout( 2, 1 ));
		colPanel.add(labelPanel);
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

	private JPanel createLabelPanel(String partyName)
	{
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new FlowLayout());

		JLabel message = new JLabel( "Party " + partyName + " has finished bowling.\nWould they like to bowl another game?" );

		labelPanel.add(message);
		return labelPanel;
	}

	private JPanel createButtonPanel()
	{
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2));

		yesButton = createButton("Yes", buttonPanel);
		noButton = createButton("No", buttonPanel);

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

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(yesButton))
		{
			result=1;
		}
		else if (e.getSource().equals(noButton))
		{
			result=2;
		}
	}

	public int getResult()
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

		return result;
	}

	@SuppressWarnings("deprecation")
	public void distroy()
	{
		win.hide();
	}

}

