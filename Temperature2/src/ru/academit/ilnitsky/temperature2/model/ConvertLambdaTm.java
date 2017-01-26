package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.TemperatureConverter;

/**
 * Класс преобразования температуры в длину волны (м)
 * максимума интенсивности излучения абсолютно чёрного тела для данной температуры
 * Created by Mike on 27.01.2017.
 */
public class ConvertLambdaTm implements TemperatureConverter {
    protected String description = "Длина волны макисмума интенсивности,   м";

    @Override
    public double toK(double value) {
        return ConvertFunctions.to_K_from_LambdaTm(value);
    }

    @Override
    public double fromK(double k) {
        return ConvertFunctions.to_LambdaTm_from_K(k);
    }

    @Override
    public String toString() {
        return description;
    }
}