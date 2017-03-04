package ru.academit.ilnitsky.minesweeper.common;

import java.time.Instant;

/**
 * Интерфейс модели игры "Сапёр"
 * Created by Mike on 02.02.2017.
 */
public interface MinesweeperCoreInterface {

    void startGame(int xSize, int ySize, int numMines);

    void stopGame();

    int getGameNumMines();

    int getNumActions();

    Instant getStartTime();

    long getGameTime();

    long getWinGameTime();

    GameInfo getWinGameInfo();

    GameStatus getGameStatus();

    GameBoardSize getGameBoardSize();

    GameBoard linkToGameBoard();

    int getMinSize();

    int getMaxSize();

    double getNumMinesCoefficient();

    void setQuery(int xPosition, int yPosition);

    void setFlag(int xPosition, int yPosition);

    boolean setOpen(int xPosition, int yPosition);

    boolean setOpenAllAround(int xPosition, int yPosition);
}
