package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.Unit;

/**
 * Класс преобразования температуры для K
 * Created by Mike on 27.01.2017.
 */
public class ConvertTemperatureK extends ConvertAbstract {
    public ConvertTemperatureK() {
        description = "Температура, К";
        unit = Unit.K;
    }

    @Override
    public double toK(double value) {
        return value;
    }

    @Override
    public double fromK(double k) {
        return k;
    }

}
