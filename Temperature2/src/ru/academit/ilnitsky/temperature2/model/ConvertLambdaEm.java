package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.TemperatureConverter;

/**
 * Класс преобразования температуры в длину волны (м) фотона с энергией равной тепловой энергии
 * Created by Mike on 27.01.2017.
 */
public class ConvertLambdaEm implements TemperatureConverter {
    protected String description = "Длина волны излучения,   м";

    @Override
    public double toK(double value) {
        return ConvertFunctions.to_K_from_LambdaEm(value);
    }

    @Override
    public double fromK(double k) {
        return ConvertFunctions.to_LambdaEm_from_K(k);
    }

    @Override
    public String toString() {
        return description;
    }
}