package ru.academit.ilnitsky.shapes;

import java.util.Comparator;

/**
 * Created by UserLabView on 11.10.16.
 * Компаратор для сранения фигур по площади
 */
public class SortedByArea implements Comparator<Shape> {
    public int compare(Shape shape1, Shape shape2) {

        double area1 = shape1.getArea();
        double area2 = shape2.getArea();

        if (area1 > area2) {
            return 1;
        } else if (area1 < area2) {
            return -1;
        } else {
            return 0;
        }
    }
}
