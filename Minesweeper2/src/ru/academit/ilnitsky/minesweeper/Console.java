package ru.academit.ilnitsky.minesweeper;

import ru.academit.ilnitsky.minesweeper.common.GameSize;
import ru.academit.ilnitsky.minesweeper.common.View;
import ru.academit.ilnitsky.minesweeper.console.ConsoleView;
import ru.academit.ilnitsky.minesweeper.controller.Controller;
import ru.academit.ilnitsky.minesweeper.core.MinesweeperCore;

/**
 * Консольная программа "Сапёр"
 * Created by Mike on 06.02.2017.
 */
public class Console {
    public static void main(String[] args) {

        final GameSize[] standardGameSizes = {
                new GameSize(9, 9, 10),
                new GameSize(16, 16, 40),
                new GameSize(30, 16, 99)
        };

        final String[] standardGameNames = {
                "Новичок (малый размер 9х9 ячеек, 10 мин)",
                "Любитель (средний размер 16х16 ячеек, 40 мин)",
                "Профессионал (большой размер 16х30 ячеек, 99 мин)"
        };

        MinesweeperCore minesweeperCore = new MinesweeperCore();
        View view = new ConsoleView(30, standardGameSizes, standardGameNames);

        Controller controller = new Controller(minesweeperCore, view);

        view.addViewListener(controller);
        view.startApplication();
    }
}
