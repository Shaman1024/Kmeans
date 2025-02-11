package org.example.model;

public class Point {
    private double x;
    private double y;
    private int clusterNumber;

    public Point(double x, double y, int clusterNumber) {
        this.x = x;
        this.y = y;
        this.clusterNumber = clusterNumber;
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

}
