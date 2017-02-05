package ru.academit.ilnitsky.minesweeper.common;

/**
 * Игровая доска для игры "Сапёр"
 * Created by UserLabView on 02.02.17.
 */
public class GameBoard {
    private int[][] cells;

    public GameBoard(int[][] externalBoard) {
        cells = externalBoard;
    }

    public GameBoard(GameBoard prototype) {
        cells = new int[prototype.cells.length][];

        for (int i = 0; i < prototype.cells.length; i++) {
            cells[i] = new int[prototype.cells[i].length];
            System.arraycopy(prototype.cells[i], 0, cells[i], 0, prototype.cells[i].length);
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

    public int getNumCells() {
        return cells.length * cells[0].length;
    }

    public int getNumCells(CellState cellState) {
        int count = 0;

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j] == cellState.getValue()) {
                    count++;
                }
            }
        }

        return count;
    }

    public int getNumCloseAndFlagCells() {
        int count = 0;

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j] == CellState.CLOSE.getValue()
                        || cells[i][j] == CellState.FLAG.getValue()) {
                    count++;
                }
            }
        }

        return count;
    }

    public CellState getCell(Position position) {
        return getCell(position.getX(), position.getY());
    }

    public CellState getCell(int xPosition, int yPosition) {
        return CellState.state(cells[xPosition][yPosition]);
    }

}
