package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.TemperatureConverter;

/**
 * Класс преобразования энергии для эВ
 * Created by Mike on 27.01.2017.
 */
public class ConvertEnergyEV implements TemperatureConverter {
    protected String description = "Энергия,    эВ";

    @Override
    public double toK(double value) {
        return ConvertFunctions.to_K_from_eV(value);
    }

    @Override
    public double fromK(double k) {
        return ConvertFunctions.to_eV_from_K(k);
    }

    @Override
    public String toString() {
        return description;
    }
}
