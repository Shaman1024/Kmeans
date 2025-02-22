package org.example.algorithm;

import org.example.model.Cluster;
import org.example.model.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeansAlgorithm {

    public List<Cluster> runKMeans(List<Point> points, int k, List<Cluster> clusters) {
        if (points == null || points.isEmpty() || k <= 0 || k > points.size()) {
            return null;
        }

        boolean changed = true;

        while (changed) {
            changed = false;

            for (Cluster cluster : clusters) {
                cluster.clearPoints();
            }

            for (Point point : points) {
                Cluster closestCluster = findClosestCluster(point, clusters);
                if (closestCluster != null) {
                    point.setClusterNumber(clusters.indexOf(closestCluster));
                    closestCluster.addPoint(point);
                }
            }

            if (updateCentroids(clusters)) {
                changed = true;
            }
        }

        return clusters;
    }

    public List<Cluster> runMaxiMin(List<Point> points, int maxCentroids, Point firstCentroid) {
        if (points == null || points.isEmpty() || maxCentroids <= 0) {
            return new ArrayList<>();
        }

        List<Cluster> clusters = new ArrayList<>();

        // 1. Выбор первого центроида
        if (firstCentroid != null) {
            clusters.add(new Cluster(firstCentroid));
        } else if (!points.isEmpty()) {
            clusters.add(new Cluster(points.get(0)));
        } else {
            return clusters;
        }

        if (maxCentroids == 1) {
            assignPointsToClusters(points, clusters);
            return clusters;
        }

        while (clusters.size() < maxCentroids) {
            Point farthestPoint = null;
            double maxMinDistance = -1;

            for (Point point : points) {
                double minDistanceToClusters = Double.MAX_VALUE;
                for (Cluster cluster : clusters) {
                    double distance = calculateDistance(point, cluster.getCentroid());
                    minDistanceToClusters = Math.min(minDistanceToClusters, distance);
                }

                if (minDistanceToClusters > maxMinDistance) {
                    maxMinDistance = minDistanceToClusters;
                    farthestPoint = point;
                }
            }

            // 5. Добавляем новую точку в качестве центроида нового кластера
            if (farthestPoint != null) {
                clusters.add(new Cluster(farthestPoint));
            } else {
                break;
            }
        }

        assignPointsToClusters(points, clusters);
        return clusters;
    }


    private void assignPointsToClusters(List<Point> points, List<Cluster> clusters) {
        for (Cluster cluster : clusters) {
            cluster.clearPoints();
        }
        for (Point point : points) {
            Cluster closestCluster = findClosestCluster(point, clusters);
            if (closestCluster != null) {
                point.setClusterNumber(clusters.indexOf(closestCluster));
                closestCluster.addPoint(point);
            }
        }
    }


    public List<Cluster> initializeCluster(List<Point> points, int k) {
        List<Cluster> clusters = new ArrayList<>();
        if (points == null || points.isEmpty() || k <= 0 || k > points.size()) {
            return clusters;
        }
        Random random = new Random();
        List<Integer> initialCentroidIndices = new ArrayList<>();

        while (initialCentroidIndices.size() < k) {
            int randomIndex = random.nextInt(points.size());
            if (!initialCentroidIndices.contains(randomIndex)) {
                initialCentroidIndices.add(randomIndex);
            }
        }

        for (int index : initialCentroidIndices) {
            clusters.add(new Cluster(points.get(index)));
        }

        return clusters;
    }

    private Cluster findClosestCluster(Point point, List<Cluster> clusters) {
        if (clusters == null || clusters.isEmpty()) {
            return null;
        }
        Cluster closestCluster = null;
        double minDistance = Double.MAX_VALUE;

        for (Cluster cluster : clusters) {
            double distance = calculateDistance(point, cluster.getCentroid());
            if (distance < minDistance) {
                minDistance = distance;
                closestCluster = cluster;
            }
        }
        return closestCluster;
    }

    private double calculateDistance(Point p1, Point p2) {
        if (p1 == null || p2 == null) {
            return Double.MAX_VALUE;
        }
        double dx = p1.getX() - p2.getX();
        double dy = p1.getY() - p2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    private boolean updateCentroids(List<Cluster> clusters) {
        boolean changed = false;
        double tolerance = 1e-6;

        for (Cluster cluster : clusters) {
            if (!cluster.getPoints().isEmpty()) {
                Point oldCentroid = cluster.getCentroid();
                Point newCentroid = calculateNewCentroid(cluster.getPoints());

                double distanceChange = calculateDistance(oldCentroid, newCentroid);

                cluster.setCentroid(newCentroid);

                if (distanceChange > tolerance) {
                    changed = true;
                }
            }
        }
        return changed;
    }

    private Point calculateNewCentroid(List<Point> points) {
        if (points == null || points.isEmpty()) {
            return null;
        }
        double sumX = 0;
        double sumY = 0;
        for (Point point : points) {
            sumX += point.getX();
            sumY += point.getY();
        }
        return new Point(sumX / points.size(), sumY / points.size(), -1);
    }


}