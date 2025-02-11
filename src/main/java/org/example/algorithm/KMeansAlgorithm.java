package org.example.algorithm;

import org.example.model.Cluster;
import org.example.model.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class KMeansAlgorithm {

    public List<Cluster> runKMeans(List<Point> points, int k) {
        if (points == null || points.isEmpty() || k <= 0 || k > points.size()) {
            return null;
        }

        boolean changed = true;
        List<Cluster> clusters = initializeCluster(points, k);

        while (changed) {

            for (Cluster cluster : clusters) {
                cluster.clearPoints();
            }

            for (Point point : points) {
                Cluster closestCluster = findClosestCluster(point, clusters);
                point.setClusterNumber(clusters.indexOf(closestCluster));
                closestCluster.addPoint(point);
            }

            changed = updateCentroids(clusters);

        }

        return clusters;
    }

    public List<Cluster> initializeCluster(List<Point> points, int k) {
        List<Cluster> clusters = new ArrayList<>();
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

    private Point calculateNewCentroid(List<Point> points){
        double sumX = 0;
        double sumY = 0;
        for (Point point : points) {
            sumX += point.getX();
            sumY += point.getY();
        }
        return new Point(sumX / points.size(), sumY / points.size() , -1);
    }

}
