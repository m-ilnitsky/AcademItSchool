package ru.academit.ilnitsky.minesweeper.common;

/**
 * Интерфейс подписчика на события View для игры "Сапёр"
 * Created by UserLabView on 02.02.17.
 */
public interface ViewListener {

    void setGameBoardSize(int xSize, int ySize);

    void setGameNumMine(int numMines);

    void initNewGame();

    void setFlag(int xPosition, int yPosition);

    boolean setOpen(int xPosition, int yPosition);
}
