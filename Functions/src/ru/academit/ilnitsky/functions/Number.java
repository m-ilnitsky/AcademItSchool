package ru.academit.ilnitsky.functions;

/**
 * Created by Mike on 14.10.2016.
 * Класс для форматированного вывода чисел
 */
public class Number {
    // Форматируем вывод числа в строку
    private static String formatNumber(double coefficient) {
        String result;

        double absCoeff = Math.abs(coefficient);

        int lowestDigit = 0;
        for (int i = 0; i < 12; i++) {
            if ((long) (absCoeff * Math.pow(10, i + 1)) != ((long) (absCoeff * Math.pow(10, i)) * 10)) {
                lowestDigit = i + 1;
            }
        }

        if (Compare.isEqual(absCoeff, 0)) {
            result = "0";
        } else if (absCoeff > 1e12 || absCoeff < 1e-3) {
            result = String.format("%f", coefficient);
        } else {
            if (lowestDigit == 0) {
                result = String.format("%d", (int) coefficient);
            } else {
                String format = "%." + lowestDigit + "f";
                result = String.format(format, coefficient);
            }
        }

        result = result.replaceAll(",", ".");

        return result;
    }
}
