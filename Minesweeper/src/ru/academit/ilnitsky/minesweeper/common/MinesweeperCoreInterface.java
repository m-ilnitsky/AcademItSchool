package ru.academit.ilnitsky.minesweeper.common;

import ru.academit.ilnitsky.minesweeper.core.GameStatus;

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

    Instant getGameTime();

    Instant getWinGameTime();

    GameInfo getWinGameInfo();

    GameStatus getGameStatus();

    GameBoardSize getGameBoardSize();

    GameBoard linkToGameBoard();

    void setFlag(int xPosition, int yPosition);

    boolean setOpen(int xPosition, int yPosition);
}
