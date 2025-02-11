package org.example.model;

import java.util.Objects;

public class Point {
    private double x;
    private double y;
    private int clusterNumber;

    public Point(double x, double y, int clusterNumber) {
        this.x = x;
        this.y = y;
        this.clusterNumber = -1;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getClusterNumber() {
        return clusterNumber;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setClusterNumber(int clusterNumber) {
        this.clusterNumber = clusterNumber;
    }
    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
