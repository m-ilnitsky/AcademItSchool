package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.ConvertUnit;
import ru.academit.ilnitsky.temperature2.common.Unit;
import ru.academit.ilnitsky.temperature2.common.UnitConverter;
import ru.academit.ilnitsky.temperature2.common.UnitGroup;

import java.util.NoSuchElementException;

/**
 * Класс модели конвертера температуры в произвольные величины
 * Created by UserLabView on 27.01.17.
 */
public class TemperatureConverter implements UnitConverter {
    private UnitGroup[] groups;
    private ConvertUnit[] converters;
    private double[] results;

    private int arraysLength;
    private double temperatureK;

    public TemperatureConverter(ConvertUnit[] converters, UnitGroup[] groups) {
        this.converters = converters;
        this.groups = groups;

        temperatureK = 0;
        arraysLength = converters.length;

        results = new double[arraysLength];
        for (int i = 0; i < arraysLength; i++) {
            results[i] = converters[i].fromK(temperatureK);
        }
    }

    @Override
    public void set(double value, Unit unit) {
        for (ConvertUnit c : converters) {
            if (c.getUnit() == unit) {
                temperatureK = c.toK(value);
                return;
            }
        }

        throw new NoSuchElementException("No Such Units: " + unit);
    }

    @Override
    public double get(Unit unit) {
        for (ConvertUnit c : converters) {
            if (c.getUnit() == unit) {
                return c.fromK(temperatureK);
            }
        }

        throw new NoSuchElementException("No Such Units: " + unit);
    }

    @Override
    public double[] getAll() {
        for (int i = 0; i < arraysLength; i++) {
            results[i] = converters[i].fromK(temperatureK);
        }

        return results;
    }

    @Override
    public Unit[] getUnits() {
        Unit[] units = new Unit[arraysLength];

        for (int i = 0; i < arraysLength; i++) {
            units[i] = converters[i].getUnit();
        }

        return units;
    }

    @Override
    public ConvertUnit[] getConverters() {
        return converters;
    }

    @Override
    public UnitGroup[] getGroups() {
        return groups;
    }

    @Override
    public int getSize() {
        return arraysLength;
    }
}
