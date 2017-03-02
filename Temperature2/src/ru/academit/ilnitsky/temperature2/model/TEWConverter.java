package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.ConvertUnit;
import ru.academit.ilnitsky.temperature2.common.UnitGroup;

/**
 * Класс модели конвертера температуры в тепловую энергию, частоту и длину волны излучения.
 * Частота и длина волны излучения рассчитываются для заданной энергии фотона.
 * Created by UserLabView on 02.03.17.
 */
public class TEWConverter extends TemperatureConverter {
    public TEWConverter() {
        super(new ConvertUnit[]{
                        new ConvertTemperatureK(),
                        new ConvertTemperatureC(),
                        new ConvertTemperatureF(),

                        new ConvertEnergyJ(),
                        new ConvertEnergyErg(),
                        new ConvertEnergyEV(),

                        new ConvertFrequencyEHz(),
                        new ConvertWavelengthEm(),
                        new ConvertWavelengthEum()
                },
                new UnitGroup[]{
                        new UnitGroup("Температура для данной энергии", 0),
                        new UnitGroup("Энергия для данной температуры", 3),
                        new UnitGroup("Параметры излучения в вакууме для данной энергии фотона", 6)
                }
        );
    }
}
