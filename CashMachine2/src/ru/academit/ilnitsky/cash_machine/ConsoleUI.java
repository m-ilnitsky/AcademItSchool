package ru.academit.ilnitsky.cash_machine;

import java.util.Scanner;

import static ru.academit.ilnitsky.cash_machine.MenuLevel.*;
import static ru.academit.ilnitsky.cash_machine.RubleBanknote.*;

/**
 * Created by Mike on 23.10.2016.
 * Класс "Консольный интерфейс банкомата"
 */
public class ConsoleUI {
    private ATM atm;
    private MenuLevel menuLevel;
    private Scanner scanner;
    private int value = 0;
    private int choice = 0;
    private boolean exit = false;
    private boolean refresh = false;

    public ConsoleUI() {
        atm = new ATM();
        menuLevel = M0;
        exit = false;
        value = 0;
        choice = 0;
        scanner = new Scanner(System.in);
    }

    private void readChoice() {
        String line = scanner.nextLine();
        if (menuLevel == M1 || menuLevel == M3_3) {
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
        System.out.println("4: ВЫХОД");
        System.out.println("Введите номер меню и нажмите [Enter]");

        readChoice();
        if (!refresh) {
            switch (choice) {
                case 4:
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

    private void printMenu1() {
        clear();
        System.out.println("БАЛАНС");
        System.out.printf("Ваш текущий баланс: %d руб.%n", atm.getBalance());
        System.out.println("Чтоб выйти в основное меню нажмите [Enter]");

        readChoice();
    }

    private void printMenu2() {
        clear();
        System.out.println("ПРИЁМ НАЛИЧНЫХ");
        System.out.println("1: Пополнить счёт на   50 рублей");
        System.out.println("2: Пополнить счёт на  100 рублей");
        System.out.println("3: Пополнить счёт на  500 рублей");
        System.out.println("4: Пополнить счёт на 1000 рублей");
        System.out.println("5: Пополнить счёт на 5000 рублей");
        System.out.println("0: ВЫХОД В ОСНОВНОЕ МЕНЮ");
        System.out.println("Введите номер меню и нажмите [Enter]");

        readChoice();
        if (!refresh) {
            switch (choice) {
                case 0:
                    menuLevel = M0;
                    break;
                case 1:
                    if (atm.isUnoccupiedSpace(R50)) {
                        value = 50;
                        menuLevel = M21;
                        atm.addMoney(R50);
                    } else {
                        menuLevel = M22;
                    }
                    break;
                case 2:
                    if (atm.isUnoccupiedSpace(R100)) {
                        value = 100;
                        menuLevel = M21;
                        atm.addMoney(R100);
                    } else {
                        menuLevel = M22;
                    }
                    break;
                case 3:
                    if (atm.isUnoccupiedSpace(R500)) {
                        value = 500;
                        menuLevel = M21;
                        atm.addMoney(R500);
                    } else {
                        menuLevel = M22;
                    }
                    break;
                case 4:
                    if (atm.isUnoccupiedSpace(R1000)) {
                        value = 1000;
                        menuLevel = M21;
                        atm.addMoney(R1000);
                    } else {
                        menuLevel = M22;
                    }
                    break;
                case 5:
                    if (atm.isUnoccupiedSpace(R5000)) {
                        value = 5000;
                        menuLevel = M21;
                        atm.addMoney(R5000);
                    } else {
                        menuLevel = M22;
                    }
                    break;
                default:
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
        if (atm.isAvailable(value)) {
            if (value > atm.getBalance()) {
                menuLevel = M3_2;
            } else {
                this.value = value;
                menuLevel = M3_0;
            }
        } else {
            menuLevel = M3_1;
            refresh = true;
        }
    }

    private void printMenu3() {
        clear();
        System.out.println("СНЯТИЕ НАЛИЧНЫХ");
        if (atm.isAvailable(50)) {
            System.out.println("1: Снять   50 рублей");
        }
        if (atm.isAvailable(100)) {
            System.out.println("2: Снять  100 рублей");
        }
        if (atm.isAvailable(200)) {
            System.out.println("3: Снять  200 рублей");
        }
        if (atm.isAvailable(500)) {
            System.out.println("4: Снять  500 рублей");
        }
        if (atm.isAvailable(1000)) {
            System.out.println("5: Снять 1000 рублей");
        }
        if (atm.isAvailable(2000)) {
            System.out.println("6: Снять 2000 рублей");
        }
        if (atm.isAvailable(3000)) {
            System.out.println("7: Снять 3000 рублей");
        }
        if (atm.isAvailable(5000)) {
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
        if (value >= 50 && atm.isAvailable(R50)) {
            System.out.println("1: Снять по возможности купюрами по   50 рублей");
        }
        if (value >= 100 && atm.isAvailable(R100)) {
            System.out.println("2: Снять по возможности купюрами по  100 рублей");
        }
        if (value >= 500 && atm.isAvailable(R500)) {
            System.out.println("3: Снять по возможности купюрами по  500 рублей");
        }
        if (value >= 1000 && atm.isAvailable(R1000)) {
            System.out.println("4: Снять по возможности купюрами по 1000 рублей");
        }
        if (value >= 5000 && atm.isAvailable(R5000)) {
            System.out.println("5: Снять по возможности купюрами по 5000 рублей");
        }
        System.out.println("0: ОТМЕНА И ВЫХОД В ОСНОВНОЕ МЕНЮ");
        System.out.println("Введите номер меню и нажмите [Enter]");

        readChoice();
        if (!refresh) {
            RubleBanknote priorityBanknote;
            switch (choice) {
                case 0:
                    priorityBanknote = R5000;
                    menuLevel = M0;
                    break;
                case 1:
                    priorityBanknote = R50;
                    break;
                case 2:
                    priorityBanknote = R100;
                    break;
                case 3:
                    priorityBanknote = R500;
                    break;
                case 4:
                    priorityBanknote = R1000;
                    break;
                case 5:
                    priorityBanknote = R5000;
                    break;
                default:
                    priorityBanknote = R5000;
                    refresh = true;
            }
            if (choice > 0 && choice < 6) {
                atm.removeMoneyWithPriority(value, priorityBanknote);
                menuLevel = M3_3;
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
        System.out.println("Выбранная сумма превышает лимит средств на вашем счёте");
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

    private void printMenu3_3() {
        clear();
        System.out.println("СНЯТИЕ НАЛИЧНЫХ");
        System.out.printf("К выдаче подготовлено %d рублей и нажмите [Enter]%n", value);

        RubleBanknote[] set = atm.getSetOfNominal();
        int[] setForRemove = atm.getSetOfBanknotesForRemove();


        for (int i = 0; i < set.length; i++) {
            if (setForRemove[i] > 0) {
                System.out.printf("Возьмите %d банкнот по %3d рублей [Enter]%n", setForRemove[i], set[i].getValue());
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
                case M3_3:
                    printMenu3_3();
                    break;
            }
        }
    }
}
