package ru.academit.ilnitsky.minesweeper.common;

/**
 * Интерфейс модели игры "Сапёр"
 * Created by Mike on 02.02.2017.
 */
public interface MinesweeperCoreInterface {

    void setGameBoardSize(int xSize, int ySize);

    void setGameNumMine(int numMines);

    void initNewGame();

    int getGameNumMines();

    GameBoardSize getGameBoardSize();

    GameBoard linkToGameBoard();

    int getGameStartTime();

    int getGameTime();

    void setFlag(int xPosition, int yPosition);

    boolean setOpen(int xPosition, int yPosition);
}
