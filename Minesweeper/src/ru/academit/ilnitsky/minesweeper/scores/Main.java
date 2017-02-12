package ru.academit.ilnitsky.minesweeper.scores;

import ru.academit.ilnitsky.minesweeper.common.GameInfo;
import ru.academit.ilnitsky.minesweeper.common.GameSize;

/**
 * Тест класса TopScores
 * Created by Mike on 12.02.2017.
 */
public class Main {

    public static void main(String[] args) {

        GameSize gameSize = new GameSize(16, 16, 40);

        String gameName = "Любитель (средний размер 16х16 ячеек, 40 мин)";

        TopScores topScores = new TopScores(gameSize, 30, gameName);

        topScores.addResult(new GameInfo(gameSize, 48, 117, "TestFunction"));
        topScores.addResult(new GameInfo(gameSize, 44, 115, "TestFunction"));
        topScores.addResult(new GameInfo(gameSize, 33, 102, "TestFunction"));
        topScores.addResult(new GameInfo(gameSize, 37, 102, "TestFunction"));
        topScores.addResult(new GameInfo(gameSize, 31, 102, "TestFunction"));
    }
}
