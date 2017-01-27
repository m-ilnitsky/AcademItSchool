package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.Unit;

/**
 * Класс преобразования температуры в длину волны (мкм)
 * максимума интенсивности излучения абсолютно чёрного тела для данной температуры
 * Created by Mike on 27.01.2017.
 */
public class ConvertWavelengthTum extends ConvertAbstract {
    public ConvertWavelengthTum() {
        description = "Длина волны макисмума интенсивности, мкм";
        unit = Unit.WAVELENGTH_T_UM;
    }

    @Override
    public double toK(double value) {
        return Functions.to_K_from_WavelengthTum(value);
    }

    @Override
    public double fromK(double k) {
        return Functions.to_WavelengthTum_from_K(k);
    }

}