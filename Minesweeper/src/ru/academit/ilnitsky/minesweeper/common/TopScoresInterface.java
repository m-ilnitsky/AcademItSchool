package ru.academit.ilnitsky.minesweeper.common;

/**
 * Интерфейс к лучшим результатам игры "Сапёр"
 * Created by Mike on 12.02.2017.
 */
public interface TopScoresInterface {

    void addResult(GameInfo gameInfo);

    GameInfo[] getTopScores();

    String getTopScoresName();

    int getXSize();

    int getYSize();

    int getNumMines();
}
