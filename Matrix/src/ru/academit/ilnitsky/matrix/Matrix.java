package ru.academit.ilnitsky.matrix;

import ru.academit.ilnitsky.functions.*;
import ru.academit.ilnitsky.functions.Number;
import ru.academit.ilnitsky.vector.Vector;

/**
 * Created by UserLabView on 12.10.16.
 * Класс "Матрица"
 */
public class Matrix {

    private Vector[] rows;

    public Matrix(int numRows, int numColumns) {
        if (numRows <= 0) {
            throw new IllegalArgumentException("numRows<=0");
        }
        if (numColumns <= 0) {
            throw new IllegalArgumentException("numColumns<=0");
        }

        rows = new Vector[numRows];

        for (int i = 0; i < numRows; i++) {
            rows[i] = new Vector(numColumns);
        }
    }

    public Matrix(Matrix matrix) {
        int numRows = matrix.getRowsNumber();

        rows = new Vector[numRows];

        for (int i = 0; i < numRows; i++) {
            rows[i] = new Vector(matrix.rows[i]);
        }
    }

    public Matrix(double[][] array) {
        int numRows = array.length;
        int numColumns = 0;
        for (double[] d : array) {
            if (numColumns < d.length) {
                numColumns = d.length;
            }
        }

        rows = new Vector[numRows];

        for (int i = 0; i < numRows; i++) {
            rows[i] = new Vector(numColumns, array[i]);
        }
    }

    public Matrix(Vector[] vectorArray) {
        int numRows = vectorArray.length;
        int numColumns = 0;
        for (Vector v : vectorArray) {
            if (numColumns < v.getSize()) {
                numColumns = v.getSize();
            }
        }

        rows = new Vector[numRows];

        for (int i = 0; i < numRows; i++) {
            rows[i] = new Vector(numColumns, vectorArray[i]);
        }
    }

    public int getRowsNumber() {
        return rows.length;
    }

    public int getColumnsNumber() {
        return rows[0].getSize();
    }

    public int getSize() {
        return rows.length * rows[0].getSize();
    }

    public void setDiagonal(double value) {
        int min = Math.min(getColumnsNumber(), getRowsNumber());
        for (int i = 0; i < min; i++) {
            rows[i].setElement(i, value);
        }
    }

    public Vector getDiagonal() {
        int min = Math.min(getColumnsNumber(), getRowsNumber());

        Vector diagonal = new Vector(min);

        for (int i = 0; i < min; i++) {
            diagonal.setElement(i, rows[i].getElement(i));
        }

        return diagonal;
    }

    public void setElement(int numRows, int numColumns, double value) {
        if (numRows < 0) {
            throw new ArrayIndexOutOfBoundsException("numRows < 0");
        } else if (numRows >= getRowsNumber()) {
            throw new ArrayIndexOutOfBoundsException("numRows > max");
        }
        if (numColumns < 0) {
            throw new ArrayIndexOutOfBoundsException("numColumns < 0");
        } else if (numColumns >= getColumnsNumber()) {
            throw new ArrayIndexOutOfBoundsException("numColumns > max");
        }

        rows[numRows].setElement(numColumns, value);
    }

    public double getElement(int numRows, int numColumns) {
        if (numRows < 0) {
            throw new ArrayIndexOutOfBoundsException("numRows < 0");
        } else if (numRows >= getRowsNumber()) {
            throw new ArrayIndexOutOfBoundsException("numRows > max");
        }
        if (numColumns < 0) {
            throw new ArrayIndexOutOfBoundsException("numColumns < 0");
        } else if (numColumns >= getColumnsNumber()) {
            throw new ArrayIndexOutOfBoundsException("numColumns > max");
        }

        return rows[numRows].getElement(numColumns);
    }

    public Vector getRow(int numRows) {
        if (numRows < 0) {
            throw new ArrayIndexOutOfBoundsException("numRows < 0");
        } else if (numRows >= getRowsNumber()) {
            throw new ArrayIndexOutOfBoundsException("numRows > max");
        }

        return new Vector(rows[numRows]);
    }

