package ru.academit.ilnitsky.shapes_utils;

import ru.academit.ilnitsky.shapes.Shape;

import java.util.Comparator;

/**
 * Created by UserLabView on 11.10.16.
 * Компаратор для сранения фигур по периметру
 */
public class SortedByPerimeter implements Comparator<Shape> {
    public int compare(Shape shape1, Shape shape2) {

        double perimeter1 = shape1.getPerimeter();
        double perimeter2 = shape2.getPerimeter();

        if (perimeter1 > perimeter2) {
            return 1;
        } else if (perimeter1 < perimeter2) {
            return -1;
        } else {
            return 0;
        }
    }
}
