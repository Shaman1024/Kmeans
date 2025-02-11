package org.example.gui;

import org.example.algorithm.KMeansAlgorithm;
import org.example.model.Point;
import org.example.model.Cluster;
import org.example.util.RandomDataGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class KMeansGUI extends JFrame {

    private JTextField kTextField;
    private JButton recalculateButton;
    private JTextArea outputTextArea;
    private PointsPanel pointsPanel;

    public KMeansGUI() {
        setTitle("K-Means Clustering");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel kLabel = new JLabel("Количество кластеров (K):");
        kTextField = new JTextField(5);
        recalculateButton = new JButton("Пересчитать");

        inputPanel.add(kLabel);
        inputPanel.add(kTextField);
        inputPanel.add(recalculateButton);

        outputTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        outputTextArea.setEditable(false);

        pointsPanel = new PointsPanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, pointsPanel);
        splitPane.setResizeWeight(0.5);

        add(inputPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);

        recalculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runKMeansClustering();
            }
        });
    }

    private void runKMeansClustering() {
        try {
            int k = Integer.parseInt(kTextField.getText());
            if (k <= 0) {
                JOptionPane.showMessageDialog(this, "Количество кластеров должно быть положительным числом.", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int numberOfPoints = 1000;
            List<Point> points = RandomDataGenerator.generateRandomPoints(numberOfPoints, 0, 100);

            KMeansAlgorithm kmeans = new KMeansAlgorithm();
            List<Cluster> clusters = kmeans.runKMeans(points, k);

            displayResults(points, clusters);

            pointsPanel.setPointsAndClusters(points, k);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Введите корректное число кластеров.", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayResults(List<Point> points, List<Cluster> clusters) {
        outputTextArea.setText("");
        StringBuilder sb = new StringBuilder();
        sb.append("Результаты K-Means Clustering:\n");
        for (Point point : points) {
            sb.append("Точка ").append(point).append(" - Кластер ").append(point.getClusterNumber()).append("\n");
        }
        outputTextArea.append(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            KMeansGUI gui = new KMeansGUI();
            gui.setVisible(true);
        });
    }
}