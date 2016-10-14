package ru.academit.ilnitsky.functions;

/**
 * Created by Mike on 14.10.2016.
 * Класс для форматированного вывода чисел
 */
public class Number {
    public static final double EXP_TOP = 1e9;
    public static final double EXP_BOTTOM = 1e-3;

    public static boolean isEXP(double value, double expTop, double expBottom) {
        double absValue = Math.abs(value);
        return (absValue > expTop || absValue < expBottom);
    }

    public static boolean isEXP(double value) {
        return isEXP(value, EXP_TOP, EXP_BOTTOM);
    }

    public static boolean isInteger(int value) {
        return true;
    }

    public static boolean isInteger(double value) {
        return (lowestDigit(value) == 0);
    }

    public static int length(int value){
        return Integer.toString(value).length();
    }

    public static int length(double value){
        return Double.toString(value).length();
    }

    public static int lowestDigit(double value) {
        double absValue = Math.abs(value);
        int result = 0;

        for (int i = 0; i < 9; i++) {
            if ((long) (absValue * Math.pow(10, i + 1)) != ((long) (absValue * Math.pow(10, i)) * 10)) {
                result = i + 1;
            }
        }

        return result;
    }

    public static String format(double value, double expTop, double expBottom) {
        String result;

        int lowestDigit = lowestDigit(value);

        if (Compare.isEqual(value, 0)) {
            result = "0";
        } else if (isEXP(value, expTop, expBottom)) {
            result = String.format("%.4e", value);
        } else {
            if (lowestDigit == 0) {
                result = String.format("%d", (int) value);
            } else {
                String format = "%." + lowestDigit + "f";
                result = String.format(format, value);
            }
        }

        result = result.replaceAll(",", ".");

        return result;
    }

    public static String format(double value) {
        return format(value, EXP_TOP, EXP_BOTTOM);
    }

    public static String formatExp(double value, int numDigits) {
        String format = "%." + numDigits + "e";
        String result;

        if (Compare.isEqual(value, 0)) {
            result = "0";
        } else {
            result = String.format(format, value);
        }

        result = result.replaceAll(",", ".");

        return result;
    }

    public static String formatExp(double value, int numDigits, int length) {
        double absValue = Math.abs(value);
        String format = "%." + numDigits + "e";
        String result;

        if (Compare.isEqual(value, 0)) {
            result = " 0";
        } else if (absValue > 0.1 && absValue < 10) {
            int lowestDigit = lowestDigit(value);
            lowestDigit = Math.min(lowestDigit, length - 5);
            String format2 = "%." + lowestDigit + "f";
            result = String.format(format2, value);
            if (value > 0) {
                result = " " + result;
            }
        } else {
            result = String.format(format, value);
            if (value > 0) {
                result = " " + result;
            }
        }

        if (result.length() < length) {
            int add = length - result.length();
            StringBuilder sb = new StringBuilder();
            sb.append(result);
            for (int i = 0; i < add; i++) {
                sb.append(" ");
            }
            result = sb.toString();
        }
        result = result.replaceAll(",", ".");

        return result;
    }

    public static void main(String[] args) {
        double[] dArray = {1.0, 1.12, 1234567890.0, 12, 34.00067, 0.127, -4.0000000001, -0.123456789, -127.127, 2345, 1e-9, 123e-7, 1.25e33, 9.123456789, 0};
        int[] iArray = {1, 23, 456789233, 1234, 123, 123456789, 1129876, 12235, 1233, -123456789, -123, -12345, -12234356, -8, 0};

        for (int i = 0; i < dArray.length; i++) {
            System.out.printf("[%2d]  %12s   %12s", i, format(dArray[i]), format(iArray[i]));
            System.out.printf("  **   %10s   %10s", formatExp(dArray[i], 3), formatExp(iArray[i], 3));
            System.out.printf("  **   %10s   %10s", formatExp(dArray[i], 3, 10), formatExp(iArray[i], 3, 10));
            System.out.println("   **   " + dArray[i] + "             " + iArray[i]);
        }
    }
}
