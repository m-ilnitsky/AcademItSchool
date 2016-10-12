package ru.academit.ilnitsky.matrix;

import ru.academit.ilnitsky.functions.*;
import ru.academit.ilnitsky.vector.Vector;

/**
 * Created by UserLabView on 12.10.16.
 * Класс "Матрица"
 */
public class Matrix {

    private Vector[] vectors;

    public Matrix(int iy, int ix) {
        vectors = new Vector[iy];

        for (int i = 0; i < iy; i++) {
            vectors[i] = new Vector(ix);
        }
    }

    public Matrix(Matrix matrix) {
        int iy = matrix.getSizeY();

        vectors = new Vector[iy];

        for (int i = 0; i < iy; i++) {
            vectors[i] = new Vector(matrix.vectors[i]);
        }
    }

    public Matrix(double[][] array) {
        int iy = array.length;

        vectors = new Vector[iy];

        for (int i = 0; i < iy; i++) {
            vectors[i] = new Vector(array[i]);
        }
    }

    public Matrix(Vector[] vectorArray) {
        int iy = vectorArray.length;

        vectors = new Vector[iy];

        for (int i = 0; i < iy; i++) {
            vectors[i] = new Vector(vectorArray[i]);
        }
    }

    public int getSizeY() {
        return vectors.length;
    }

    public int getSizeX() {
        return vectors[0].getSize();
    }

    public double getElement(int iy, int ix) {
        return vectors[iy].getCoordinate(ix);
    }

    public Vector getRow(int iy) {
        return new Vector(vectors[iy]);
    }

    public Vector getColumn(int ix) {
        int iy = vectors.length;
        Vector column = new Vector(iy);

        for (int i = 0; i < iy; i++) {
            column.setCoordinate(i, vectors[i].getCoordinate(ix));
        }

        return column;
    }
}
