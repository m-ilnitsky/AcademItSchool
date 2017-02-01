package ru.academit.ilnitsky.minesweeper.common;

/**
 * Размер игрового поля
 * Created by Mike on 02.02.2017.
 */
public class GameBoardSize {
    private int xSize;
    private int ySize;

    public GameBoardSize() {
    }

    public GameBoardSize(int xSize, int ySize) {
        setSize(xSize, ySize);
    }

    public GameBoardSize(GameBoardSize boardSize) {
        setSize(boardSize);
    }

    public void setSize(int xSize, int ySize) {
        if (xSize < 0) {
            throw new IllegalArgumentException("xSize < 0");
        }
        if (ySize < 0) {
            throw new IllegalArgumentException("ySize < 0");
        }

        this.xSize = xSize;
        this.ySize = ySize;
    }

    public void setSize(GameBoardSize boardSize) {
        setSize(boardSize.xSize, boardSize.ySize);
    }

    public GameBoardSize getSize() {
        return new GameBoardSize(this);
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }
}
