package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.TemperatureConverter;

/**
 * Класс преобразования температуры для K
 * Created by Mike on 27.01.2017.
 */
public class ConvertTemperatureK implements TemperatureConverter {
    protected static final String description = "Температура, К";

    @Override
    public double toK(double value) {
        return value;
    }

    @Override
    public double fromK(double k) {
        return k;
    }

    @Override
    public String toString() {
        return description;
    }
}
