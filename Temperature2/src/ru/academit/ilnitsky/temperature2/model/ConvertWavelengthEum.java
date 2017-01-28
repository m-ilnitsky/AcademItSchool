package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.Unit;

/**
 * Класс преобразования температуры в длину волны (мкм) фотона с энергией равной тепловой энергии
 * Created by Mike on 27.01.2017.
 */
public class ConvertWavelengthEum extends ConvertAbstract {
    public ConvertWavelengthEum() {
        super("Длина волны излучения, мкм", Unit.WAVELENGTH_E_UM);
    }

    @Override
    public double toK(double value) {
        return Functions.to_K_from_WavelengthEum(value);
    }

    @Override
    public double fromK(double k) {
        return Functions.to_WavelengthEum_from_K(k);
    }

}