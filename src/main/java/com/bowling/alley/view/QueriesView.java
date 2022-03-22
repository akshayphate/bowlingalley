package com.bowling.alley.view;

import com.bowling.alley.db.DBUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;


/**
 *
 * @author yaswanth
 */

public class QueriesView implements ActionListener {
    private JFrame win;
    private JTextField nickField;
    private JButton minScoreButton, maxScoreButton, topPlayerButton;
    private TextArea outputArea;

    private DBUtil dbUtil;

    private int minScore, maxScore;
    Vector<String> topPlayers;

    public QueriesView() throws Exception {
        dbUtil = new DBUtil();

        initWindow();

        JPanel controlPanel = createControlPanel();
        JPanel outputPanel = createOutputPanel();

        createMainPanel(controlPanel, outputPanel);
    }

    private void initWindow()
    {
        win = new JFrame("Queries");
        win.getContentPane().setLayout(new BorderLayout());
        ((JPanel) win.getContentPane()).setOpaque(false);
    }

    private JPanel createControlPanel()
    {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));

        topPlayerButton = createButton("Find Top Player", buttonPanel);
        nickField = createField("Enter Nick Name", buttonPanel);
        maxScoreButton = createButton("Check max Score", buttonPanel);
        minScoreButton = createButton("Check min Score", buttonPanel);

        return buttonPanel;
    }

    private JPanel createOutputPanel() {
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new FlowLayout());
        outputPanel.setBorder(new TitledBorder("Output"));
        outputArea = new TextArea("", 10, 30);
        outputPanel.add(outputArea);
        return outputPanel;
    }

    @SuppressWarnings("deprecation")
    private void createMainPanel(JPanel controlPanel, JPanel outputPanel)
    {
        // Clean up main panel
        JPanel colPanel = new JPanel();
        colPanel.setLayout(new GridLayout(1, 2));
        colPanel.add(controlPanel);
        colPanel.add(outputPanel);

        win.getContentPane().add("Center", colPanel);

        win.pack();

        // Center Window on Screen
        Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
        win.setLocation(
                ((screenSize.width) / 2) - ((win.getSize().width) / 2),
                ((screenSize.height) / 2) - ((win.getSize().height) / 2));
        win.show();
    }

    private JTextField createField(String title, JPanel parent)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        JLabel label = new JLabel(title);
        JTextField field = new JTextField(10);
        panel.add(label);
        panel.add(field);
        parent.add(panel);
        return field;
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

    @SuppressWarnings("deprecation")
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(topPlayerButton))
        {
            topPlayers = dbUtil.getTopBowlers();
            if(topPlayers.size() == 0){
                outputArea.setText("No top players as of now !!");
            }
            else{
                StringBuilder players = new StringBuilder();
                for (String player: topPlayers){
                    players.append(player).append("\n");
                }
                outputArea.setText(players.toString());
            }
        }

        if (e.getSource().equals(maxScoreButton))
        {
            maxScore = dbUtil.getHighestScoreOfBowler(nickField.getText());
            outputArea.setText(Integer.toString(maxScore));
        }

        if (e.getSource().equals(minScoreButton))
        {
            minScore = dbUtil.getLowestScoreOfBowler(nickField.getText());
            outputArea.setText(Integer.toString(minScore));
        }
    }
}
