package ru.academit.ilnitsky.console_ui;

import ru.academit.ilnitsky.moneybox.MoneyBox;
import ru.academit.ilnitsky.moneybox.RubleBanknote;

import java.util.Scanner;

import static ru.academit.ilnitsky.console_ui.MenuLevel.*;

/**
 * Класс "Консольный интерфейс копилки"
 * Created by Mike on 23.10.2016.
 */
public class ConsoleUI {
    private final MoneyBox moneyBox;

    private final RubleBanknote[] nominals;

    private MenuLevel menuLevel;
    private Scanner scanner;
    private int value = 0;
    private int choice = 0;
    private boolean exit = false;
    private boolean refresh = false;

    public ConsoleUI(int containerSize, int numBanknotes) {
        moneyBox = new MoneyBox(containerSize, numBanknotes);

        nominals = moneyBox.getNominals();

        menuLevel = M0;
        exit = false;
        value = 0;
        choice = 0;

        scanner = new Scanner(System.in);
    }

    private void readChoice() {
        String line = scanner.nextLine();
        if (menuLevel == M0_1 || menuLevel == M0_3_2_2) {
            menuLevel = M0;
            refresh = true;
        } else if (menuLevel == M0_3_1) {
            try {
                value = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                value = 0;
                refresh = true;
            }
        } else if (line.isEmpty()) {
            refresh = true;
        } else if (line.length() == 1 && Character.isDigit(line.charAt(0))) {
            choice = Integer.parseInt(line);
        } else {
            refresh = true;
        }
    }

    private void clear() {
        refresh = false;
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    private void printMenu0() {
        clear();
        System.out.println("ОСНОВНОЕ МЕНЮ");
        System.out.println("1: Распечатать текущий баланс");
        System.out.println("2: Положить на счёт наличнымие");
        System.out.println("3: Снять наличные со счёта");
        System.out.println("0: ВЫХОД");
        System.out.println("Введите номер меню и нажмите [Enter]");

        readChoice();
        if (!refresh) {
            switch (choice) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    menuLevel = M0_1;
                    break;
                case 2:
                    menuLevel = M0_2;
                    break;
                case 3:
                    menuLevel = M0_3;
                    break;
            }
        }
    }

    private String printBanknotes(int numBanknotes) {
        int num = numBanknotes % 10;
        if (numBanknotes > 4 && numBanknotes < 21) {
            return "купюр";
        } else if (num == 1) {
            return "купюра";
        } else if (num > 1 && num < 5) {
            return "купюры";
        } else {
            return "купюр";
        }
    }

    private String printBanknotes2(int numBanknotes) {
        int num = numBanknotes % 10;
        if (numBanknotes > 4 && numBanknotes < 21) {
            return "купюр";
        } else if (num == 1) {
            return "купюру";
        } else if (num > 1 && num < 5) {
            return "купюры";
        } else {
            return "купюр";
        }
    }

    private void printMenu0_1() {
        clear();
        System.out.println("БАЛАНС");
        System.out.println("Имеется:");

        for (RubleBanknote nominal : nominals) {
            if (moneyBox.isAvailable(nominal)) {
                System.out.printf("%4d %-6s номиналом %4d рублей.%n", moneyBox.getAvailableBanknote(nominal), printBanknotes(moneyBox.getAvailableBanknote(nominal)), nominal.getValue());
            }
        }

        System.out.printf("Всего имеется: %d руб.%n", moneyBox.getAvailableMoney());
        System.out.println("Чтоб выйти в основное меню нажмите [Enter]");

        readChoice();
    }

    private void printMenu0_2() {
        clear();
        System.out.println("ПРИЁМ НАЛИЧНЫХ");

        for (int i = nominals.length - 1; i >= 0; i--) {
            if (moneyBox.hasUnoccupiedSpace(nominals[i])) {
                int j = nominals.length - i;
                System.out.printf("%d: Пополнить счёт на %4d рублей%n", j, nominals[i].getValue());
            }
        }

        System.out.println("0: ВЫХОД В ОСНОВНОЕ МЕНЮ");
        System.out.println("Введите номер меню и нажмите [Enter]");

        readChoice();
        if (!refresh) {

            if (choice == 0) {
                menuLevel = M0;
            } else if (choice > 0 && choice <= nominals.length) {
                int i = nominals.length - choice;
                if (moneyBox.hasUnoccupiedSpace(nominals[i])) {
                    value = nominals[i].getValue();
                    moneyBox.addMoney(nominals[i]);
                    menuLevel = M0_2_1;
                } else {
                    menuLevel = M0_2_2;
                }
            }
        }
    }

    private void printMenu0_2_1() {
        clear();
        System.out.println("ПРИЁМ НАЛИЧНЫХ");
        System.out.printf("Счёт пополнен на %d рублей%n", value);
        System.out.println("1: Продолжить пополнение счёта наличными");
        System.out.println("0: ВЫХОД В ОСНОВНОЕ МЕНЮ");
        System.out.println("Введите номер меню и нажмите [Enter]");

        readChoice();
        if (!refresh) {
            switch (choice) {
                case 0:
                    menuLevel = M0;
                    break;
                case 1:
                    menuLevel = M0_2;
                    break;
            }
        }
    }

