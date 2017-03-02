package ru.academit.ilnitsky.temperature2.model;

import ru.academit.ilnitsky.temperature2.common.ConvertUnit;
import ru.academit.ilnitsky.temperature2.common.UnitGroup;

/**
 * Класс модели конвертера температуры в тепловую энергию, частоту и длину волны излучения
 * Частота и длина волны излучения рассчитываются как для заданной энергии фотона,
 * так и для заданной температуры тела (в максимуме интенсивности излучения).
 * Created by Mike on 28.01.2017.
 */
public class TEW2Converter extends TemperatureConverter {
    public TEW2Converter() {
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
                        new UnitGroup("Параметры излучения в вакууме в максимуме интенсивности излучения для данной температуры", 6),
                        new UnitGroup("Параметры излучения в вакууме для данной энергии фотона", 9)
                }
        );
    }
}
