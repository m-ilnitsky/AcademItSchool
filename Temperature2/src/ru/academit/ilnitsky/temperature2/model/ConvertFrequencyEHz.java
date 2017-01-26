package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.TemperatureConverter;

/**
 * Класс преобразования температуры в частоту (Гц) фотона с энергией равной тепловой энергии
 * Created by Mike on 27.01.2017.
 */
public class ConvertFrequencyEHz implements TemperatureConverter {
    protected String description = "Частота излучения,      Гц";

    @Override
    public double toK(double value) {
        return ConvertFunctions.to_K_from_FrequencyEHz(value);
    }

    @Override
    public double fromK(double k) {
        return ConvertFunctions.to_FrequencyEHz_from_K(k);
    }

    @Override
    public String toString() {
        return description;
    }
}