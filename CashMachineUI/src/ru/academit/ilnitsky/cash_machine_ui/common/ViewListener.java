package ru.academit.ilnitsky.cash_machine_ui.common;

import ru.academit.ilnitsky.console_ui.MenuLevel;

/**
 * Интерфейс подписчика на события View
 * Created by Mike on 29.01.2017.
 */
public interface ViewListener {
    void onMenuChoice(MenuLevel currentMenuLevel, int choice);

    void onInputValue(MenuLevel currentMenuLevel, int choice, int value);
}