    public Vector getColumn(int numColumns) {
        if (numColumns < 0) {
            throw new ArrayIndexOutOfBoundsException("numColumns < 0");
        } else if (numColumns >= getColumnsNumber()) {
            throw new ArrayIndexOutOfBoundsException("numColumns > max");
        }

        int numRows = rows.length;
        Vector column = new Vector(numRows);

        for (int i = 0; i < numRows; i++) {
            column.setElement(i, rows[i].getElement(numColumns));
        }

        return column;
    }

    public void setRandom() {
        for (Vector row : rows) {
            row.setRandom();
        }
    }

    public void setRandom(int exponent) {
        for (Vector row : rows) {
            row.setRandom(exponent);
        }
    }

    public void setRandomInt() {
        for (Vector row : rows) {
            row.setRandomInt();
        }
    }

    public void setRandomInt(int min, int max) {
        for (Vector row : rows) {
            row.setRandomInt(min, max);
        }
    }

    public void multiply(double value) {
        int numRows = rows.length;

        for (Vector v : rows) {
            v.multiply(value);
        }
    }

    public void resize(int numRows, int numColumns) {
        int numRows1 = rows.length;
        int numColumns1 = rows[0].getSize();

        if (numColumns != numColumns1 || numRows != numRows1) {
            int min = Math.min(numColumns1, numColumns);

            Vector[] temp = new Vector[numRows];

            for (int i = 0; i < numRows; i++) {
                temp[i] = new Vector(numColumns);

                if (i < numRows1) {
                    for (int j = 0; j < min; j++) {
                        temp[i].setElement(j, rows[i].getElement(j));
                    }
                }
            }

            rows = temp;
        }
    }

    public void resize(Matrix matrix) {
        int numRows1 = rows.length;
        int numColumns1 = rows[0].getSize();

        int numRows2 = matrix.rows.length;
        int numColumns2 = matrix.rows[0].getSize();

        if (numColumns2 > numColumns1 || numRows2 > numRows1) {
            int maxNumColumns = Math.max(numColumns1, numColumns2);
            int maxNumRows = Math.max(numRows1, numRows2);

            resize(maxNumRows, maxNumColumns);
        }
    }

    public void transpose() {
        int numColumns = rows.length;
        int numRows = rows[0].getSize();

        Vector[] newRows = new Vector[numRows];

        for (int i = 0; i < numRows; i++) {
            newRows[i] = new Vector(numColumns);

            for (int j = 0; j < numColumns; j++) {
                newRows[i].setElement(j, rows[j].getElement(i));
            }
        }

        rows = newRows;
    }

    public void add(Matrix matrix) {
        resize(matrix);

        int numRows = matrix.rows.length;

        for (int i = 0; i < numRows; i++) {
            rows[i].add(matrix.rows[i]);
        }
    }

    public void subtract(Matrix matrix) {
        resize(matrix);

        int numRows = matrix.rows.length;

        for (int i = 0; i < numRows; i++) {
            rows[i].subtract(matrix.rows[i]);
        }
    }

    public void multiply(Matrix matrix) {
        int numRows1 = rows.length;
        int numColumns1 = rows[0].getSize();

        int numRows2 = matrix.rows.length;
        int numColumns2 = matrix.rows[0].getSize();

        int min = Math.min(numColumns1, numRows2);

        Vector[] newRows = new Vector[numRows1];

        for (int i = 0; i < numRows1; i++) {
            newRows[i] = new Vector(numColumns2);

            for (int j = 0; j < numColumns2; j++) {
                double sum = 0;

                for (int k = 0; k < min; k++) {
                    sum += rows[i].getElement(k) * matrix.rows[k].getElement(j);
                }

                newRows[i].setElement(j, sum);
            }
        }

        rows = newRows;
    }

    public static Matrix sum(Matrix matrix1, Matrix matrix2) {
        Matrix matrix3 = new Matrix(matrix1);

        matrix3.add(matrix2);

        return matrix3;
    }

