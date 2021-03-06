package ru.academit.ilnitsky.minesweeper.common;

import java.time.Instant;

/**
 * Интерфейс подписчика на события View для игры "Сапёр"
 * Created by UserLabView on 02.02.17.
 */
public interface ViewListener {

    GameBoard startGame(int xSize, int ySize, int numMines);

    void stopGame();

    void setQuery(int xPosition, int yPosition);

    void setFlag(int xPosition, int yPosition);

    void setOpen(int xPosition, int yPosition);

    void setOpenAllAround(int xPosition, int yPosition);

    int getNumActions();

    Instant getStartTime();

    long getGameTime();

    GameStatus getGameStatus();

    GameInfo getWinGameInfo();

    void saveWinGameInfo(GameInfo gameInfo);

    boolean isTopScores(GameSize gameSize);

    GameInfo[] getTopScores(GameSize gameSize);

    String getTopScoresName(GameSize gameSize);
}
