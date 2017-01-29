package ru.academit.ilnitsky.common;

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

    void showMenu(MenuLevel menuLevel, RubleBanknote[] rubleBanknotes, int[] numberBanknotes);

    void showMenu(MenuLevel menuLevel, int value, RubleBanknote[] rubleBanknotes, int[] numberBanknotes);
}
