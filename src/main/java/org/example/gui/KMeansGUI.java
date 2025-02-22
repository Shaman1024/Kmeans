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
    private JButton maximinButton;
    private JTextArea outputTextArea;
    private PointsPanel pointsPanel;

    private List<Point> generatedPoints; // Переменная для хранения сгенерированных точек
    private List<Cluster> clusters;

    public KMeansGUI() {
        setTitle("K-Means Clustering");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel kLabel = new JLabel("Количество кластеров (K):");
        kTextField = new JTextField(5);
        recalculateButton = new JButton("К- Средних");
        maximinButton = new JButton("Максимин");

        inputPanel.add(kLabel);
        inputPanel.add(kTextField);
        inputPanel.add(recalculateButton);
        inputPanel.add(maximinButton);

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

        maximinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runMaxiMin();
            }
        });
    }

    private void runMaxiMin() {
        try {
            int k = Integer.parseInt(kTextField.getText());
            if (k <= 0) {
                JOptionPane.showMessageDialog(this, "Количество кластеров должно быть положительным числом.", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int numberOfPoints = 10000;
            generatedPoints = RandomDataGenerator.generateRandomPoints(numberOfPoints, 0, 100);

            KMeansAlgorithm kmeans = new KMeansAlgorithm();
            clusters = kmeans.runMaxiMin(generatedPoints, k ,generatedPoints.getFirst());

            displayResults(generatedPoints, clusters);

            pointsPanel.setPointsAndClusters(generatedPoints, clusters, k);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Введите корректное число кластеров.", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void runKMeansClustering() {
        try {
            int k = Integer.parseInt(kTextField.getText());
            if (k <= 0) {
                JOptionPane.showMessageDialog(this, "Количество кластеров должно быть положительным числом.", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (generatedPoints == null) {
                JOptionPane.showMessageDialog(this, "Сначала запустите алгоритм Maximin или сгенерируйте точки.", "Нет данных", JOptionPane.WARNING_MESSAGE);
                return;
            }

            KMeansAlgorithm kmeans = new KMeansAlgorithm();
            clusters = kmeans.runKMeans(generatedPoints, k, clusters);

            displayResults(generatedPoints, clusters);

            pointsPanel.setPointsAndClusters(generatedPoints, clusters, k);

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