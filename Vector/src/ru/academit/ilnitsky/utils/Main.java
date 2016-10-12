package ru.academit.ilnitsky.utils;

import ru.academit.ilnitsky.vector.*;

/**
 * Created by Mike on 02.10.2016.
 * Программма для тестирования класса "Вектор" (Vector)
 */
public class Main {
    public static void main(String[] args) {
        //Vector testV = new Vector(-3);

        Vector[] vector = new Vector[8];

        vector[0] = new Vector(4);
        vector[0].setX(2, 2);
        vector[0].setX(3, Math.PI);

        double[] array = {1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7};

        vector[1] = new Vector(array);
        vector[2] = new Vector(3, array);
        vector[3] = new Vector(4, array);

        vector[4] = new Vector(vector[0]);
        vector[4].setX(0, 1);

        vector[5] = new Vector(256);

        vector[6] = new Vector(vector[0]);
        vector[7] = new Vector(vector[1]);

        for (Vector v : vector) {
            System.out.println(v);
        }

        System.out.println();
        for (int i = 0; i < vector.length; i++) {
            System.out.printf("[%d] Size = %3d, Length = %9.4f, hashCode = %11d%n", i, vector[i].getSize(), vector[i].getLength(), vector[i].hashCode());
        }

        System.out.println();
        for (int i = 0; i < vector.length - 1; i++) {
            for (int j = i + 1; j < vector.length; j++) {
                if (vector[i].equals(vector[j])) {
                    System.out.printf("[%d] equals [%d]%n", i, j);
                }
            }
        }
    }
}
