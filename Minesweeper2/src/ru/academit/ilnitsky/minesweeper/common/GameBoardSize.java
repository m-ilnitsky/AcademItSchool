package ru.academit.ilnitsky.minesweeper.common;

/**
 * Размер игрового поля для игры "Сапёр"
 * Created by Mike on 02.02.2017.
 */
public class GameBoardSize {
    private final int xSize;
    private final int ySize;

    public GameBoardSize(int xSize, int ySize) {
        if (xSize < 0) {
            throw new IllegalArgumentException("xSize < 0");
        }
        if (ySize < 0) {
            throw new IllegalArgumentException("ySize < 0");
        }

        this.xSize = xSize;
        this.ySize = ySize;
    }

    public GameBoardSize(GameBoardSize boardSize) {
        this(boardSize.xSize, boardSize.ySize);
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
