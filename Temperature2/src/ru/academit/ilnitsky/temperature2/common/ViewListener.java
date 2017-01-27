package ru.academit.ilnitsky.temperature2.common;

/**
 * Интерфейс подписчика на события View
 * Этот интерфейс реализует контроллер, чтобы View могла оповещать controller о возникновении событий
 */
public interface ViewListener {
    // событие, которое означает, что view хочет преобразовать температуру
    void needConvert(double value, Unit unit);
}
