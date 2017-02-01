package ru.academit.ilnitsky.minesweeper.common;

/**
 * Интерфейс модели игры "Минёр"
 * Created by Mike on 02.02.2017.
 */
public interface MinesweeperCoreInterface {

    void setGameBoardSize(int xSize, int ySize);

    void setGameNumMine(int numMines);

    int getGameNumMines();

    GameBoardSize getGameBoardSize();

    void setFlag(int xPosition, int yPosition);

    boolean setOpen(int xPosition, int yPosition);
}
