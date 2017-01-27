package ru.academit.ilnitsky.temperature2.common;

/**
 * Интерфейс модели конвертера
 * Created by UserLabView on 27.01.17.
 */
public interface UnitConverter {
    void set(double value, Unit unit);

    double get(Unit unit);

    double[] getAll();

    Unit[] getUnits();

    int getSize();
}
