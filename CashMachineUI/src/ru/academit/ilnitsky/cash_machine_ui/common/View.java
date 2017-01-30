package ru.academit.ilnitsky.cash_machine_ui.common;

import ru.academit.ilnitsky.console_ui.MenuLevel;
import ru.academit.ilnitsky.moneybox.RubleBanknote;

/**
 * Интерфейс представления View
 * Created by Mike on 29.01.2017.
 */
public interface View extends AutoCloseable {
    void addViewListener(ViewListener listener);

    void removeViewListener(ViewListener listener);

    void startApplication();

    void showMenu(MenuLevel menuLevel);

    void showMenu(MenuLevel menuLevel, int value);

    void showMenu(MenuLevel menuLevel, int[] numBanknotes);

    void showMenu(MenuLevel menuLevel, int value, int[] numBanknotes);
}
