package com.bowling.alley.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class SimulateThrowTemp extends JFrame {
    private double skill;
    private Random rd = new Random();
    private boolean hasBowled = false;
    private int laneNumber;

    private JButton playButton;
    private JLabel playButtonLabel;
    private JPanel topPanel;
    private JPanel emojiPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel playPanel;
    private JLabel jLabel1;


    public SimulateThrowTemp() {

        init();
    }

    private void init() {
        playButtonLabel = new JLabel();
        topPanel = new JPanel();
        emojiPanel = new JPanel();
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        playPanel = new JPanel();

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(500, 500);
        this.setLayout(new BorderLayout(10,0));

        topPanel.setPreferredSize(new Dimension(100,100));
        emojiPanel.setPreferredSize(new Dimension(100,100));
        leftPanel.setPreferredSize(new Dimension(100,100));
        rightPanel.setPreferredSize(new Dimension(100,100));
        playPanel.setPreferredSize(new Dimension(100,100));

        this.add(topPanel, BorderLayout.NORTH);
        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.EAST);
        this.add(emojiPanel, BorderLayout.CENTER);
        this.add(playPanel, BorderLayout.SOUTH);

        playButton = new JButton("Play");
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    actionPerf(evt);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        playPanel.add(playButton);
        this.setVisible(true);


    }

    private void actionPerf(java.awt.event.ActionEvent evt) throws IOException {//GEN-FIRST:event_jButton1ActionPerformed
        this.skill = rd.nextDouble();
        this.hasBowled = true;

        //BufferedImage wPic = ImageIO.read(this.getClass().getResource("happy.png"));
        JLabel picLabel = new JLabel(new ImageIcon("/home/akshay/IdeaProjects/bowlingalley/src/main/resources/happy.png"));
        picLabel.setVisible(true);
        picLabel.setText("Good Throw");
        this.emojiPanel.add(picLabel);
    }

    public double getSkill() {
        while (!hasBowled) {
            System.out.print("");
        }

        System.out.println(this.skill);
        this.hasBowled = false;
        return this.skill;
    }

    public double getSkill(int currentLaneNumber) {
        if (!this.isVisible())
            this.setVisible(true);
        while (!hasBowled) {
            System.out.print("");
        }


        this.jLabel1.setText("Lane : "+currentLaneNumber);

        System.out.println(this.skill);
        return this.skill;
    }
}

//
///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
// */
//package com.bowling.alley.util;
//
//        import javax.swing.*;
//        import java.util.Random;
//
///**
// *
// * @author akshay
// */
//public class SimulateThrow extends javax.swing.JFrame {
//    private double skill;
//    private Random rd = new Random();
//    private boolean hasBowled = false;
//    private int laneNumber;
//    /**
//     * Creates new form SimulateThrow
//     */
//    public SimulateThrow(int laneNumber) {
//        initComponents();
//        this.laneNumber = laneNumber;
//        this.jLabel1.setText("Lane : "+laneNumber);
//
//    }
//
//    public SimulateThrow() {
//        initComponents();
//    }
//
//    public void initComp() {
//        jSeparator1 = new JSeparator();
//        jLabel1 = new javax.swing.JLabel();
//        jButton1 = new javax.swing.JButton();
//        jSlider1 = new javax.swing.JSlider();
//        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
//        //jLabel1.setText("jLabel1");
//        jButton1.setBackground(new java.awt.Color(204, 255, 204));
//        jButton1.setText("Play");
//        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
//        jButton1.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jButton1ActionPerformed(evt);
//            }
//        });
//        this.add(jButton1);
//
//    }
//
//    /**
//     * This method is called from within the constructor to initialize the form.
//     * WARNING: Do NOT modify this code. The content of this method is always
//     * regenerated by the Form Editor.
//     */
//    @SuppressWarnings("unchecked")
//    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
//    private void initComponents() {
//
//        jSeparator1 = new javax.swing.JSeparator();
//        jLabel1 = new javax.swing.JLabel();
//        jButton1 = new javax.swing.JButton();
//        jSlider1 = new javax.swing.JSlider();
//
//        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
//        setAlwaysOnTop(true);
//        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
//        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 104, 251));
//
//        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
//        jLabel1.setText("jLabel1");
//        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 80, 50));
//
//        jButton1.setBackground(new java.awt.Color(204, 255, 204));
//        jButton1.setText("Play");
//        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
//        jButton1.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jButton1ActionPerformed(evt);
//            }
//        });
//        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 100, 40));
//        //getContentPane().add(jSlider1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 260, 30));
//
//        pack();
//    }// </editor-fold>//GEN-END:initComponents
//
//    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//        this.skill = rd.nextDouble();
//        this.hasBowled = true;
//    }//GEN-LAST:event_jButton1ActionPerformed
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(SimulateThrow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(SimulateThrow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(SimulateThrow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(SimulateThrow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new SimulateThrow().setVisible(true);
//            }
//        });
//    }
//
//    public double getSkill() {
//        while (!hasBowled) {
//            System.out.print("");
//        }
//
//        System.out.println(this.skill);
//        this.hasBowled = false;
//        return this.skill;
//    }
//
//    public double getSkill(int currentLaneNumber) {
//        if (!this.isVisible())
//            this.setVisible(true);
//        while (!hasBowled) {
//            System.out.print("");
//        }
//
//
//        this.jLabel1.setText("Lane : "+currentLaneNumber);
//
//        System.out.println(this.skill);
//        return this.skill;
//    }
//
//    public void resetWindow() {
//        hasBowled = false;
//    }
//
//    public void closeWindow() {
//        this.hide();
//    }
//
//    // Variables declaration - do not modify//GEN-BEGIN:variables
//    private javax.swing.JButton jButton1;
//    private javax.swing.JLabel jLabel1;
//    private javax.swing.JSeparator jSeparator1;
//    private javax.swing.JSlider jSlider1;
//    // End of variables declaration//GEN-END:variables
//}
