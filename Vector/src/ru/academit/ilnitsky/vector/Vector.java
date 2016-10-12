package ru.academit.ilnitsky.vector;

/**
 * Created by Mike on 12.10.2016.
 * Класс "Вектор"
 */
public class Vector {
    private static final double EPS = 0.0001;

    private static boolean isEqual(double a, double b) {
        return (Math.abs(a - b) < EPS);
    }

    private static int hashCodeForDouble(double value) {
        int shift = 1000;
        int exponent = Math.getExponent(value);
        int mantissa = (int) (value / Math.pow(2, exponent) * shift);

        int result = 127;
        result += (1024 + exponent) * shift;
        result += 3 * shift + mantissa;
        return result;
    }

    private final int SIZE;
    private double[] x;

    public Vector(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("size<=0");
        }
        this.SIZE = size;
        this.x = new double[SIZE];
    }

    public Vector(Vector vector) {
        this.SIZE = vector.SIZE;
        this.x = new double[SIZE];

        System.arraycopy(vector.x, 0, x, 0, SIZE);
    }

    public Vector(double[] array) {
        this.SIZE = array.length;
        this.x = new double[SIZE];

        System.arraycopy(array, 0, x, 0, SIZE);
    }

    public Vector(int size, double[] array) {
        if (size <= 0) {
            throw new IllegalArgumentException("size<=0");
        }

        this.SIZE = size;
        this.x = new double[SIZE];

        System.arraycopy(array, 0, x, 0, Math.min(SIZE, array.length));
    }

    public int getSize() {
        return SIZE;
    }

    public double getX(int i) {
        return x[i];
    }

    public void setX(int i, double value) {
        x[i] = value;
    }

    public void add(Vector vector) {
        int size = Math.min(SIZE, vector.SIZE);

        for (int i = 0; i < size; i++) {
            x[i] += vector.x[i];
        }
    }

    public void subtract(Vector vector) {
        int size = Math.min(SIZE, vector.SIZE);

        for (int i = 0; i < size; i++) {
            x[i] -= vector.x[i];
        }
    }

    public void multiply(double value) {
        for (int i = 0; i < SIZE; i++) {
            x[i] *= value;
        }
    }

    public void turn() {
        for (int i = 0; i < SIZE; i++) {
            x[i] *= -1;
        }
    }

    public double getLength() {
        double result = 0;
        for (double v : x) {
            result += v * v;
        }
        return Math.sqrt(result);
    }

    @Override
    public int hashCode() {
        final int prime = 3;
        int result = 1;
        for (double v : x) {
            result = prime * result + hashCodeForDouble(v) / SIZE;
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

            if (SIZE != other.SIZE) {
                return false;
            } else {
                for (int i = 0; i < SIZE; i++) {
                    if (!isEqual(x[i], other.x[i])) {
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

        sb.append("{ ");
        for (int i = 0; i < SIZE; i++) {
            sb.append(x[i]);
            if (i + 1 != SIZE) {
                sb.append(", ");
            }
        }
        sb.append(" }");

        return sb.toString();
    }

    public static Vector sum(Vector vector1, Vector vector2) {
        int min = Math.min(vector1.SIZE, vector2.SIZE);
        int max = Math.max(vector1.SIZE, vector2.SIZE);

        Vector vMin;
        Vector vMax;

        if (min == max || max == vector1.SIZE) {
            vMax = vector1;
            vMin = vector2;
        } else {
            vMax = vector2;
            vMin = vector1;
        }

        Vector vector3 = new Vector(vMax);

        for (int i = 0; i < min; i++) {
            vector3.x[i] += vMin.x[i];
        }

        return vector3;
    }

    public static Vector difference(Vector vector1, Vector vector2) {
        int max = Math.max(vector1.SIZE, vector2.SIZE);

        Vector vector3 = new Vector(max, vector1.x);

        for (int i = 0; i < vector2.SIZE; i++) {
            vector3.x[i] -= vector2.x[i];
        }

        return vector3;
    }

    public static double scalarProduct(Vector vector1, Vector vector2) {
        int min = Math.min(vector1.SIZE, vector2.SIZE);

        double result = 0;

        for (int i = 0; i < min; i++) {
            result += vector1.x[i] * vector2.x[i];
        }

        return result;
    }
}
