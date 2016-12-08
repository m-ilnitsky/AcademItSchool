package ru.academit.ilnitsky.temperature;

/**
 * Класс для преобразования энегрии и температуры в другие величины
 * Created by UserLabView on 08.12.16.
 */
public class EnergyConverter extends TemperatureConverter {
    public static final double k = 1.38064852e-23;

    private double energyJ = 0;

    public void setK(double tK) {
        super.setK(tK);
        energyJ = temperatureK * k;
    }

    public void setC(double tC) {
        super.setC(tC);
        energyJ = temperatureK * k;
    }

    public void setF(double tF) {
        super.setF(tF);
        energyJ = temperatureK * k;
    }

    public void setJ(double eJ) {
        energyJ = eJ;
        temperatureK = eJ / k;
    }
}
