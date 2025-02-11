package org.example.model;


import java.util.ArrayList;
import java.util.List;

public class Cluster {
    private Point centroid;
    private List<Point> points;

    public Cluster(Point centroid){
        this.centroid = centroid;
        this.points = new ArrayList<>();
    }

    public Point getCentroid() {
        return centroid;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setCentroid(Point centroid) {
        this.centroid = centroid;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public void addPoint(Point point) {
        this.points.add(point);
    }

    public void clearPoints() {
        this.points.clear();
    }
}
