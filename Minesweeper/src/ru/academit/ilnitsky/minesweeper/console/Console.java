package ru.academit.ilnitsky.minesweeper.console;

import ru.academit.ilnitsky.minesweeper.controller.Controller;
import ru.academit.ilnitsky.minesweeper.core.MinesweeperCore;

/**
 * Консольная программа "Сапёр"
 * Created by Mike on 06.02.2017.
 */
public class Console {
    public static void main(String[] args) {

        MinesweeperCore minesweeperCore = new MinesweeperCore();
        ConsoleView consoleView = new ConsoleView();

        Controller controller = new Controller(minesweeperCore, consoleView);

        consoleView.addViewListener(controller);
        consoleView.startApplication();
    }
}
