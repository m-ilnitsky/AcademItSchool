package ru.academit.ilnitsky.temperature2.presenter;

import ru.academit.ilnitsky.temperature2.common.ConvertUnit;
import ru.academit.ilnitsky.temperature2.common.UnitGroup;
import ru.academit.ilnitsky.temperature2.common.View;
import ru.academit.ilnitsky.temperature2.model.*;

/**
 * Презентер
 * Created by UserLabView on 27.01.17.
 */
public class Presenter {
    private UnitGroup[] groups;
    private TemperatureConverter model;
    private View view;

    public Presenter() {
        model = new TemperatureConverter(new ConvertUnit[]{
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
        });

        groups = new UnitGroup[]{
                new UnitGroup("Температура для данной энергии", 0),
                new UnitGroup("Энергия для данной температуры", 3),
                new UnitGroup("Параметры излучения в макисмуме интенсивности излучения для данной температуры", 6),
                new UnitGroup("Параметры излучения в вакууме для данной энергии фотона", 9)
        };
    }
}
