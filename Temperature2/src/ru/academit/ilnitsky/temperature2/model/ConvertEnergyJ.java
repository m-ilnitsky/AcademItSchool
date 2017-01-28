package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.Unit;

/**
 * Класс преобразования энергии для Дж
 * Created by Mike on 27.01.2017.
 */
public class ConvertEnergyJ extends ConvertAbstract {
    public ConvertEnergyJ() {
        super("Энергия,    Дж", Unit.J);
    }

    @Override
    public double toK(double value) {
        return Functions.to_K_from_J(value);
    }

    @Override
    public double fromK(double k) {
        return Functions.to_J_from_K(k);
    }

}