    public static Matrix difference(Matrix matrix1, Matrix matrix2) {
        Matrix matrix3 = new Matrix(matrix1);

        matrix3.subtract(matrix2);

        return matrix3;
    }

    public static Matrix multiply(Matrix matrix1, Matrix matrix2) {
        Matrix matrix3 = new Matrix(matrix1);

        matrix3.multiply(matrix2);

        return matrix3;
    }

    public static Matrix multiply(Matrix matrix1, double value) {
        Matrix matrix2 = new Matrix(matrix1);

        matrix2.multiply(value);

        return matrix2;
    }

    public static Matrix multiply(Vector vector1, Vector vector2) {
        int numRows = vector1.getSize();
        int numColumns = vector2.getSize();

        Matrix matrix = new Matrix(numRows, numColumns);

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                matrix.rows[i].setElement(j, vector1.getElement(i) * vector2.getElement(j));
            }
        }

        return matrix;
    }

    public static Vector multiply(Matrix matrix, Vector vector1) {
        int numRows = matrix.rows.length;
        int numColumns = matrix.rows[0].getSize();
        int numElements = vector1.getSize();

        int min = Math.min(numColumns, numElements);

        Vector vector2 = new Vector(numRows);

        for (int i = 0; i < numRows; i++) {
            double sum = 0;

            for (int j = 0; j < min; j++) {
                sum += matrix.rows[i].getElement(j) * vector1.getElement(j);
            }

            vector2.setElement(i, sum);
        }

        return vector2;
    }

    @Override
    public int hashCode() {
        final int prime = 3;
        int result = 127;

        int numRows = getRowsNumber();
        for (int i = 0; i < numRows; i++) {
            result = prime * result;
            if (i % 2 == 1) {
                result /= i;
            }
            if (result > 3e8) {
                result /= 10;
            }
            result += rows[i].hashCode();
        }
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        } else if (this == object) {
            return true;
        } else if (this.getClass() == object.getClass()) {
            Matrix other = (Matrix) object;

            if ((this.getColumnsNumber() != other.getColumnsNumber())
                    || (this.getRowsNumber() != other.getRowsNumber())) {
                return false;
            } else {
                int numRows = this.getRowsNumber();
                for (int i = 0; i < numRows; i++) {
                    if (!this.rows[i].equals(other.rows[i])) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int numRows = this.getRowsNumber();

        sb.append("{ ");
        for (int i = 0; i < numRows; i++) {
            sb.append(rows[i]);
            if (i + 1 != numRows) {
                sb.append(", ");
            }
        }
        sb.append(" }");

        return sb.toString();
    }

    public void print() {
        int numColumns = getColumnsNumber();
        int numRows = rows.length;

        boolean[] isInteger = new boolean[numColumns];
        int[] numSymbols = new int[numColumns];
        String[] columnFormat = new String[numColumns];

        for (int i = 0; i < numColumns; i++) {
            boolean isInt = true;
            int numSym = 0;

            for (Vector r : rows) {
                if (!Number.isInteger(r.getElement(i))) {
                    isInt = false;
                    break;
                }

                int length = Number.length((int) r.getElement(i));

                if (length > numSym) {
                    numSym = length;
                }
            }

            if (isInt && numSym < 10) {
                numSymbols[i] = numSym;
                isInteger[i] = true;
                columnFormat[i] = "%" + numSym + "d";
            } else {
                numSymbols[i] = 10;
                isInteger[i] = false;
            }
        }

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (j == 0) {
                    System.out.print("|  ");
                }

                if (isInteger[j]) {
                    System.out.printf(columnFormat[j], (int) rows[i].getElement(j));
                } else {
                    System.out.print(Number.formatExp(rows[i].getElement(j), 3, 10));
                }

                if (j + 1 < numColumns) {
                    System.out.print("  ");
                } else {
                    System.out.print("  |\n");
                }
            }
        }
    }
}
