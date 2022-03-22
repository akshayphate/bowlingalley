package com.bowling.alley.view;
/*
 *  constructs a prototype Lane View
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.bowling.alley.event.LaneEvent;
import com.bowling.alley.model.Bowler;
import com.bowling.alley.model.Party;
import com.bowling.alley.observer.LaneObserver;
import com.bowling.alley.publisher.Lane;
import com.bowling.alley.util.SimulateThrow;
import com.bowling.alley.util.SimulateThrowFactory;

import java.util.*;

public class LaneView implements LaneObserver, ActionListener
{
	private boolean initDone = true;

	JFrame frame;
	Container cpanel;
	Vector<Bowler> bowlers;
	int cur;
	Iterator<Bowler> bowlIt;

	JPanel[][] balls;
	JLabel[][] ballLabel;
	JPanel[][] scores;
	JLabel[][] scoreLabel;
	JPanel[][] ballGrid;
	JPanel[] pins;

	JButton maintenance;
	Lane lane;
	JButton throwButton;
	int noOfFrames;

	public LaneView(Lane lane, int laneNum, int noOfFrames)
	{
		this.lane = lane;
		this.lane.setCurrentLaneNumber(laneNum);
		this.noOfFrames = noOfFrames;

		initDone = true;
		frame = new JFrame("Lane " + laneNum + ":");
		cpanel = frame.getContentPane();
		cpanel.setLayout(new BorderLayout());
		frame.addWindowListener(new WindowAdapter() {
			@SuppressWarnings("deprecation")
			public void windowClosing(WindowEvent e) {
				frame.hide();
			}
		});

		cpanel.add(new JPanel());
		this.throwButton = new JButton("Hide Bowling Window");
		this.throwButton.setBackground(Color.GREEN);
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

	private JPanel makeFrame(Party party)
	{
		initDone = false;
		bowlers = party.getMembers();
		int numBowlers = bowlers.size();
		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(0, 1));
		int val = noOfFrames*2 + 3;
		balls = new JPanel[numBowlers][val];
		ballLabel = new JLabel[numBowlers][val];
		scores = new JPanel[numBowlers][noOfFrames];
		scoreLabel = new JLabel[numBowlers][noOfFrames];
		ballGrid = new JPanel[numBowlers][noOfFrames];
		pins = new JPanel[numBowlers];

		for (int i = 0; i != numBowlers; i++)
		{
			for (int j = 0; j != val; j++)
			{
				ballLabel[i][j] = new JLabel(" ");
				balls[i][j] = new JPanel();
				balls[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE));
				balls[i][j].add(ballLabel[i][j]);
			}
		}

		for (int i = 0; i != numBowlers; i++)
		{
			for (int j = 0; j != noOfFrames-1; j++)
			{
				ballGrid[i][j] = new JPanel();
				ballGrid[i][j].setLayout(new GridLayout(0, 3));
				ballGrid[i][j].add(new JLabel("  "), BorderLayout.EAST);
				ballGrid[i][j].add(balls[i][2 * j], BorderLayout.EAST);
				ballGrid[i][j].add(balls[i][2 * j + 1], BorderLayout.EAST);
			}

			int j = noOfFrames-1;
			ballGrid[i][j] = new JPanel();
			ballGrid[i][j].setLayout(new GridLayout(0, 3));
			ballGrid[i][j].add(balls[i][2 * j]);
			ballGrid[i][j].add(balls[i][2 * j + 1]);
			ballGrid[i][j].add(balls[i][2 * j + 2]);
		}

		for (int i = 0; i != numBowlers; i++)
		{
			pins[i] = new JPanel();
			pins[i].setBorder(BorderFactory.createTitledBorder(((Bowler) bowlers.get(i)).getNick()));
			pins[i].setLayout(new GridLayout(0, 10));

			for (int k = 0; k != noOfFrames; k++)
			{
				scores[i][k] = new JPanel();
				scoreLabel[i][k] = new JLabel("  ", SwingConstants.CENTER);
				scores[i][k].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				scores[i][k].setLayout(new GridLayout(0, 1));
				scores[i][k].add(ballGrid[i][k], BorderLayout.EAST);
				scores[i][k].add(scoreLabel[i][k], BorderLayout.SOUTH);
				pins[i].add(scores[i][k], BorderLayout.EAST);
			}

			panel.add(pins[i]);
		}

		initDone = true;
		return panel;
	}

	public void receiveLaneEvent(LaneEvent le)
	{
		if (lane.isPartyAssigned())
		{
			int numBowlers = le.getParty().getMembers().size();

			while (!initDone)
			{
				try
				{
					Thread.sleep(1);
				}
				catch (Exception e) {}
			}

			if (le.getFrameNum() == 1 && le.getBall() == 0 && le.getIndex() == 0)
			{
				System.out.println("Making the frame.");
				cpanel.removeAll();
				cpanel.add(makeFrame(le.getParty()), "Center");

				// Button Panel
				JPanel buttonPanel = new JPanel();
				buttonPanel.setLayout(new FlowLayout());

				maintenance = new JButton("Maintenance Call");
				JPanel maintenancePanel = new JPanel();
				maintenancePanel.setLayout(new FlowLayout());
				maintenance.addActionListener(this);
				maintenancePanel.add(maintenance);
				maintenancePanel.add(throwButton);
				throwButton.addActionListener(this);
				buttonPanel.add(maintenancePanel);

				cpanel.add(buttonPanel, "South");
				frame.pack();
			}

			int[][] lescores = le.getCumulScore();

			for (int k = 0; k < numBowlers; k++)
			{
				for (int i = 0; i <= le.getFrameNum() - 1; i++)
				{
					if (lescores[k][i] != 0)
					{
						scoreLabel[k][i].setText(String.valueOf(lescores[k][i]));
					}
				}

				for (int i = 0; i < 21; i++)
				{
					int tempScore = le.getScore().get(bowlers.get(k))[i];

					if (tempScore != -1)
					{
						if (tempScore == 10 && (i % 2 == 0 || i == 19))
						{
							ballLabel[k][i].setText("X");
						}
						else if (i>0 && (tempScore + le.getScore().get(bowlers.get(k))[i-1]) == 10 && i % 2 == 1)
						{
							ballLabel[k][i].setText("/");
						}
						else if (tempScore == -2)
						{
							ballLabel[k][i].setText("F");
						}
						else
						{
							ballLabel[k][i].setText(String.valueOf(tempScore));
						}
					}
				}
			}

		}
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(maintenance))
		{
			lane.pauseGame();
		}

		if (e.getSource().equals(throwButton))
		{
			SimulateThrow simulateThrow = SimulateThrowFactory.getSimulationFrame(this.lane.getCurrentLaneNumber());
			if (simulateThrow.isVisible()) {
				simulateThrow.setVisible(false);
				this.throwButton.setBackground(Color.RED);
				this.throwButton.setText("Enable Bowling");

			} else {
				simulateThrow.setVisible(true);
				this.throwButton.setBackground(Color.GREEN);
				this.throwButton.setText("Disable Bowling");
			}
		}

	}


	public JButton getThrowButton() {
		return throwButton;
	}
}
