package org.hsm.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *The frame for creating a new avaiable plant.
 *
 */
public class PlantAddFrame {

    private static final String FRAME_TITLE = "Add a plant";
    private static final String START_LABEL = "Chose the plant";
    private static final int MIN_X_DIMENSION = 300;
    private final JFrame frame;
    private final String[] string = new String[]{"Pomodoro dei Cannibali dell Fiji", "Melanzana", "Mandarino"};

    /**
     *Create the frame.
     */
    public PlantAddFrame() {
        this.frame = new JFrame(FRAME_TITLE);
        this.frame.setMinimumSize(new Dimension(MIN_X_DIMENSION, 0));
        final JPanel southPanel = new JPanel(new FlowLayout());
        final JPanel northPanel = new JPanel(new FlowLayout());
        final JPanel centerPanel = new JPanel();
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final JLabel label = new JLabel(START_LABEL);
        final JButton add = new JButton("Add");
        southPanel.add(add);
        final JComboBox<String> plantsList = new JComboBox<>(string);
        plantsList.setSelectedIndex(string.length - 1);
        northPanel.add(label);
        centerPanel.add(plantsList);
        this.frame.add(northPanel, BorderLayout.NORTH);
        this.frame.add(centerPanel);
        this.frame.add(southPanel, BorderLayout.SOUTH);
    }

    /**
     *Set the frame visible.
     */
    public void start() {
        this.frame.pack();
        this.frame.setLocationByPlatform(true);
        this.frame.setVisible(true);
    }
}
