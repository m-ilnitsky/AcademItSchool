package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.TemperatureConverter;

/**
 * Класс преобразования температуры для F
 * Created by Mike on 27.01.2017.
 */
public class ConvertTemperatureF implements TemperatureConverter {
    protected static final String description = "Температура, F";

    @Override
    public double toK(double value) {
        return ConvertFunctions.to_K_from_F(value);
    }

    @Override
    public double fromK(double k) {
        return ConvertFunctions.to_F_from_K(k);
    }

    @Override
    public String toString() {
        return description;
    }
}
