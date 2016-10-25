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
        if (menuLevel == M1 || menuLevel == M3_2) {
            menuLevel = M0;
            refresh = true;
        } else if (menuLevel == M39) {
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
                    menuLevel = M1;
                    break;
                case 2:
                    menuLevel = M2;
                    break;
                case 3:
                    menuLevel = M3;
                    break;
                default:
                    refresh = true;
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

    private void printMenu1() {
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

    private void printMenu2() {
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
                    menuLevel = M21;
                } else {
                    menuLevel = M22;
                }
            } else {
                refresh = true;
            }
        }
    }

    private void printMenu21() {
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
                    menuLevel = M2;
                    break;
                default:
                    refresh = true;
            }
        }
    }

    private void printMenu22() {
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
                    menuLevel = M2;
                    break;
                default:
                    refresh = true;
            }
        }
    }

    private void caseMenu3(int value) {
        if (moneyBox.isAvailable(value)) {
            this.value = value;
            menuLevel = M3_0;
        } else {
            menuLevel = M3_1;
            refresh = true;
        }
    }

    private void printMenu3() {
        clear();
        System.out.println("СНЯТИЕ НАЛИЧНЫХ");
        if (moneyBox.isAvailable(50)) {
            System.out.println("1: Снять   50 рублей");
        }
        if (moneyBox.isAvailable(100)) {
            System.out.println("2: Снять  100 рублей");
        }
        if (moneyBox.isAvailable(200)) {
            System.out.println("3: Снять  200 рублей");
        }
        if (moneyBox.isAvailable(500)) {
            System.out.println("4: Снять  500 рублей");
        }
        if (moneyBox.isAvailable(1000)) {
            System.out.println("5: Снять 1000 рублей");
        }
        if (moneyBox.isAvailable(2000)) {
            System.out.println("6: Снять 2000 рублей");
        }
        if (moneyBox.isAvailable(3000)) {
            System.out.println("7: Снять 3000 рублей");
        }
        if (moneyBox.isAvailable(5000)) {
            System.out.println("8: Снять 5000 рублей");
        }
        System.out.println("9: Выбрать другую сумму");
        System.out.println("0: ВЫХОД В ОСНОВНОЕ МЕНЮ");
        System.out.println("Введите номер меню и нажмите [Enter]");

        readChoice();
        if (!refresh) {
            switch (choice) {
                case 0:
                    menuLevel = M0;
                    break;
                case 1:
                    caseMenu3(50);
                    break;
                case 2:
                    caseMenu3(100);
                    break;
                case 3:
                    caseMenu3(200);
                    break;
                case 4:
                    caseMenu3(500);
                    break;
                case 5:
                    caseMenu3(1000);
                    break;
                case 6:
                    caseMenu3(2000);
                    break;
                case 7:
                    caseMenu3(3000);
                    break;
                case 8:
                    caseMenu3(5000);
                    break;
                case 9:
                    menuLevel = M39;
                    break;
                default:
                    refresh = true;
            }
        }
    }

    private void printMenu39() {
        clear();
        System.out.println("СНЯТИЕ НАЛИЧНЫХ");
        System.out.println("Введите сумму и нажмите [Enter]");
        System.out.print("Необходимая сумма:");

        readChoice();
        if (!refresh) {
            caseMenu3(value);
        }
    }

    private void printMenu3_0() {
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
                    menuLevel = M3_2;
                } else {
                    menuLevel = M3_1;
                }
            } else {
                refresh = true;
            }
        }
    }

    private void printMenu3_1() {
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
                menuLevel = M3;
            } else {
                refresh = true;
            }
        }
    }

    private void printMenu3_2() {
        clear();
        System.out.println("СНЯТИЕ НАЛИЧНЫХ");
        System.out.printf("К выдаче подготовлено %d рублей%n", value);

        int[] setForRemove = moneyBox.getSetOfBanknotes();

        for (int i = 0; i < nominals.length; i++) {
            if (setForRemove[i] > 0) {
                System.out.printf("Возьмите %d %s по %3d рублей%n", setForRemove[i], printBanknotes2(setForRemove[i]), nominals[i].getValue());
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
                case M1:
                    printMenu1();
                    break;
                case M2:
                    printMenu2();
                    break;
                case M21:
                    printMenu21();
                    break;
                case M22:
                    printMenu22();
                    break;
                case M3:
                    printMenu3();
                    break;
                case M3_0:
                    printMenu3_0();
                    break;
                case M39:
                    printMenu39();
                    break;
                case M3_1:
                    printMenu3_1();
                    break;
                case M3_2:
                    printMenu3_2();
                    break;
            }
        }
    }
}
