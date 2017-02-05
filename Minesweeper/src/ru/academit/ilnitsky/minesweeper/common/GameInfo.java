package ru.academit.ilnitsky.minesweeper.common;

import java.time.Instant;

/**
 * Информация о конкретной партии игры "Сапёр"
 * Created by Mike on 05.02.2017.
 */
public class GameInfo {
    private int xSize;
    private int ySize;
    private int numMines;
    private int numActions;
    private Instant time;
    private String userName;

    public GameInfo(int xSize, int ySize, int numMines, int numActions, Instant time) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.numMines = numMines;
        this.numActions = numActions;
        this.time = time;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getxSize() {
        return xSize;
    }

    public int getySize() {
        return ySize;
    }

    public int getNumMines() {
        return numMines;
    }

    public int getNumActions() {
        return numActions;
    }

    public Instant getTime() {
        return time;
    }

    public String getUserName() {
        return userName;
    }
}
