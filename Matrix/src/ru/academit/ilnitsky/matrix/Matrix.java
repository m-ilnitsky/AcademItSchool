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
        if (iy <= 0 || ix <= 0) {
            throw new IllegalArgumentException("size<=0");
        }

        vectors = new Vector[iy];

        for (int i = 0; i < iy; i++) {
            vectors[i] = new Vector(ix);
        }
    }

    public Matrix(int iyx, double value) {
        this(iyx, iyx);

        for (int i = 0; i < iyx; i++) {
            vectors[i].setCoordinate(i, value);
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

    public void setElement(int iy, int ix, double value) {
        if (iy < 0 || ix < 0) {
            throw new ArrayIndexOutOfBoundsException("index < 0");
        } else if (iy >= getSizeY() || ix >= getSizeX()) {
            throw new ArrayIndexOutOfBoundsException("index > max");
        }

        vectors[iy].setCoordinate(ix, value);
    }

    public double getElement(int iy, int ix) {
        if (iy < 0 || ix < 0) {
            throw new ArrayIndexOutOfBoundsException("index < 0");
        } else if (iy >= getSizeY() || ix >= getSizeX()) {
            throw new ArrayIndexOutOfBoundsException("index > max");
        }

        return vectors[iy].getCoordinate(ix);
    }

    public Vector getRow(int iy) {
        if (iy < 0) {
            throw new ArrayIndexOutOfBoundsException("index < 0");
        } else if (iy >= getSizeY()) {
            throw new ArrayIndexOutOfBoundsException("index > max");
        }

        return new Vector(vectors[iy]);
    }

    public Vector getColumn(int ix) {
        if (ix < 0) {
            throw new ArrayIndexOutOfBoundsException("index < 0");
        } else if (ix >= getSizeX()) {
            throw new ArrayIndexOutOfBoundsException("index > max");
        }

        int iy = vectors.length;
        Vector column = new Vector(iy);

        for (int i = 0; i < iy; i++) {
            column.setCoordinate(i, vectors[i].getCoordinate(ix));
        }

        return column;
    }

    public void multiply(double value) {
        int iy = vectors.length;

        for (int j = 0; j < iy; j++) {
            vectors[j].multiply(value);
        }
    }

    public void resize(int iy, int ix) {
        int iy1 = vectors.length;
        int ix1 = vectors[0].getSize();

        if (ix != ix1 || iy != iy1) {
            int ixMin = Math.min(ix1, ix);

            Vector[] temp = new Vector[iy];

            for (int i = 0; i < iy; i++) {
                temp[i] = new Vector(ix);

                if (i < iy1) {
                    for (int j = 0; j < ixMin; j++) {
                        temp[i].setCoordinate(j, vectors[i].getCoordinate(j));
                    }
                }
            }
            vectors = temp;
        }
    }

    public void resize(Matrix matrix) {
        int iy1 = vectors.length;
        int ix1 = vectors[0].getSize();

        int iy2 = matrix.vectors.length;
        int ix2 = matrix.vectors[0].getSize();

        if (ix2 > ix1 || iy2 > iy1) {
            int ixMax = Math.max(ix1, ix2);
            int iyMax = Math.max(iy1, iy2);

            Vector[] temp = new Vector[iyMax];

            for (int i = 0; i < iyMax; i++) {
                temp[i] = new Vector(ixMax);

                if (i < iy1) {
                    for (int j = 0; j < ix1; j++) {
                        temp[i].setCoordinate(j, vectors[i].getCoordinate(j));
                    }
                }
            }
            vectors = temp;
        }
    }

    public void add(Matrix matrix) {
        resize(matrix);

        int iy = matrix.vectors.length;

        for (int i = 0; i < iy; i++) {
            vectors[i].add(matrix.vectors[i]);
        }
    }

    public void subtract(Matrix matrix) {
        resize(matrix);

        int iy = matrix.vectors.length;

        for (int i = 0; i < iy; i++) {
            vectors[i].subtract(matrix.vectors[i]);
        }
    }

    public static Matrix sum(Matrix matrix1, Matrix matrix2) {
        int iy1 = matrix1.vectors.length;
        int iy2 = matrix2.vectors.length;
        int iyMax = Math.max(iy1, iy2);
        int ixMax = Math.max(matrix2.vectors[0].getSize(), matrix2.vectors[0].getSize());

        Matrix matrix3 = new Matrix(iyMax, ixMax);

        for (int i = 0; i < iy1; i++) {
            matrix3.vectors[i].add(matrix1.vectors[i]);
        }

        for (int i = 0; i < iy2; i++) {
            matrix3.vectors[i].add(matrix2.vectors[i]);
        }

        return matrix3;
    }

    public static Matrix difference(Matrix matrix1, Matrix matrix2) {
        int iy1 = matrix1.vectors.length;
        int iy2 = matrix2.vectors.length;
        int iyMax = Math.max(iy1, iy2);
        int ixMax = Math.max(matrix2.vectors[0].getSize(), matrix2.vectors[0].getSize());

        Matrix matrix3 = new Matrix(iyMax, ixMax);

        for (int i = 0; i < iy1; i++) {
            matrix3.vectors[i].add(matrix1.vectors[i]);
        }

        for (int i = 0; i < iy2; i++) {
            matrix3.vectors[i].subtract(matrix2.vectors[i]);
        }

        return matrix3;
    }

    public static Matrix multiply(Matrix matrix1, Matrix matrix2) {
        int iy1 = matrix1.vectors.length;
        int ix1 = matrix1.vectors[0].getSize();

        int iy2 = matrix2.vectors.length;
        int ix2 = matrix2.vectors[0].getSize();

        Matrix matrix3 = new Matrix(iy1, ix2);

        int min = Math.min(ix1, iy2);

        for (int i = 0; i < iy1; i++) {
            for (int j = 0; j < ix2; j++) {
                double sum = 0;

                for (int k = 0; k < min; k++) {
                    sum += matrix1.vectors[i].getCoordinate(k) * matrix1.vectors[k].getCoordinate(j);
                }

                matrix3.vectors[i].setCoordinate(j, sum);
            }
        }

        return matrix3;
    }

    public static Matrix multiply(Vector vector1, Vector vector2) {
        int iy = vector1.getSize();
        int ix = vector2.getSize();

        Matrix matrix = new Matrix(iy, ix);

        for (int i = 0; i < iy; i++) {
            for (int j = 0; j < ix; j++) {
                matrix.vectors[i].setCoordinate(j, vector1.getCoordinate(i) * vector2.getCoordinate(j));
            }
        }

        return matrix;
    }

    public static Vector multiply(Matrix matrix, Vector vector1) {
        int iy = matrix.vectors.length;
        int ix = matrix.vectors[0].getSize();
        int iv = vector1.getSize();

        int min = Math.min(ix, iv);

        Vector vector2 = new Vector(iy);

        for (int i = 0; i < iy; i++) {
            double sum = 0;

            for (int j = 0; j < min; j++) {
                sum += matrix.vectors[i].getCoordinate(j) * vector1.getCoordinate(j);
            }

            vector2.setCoordinate(i, sum);
        }

        return vector2;
    }

    @Override
    public int hashCode() {
        final int prime = 3;
        int result = 1;

        int iy = getSizeY();
        for (int i = 0; i < iy; i++) {
            result = prime * result / (i + 1) + vectors[i].hashCode();
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

            if ((this.getSizeX() != other.getSizeX())
                    || (this.getSizeY() != other.getSizeY())) {
                return false;
            } else {
                int iy = this.getSizeY();
                for (int i = 0; i < iy; i++) {
                    if (!this.vectors[i].equals(other.vectors[i])) {
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
        int iy = this.getSizeY();

        sb.append("{ ");
        for (int i = 0; i < iy; i++) {
            sb.append(vectors[i]);
            if (i + 1 != iy) {
                sb.append(", ");
            }
        }
        sb.append(" }");

        return sb.toString();
    }
}
