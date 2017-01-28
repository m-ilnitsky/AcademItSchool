package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.ConvertUnit;
import ru.academit.ilnitsky.temperature2.common.Unit;

/**
 * Абстарктный класс для перевода величин
 * Created by UserLabView on 27.01.17.
 */
public abstract class ConvertAbstract implements ConvertUnit {
    protected String description;
    protected Unit unit;

    protected ConvertAbstract(String description, Unit unit) {
        this.description = description;
        this.unit = unit;
    }

    @Override
    public Unit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return description;
    }
}
