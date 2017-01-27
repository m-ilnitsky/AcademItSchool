package ru.academit.ilnitsky.temperature2.common;

/**
 * Интерфейс представления View
 * Содержит методы для управления слушателями ViewListener.
 * акже содержит методы, которые будут вызываться контроллером.
 */
public interface View extends AutoCloseable {
    /**
     * Запуск View
     */
    void startApplication();

    /**
     * Метод вызывается, когда контроллер переведет температуру
     */
    void onTemperatureConverted(double convertedTemperature);

    /**
     * Добавление ViewListener'а
     */
    void addViewListener(ViewListener listener);

    /**
     * Удаление ViewListener'а
     */
    void removeViewListener(ViewListener listener);
}
