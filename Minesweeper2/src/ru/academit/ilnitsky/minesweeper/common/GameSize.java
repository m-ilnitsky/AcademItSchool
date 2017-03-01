package ru.academit.ilnitsky.minesweeper.common;

/**
 * Размеры задающие сложность игры для игры "Сапёр"
 * Created by Mike on 12.02.2017.
 */
public class GameSize extends GameBoardSize {
    private final int numMines;

    public GameSize(int xSize, int ySize, int numMines) {
        super(xSize, ySize);

        if (numMines < 0) {
            throw new IllegalArgumentException("numMines < 0");
        }

        this.numMines = numMines;
    }

    public int getNumMines() {
        return numMines;
    }
}
