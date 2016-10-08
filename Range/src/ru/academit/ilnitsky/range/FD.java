package ru.academit.ilnitsky.range;

/**
 * Created by Mike on 09.10.2016.
 * Класс для статических функций сравнения float и double
 */
public class FD {
    public static final float epsF = Float.MIN_VALUE * 8;
    public static final double epsD = Double.MIN_VALUE * 8;

    public static boolean isEqual(float a, float b) {
        return (Math.abs(a - b) < epsF);
    }

    public static boolean isEqual(double a, double b) {
        return (Math.abs(a - b) < epsD);
    }

    public static boolean isBigger(float a, float b) {
        return (a - b > epsF);
    }

    public static boolean isBigger(double a, double b) {
        return (a - b > epsD);
    }

    public static boolean isBiggerOrEqual(float a, float b) {
        return (a - b > -epsF);
    }

    public static boolean isBiggerOrEqual(double a, double b) {
        return (a - b > -epsD);
    }
}
