package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.Unit;

/**
 * Класс преобразования температуры в частоту (Гц) фотона с энергией равной тепловой энергии
 * Created by Mike on 27.01.2017.
 */
public class ConvertFrequencyEHz extends ConvertAbstract {
    public ConvertFrequencyEHz() {
        description = "Частота излучения,      Гц";
        unit = Unit.FREQUENCY_E_HZ;
    }

    @Override
    public double toK(double value) {
        return Functions.to_K_from_FrequencyEHz(value);
    }

    @Override
    public double fromK(double k) {
        return Functions.to_FrequencyEHz_from_K(k);
    }

}