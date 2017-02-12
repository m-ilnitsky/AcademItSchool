package ru.academit.ilnitsky.minesweeper.controller;

import ru.academit.ilnitsky.minesweeper.common.*;
import ru.academit.ilnitsky.minesweeper.core.MinesweeperCore;
import ru.academit.ilnitsky.minesweeper.scores.TopScores;

import java.time.Instant;

/**
 * Контроллер для игры "Сапёр"
 * Created by Mike on 05.02.2017.
 */
public class Controller implements ViewListener {
    private MinesweeperCore model;
    private View view;
    private TopScoresInterface[] topScores;

    public Controller(MinesweeperCore model, View view) {
        this.model = model;
        this.view = view;

        GameSize[] gameSizes = view.getStandardGameSizes();
        String[] gameNames = view.getStandardGameNames();
        int topLength = view.getTopLength();

        if (topLength < 1) {
            throw new IllegalArgumentException("topLength < 1");
        }
        if (gameSizes.length != gameNames.length) {
            throw new IllegalArgumentException("gameSizes.length != gameNames.length");
        }

        topScores = new TopScoresInterface[gameSizes.length];

        for (int i = 0; i < topScores.length; i++) {
            topScores[i] = new TopScores(gameSizes[i].getXSize(), gameSizes[i].getYSize(),
                    gameSizes[i].getNumMines(), topLength, gameNames[i]);
        }
    }

    @Override
    public GameBoard startGame(int xSize, int ySize, int numMines) {
        model.startGame(xSize, ySize, numMines);
        return model.linkToGameBoard();
    }

    @Override
    public void stopGame() {
        model.stopGame();
    }

    @Override
    public void setFlag(int xPosition, int yPosition) {
        model.setFlag(xPosition, yPosition);
    }

    @Override
    public void setOpen(int xPosition, int yPosition) {
        model.setOpen(xPosition, yPosition);
    }

    @Override
    public int getNumActions() {
        return model.getNumActions();
    }

    @Override
    public Instant getStartTime() {
        return model.getStartTime();
    }

    @Override
    public long getGameTime() {
        return model.getGameTime();
    }

    @Override
    public GameStatus getGameStatus() {
        return model.getGameStatus();
    }

    @Override
    public GameInfo getWinGameInfo() {
        return model.getWinGameInfo();
    }

    @Override
    public void saveWinGameInfo(GameInfo gameInfo) {
        int index = -1;

        for (int i = 0; i < topScores.length; i++) {
            if (topScores[i].getXSize() == gameInfo.getXSize()
                    && topScores[i].getYSize() == gameInfo.getYSize()
                    && topScores[i].getNumMines() == gameInfo.getNumMines()) {
                index = i;
                break;
            }
        }

        if (index > -1) {
            topScores[index].addResult(gameInfo);
        }
    }

    private int getTopScoresIndex(GameSize gameSize) {
        int index = -1;

        for (int i = 0; i < topScores.length; i++) {
            if (topScores[i].getXSize() == gameSize.getXSize()
                    && topScores[i].getYSize() == gameSize.getYSize()
                    && topScores[i].getNumMines() == gameSize.getNumMines()) {
                index = i;
                break;
            }
        }

        return index;
    }

    @Override
    public boolean isTopScores(GameSize gameSize) {
        return getTopScoresIndex(gameSize) > -1;
    }

    @Override
    public GameInfo[] getTopScores(GameSize gameSize) {

        int index = getTopScoresIndex(gameSize);

        if (index > -1) {
            return topScores[index].getTopScores();
        } else {
            throw new IllegalArgumentException("Nonentity TopScore for xSize = " + gameSize.getXSize()
                    + ", ySize = " + gameSize.getXSize() + ", numMines = " + gameSize.getNumMines());
        }
    }

    @Override
    public String getTopScoresName(GameSize gameSize) {

        int index = getTopScoresIndex(gameSize);

        if (index > -1) {
            return topScores[index].getTopScoresName();
        } else {
            throw new IllegalArgumentException("Nonentity TopScore for xSize = " + gameSize.getXSize()
                    + ", ySize = " + gameSize.getXSize() + ", numMines = " + gameSize.getNumMines());
        }
    }
}
