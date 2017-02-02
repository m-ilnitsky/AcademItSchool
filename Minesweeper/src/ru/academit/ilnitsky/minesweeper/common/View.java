package ru.academit.ilnitsky.minesweeper.common;

/**
 * Интерфейс представления View для игры "Сапёр"
 * Created by UserLabView on 02.02.17.
 */
public interface View extends AutoCloseable {

    void addViewListener(ViewListener listener);

    void removeViewListener(ViewListener listener);

    void startApplication();
}
