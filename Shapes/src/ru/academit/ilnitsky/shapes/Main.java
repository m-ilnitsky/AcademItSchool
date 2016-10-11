package ru.academit.ilnitsky.shapes;

import java.util.Arrays;
import java.util.Collections;

public class Main {

    private static void viewSortedByArea(Shape[] shapes, int numberTop, int numberBottom) {
        Shape[] shapes2 = new Shape[shapes.length];
        System.arraycopy(shapes, 0, shapes2, 0, shapes.length);

        Arrays.sort(shapes2, Collections.reverseOrder(new SortedByArea()));

        for (int i = numberTop - 1; i < numberBottom; i++) {
            System.out.printf("№%-2d  S = %-12f  %-80s%n", i + 1, shapes2[i].getArea(), shapes2[i]);
        }
    }

    private static void viewSortedByPerimeter(Shape[] shapes, int numberTop, int numberBottom) {
        Shape[] shapes2 = new Shape[shapes.length];
        System.arraycopy(shapes, 0, shapes2, 0, shapes.length);

        Arrays.sort(shapes2, Collections.reverseOrder(new SortedByPerimeter()));

        for (int i = numberTop - 1; i < numberBottom; i++) {
            System.out.printf("№%-2d  P = %-12f  %-80s%n", i + 1, shapes2[i].getPerimeter(), shapes2[i]);
        }
    }

    private static Shape findMaxArea(Shape[] shapes, int numberBySquare) {

        double[] maxAreas = new double[numberBySquare];
        int[] maxIndex = new int[numberBySquare];
        for (int i = 0; i < numberBySquare; i++) {
            maxAreas[i] = 0;
            maxIndex[i] = -1;
        }

        double[] areas = new double[shapes.length];

        for (int i = 0; i < shapes.length; i++) {
            areas[i] = shapes[i].getArea();
            if (areas[i] > maxAreas[0]) {
                maxAreas[0] = areas[i];
                maxIndex[0] = i;
            }
        }

        if (numberBySquare > 1) {
            for (int m = 1; m < numberBySquare; m++) {
                for (int i = 0; i < shapes.length; i++) {
                    if (areas[i] > maxAreas[m] && areas[i] < maxAreas[m - 1]) {
                        maxAreas[m] = areas[i];
                        maxIndex[m] = i;
                    }
                }
            }
            return shapes[maxIndex[numberBySquare - 1]];
        } else {
            return shapes[maxIndex[0]];
        }
    }

    private static Shape findMaxPerimeter(Shape[] shapes, int numberByPerimeter) {

        double[] maxPerimeter = new double[numberByPerimeter];
        int[] maxIndex = new int[numberByPerimeter];
        for (int i = 0; i < numberByPerimeter; i++) {
            maxPerimeter[i] = 0;
            maxIndex[i] = -1;
        }

        double[] perimeters = new double[shapes.length];

        for (int i = 0; i < shapes.length; i++) {
            perimeters[i] = shapes[i].getPerimeter();
            if (perimeters[i] > maxPerimeter[0]) {
                maxPerimeter[0] = perimeters[i];
                maxIndex[0] = i;
            }
        }

        if (numberByPerimeter > 1) {
            for (int m = 1; m < numberByPerimeter; m++) {
                for (int i = 0; i < shapes.length; i++) {
                    if (perimeters[i] > maxPerimeter[m] && perimeters[i] < maxPerimeter[m - 1]) {
                        maxPerimeter[m] = perimeters[i];
                        maxIndex[m] = i;
                    }
                }
            }
            return shapes[maxIndex[numberByPerimeter - 1]];
        } else {
            return shapes[maxIndex[0]];
        }
    }

    public static void main(String[] args) {
        Shape[] shape = new Shape[12];

        shape[0] = new Rectangle(12, 25);
        shape[1] = new Rectangle(25, 12);
        shape[2] = new Rectangle(12, 25);
        shape[3] = new Square(25);

        shape[4] = new Square(12);
        shape[5] = new Square(2);
        shape[6] = new Circle(15);
        shape[7] = new Circle(2);

        shape[8] = new Triangle(12, 0, 0, 12, 0, 0);
        shape[9] = new Triangle(7, 0, 0, 3, 0, 0);
        shape[10] = new Triangle(12, 5, 3, 12, -5, -5);
        shape[11] = new Triangle(12, 11, 0, 11, 0, 0);

        for (Shape sh : shape) {
            System.out.printf("%-70s  S = %-12f  P = %-12f  HashCode = %11d%n", sh, sh.getArea(), sh.getPerimeter(), sh.hashCode());
        }

        Shape[] maxArea = new Shape[5];

        System.out.println();
        System.out.println("findMaxArea:");
        for (int i = 0; i < maxArea.length; i++) {
            maxArea[i] = findMaxArea(shape, i + 1);
            System.out.printf("№%-2d  S = %-12f  %-80s%n", i + 1, maxArea[i].getArea(), maxArea[i]);
        }

        System.out.println();
        System.out.println("viewSortedByArea:");
        viewSortedByArea(shape, 1, 5);


        Shape[] maxPerimeter = new Shape[5];

        System.out.println();
        System.out.println("findMaxPerimeter:");
        for (int i = 0; i < maxPerimeter.length; i++) {
            maxPerimeter[i] = findMaxPerimeter(shape, i + 1);
            System.out.printf("№%-2d  P = %-12f  %-80s%n", i + 1, maxPerimeter[i].getPerimeter(), maxPerimeter[i]);
        }

        System.out.println();
        System.out.println("viewSortedByPerimeter:");
        viewSortedByPerimeter(shape, 1, 5);

        System.out.println();
        System.out.println(HashCode.hashCode(100));
        System.out.println(HashCode.hashCode(1.0001));
        System.out.println(HashCode.hashCode(1.0000002));
        System.out.println(HashCode.hashCode(1.345e77));
        System.out.println(HashCode.hashCode(-1.345e77));
        System.out.println(HashCode.hashCode(1.345e-77));

        System.out.println();
        System.out.println(new Point(1, 12).hashCode());
        System.out.println(new Point(2, 7).hashCode());
        System.out.println(new Point(7, 2).hashCode());
        System.out.println(new Point(0, 127).hashCode());
    }
}
