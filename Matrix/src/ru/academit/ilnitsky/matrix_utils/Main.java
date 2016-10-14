package ru.academit.ilnitsky.matrix_utils;

import ru.academit.ilnitsky.vector.*;
import ru.academit.ilnitsky.matrix.*;

/**
 * Created by Mike on 13.10.2016.
 * Программма для тестирования класса "Мтрица" (Matrix)
 */
public class Main {
    public static void main(String[] args) {
        //Matrix matrix1 = new Matrix(-1,5);
        //Matrix matrix2 = new Matrix(11,0);

        Matrix[] matrix = new Matrix[6];

        matrix[0] = new Matrix(2, 4);
        matrix[1] = new Matrix(4, 2);
        matrix[2] = new Matrix(12, 24);
        matrix[2].setDiagonal(2);
        matrix[3] = new Matrix(3, 3);
        matrix[2].setDiagonal(3);
        matrix[4] = new Matrix(matrix[3]);
        matrix[5] = new Matrix(matrix[2]);

        for (int i = 0; i < matrix.length; i++) {
            System.out.printf("[%d] %s%n", i, matrix[i]);
        }

        System.out.println();
        for (int i = 0; i < matrix.length; i++) {
            System.out.printf("[%d] size = %4d, rows = %2d, columns = %2d, hashCode = %11d%n", i, matrix[i].getSize(), matrix[i].getRowsNumber(), matrix[i].getColumnsNumber(), matrix[i].hashCode());
        }

        System.out.println();
        for (int i = 0; i < matrix.length - 1; i++) {
            for (int j = i + 1; j < matrix.length; j++) {
                if (matrix[i].equals(matrix[j])) {
                    System.out.printf("[%d] equals [%d]%n", i, j);
                }
            }
        }

        //тест heshCode
        Matrix[][] mat = new Matrix[30][5];
        for (int i = 0; i < 30; i++) {
            mat[i][0] = new Matrix(i + 1, 1);
            mat[i][1] = new Matrix(i + 1, 3);
            mat[i][2] = new Matrix(i + 1, 10);
            mat[i][3] = new Matrix(i + 1, 30);
            mat[i][4] = new Matrix(i + 1, 100);

            System.out.printf("rows=%2d, HC[1] = %11d, HC[3] = %11d, HC[10] = %11d, HC[30] = %11d, HC[100] = %11d%n", i + 1, mat[i][0].hashCode(), mat[i][1].hashCode(), mat[i][2].hashCode(), mat[i][3].hashCode(), mat[i][4].hashCode());
        }
    }
}
