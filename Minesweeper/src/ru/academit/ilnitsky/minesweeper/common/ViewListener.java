package ru.academit.ilnitsky.minesweeper.common;

import ru.academit.ilnitsky.minesweeper.core.GameStatus;

import java.time.Instant;

/**
 * Интерфейс подписчика на события View для игры "Сапёр"
 * Created by UserLabView on 02.02.17.
 */
public interface ViewListener {

    GameBoard startGame(int xSize, int ySize, int numMines);

    void stopGame();

    void setFlag(int xPosition, int yPosition);

    boolean setOpen(int xPosition, int yPosition);

    Instant getStartTime();

    Instant getGameTime();

    GameInfo getWinGameInfo();

    GameStatus getGameStatus();
}
