package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.Unit;

/**
 * ласс преобразования температуры в частоту волны (Гц) в вакууме
 * максимума интенсивности излучения абсолютно чёрного тела для данной температуры
 * Created by UserLabView on 27.01.17.
 */
public class ConvertFrequencyTHz extends ConvertAbstract {
    public ConvertFrequencyTHz() {
        description = "Частота макисмума интенсивности,      Гц";
        unit = Unit.FREQUENCY_T_HZ;
    }

    @Override
    public double toK(double value) {
        return Functions.to_K_from_FrequencyTHz(value);
    }

    @Override
    public double fromK(double k) {
        return Functions.to_FrequencyTHz_from_K(k);
    }

}