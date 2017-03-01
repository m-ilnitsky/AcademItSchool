package ru.academit.ilnitsky.minesweeper;

import ru.academit.ilnitsky.minesweeper.common.GameSize;
import ru.academit.ilnitsky.minesweeper.common.ViewAutoCloseable;
import ru.academit.ilnitsky.minesweeper.controller.Controller;
import ru.academit.ilnitsky.minesweeper.core.MinesweeperCore;
import ru.academit.ilnitsky.minesweeper.gui.FrameView;

/**
 * Программа "Сапёр" с графическим интерфейсом
 * Created by UserLabView on 21.02.17.
 */
public class Application {
    public static void main(String[] args) {

        final GameSize[] standardGameSizes = {
                new GameSize(9, 9, 10),
                new GameSize(16, 16, 40),
                new GameSize(30, 16, 99),
                new GameSize(40, 20, 99)
        };

        final String[] standardGameNames = {
                "Новичок (малый размер 9х9 ячеек, 10 мин)",
                "Любитель (средний размер 16х16 ячеек, 40 мин)",
                "Профессионал (большой размер 16х30 ячеек, 99 мин)",
                "Маньяк (очень большой размер 20х40 ячеек, 99 мин)"
        };

        try (ViewAutoCloseable view = new FrameView(30, standardGameSizes, standardGameNames)) {
            MinesweeperCore minesweeperCore = new MinesweeperCore();

            Controller controller = new Controller(minesweeperCore, view);

            view.addViewListener(controller);
            view.startApplication();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
