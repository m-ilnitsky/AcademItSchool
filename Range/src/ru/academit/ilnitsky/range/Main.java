package ru.academit.ilnitsky.range;

/**
 * Created by Mike on 09.10.2016.
 * Программа для тестирования класса Range
 */
public class Main {
    public static void main(String[] args) {
        Range range0 = new Range();
        Range range1 = new Range(1, 11);
        Range range2 = new Range(1.5, -11);

        System.out.println("range0 = " + range0);
        System.out.println("range1 = " + range1);
        System.out.println("range2 = " + range2);

        System.out.println();
        System.out.println("range0.calcLength = " + range0.calcLength());
        System.out.println("range1.calcLength = " + range1.calcLength());
        System.out.println("range2.calcLength = " + range2.calcLength());

        System.out.println();
        System.out.println("range0.isPoint = " + range0.isPoint());
        System.out.println("range1.isPoint = " + range1.isPoint());
        System.out.println("range2.isPoint = " + range2.isPoint());

        double point = 0;
        System.out.println();
        System.out.println("For point=0 :");
        System.out.println("range0.isInside = " + range0.isInside(point));
        System.out.println("range1.isInside = " + range1.isInside(point));
        System.out.println("range2.isInside = " + range2.isInside(point));

        Range range3 = new Range(1, 25);
        Range range4 = new Range(11, 25);
        Range range5 = new Range(10, 20);
        Range range6 = new Range(10, 37);
        Range range7 = new Range(32, 50);

        System.out.println();
        System.out.println("range3 = " + range3);
        System.out.println("range4 = " + range4);
        System.out.println("range5 = " + range5);
        System.out.println("range6 = " + range6);
        System.out.println("range7 = " + range7);

        System.out.println();
        System.out.println("range3.isInside(range4) = " + range3.isInside(range4));
        System.out.println("range3.isInside(range5) = " + range3.isInside(range5));
        System.out.println("range5.isInside(range3) = " + range5.isInside(range3));

        System.out.println();

        if (range3.isIntersection(range4)) {
            System.out.println("range3.calcIntersection(range4) = " + range3.calcIntersection(range4));
        } else {
            System.out.println("range3.isIntersection(range4) = false");
        }

        if (range3.isIntersection(range5)) {
            System.out.println("range3.calcIntersection(range5) = " + range3.calcIntersection(range5));
        } else {
            System.out.println("range3.isIntersection(range5) = false");
        }

        if (range3.isIntersection(range6)) {
            System.out.println("range3.calcIntersection(range6) = " + range3.calcIntersection(range6));
        } else {
            System.out.println("range3.isIntersection(range6) = false");
        }

        if (range6.isIntersection(range3)) {
            System.out.println("range6.calcIntersection(range3) = " + range6.calcIntersection(range3));
        } else {
            System.out.println("range6.isIntersection(range3) = false");
        }

        if (range6.isIntersection(range7)) {
            System.out.println("range6.calcIntersection(range7) = " + range6.calcIntersection(range7));
        } else {
            System.out.println("range6.isIntersection(range7) = false");
        }

        if (range7.isIntersection(range6)) {
            System.out.println("range7.calcIntersection(range6) = " + range7.calcIntersection(range6));
        } else {
            System.out.println("range7.isIntersection(range6) = false");
        }

        if (range7.isIntersection(range5)) {
            System.out.println("range7.calcIntersection(range5) = " + range7.calcIntersection(range5));
        } else {
            System.out.println("range7.isIntersection(range5) = false");
        }

        System.out.println();

        Range[] range6d7 = range6.calcDifference(range7);
        if(range6d7!=null) {
            System.out.print("range6.calcDifference(range7) = ");
            for (int i = 0; i < range6d7.length; i++) {
                System.out.print(range6d7[i] + " ");
            }
            System.out.println();
        }

        Range[] range3d7 = range3.calcDifference(range7);
        if(range3d7!=null) {
            System.out.print("range3.calcDifference(range7) = ");
            for (int i = 0; i < range3d7.length; i++) {
                System.out.print(range3d7[i] + " ");
            }
            System.out.println();
        }

        System.out.println();

        Range[] range6u7 = range6.calcUnion(range7);
        if(range6u7!=null) {
            System.out.print("range6.calcUnion(range7) = ");
            for (int i = 0; i < range6u7.length; i++) {
                System.out.print(range6u7[i] + " ");
            }
            System.out.println();
        }

        Range[] range3u7 = range3.calcUnion(range7);
        if(range3u7!=null) {
            System.out.print("range3.calcUnion(range7) = ");
            for (int i = 0; i < range3u7.length; i++) {
                System.out.print(range3u7[i] + " ");
            }
            System.out.println();
        }
    }
}
