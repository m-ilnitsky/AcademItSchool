package ru.academit.ilnitsky.temperature2.controller;

import ru.academit.ilnitsky.temperature2.common.Unit;
import ru.academit.ilnitsky.temperature2.common.UnitConverter;
import ru.academit.ilnitsky.temperature2.common.View;
import ru.academit.ilnitsky.temperature2.common.ViewListener;

/**
 * Презентер
 * Created by UserLabView on 27.01.17.
 */
public class Controller implements ViewListener {
    private UnitConverter model;
    private View view;

    public Controller(UnitConverter model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void needConvertValue(double value, Unit unit) {
        model.set(value, unit);
        view.onValueConverted(model.getAll());
    }
}
