package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.ConvertUnit;
import ru.academit.ilnitsky.temperature2.common.UnitGroup;

/**
 * Класс модели конвертера температуры в тепловую энергию, частоту и длину волны излучения
 * Created by Mike on 28.01.2017.
 */
public class TEFWConverter extends TemperatureConverter {
    public TEFWConverter() {
        super(new ConvertUnit[]{
                        new ConvertTemperatureK(),
                        new ConvertTemperatureC(),
                        new ConvertTemperatureF(),

                        new ConvertEnergyJ(),
                        new ConvertEnergyErg(),
                        new ConvertEnergyEV(),

                        new ConvertFrequencyTHz(),
                        new ConvertWavelengthTm(),
                        new ConvertWavelengthTum(),

                        new ConvertFrequencyEHz(),
                        new ConvertWavelengthEm(),
                        new ConvertWavelengthEum()
                },
                new UnitGroup[]{
                        new UnitGroup("Температура для данной энергии", 0),
                        new UnitGroup("Энергия для данной температуры", 3),
                        new UnitGroup("Параметры излучения в макисмуме интенсивности излучения для данной температуры", 6),
                        new UnitGroup("Параметры излучения в вакууме для данной энергии фотона", 9)
                }
        );
    }
}
