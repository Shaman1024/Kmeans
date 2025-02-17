package org.example.gui;

import org.example.model.Cluster;
import org.example.model.Point;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PointsPanel extends JPanel {

    private List<Point> points;
    private List<Cluster> clusters; // Добавляем список кластеров, чтобы получить доступ к центроидам
    private int numberOfClusters;

    // Цвета для кластеров (можно расширить)
    private static final Color[] CLUSTER_COLORS = {
            Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW,
            Color.MAGENTA, Color.CYAN, Color.ORANGE, Color.PINK,
            Color.LIGHT_GRAY, Color.DARK_GRAY, Color.GRAY,
            new Color(128, 0, 0), // Maroon
            new Color(0, 128, 0), // Olive
            new Color(0, 0, 128), // Navy
            new Color(128, 128, 0), // Olive Green
            new Color(128, 0, 128), // Purple
            new Color(0, 128, 128), // Teal
            new Color(192, 192, 192), // Silver
            new Color(255, 215, 0), // Gold
            new Color(0, 255, 255)  // Aqua
    };

    public PointsPanel() {
        this.points = null;
        this.clusters = null; // Инициализируем список кластеров
        this.numberOfClusters = 0;
        setBackground(Color.WHITE);
    }

    public void setPointsAndClusters(List<Point> points, List<Cluster> clusters, int numberOfClusters) {
        this.points = points;
        this.clusters = clusters; // Сохраняем список кластеров
        this.numberOfClusters = numberOfClusters;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (points == null || points.isEmpty() || clusters == null || clusters.isEmpty()) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Находим минимальные и максимальные координаты для масштабирования
        double minX = points.stream().mapToDouble(Point::getX).min().orElse(0);
        double maxX = points.stream().mapToDouble(Point::getX).max().orElse(100);
        double minY = points.stream().mapToDouble(Point::getY).min().orElse(0);
        double maxY = points.stream().mapToDouble(Point::getY).max().orElse(100);

        double rangeX = maxX - minX;
        double rangeY = maxY - minY;

        if (rangeX == 0) rangeX = 100;
        if (rangeY == 0) rangeY = 100;

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int pointSize = 8;
        int centroidSize = 12; // Размер центроидов больше

        // Рисуем точки
        for (Point point : points) {
            int clusterIndex = point.getClusterNumber();
            Color pointColor = CLUSTER_COLORS[clusterIndex % CLUSTER_COLORS.length];
            g2d.setColor(pointColor.darker().darker()); // Делаем точки немного темнее

            // Масштабирование координат точек
            double scaledX = (point.getX() - minX) / rangeX;
            double scaledY = (point.getY() - minY) / rangeY;

            int xPixel = (int) (scaledX * (panelWidth - pointSize) + pointSize / 2);
            int yPixel = (int) (scaledY * (panelHeight - pointSize) + pointSize / 2);
            yPixel = panelHeight - yPixel;

            g2d.fillOval(xPixel - pointSize / 2, yPixel - pointSize / 2, pointSize, pointSize);
        }

        // Рисуем центроиды поверх точек
        for (Cluster cluster : clusters) {
            Point centroid = cluster.getCentroid();
            int clusterIndex = clusters.indexOf(cluster); // Индекс кластера для цвета центроида
            Color centroidColor = CLUSTER_COLORS[clusterIndex % CLUSTER_COLORS.length];
            g2d.setColor(centroidColor);

            // Масштабирование координат центроидов
            double scaledX = (centroid.getX() - minX) / rangeX;
            double scaledY = (centroid.getY() - minY) / rangeY;

            int xPixelCentroid = (int) (scaledX * (panelWidth - centroidSize) + centroidSize / 2);
            int yPixelCentroid = (int) (scaledY * (panelHeight - centroidSize) + centroidSize / 2);
            yPixelCentroid = panelHeight - yPixelCentroid;

            g2d.fillOval(xPixelCentroid - centroidSize / 2, yPixelCentroid - centroidSize / 2, centroidSize, centroidSize); // Рисуем центроиды больше
            g2d.setColor(centroidColor.brighter()); // Добавляем более светлую обводку для выделения
            g2d.drawOval(xPixelCentroid - centroidSize / 2, yPixelCentroid - centroidSize / 2, centroidSize, centroidSize); // Обводка
        }
    }
}