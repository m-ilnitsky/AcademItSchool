package ru.academit.ilnitsky.vector;

import ru.academit.ilnitsky.functions.*;

import java.util.Arrays;

/**
 * Created by Mike on 12.10.2016.
 * Класс "Вектор"
 */
public class Vector {

    private double[] coordinate;

    public Vector(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("size<=0");
        }

        this.coordinate = new double[size];
    }

    public Vector(int size, double value) {
        if (size <= 0) {
            throw new IllegalArgumentException("size<=0");
        }

        this.coordinate = new double[size];

        Arrays.fill(coordinate, value);
    }

    public Vector(Vector vector) {
        this(vector.coordinate);
    }

    public Vector(double[] array) {
        this(array.length, array);
    }

    public Vector(int size, double[] array) {
        if (size <= 0) {
            throw new IllegalArgumentException("size<=0");
        }

        this.coordinate = new double[size];

        System.arraycopy(array, 0, coordinate, 0, Math.min(size, array.length));
    }

    public int getSize() {
        return coordinate.length;
    }

    public double getCoordinate(int i) {
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException("index < 0");
        } else if (i >= coordinate.length) {
            throw new ArrayIndexOutOfBoundsException("index > max");
        }

        return coordinate[i];
    }

    public void setCoordinate(int i, double value) {
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException("index < 0");
        } else if (i >= coordinate.length) {
            throw new ArrayIndexOutOfBoundsException("index > max");
        }

        coordinate[i] = value;
    }

    public void add(Vector vector) {
        if (vector.getSize() > this.getSize()) {
            double[] temp = new double[vector.getSize()];
            System.arraycopy(this.coordinate, 0, temp, 0, this.getSize());
            this.coordinate = temp;
        }

        int size = vector.getSize();
        for (int i = 0; i < size; i++) {
            coordinate[i] += vector.coordinate[i];
        }
    }

    public void subtract(Vector vector) {
        if (vector.getSize() > this.getSize()) {
            double[] temp = new double[vector.getSize()];
            System.arraycopy(this.coordinate, 0, temp, 0, this.getSize());
            this.coordinate = temp;
        }

        int size = vector.getSize();
        for (int i = 0; i < size; i++) {
            coordinate[i] -= vector.coordinate[i];
        }
    }

    public void multiply(double value) {
        int size = this.getSize();
        for (int i = 0; i < size; i++) {
            coordinate[i] *= value;
        }
    }

    public void turn() {
        multiply(-1);
    }

    public double getLength() {
        double result = 0;
        for (double v : coordinate) {
            result += v * v;
        }
        return Math.sqrt(result);
    }

    @Override
    public int hashCode() {
        final int prime = 3;
        int result = 1;

        int size = this.getSize();
        for (int i = 0; i < size; i++) {
            result = prime * result / (i + 1) + HashCode.hashCode(coordinate[i]);
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
            Vector other = (Vector) object;

            if (this.getSize() != other.getSize()) {
                return false;
            } else {
                int size = this.getSize();
                for (int i = 0; i < size; i++) {
                    if (!Compare.isEqual(coordinate[i], other.coordinate[i])) {
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
        int size = this.getSize();

        sb.append("{ ");
        for (int i = 0; i < size; i++) {
            sb.append(coordinate[i]);
            if (i + 1 != size) {
                sb.append(", ");
            }
        }
        sb.append(" }");

        return sb.toString();
    }

    public static Vector sum(Vector vector1, Vector vector2) {
        Vector vMin;
        Vector vMax;

        if (vector1.getSize() >= vector2.getSize()) {
            vMax = vector1;
            vMin = vector2;
        } else {
            vMax = vector2;
            vMin = vector1;
        }

        Vector vector3 = new Vector(vMax);
        vector3.add(vMin);

        return vector3;
    }

    public static Vector difference(Vector vector1, Vector vector2) {
        int max = Math.max(vector1.getSize(), vector2.getSize());

        Vector vector3 = new Vector(max, vector1.coordinate);
        vector3.subtract(vector2);

        return vector3;
    }

    public static double scalarProduct(Vector vector1, Vector vector2) {
        int min = Math.min(vector1.getSize(), vector2.getSize());

        double result = 0;

        for (int i = 0; i < min; i++) {
            result += vector1.coordinate[i] * vector2.coordinate[i];
        }

        return result;
    }
}
