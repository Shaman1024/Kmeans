package org.example.util;

import org.example.model.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomDataGenerator {
    public static List<Point> generateRandomPoints(int pointsNumber, double mimCoordinate, double maxCoordinate) {
        List<Point> points = new ArrayList<>();
        Random random= new Random();

        for (int i = 0; i < pointsNumber; i++) {
            double x = mimCoordinate + (maxCoordinate - mimCoordinate) * random.nextDouble();
            double y = mimCoordinate + (maxCoordinate - mimCoordinate) * random.nextDouble();
            points.add(new Point(x, y, -1));
        }

        return points;
    }

}
