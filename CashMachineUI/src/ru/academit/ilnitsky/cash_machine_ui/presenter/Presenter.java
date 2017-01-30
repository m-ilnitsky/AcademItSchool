package ru.academit.ilnitsky.cash_machine_ui.presenter;

import ru.academit.ilnitsky.cash_machine_ui.common.View;
import ru.academit.ilnitsky.cash_machine_ui.common.ViewListener;
import ru.academit.ilnitsky.console_ui.MenuLevel;
import ru.academit.ilnitsky.moneybox.MoneyBox;
import ru.academit.ilnitsky.moneybox.RubleBanknote;

import static ru.academit.ilnitsky.console_ui.MenuLevel.*;

/**
 * Презентер
 * Created by Mike on 29.01.2017.
 */
public class Presenter implements ViewListener {
    private MoneyBox moneyBox;
    private View view;

    private RubleBanknote[] nominals;

    public Presenter(MoneyBox moneyBox, View view) {
        this.moneyBox = moneyBox;
        this.view = view;

        nominals = moneyBox.getNominals();
    }

    @Override
    public void onMenuChoice(MenuLevel currentMenuLevel, int choice) {
        MenuLevel nextMenuLevel = M0;
        int value = 0;

        switch (currentMenuLevel) {
            case M0:
                switch (choice) {
                    case 1:
                        nextMenuLevel = M0_1;
                        int[] numBanknotes = new int[nominals.length];
                        for (int i = 0; i < nominals.length; i++) {
                            numBanknotes[i] = moneyBox.getAvailableBanknote(nominals[i]);
                        }
                        int sumMoney = moneyBox.getAvailableMoney();
                        view.showMenu(nextMenuLevel, sumMoney, numBanknotes);
                        break;
                    case 2:
                        nextMenuLevel = M0_2;
                        view.showMenu(nextMenuLevel);
                        break;
                    case 3:
                        nextMenuLevel = M0_3;
                        view.showMenu(nextMenuLevel);
                        break;
                }

                break;
            case M0_1:
                nextMenuLevel = M0;
                view.showMenu(nextMenuLevel);

                break;
            case M0_2:
                if (choice == 0) {
                    nextMenuLevel = M0;
                    view.showMenu(nextMenuLevel);
                } else if (choice > 0 && choice <= nominals.length) {
                    int i = nominals.length - choice;
                    if (moneyBox.hasUnoccupiedSpace(nominals[i])) {
                        value = nominals[i].getValue();
                        moneyBox.addMoney(nominals[i]);
                        nextMenuLevel = M0_2_1;
                    } else {
                        nextMenuLevel = M0_2_2;
                    }
                    view.showMenu(nextMenuLevel, value);
                }

                break;
            case M0_2_1:
            case M0_2_2:
                switch (choice) {
                    case 0:
                        nextMenuLevel = M0;
                        break;
                    case 1:
                        nextMenuLevel = M0_2;
                        break;
                }
                view.showMenu(nextMenuLevel);

                break;
            case M0_3:
                if (choice == 0) {
                    nextMenuLevel = M0;
                    view.showMenu(nextMenuLevel);
                } else if (choice > 0 && choice < 9) {
                    value = nominals[choice - 1].getValue();
                    if (moneyBox.isAvailable(value)) {
                        nextMenuLevel = M0_3_2;
                        int[] numBanknotes = new int[nominals.length];
                        for (int i = 0; i < nominals.length; i++) {
                            numBanknotes[i] = moneyBox.getAvailableBanknote(nominals[i]);
                        }
                        view.showMenu(nextMenuLevel, value, numBanknotes);
                    } else {
                        nextMenuLevel = M0_3_2_1;
                        view.showMenu(nextMenuLevel);
                    }
                } else if (choice == 9) {
                    nextMenuLevel = M0_3_1;
                    view.showMenu(nextMenuLevel);
                }

                break;
            case M0_3_1:
                nextMenuLevel = M0;
                view.showMenu(nextMenuLevel);

                break;
            case M0_3_2:
                nextMenuLevel = M0;
                view.showMenu(nextMenuLevel);

                break;
            case M0_3_2_1:
                if (choice == 0) {
                    nextMenuLevel = M0;
                } else if (choice == 1) {
                    nextMenuLevel = M0_3;
                }
                view.showMenu(nextMenuLevel);

                break;
            case M0_3_2_2:
                nextMenuLevel = M0;
                view.showMenu(nextMenuLevel);
        }
    }

    @Override
    public void onInputValue(MenuLevel currentMenuLevel, int choice, int value) {
        MenuLevel nextMenuLevel;

        switch (currentMenuLevel) {
            case M0_3_1:
                if (moneyBox.isAvailable(value)) {
                    nextMenuLevel = M0_3_2;
                    int[] numBanknotes = new int[nominals.length];
                    for (int i = 0; i < nominals.length; i++) {
                        numBanknotes[i] = moneyBox.getAvailableBanknote(nominals[i]);
                    }
                    view.showMenu(nextMenuLevel, value, numBanknotes);
                } else {
                    nextMenuLevel = M0_3_2_1;
                    view.showMenu(nextMenuLevel);
                }

                break;
            case M0_3_2:
                if (choice == 0) {
                    nextMenuLevel = M0;
                    view.showMenu(nextMenuLevel);
                } else if (choice > 0 && choice <= nominals.length) {
                    RubleBanknote priorityBanknote = nominals[nominals.length - choice];
                    if (moneyBox.isAvailable(value, priorityBanknote)) {
                        moneyBox.removeMoney(value, priorityBanknote);
                        nextMenuLevel = M0_3_2_2;
                        int[] setForRemove = moneyBox.getSetOfBanknotes();
                        view.showMenu(nextMenuLevel, value, setForRemove);
                    } else {
                        nextMenuLevel = M0_3_2_1;
                        view.showMenu(nextMenuLevel);
                    }
                }
        }
    }
}
