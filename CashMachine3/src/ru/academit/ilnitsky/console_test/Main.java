package ru.academit.ilnitsky.console_test;

import ru.academit.ilnitsky.console_ui.ConsoleUI;

/**
 * Класс для тестирования классов ConsoleUI и MoneyBox
 * Created by Mike on 23.10.2016.
 */
public class Main {
    public static void main(String[] args) {
        ConsoleUI consoleMoneyBox = new ConsoleUI(50, 10);

        consoleMoneyBox.menu();
    }
}
