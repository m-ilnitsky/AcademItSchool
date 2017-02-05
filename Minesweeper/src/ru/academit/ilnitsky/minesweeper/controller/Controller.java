package ru.academit.ilnitsky.minesweeper.controller;

import ru.academit.ilnitsky.minesweeper.common.GameBoard;
import ru.academit.ilnitsky.minesweeper.common.GameInfo;
import ru.academit.ilnitsky.minesweeper.common.View;
import ru.academit.ilnitsky.minesweeper.common.ViewListener;
import ru.academit.ilnitsky.minesweeper.common.GameStatus;
import ru.academit.ilnitsky.minesweeper.core.MinesweeperCore;

import java.time.Instant;

/**
 * Контроллер для игры "Сапёр"
 * Created by Mike on 05.02.2017.
 */
public class Controller implements ViewListener {
    private MinesweeperCore model;
    private View view;

    public Controller(MinesweeperCore model, View view) {
        this.model = model;
        this.view = view;
    }

    public GameBoard startGame(int xSize, int ySize, int numMines) {
        model.startGame(xSize, ySize, numMines);
        return model.linkToGameBoard();
    }

    public void stopGame() {
        model.stopGame();
    }

    public void setFlag(int xPosition, int yPosition) {
        model.setFlag(xPosition, yPosition);
    }

    public void setOpen(int xPosition, int yPosition) {
        model.setOpen(xPosition, yPosition);
    }

    public Instant getStartTime() {
        return model.getStartTime();
    }

    public Instant getGameTime() {
        return model.getGameTime();
    }

    public GameInfo getWinGameInfo() {
        return model.getWinGameInfo();
    }

    public GameStatus getGameStatus() {
        return model.getGameStatus();
    }
}
