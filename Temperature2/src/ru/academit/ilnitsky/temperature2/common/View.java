package ru.academit.ilnitsky.temperature2.common;

/**
 * Интерфейс представления View
 * Содержит методы для управления слушателями ViewListener.
 * акже содержит методы, которые будут вызываться контроллером.
 */
public interface View extends AutoCloseable {
    void addViewListener(ViewListener listener);

    void removeViewListener(ViewListener listener);

    void startApplication();

    void onValueConverted(double[] results);
}
