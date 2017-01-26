package ru.academit.ilnitsky.temperature2.common;

/**
 * Интерфейс модели преобразования температуры
 * Created by Mike on 27.01.2017.
 */
public interface TemperatureConverter {
    double toK(double value);
    double fromK(double k);
}