    private void printMenu0_2_2() {
        clear();
        System.out.println("ПРИЁМ НАЛИЧНЫХ");
        System.out.println("Извините, счёт не был пополнен");
        System.out.printf("В данный момент, банкомат не может принимать купюры номиналом %d рублей%n", value);
        System.out.println("1: Продолжить пополнение счёта наличными другого номинла");
        System.out.println("0: ВЫХОД В ОСНОВНОЕ МЕНЮ");
        System.out.println("Введите номер меню и нажмите [Enter]");

        readChoice();
        if (!refresh) {
            switch (choice) {
                case 0:
                    menuLevel = M0;
                    break;
                case 1:
                    menuLevel = M0_2;
                    break;
            }
        }
    }

    private void caseMenu0_3(int value) {
        if (moneyBox.isAvailable(value)) {
            this.value = value;
            menuLevel = M0_3_2;
        } else {
            menuLevel = M0_3_2_1;
        }
    }

    private void printMenu0_3() {
        clear();
        System.out.println("СНЯТИЕ НАЛИЧНЫХ");

        int[] remove = {50, 100, 200, 500, 1000, 2000, 3000, 5000};

        for (int i = 0; i < remove.length; i++) {
            if (moneyBox.isAvailable(remove[i])) {
                System.out.printf("%d: Снять %4d рублей%n", i + 1, remove[i]);
            }
        }

        System.out.println("9: Выбрать другую сумму");
        System.out.println("0: ВЫХОД В ОСНОВНОЕ МЕНЮ");
        System.out.println("Введите номер меню и нажмите [Enter]");

        readChoice();
        if (!refresh) {
            if (choice == 0) {
                menuLevel = M0;
            } else if (choice > 0 && choice < 9) {
                caseMenu0_3(remove[choice - 1]);
            } else if (choice == 9) {
                menuLevel = M0_3_1;
            }
        }
    }

    private void printMenu0_3_1() {
        clear();
        System.out.println("СНЯТИЕ НАЛИЧНЫХ");
        System.out.println("Введите сумму и нажмите [Enter]");
        System.out.print("Необходимая сумма:");

        readChoice();
        if (!refresh) {
            caseMenu0_3(value);
        }
    }

    private void printMenu0_3_2() {
        clear();
        System.out.println("СНЯТИЕ НАЛИЧНЫХ");
        System.out.println("Выберете приоритетные купюры");

        for (int i = nominals.length - 1; i >= 0; i--) {
            if (value >= nominals[i].getValue() && moneyBox.isAvailable(nominals[i])) {
                int j = nominals.length - i;
                System.out.printf("%d: Снять по возможности купюрами по %4d рублей%n", j, nominals[i].getValue());
            }
        }

        System.out.println("0: ОТМЕНА И ВЫХОД В ОСНОВНОЕ МЕНЮ");
        System.out.println("Введите номер меню и нажмите [Enter]");

        readChoice();
        if (!refresh) {
            if (choice == 0) {
                menuLevel = M0;
            } else if (choice > 0 && choice <= nominals.length) {
                RubleBanknote priorityBanknote = nominals[nominals.length - choice];
                if (moneyBox.isAvailable(value, priorityBanknote)) {
                    moneyBox.removeMoney(value, priorityBanknote);
                    menuLevel = M0_3_2_2;
                } else {
                    menuLevel = M0_3_2_1;
                }
            }
        }
    }

    private void printMenu0_3_2_1() {
        clear();
        System.out.println("СНЯТИЕ НАЛИЧНЫХ");
        System.out.println("Выбранная сумма не может быть выдана имеющимися купюрами");
        System.out.println("1: Выход в меню снятия наличных");
        System.out.println("0: ВЫХОД В ОСНОВНОЕ МЕНЮ");
        System.out.println("Введите номер меню и нажмите [Enter]");

        readChoice();
        if (!refresh) {
            if (choice == 0) {
                menuLevel = M0;
            } else if (choice == 1) {
                menuLevel = M0_3;
            }
        }
    }

    private void printMenu0_3_2_2() {
        clear();
        System.out.println("СНЯТИЕ НАЛИЧНЫХ");
        System.out.printf("К выдаче подготовлено %d рублей%n", value);

        int[] setForRemove = moneyBox.getSetOfBanknotes();

        for (int i = 0; i < nominals.length; i++) {
            if (setForRemove[i] > 0) {
                System.out.printf("Возьмите %d %s номиналом %3d рублей%n", setForRemove[i], printBanknotes2(setForRemove[i]), nominals[i].getValue());
            }
        }

        System.out.println("Заберите деньги и нажмите [Enter]");
        readChoice();
    }

    public void menu() {
        while (!exit) {
            switch (menuLevel) {
                case M0:
                    printMenu0();
                    break;
                case M0_1:
                    printMenu0_1();
                    break;
                case M0_2:
                    printMenu0_2();
                    break;
                case M0_2_1:
                    printMenu0_2_1();
                    break;
                case M0_2_2:
                    printMenu0_2_2();
                    break;
                case M0_3:
                    printMenu0_3();
                    break;
                case M0_3_1:
                    printMenu0_3_1();
                    break;
                case M0_3_2:
                    printMenu0_3_2();
                    break;
                case M0_3_2_1:
                    printMenu0_3_2_1();
                    break;
                case M0_3_2_2:
                    printMenu0_3_2_2();
                    break;
            }
        }
    }
}
