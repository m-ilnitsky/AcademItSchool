package ru.academit.ilnitsky.temperature;

/**
 * Класс для перевода температур из одной шкалы в другую шкалу
 * Created by UserLabView on 08.12.16.
 */
public class TemperatureConverter {
    public static final double zeroC = 273.15;

    protected double temperatureK = 0;

    public static double toKfromC(double tC) {
        return tC + zeroC;
    }

    public static double toCfromK(double tK) {
        return tK - zeroC;
    }

    public static double toCfromF(double tF) {
        return (tF - 32) * 5.0 / 9.0;
    }

    public static double toFfromC(double tC) {
        return tC * 9.0 / 5.0 + 32;
    }

    public static double toKfromF(double tF) {
        return toKfromC(toCfromF(tF));
    }

    public static double toFfromK(double tK) {
        return toFfromC(toCfromK(tK));
    }

    public void setK(double tK) {
        temperatureK = tK;
    }

    public void setC(double tC) {
        temperatureK = toKfromC(tC);
    }

    public void setF(double tF) {
        temperatureK = toKfromF(tF);
    }

    public double getK() {
        return temperatureK;
    }

    public double getC() {
        return toCfromK(temperatureK);
    }

    public double getF() {
        return toFfromK(temperatureK);
    }
}
