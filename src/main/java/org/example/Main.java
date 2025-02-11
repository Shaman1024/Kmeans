package org.example;

import org.example.gui.KMeansGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            KMeansGUI gui = new KMeansGUI();
            gui.setVisible(true);
        });
    }
}