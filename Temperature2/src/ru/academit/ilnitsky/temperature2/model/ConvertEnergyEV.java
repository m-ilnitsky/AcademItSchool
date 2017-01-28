package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.Unit;

/**
 * Класс преобразования энергии для эВ
 * Created by Mike on 27.01.2017.
 */
public class ConvertEnergyEV extends ConvertAbstract {
    public ConvertEnergyEV() {
        super("Энергия,    эВ", Unit.EV);
    }

    @Override
    public double toK(double value) {
        return Functions.to_K_from_eV(value);
    }

    @Override
    public double fromK(double k) {
        return Functions.to_eV_from_K(k);
    }

}
