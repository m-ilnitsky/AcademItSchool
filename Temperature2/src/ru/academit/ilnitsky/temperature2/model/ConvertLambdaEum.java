package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.TemperatureConverter;

/**
 * Класс преобразования температуры в длину волны (мкм) фотона с энергией равной тепловой энергии
 * Created by Mike on 27.01.2017.
 */
public class ConvertLambdaEum implements TemperatureConverter {
    protected String description = "Длина волны излучения, мкм";

    @Override
    public double toK(double value) {
        return ConvertFunctions.to_K_from_LambdaEum(value);
    }

    @Override
    public double fromK(double k) {
        return ConvertFunctions.to_LambdaEum_from_K(k);
    }

    @Override
    public String toString() {
        return description;
    }
}