package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.TemperatureConverter;

/**
 * Класс преобразования энергии для Дж
 * Created by Mike on 27.01.2017.
 */
public class ConvertEnergyJ implements TemperatureConverter {
    protected static final String description = "Энергия,    Дж";

    @Override
    public double toK(double value) {
        return ConvertFunctions.to_K_from_J(value);
    }

    @Override
    public double fromK(double k) {
        return ConvertFunctions.to_J_from_K(k);
    }

    @Override
    public String toString() {
        return description;
    }
}
