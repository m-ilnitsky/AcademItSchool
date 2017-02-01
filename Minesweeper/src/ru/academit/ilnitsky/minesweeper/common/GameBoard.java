package ru.academit.ilnitsky.minesweeper.common;

/**
 * Игровая доска для игры "Минёр"
 * Created by Mike on 02.02.2017.
 */
public class GameBoard {
    private int[][] cells;

    public GameBoard(GameBoardSize size) {
        this(size.getXSize(), size.getYSize());
    }

    public GameBoard(int xSize, int ySize) {
        if (xSize < 5) {
            throw new IllegalArgumentException("xSize < 5");
        }

        if (ySize < 5) {
            throw new IllegalArgumentException("ySize < 5");
        }

        cells = new int[xSize][];

        for (int i = 0; i < xSize; i++) {
            cells[i] = new int[ySize];
        }
    }

    private GameBoard() {
    }

    public GameBoard getGameBoard() {
        GameBoard newBoard = new GameBoard();
        int[][] newArray = new int[cells.length][];

        for (int i = 0; i < cells.length; i++) {
            newArray[i] = new int[cells[i].length];
            System.arraycopy(cells[i], 0, newArray[i], 0, cells[i].length);
        }

        newBoard.cells = newArray;

        return newBoard;
    }

    public GameBoardSize getSize() {
        return new GameBoardSize(cells.length, cells[0].length);
    }

    public int getXSize() {
        return cells.length;
    }

    public int getYSize() {
        return cells[0].length;
    }

    public void setCell(Position position, CellState cellState) {
        setCell(position.getX(), position.getY(), cellState);
    }

    public void setCell(int xPosition, int yPosition, CellState cellState) {
        if (xPosition < 0) {
            throw new IllegalArgumentException("xPosition < 0");
        } else if (xPosition >= cells.length) {
            throw new IllegalArgumentException("xPosition >= xSize");
        }
        if (yPosition < 0) {
            throw new IllegalArgumentException("yPosition < 0");
        } else if (yPosition >= cells[0].length) {
            throw new IllegalArgumentException("yPosition >= ySize");
        }

        cells[xPosition][yPosition] = cellState.getState();
    }

    public void setAllCells(CellState cellState) {
        int value = cellState.getState();

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = value;
            }
        }
    }
}
