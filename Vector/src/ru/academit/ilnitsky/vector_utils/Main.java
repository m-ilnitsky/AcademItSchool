package ru.academit.ilnitsky.vector_utils;

import ru.academit.ilnitsky.vector.*;

/**
 * Created by Mike on 02.10.2016.
 * Программма для тестирования класса "Вектор" (Vector)
 */
public class Main {
    public static void main(String[] args) {
        //Vector testV = new Vector(-3);
        //Vector testV = new Vector(-3, 17);

        Vector[] vector = new Vector[9];

        vector[0] = new Vector(4);
        vector[0].setCoordinate(2, 2);
        vector[0].setCoordinate(3, Math.PI);

        double[] array = {1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7};

        vector[1] = new Vector(array);
        vector[2] = new Vector(3, array);
        vector[3] = new Vector(4, array);

        vector[4] = new Vector(vector[0]);
        vector[4].setCoordinate(0, 1);

        vector[5] = new Vector(24, 127);

        vector[6] = new Vector(vector[0]);
        vector[7] = new Vector(vector[1]);
        vector[8] = new Vector(7, 1.0);

        int v1size = vector[1].getSize();
        System.out.print("vector[1] = { ");
        for (int i = 0; i < v1size; i++) {
            System.out.print(vector[1].getCoordinate(i));
            if (i + 1 < v1size) {
                System.out.print(", ");
            }
        }
        System.out.println(" }");

        System.out.println();
        for (int i = 0; i < vector.length; i++) {
            System.out.printf("[%d] %s%n", i, vector[i]);
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

        System.out.println();
        System.out.println(vector[2]);
        System.out.println("+");
        System.out.println(vector[8]);
        System.out.println("=");
        System.out.println(Vector.sum(vector[2], vector[8]));

        System.out.println();
        System.out.println(vector[0]);
        System.out.println("+");
        System.out.println(vector[4]);
        System.out.println("=");
        System.out.println(Vector.sum(vector[0], vector[4]));

        System.out.println();
        System.out.println();
        System.out.println(vector[0]);
        System.out.println("-");
        System.out.println(vector[4]);
        System.out.println("=");
        System.out.println(Vector.difference(vector[0], vector[4]));

        System.out.println();
        System.out.println();
        System.out.print(vector[0]);
        System.out.print(" * ");
        System.out.print(vector[1]);
        System.out.print(" = ");
        System.out.println(Vector.scalarProduct(vector[0], vector[1]));

        System.out.println();
        System.out.print(vector[0]);
        System.out.print(" add ");
        System.out.print(vector[2]);
        System.out.print(" = ");
        vector[0].add(vector[2]);
        System.out.println(vector[0]);

        System.out.println();
        System.out.print(vector[1]);
        System.out.print(" subtract ");
        System.out.print(vector[3]);
        System.out.print(" = ");
        vector[1].subtract(vector[3]);
        System.out.println(vector[1]);

        System.out.println();
        System.out.print(vector[8]);
        System.out.print(" multiply( ");
        System.out.print(37);
        System.out.print(" ) = ");
        vector[8].multiply(37);
        System.out.println(vector[8]);

        System.out.println();
        System.out.print(vector[7]);
        System.out.print(".turn() = ");
        vector[7].turn();
        System.out.println(vector[7]);
    }
}
