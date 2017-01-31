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

    private int valueForRemove = 0;

    public Presenter(MoneyBox moneyBox, View view) {
        this.moneyBox = moneyBox;
        this.view = view;

        nominals = moneyBox.getNominals();
    }

    @Override
    public void onMenuChoice(MenuLevel currentMenuLevel, int choice) {
        MenuLevel nextMenuLevel = M0;

        switch (currentMenuLevel) {
            case M0://ОСНОВНОЕ МЕНЮ
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
            case M0_1://БАЛАНС
                nextMenuLevel = M0;
                view.showMenu(nextMenuLevel);

                break;
            case M0_2://ПРИЁМ НАЛИЧНЫХ
                if (choice == 0) {
                    nextMenuLevel = M0;
                    view.showMenu(nextMenuLevel);
                } else if (choice > 0 && choice <= nominals.length) {
                    int i = nominals.length - choice;
                    int value = nominals[i].getValue();
                    if (moneyBox.hasUnoccupiedSpace(nominals[i])) {
                        moneyBox.addMoney(nominals[i]);
                        nextMenuLevel = M0_2_1;
                    } else {
                        nextMenuLevel = M0_2_2;
                    }
                    view.showMenu(nextMenuLevel, value);
                }

                break;
            case M0_2_1://ПРИЁМ НАЛИЧНЫХ Счёт пополнен на %d рублей
            case M0_2_2://ПРИЁМ НАЛИЧНЫХ Извините, счёт не был пополнен
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
            case M0_3://СНЯТИЕ НАЛИЧНЫХ
                if (choice == 0) {
                    nextMenuLevel = M0;
                } else if (choice == 9) {
                    nextMenuLevel = M0_3_1;
                }
                view.showMenu(nextMenuLevel);

                break;
            case M0_3_1://СНЯТИЕ НАЛИЧНЫХ Введите сумму
                nextMenuLevel = M0;
                view.showMenu(nextMenuLevel);

                break;
            case M0_3_2://СНЯТИЕ НАЛИЧНЫХ Выберете приоритетные купюры
                if (choice == 0) {
                    nextMenuLevel = M0;
                    view.showMenu(nextMenuLevel);
                } else if (choice > 0 && choice <= nominals.length) {
                    RubleBanknote priorityBanknote = nominals[nominals.length - choice];
                    if (moneyBox.isAvailable(valueForRemove, priorityBanknote)) {
                        moneyBox.removeMoney(valueForRemove, priorityBanknote);
                        int[] setForRemove = moneyBox.getSetOfBanknotes();
                        nextMenuLevel = M0_3_2_2;
                        view.showMenu(nextMenuLevel, valueForRemove, setForRemove);
                    } else {
                        nextMenuLevel = M0_3_2_1;
                        view.showMenu(nextMenuLevel);
                    }
                }

                break;
            case M0_3_2_1://СНЯТИЕ НАЛИЧНЫХ Выбранная сумма не может быть выдана имеющимися купюрами
                if (choice == 0) {
                    nextMenuLevel = M0;
                } else if (choice == 1) {
                    nextMenuLevel = M0_3;
                }
                view.showMenu(nextMenuLevel);

                break;
            case M0_3_2_2://СНЯТИЕ НАЛИЧНЫХ К выдаче подготовлено %d рублей
                nextMenuLevel = M0;
                view.showMenu(nextMenuLevel);
        }
    }

    @Override
    public void onInputValue(int value) {
        MenuLevel nextMenuLevel;

        if (moneyBox.isAvailable(value)) {
            nextMenuLevel = M0_3_2;
            int[] numBanknotes = new int[nominals.length];
            for (int i = 0; i < nominals.length; i++) {
                numBanknotes[i] = moneyBox.getAvailableBanknote(nominals[i]);
            }
            valueForRemove = value;
            view.showMenu(nextMenuLevel, valueForRemove, numBanknotes);
        } else {
            nextMenuLevel = M0_3_2_1;
            view.showMenu(nextMenuLevel);
        }
    }

}
