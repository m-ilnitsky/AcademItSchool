package ru.academit.ilnitsky.minesweeper.common;

/**
 * Информация о конкретной партии игры "Сапёр"
 * Created by Mike on 05.02.2017.
 */
public class GameInfo extends GameSize {
    private final int numActions;
    private final long time;

    private String userName;

    public GameInfo(GameSize gameSize, int numActions, long time) {
        this(gameSize.getXSize(), gameSize.getYSize(), gameSize.getNumMines(), numActions, time);
    }

    public GameInfo(GameBoardSize gameBoardSize, int numMines, int numActions, long time) {
        this(gameBoardSize.getXSize(), gameBoardSize.getYSize(), numMines, numActions, time);
    }

    public GameInfo(int xSize, int ySize, int numMines, int numActions, long time) {
        super(xSize, ySize, numMines);

        this.numActions = numActions;
        this.time = time;
    }

    public GameInfo(GameSize gameSize, int numActions, long time, String userName) {
        this(gameSize.getXSize(), gameSize.getYSize(), gameSize.getNumMines(), numActions, time, userName);
    }

    public GameInfo(GameBoardSize gameBoardSize, int numMines, int numActions, long time, String userName) {
        this(gameBoardSize.getXSize(), gameBoardSize.getYSize(), numMines, numActions, time, userName);
    }

    public GameInfo(int xSize, int ySize, int numMines, int numActions, long time, String userName) {
        super(xSize, ySize, numMines);

        this.numActions = numActions;
        this.time = time;
        this.userName = userName;
    }

    public void setUserName(String userName) {
        if (this.userName == null) {
            this.userName = userName;
        }
    }

    public int getNumActions() {
        return numActions;
    }

    public long getTime() {
        return time;
    }

    public String getUserName() {
        if (userName == null) {
            return "";
        } else {
            return userName;
        }
    }
}
