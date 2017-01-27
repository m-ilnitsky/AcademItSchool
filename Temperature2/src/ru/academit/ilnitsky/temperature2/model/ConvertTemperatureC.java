package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.Unit;

/**
 * Класс преобразования температуры для C
 * Created by Mike on 27.01.2017.
 */
public class ConvertTemperatureC extends ConvertAbstract {
    public ConvertTemperatureC() {
        description = "Температура, C";
        unit = Unit.C;
    }

    @Override
    public double toK(double value) {
        return Functions.to_K_from_C(value);
    }

    @Override
    public double fromK(double k) {
        return Functions.to_C_from_K(k);
    }

}
