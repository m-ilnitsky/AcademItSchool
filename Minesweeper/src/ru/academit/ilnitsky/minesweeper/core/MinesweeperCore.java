package ru.academit.ilnitsky.minesweeper.core;

import ru.academit.ilnitsky.minesweeper.common.CellState;
import ru.academit.ilnitsky.minesweeper.common.GameBoard;
import ru.academit.ilnitsky.minesweeper.common.GameBoardSize;
import ru.academit.ilnitsky.minesweeper.common.MinesweeperCoreInterface;

/**
 * Created by UserLabView on 02.02.17.
 */
public class MinesweeperCore implements MinesweeperCoreInterface {
    private int xSize, ySize;
    private int numMines;

    private int[][] hiddenBoard;
    private int[][] visibleBoard;
    private GameBoard outputBoard;

    private boolean isStarted;
    private boolean isEnded;
    private int numFlagActions;
    private int numActions;
    private int startTime;

    private final static double mineCoefficient = 2.0 / 3.0;

    @Override
    public void setGameBoardSize(int xSize, int ySize) {
        if (xSize < 5) {
            throw new IllegalArgumentException("xSize < 5");
        }
        if (ySize < 5) {
            throw new IllegalArgumentException("ySize < 5");
        }

        this.xSize = xSize;
        this.ySize = ySize;
        initNewGame();
    }

    @Override
    public void setGameNumMine(int numMines) {
        int maxNumMines = (int) (mineCoefficient * xSize * ySize);

        if (numMines > maxNumMines) {
            throw new IllegalArgumentException("numMines > max (" + maxNumMines + ")");
        }

        this.numMines = numMines;
        initNewGame();
    }

    @Override
    public void initNewGame() {
        isStarted = false;
        isEnded = false;
        numActions = 0;
        numFlagActions = 0;
    }

    public void startNewGame() {
        isStarted = true;
        isEnded = false;
        numActions = 0;
        numFlagActions = 0;

        hiddenBoard = new int[xSize][];
        visibleBoard = new int[xSize][];

        for (int i = 0; i < xSize; i++) {
            hiddenBoard[i] = new int[ySize];
            visibleBoard[i] = new int[ySize];

            for (int j = 0; j < ySize; j++) {
                hiddenBoard[i][j] = 0;
                visibleBoard[i][j] = -1;
            }

            outputBoard = new GameBoard(visibleBoard);
        }
    }

    @Override
    public int getGameNumMines() {
        return numMines;
    }

    @Override
    public GameBoardSize getGameBoardSize() {
        return new GameBoardSize(xSize, ySize);
    }

    @Override
    public GameBoard linkToGameBoard() {
        return outputBoard;
    }

    public int getGameStartTime() {
        return startTime;
    }

    @Override
    public int getGameTime() {
        return 0;
    }

    @Override
    public void setFlag(int xPosition, int yPosition) {
        if (xPosition < 0) {
            throw new IllegalArgumentException("xPosition < 0");
        } else if (xPosition >= xSize) {
            throw new IllegalArgumentException("xPosition >= xSize");
        }
        if (yPosition < 0) {
            throw new IllegalArgumentException("yPosition < 0");
        } else if (yPosition >= ySize) {
            throw new IllegalArgumentException("yPosition >= ySize");
        }

        if (visibleBoard[xPosition][yPosition] == CellState.CLOSE.getValue()) {
            visibleBoard[xPosition][yPosition] = CellState.FLAG.getValue();
        } else if (visibleBoard[xPosition][yPosition] == CellState.FLAG.getValue()) {
            visibleBoard[xPosition][yPosition] = CellState.CLOSE.getValue();
        } else {
            throw new IllegalArgumentException("FLAG in impossible position!");
        }

        numFlagActions++;
    }

    @Override
    public boolean setOpen(int xPosition, int yPosition) {
        if (isEnded || !isStarted) return false;

        if (numActions == 0) {
            firstStep(xPosition, yPosition);
            return true;
        } else {
            return nextStep(xPosition, yPosition);
        }
    }

    private void firstStep(int xPosition, int yPosition) {

    }

    private boolean nextStep(int xPosition, int yPosition) {
        return false;
    }
}
