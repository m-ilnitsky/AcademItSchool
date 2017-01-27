package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.Unit;

/**
 * Класс преобразования температуры для F
 * Created by Mike on 27.01.2017.
 */
public class ConvertTemperatureF extends ConvertAbstract {
    public ConvertTemperatureF() {
        description = "Температура, F";
        unit = Unit.F;
    }

    @Override
    public double toK(double value) {
        return Functions.to_K_from_F(value);
    }

    @Override
    public double fromK(double k) {
        return Functions.to_F_from_K(k);
    }

}
