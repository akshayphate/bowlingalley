package main.java.com.bowling.alley.view;
/*
 * PinSetterView/.java
 *
 * Version:
 *   $Id$
 *
 * Revision:
 *   $Log$
 */

/**
 *  constructs a prototype PinSetter GUI
 *
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.com.bowling.alley.event.PinsetterEvent;
import main.java.com.bowling.alley.observer.PinsetterObserver;


public class PinSetterView implements PinsetterObserver {


	private Vector<JLabel> pinVect = new Vector<>();
	private Vector<JPanel> pinPanels = new Vector<>();
	private JPanel firstRoll;
	private JPanel secondRoll;

	/**
	 * Constructs a Pin Setter GUI displaying which roll it is with
	 * yellow boxes along the top (1 box for first roll, 2 boxes for second)
	 * and displays the pins as numbers in this format:
	 *
	 *                7   8   9   10
	 *                  4   5   6
	 *                    2   3
	 *                      1
	 *
	 */


	private JFrame frame;

	public PinSetterView (int laneNum)
	{
		frame = new JFrame ( "Lane " + laneNum + ":" );

		Container cpanel = frame.getContentPane();

		//********************Top of GUI indicates first or second roll
		createPins();
		JPanel pins = arrangePins();

		JPanel top = createTopPanel();
		cpanel.add ( top, BorderLayout.NORTH );

		pins.setBackground ( Color.black );
		pins.setForeground ( Color.yellow );

		cpanel.add ( pins, BorderLayout.CENTER );

		frame.pack();


		//	frame.show();
	}

	private JPanel createTopPanel()
	{
		JPanel top = new JPanel ( );

		firstRoll = new JPanel ( );
		firstRoll.setBackground( Color.yellow );

		secondRoll = new JPanel ( );
		secondRoll.setBackground ( Color.black );

		top.add ( firstRoll, BorderLayout.WEST );
		top.add ( secondRoll, BorderLayout.EAST );
		top.setBackground ( Color.black );

		return top;
	}

	private void createPins()
	{
		for (int i=1; i<=10; i++)
		{
			JPanel pinPanel = new JPanel();
			JLabel pinLabel = new JLabel (String.valueOf(i));
			pinPanel.add(pinLabel);
			pinVect.add(pinLabel);
			pinPanels.add(pinPanel);
		}
	}

	private JPanel arrangePins()
	{
		JPanel pins = new JPanel ( );
		pins.setLayout ( new GridLayout ( 4, 7 ) );

		//******************************Fourth Row**************
		pins.add (pinPanels.elementAt(6));
		pins.add ( new JPanel ( ) );
		pins.add (pinPanels.elementAt(7));
		pins.add ( new JPanel ( ) );
		pins.add (pinPanels.elementAt(8));
		pins.add ( new JPanel ( ) );
		pins.add (pinPanels.elementAt(9));

		//*****************************Third Row***********

		pins.add (new JPanel ());
		pins.add (pinPanels.elementAt(3));
		pins.add (new JPanel());
		pins.add (pinPanels.elementAt(4));
		pins.add (new JPanel ());
		pins.add (pinPanels.elementAt(5));

		//*****************************Second Row**************
		pins.add (new JPanel());
		pins.add (new JPanel());
		pins.add (new JPanel());
		pins.add (pinPanels.elementAt(1));
		pins.add (new JPanel());
		pins.add (pinPanels.elementAt(2));
		pins.add (new JPanel());
		pins.add (new JPanel());

		//******************************First Row*****************
		pins.add (new JPanel());
		pins.add (new JPanel());
		pins.add (new JPanel());
		pins.add (pinPanels.elementAt(0));
		pins.add (new JPanel());
		pins.add (new JPanel());
		pins.add (new JPanel());
		//*********************************************************

		return pins;
	}


	/**
	 * This method receives a pinsetter event.  The event is the current
	 * state of the PinSetter and the method changes how the GUI looks
	 * accordingly.  When pins are "knocked down" the corresponding label
	 * is grayed out.  When it is the second roll, it is indicated by the
	 * appearance of a second yellow box at the top.
	 *
	 * @param e    The state of the pinsetter is sent in this event.
	 */


	public void receivePinsetterEvent(PinsetterEvent pe)
	{
		if ( !(pe.isFoulCommited()) )
		{
			JLabel tempPin = new JLabel();

			for ( int c = 0; c < 10; c++ )
			{
				boolean pin = pe.pinKnockedDown (c);
				tempPin = (JLabel)pinVect.get (c);

				if (pin)
				{
					tempPin.setForeground (Color.lightGray);
				}
			}
		}

		if ( pe.getThrowNumber() == 1 )
		{
			secondRoll.setBackground ( Color.yellow );
		}

		if ( pe.pinsDownOnThisThrow() == -1)
		{
			for ( int i = 0; i != 10; i++)
			{
				((JLabel)pinVect.get(i)).setForeground(Color.black);
			}

			secondRoll.setBackground( Color.black);
		}
	}

	@SuppressWarnings("deprecation")
	public void show()
	{
		frame.show();
	}

	@SuppressWarnings("deprecation")
	public void hide()
	{
		frame.hide();
	}

}
