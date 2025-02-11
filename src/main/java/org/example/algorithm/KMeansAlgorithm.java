package org.example.algorithm;

import org.example.model.Cluster;
import org.example.model.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.sqrt;

public class KMeansAlgorithm {

    public List<Cluster> runKMeans(List<Point> points, int k) {
        if (points == null || points.isEmpty() || k <= 0 || k > points.size()) {
            return null;
        }

        List<Cluster> clusters = initializeCluster(points, k);

        for (Cluster cluster : clusters) {
            cluster.clearPoints();
        }

        for (Point point : points) {
            Cluster closestCluster = findClosestCluster(point, clusters);
            point.setClusterNumber(clusters.indexOf(closestCluster));
            closestCluster.addPoint(point);
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
            if (calculateDistance(point, cluster.getCentroid()) < minDistance) {
                closestCluster = cluster;
            }
        }
        return closestCluster;
    }

    private double calculateDistance(Point p1, Point p2) {
        return sqrt(p2.getX() * p1.getX() + p2.getY() * p2.getY());
    }


}
