package ru.academit.ilnitsky.functions;

/**
 * Created by Mike on 09.10.2016.
 * Класс для статических функций сравнения float и double
 */
public class Compare {
    public static final float EPS_FLOAT = Float.MIN_VALUE * 8;
    public static final double EPS_DOUBLE = Double.MIN_VALUE * 8;

    private Compare() {
    }

    public static boolean isEqual(float a, float b) {
        return (Math.abs(a - b) < EPS_FLOAT);
    }

    public static boolean isEqual(double a, double b) {
        return (Math.abs(a - b) < EPS_DOUBLE);
    }

    public static boolean isBigger(float a, float b) {
        return (a - b > EPS_FLOAT);
    }

    public static boolean isBigger(double a, double b) {
        return (a - b > EPS_DOUBLE);
    }

    public static boolean isBiggerOrEqual(float a, float b) {
        return (a - b > -EPS_FLOAT);
    }

    public static boolean isBiggerOrEqual(double a, double b) {
        return (a - b > -EPS_DOUBLE);
    }
}
