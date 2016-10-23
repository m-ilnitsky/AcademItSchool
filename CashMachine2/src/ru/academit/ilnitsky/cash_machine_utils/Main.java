package ru.academit.ilnitsky.cash_machine_utils;

import ru.academit.ilnitsky.cash_machine.ConsoleUI;

/**
 * Created by Mike on 23.10.2016.
 * Класс для тестирования класса ConsoleUI
 */
public class Main {
    public static void main(String[] args) {
        ConsoleUI consoleATM = new ConsoleUI();

        consoleATM.menu();
    }
}
