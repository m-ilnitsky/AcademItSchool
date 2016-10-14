package ru.academit.ilnitsky.vector;

import ru.academit.ilnitsky.functions.*;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Mike on 12.10.2016.
 * Класс "Вектор"
 */
public class Vector {

    private double[] elements;

    public Vector(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("size<=0");
        }

        this.elements = new double[size];
    }

    public Vector(int size, double value) {
        if (size <= 0) {
            throw new IllegalArgumentException("size<=0");
        }

        this.elements = new double[size];

        Arrays.fill(elements, value);
    }

    public Vector(Vector vector) {
        this(vector.elements);
    }

    public Vector(double[] array) {
        this(array.length, array);
    }

    public Vector(int size, double[] array) {
        if (size <= 0) {
            throw new IllegalArgumentException("size<=0");
        }

        this.elements = new double[size];

        System.arraycopy(array, 0, elements, 0, Math.min(size, array.length));
    }

    public Vector(int size, Vector vector) {
        if (size <= 0) {
            throw new IllegalArgumentException("size<=0");
        }

        this.elements = new double[size];

        System.arraycopy(vector.elements, 0, elements, 0, Math.min(size, vector.elements.length));
    }

    public int getSize() {
        return elements.length;
    }

    public double getElement(int i) {
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException("index < 0");
        } else if (i >= elements.length) {
            throw new ArrayIndexOutOfBoundsException("index > max");
        }

        return elements[i];
    }

    public void setElement(int i, double value) {
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException("index < 0");
        } else if (i >= elements.length) {
            throw new ArrayIndexOutOfBoundsException("index > max");
        }

        elements[i] = value;
    }

    public void setRandom(){
        final Random random = new Random();

        int size = elements.length;
        for (int i = 0; i < size; i++) {
            elements[i] += random.nextDouble()*Math.pow(10,random.nextInt(10));
        }
    }

    public void set(Vector vector, boolean fillZero) {
        int min = Math.min(elements.length, vector.elements.length);

        System.arraycopy(vector.elements, 0, elements, 0, min);

        if (fillZero && min < elements.length) {
            for (int i = min; i < elements.length; i++) {
                elements[i] = 0;
            }
        }
    }

    public void resize(int newSize) {
        double[] newElements = new double[newSize];
        int min = Math.min(elements.length, newSize);

        System.arraycopy(elements, 0, newElements, 0, min);

        elements = newElements;
    }

    public void resize(Vector vector) {
        resize(vector.elements.length);
    }

    public void clone(Vector vector) {
        int newSize = vector.elements.length;

        if (elements.length != newSize) {
            elements = new double[newSize];
        }

        System.arraycopy(vector.elements, 0, elements, 0, newSize);
    }

    public void add(Vector vector) {
        if (vector.getSize() > this.getSize()) {
            double[] temp = new double[vector.getSize()];
            System.arraycopy(this.elements, 0, temp, 0, this.getSize());
            this.elements = temp;
        }

        int size = vector.getSize();
        for (int i = 0; i < size; i++) {
            elements[i] += vector.elements[i];
        }
    }

    public void subtract(Vector vector) {
        if (vector.getSize() > this.getSize()) {
            double[] temp = new double[vector.getSize()];
            System.arraycopy(this.elements, 0, temp, 0, this.getSize());
            this.elements = temp;
        }

        int size = vector.getSize();
        for (int i = 0; i < size; i++) {
            elements[i] -= vector.elements[i];
        }
    }

    public void multiply(double value) {
        int size = this.getSize();
        for (int i = 0; i < size; i++) {
            elements[i] *= value;
        }
    }

    public void turn() {
        multiply(-1);
    }

    public double getLength() {
        double result = 0;
        for (double v : elements) {
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
            result = prime * result / (i + 1) + HashCode.hashCode(elements[i]);
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
                    if (!Compare.isEqual(elements[i], other.elements[i])) {
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
            sb.append(elements[i]);
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

        Vector vector3 = new Vector(max, vector1.elements);
        vector3.subtract(vector2);

        return vector3;
    }

    public static double scalarProduct(Vector vector1, Vector vector2) {
        int min = Math.min(vector1.getSize(), vector2.getSize());

        double result = 0;

        for (int i = 0; i < min; i++) {
            result += vector1.elements[i] * vector2.elements[i];
        }

        return result;
    }
}
