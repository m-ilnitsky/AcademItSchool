package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.ConvertUnit;
import ru.academit.ilnitsky.temperature2.common.Unit;
import ru.academit.ilnitsky.temperature2.common.UnitConverter;

import java.util.NoSuchElementException;

/**
 * Класс модели конвертера температуры
 * Created by UserLabView on 27.01.17.
 */
public class TemperatureConverter implements UnitConverter {
    private ConvertUnit[] converters;
    private double[] results;

    private int arraysLength;
    private double temperatureK;

    public TemperatureConverter(ConvertUnit[] converters) {
        this.converters = converters;

        temperatureK = 0;
        arraysLength = converters.length;

        results = new double[arraysLength];
        for (int i = 0; i < arraysLength; i++) {
            results[i] = converters[i].fromK(temperatureK);
        }
    }

    public void set(double value, Unit unit) {
        for (ConvertUnit c : converters) {
            if (c.getUnit() == unit) {
                temperatureK = c.toK(value);
                return;
            }
        }

        throw new NoSuchElementException("No Such Units: " + unit);
    }

    public double get(Unit unit) {
        for (ConvertUnit c : converters) {
            if (c.getUnit() == unit) {
                return c.fromK(temperatureK);
            }
        }

        throw new NoSuchElementException("No Such Units: " + unit);
    }

    public double[] getAll() {
        for (int i = 0; i < arraysLength; i++) {
            results[i] = converters[i].fromK(temperatureK);
        }

        return results;
    }

    public Unit[] getUnits() {
        Unit[] units = new Unit[arraysLength];

        for (int i = 0; i < arraysLength; i++) {
            units[i] = converters[i].getUnit();
        }

        return units;
    }

    public int getSize() {
        return arraysLength;
    }
}
