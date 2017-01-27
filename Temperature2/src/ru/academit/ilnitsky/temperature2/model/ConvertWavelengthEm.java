package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.Unit;

/**
 * Класс преобразования температуры в длину волны (м) фотона с энергией равной тепловой энергии
 * Created by Mike on 27.01.2017.
 */
public class ConvertWavelengthEm extends ConvertAbstract {
    public ConvertWavelengthEm() {
        description = "Длина волны излучения,   м";
        unit = Unit.WAVELENGTH_E_M;
    }

    @Override
    public double toK(double value) {
        return Functions.to_K_from_WavelengthEm(value);
    }

    @Override
    public double fromK(double k) {
        return Functions.to_WavelengthEm_from_K(k);
    }

}