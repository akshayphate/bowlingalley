/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.bowling.alley.util;

import javax.swing.*;
import java.util.Random;

/**
 *
 * @author akshay
 */
public class SimulateThrow extends javax.swing.JFrame {
    private double skill;
    private Random rd = new Random();
    private boolean hasBowled = false;
    private int laneNumber;
    private JLabel message;
    /**
     * Creates new form SimulateThrow
     */
    public SimulateThrow(int laneNumber) {
        initComponents();
        this.laneNumber = laneNumber;
        this.jLabel1.setText("Lane : "+laneNumber);
        this.message.setText("Please throw");
        
    }

    public SimulateThrow() {
        initComponents();
    }

    public void initComp() {
        jSeparator1 = new JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jSlider1 = new javax.swing.JSlider();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jLabel1.setText("");
        jButton1.setBackground(new java.awt.Color(204, 255, 204));
        jButton1.setText("Play");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        this.add(jButton1);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        message = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jSlider1 = new javax.swing.JSlider();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 104, 251));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("");
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setText("");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 80, 50));
        getContentPane().add(message, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 150, 100));

        jButton1.setBackground(new java.awt.Color(204, 255, 204));
        jButton1.setText("Play");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 100, 40));
        //getContentPane().add(jSlider1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 260, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
           this.skill = rd.nextDouble();
           this.hasBowled = true;
           if (skill < 0.2) {
               this.message.setText(" (╥︣﹏᷅╥) : Better luck next time" );
           }

           if (skill >= 0.2 && skill < 0.7) {
               this.message.setText("(͡• ͜ʖ ͡•) : Good Throw");
           }

           else {
                this.message.setText(" (•◡•) : Awesome");
           }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SimulateThrow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SimulateThrow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SimulateThrow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SimulateThrow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SimulateThrow().setVisible(true);
            }
        });
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

    public void resetWindow() {
        hasBowled = false;
    }

    public void closeWindow() {
        this.hide();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSlider jSlider1;
    // End of variables declaration//GEN-END:variables
}
