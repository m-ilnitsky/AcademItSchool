package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.Unit;

/**
 * Класс преобразования энергии для Эрг
 * Created by Mike on 27.01.2017.
 */
public class ConvertEnergyErg extends ConvertAbstract {
    public ConvertEnergyErg() {
        description = "Энергия,   Эрг";
        unit = Unit.ERG;
    }

    @Override
    public double toK(double value) {
        return Functions.to_K_from_Erg(value);
    }

    @Override
    public double fromK(double k) {
        return Functions.to_Erg_from_K(k);
    }

}
