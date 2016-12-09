package ru.academit.ilnitsky.temperature;

/**
 * Класс для преобразования энегрии и температуры в другие величины
 * Created by UserLabView on 08.12.16.
 */
public class EnergyConverter extends TemperatureConverter {
    public static final double e = 1.6021766208e-19;    // [C]
    public static final double k = 1.38064852e-23;      // [J/K]
    public static final double h = 6.62607004e-34;      // [J*s]
    public static final double c = 299792458;           // [m/s]
    public static final double lambdaTm = 0.0028999;    // [m*K]
    public static final double lambdaTum = 2899.9;      // [um*K]

    protected double energyJ = 0;

    protected void setJfromK() {
        energyJ = temperatureK * k;
    }

    protected void setKfromJ() {
        temperatureK = energyJ / k;
    }

    public void setK(double tK) {
        super.setK(tK);
        setJfromK();
    }

    public void setC(double tC) {
        super.setC(tC);
        setJfromK();
    }

    public void setF(double tF) {
        super.setF(tF);
        setJfromK();
    }

    public void setEnergyJ(double eJ) {
        energyJ = eJ;
        setKfromJ();
    }

    public void setEnergyEV(double eEV) {
        setEnergyJ(eEV * e);
    }

    public void setEnergyErg(double eErg) {
        setEnergyJ(eErg * 1e-7);
    }

    public void setLambdaTm(double lambda) {
        setK(lambdaTm / lambda);
    }

    public void setLambdaTum(double lambda) {
        setK(lambdaTum / lambda);
    }

    public void setFrequency(double hz) {
        setEnergyJ(hz * h);
    }

    public void setLambdaEm(double lambda) {
        setEnergyJ(h * c / lambda);
    }

    public void setLambdaEum(double lambda) {
        setLambdaEm(lambda / 1e6);
    }

    public double getEnergyJ() {
        return energyJ;
    }

    public double getEnergyEV() {
        return energyJ / e;
    }

    public double getEnergyErg() {
        return energyJ / 1e-7;
    }

    public double getLambdaTm() {
        return lambdaTm / temperatureK;
    }

    public double getLambdaTum() {
        return lambdaTum / temperatureK;
    }

    public double getFrequency() {
        return energyJ / h;
    }

    public double getLambdaEm() {
        return h * c / energyJ;
    }

    public double getLambdaEum() {
        return getLambdaEm() * 1e6;
    }
}
