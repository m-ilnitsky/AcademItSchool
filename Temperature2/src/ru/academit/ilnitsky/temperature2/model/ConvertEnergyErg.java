package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.TemperatureConverter;

/**
 * Класс преобразования энергии для Эрг
 * Created by Mike on 27.01.2017.
 */
public class ConvertEnergyErg implements TemperatureConverter {
    protected static final String description = "Энергия,   Эрг";

    @Override
    public double toK(double value) {
        return ConvertFunctions.to_K_from_Erg(value);
    }

    @Override
    public double fromK(double k) {
        return ConvertFunctions.to_Erg_from_K(k);
    }

    @Override
    public String toString() {
        return description;
    }
}
