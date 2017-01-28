package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.Unit;

/**
 * Класс преобразования температуры в длину волны (м)
 * максимума интенсивности излучения абсолютно чёрного тела для данной температуры
 * Created by Mike on 27.01.2017.
 */
public class ConvertWavelengthTm extends ConvertAbstract {
    public ConvertWavelengthTm() {
        super("Длина волны макисмума интенсивности,   м", Unit.WAVELENGTH_T_M);
    }

    @Override
    public double toK(double value) {
        return Functions.to_K_from_WavelengthTm(value);
    }

    @Override
    public double fromK(double k) {
        return Functions.to_WavelengthTm_from_K(k);
    }

}