package ru.academit.ilnitsky.minesweeper.core;

import ru.academit.ilnitsky.minesweeper.common.*;

import java.time.Instant;
import java.time.LocalTime;
import java.util.Random;

/**
 * Ядро (модель) игры "Сапёр"
 * Created by UserLabView on 02.02.17.
 */
public class MinesweeperCore implements MinesweeperCoreInterface {
    private int xSize, ySize;
    private int numMines;

    private int[][] hiddenBoard;
    private int[][] visibleBoard;
    private GameBoard outputBoard;

    private GameStatus gameStatus;

    private int numActions;

    private Instant startTime;
    private Instant winTime;

    private final static double MINE_COEFFICIENT = 0.3;
    private final static int MIN_SIZE = 5;
    private final static int MAX_SIZE = 64;

    MinesweeperCore() {
        gameStatus = GameStatus.NONE;
    }

    private void setBoardSize(int xSize, int ySize) {
        if (xSize < MIN_SIZE) {
            throw new IllegalArgumentException("xSize < MIN_SIZE");
        } else if (xSize > MAX_SIZE) {
            throw new IllegalArgumentException("xSize > MAX_SIZE");
        }

        if (ySize < MIN_SIZE) {
            throw new IllegalArgumentException("ySize < MIN_SIZE");
        } else if (ySize > MAX_SIZE) {
            throw new IllegalArgumentException("ySize > MAX_SIZE");
        }

        this.xSize = xSize;
        this.ySize = ySize;
    }

    private void setNumMine(int numMines) {
        int maxNumMines = (int) (MINE_COEFFICIENT * xSize * ySize);

        if (numMines > maxNumMines) {
            throw new IllegalArgumentException("numMines > max (" + maxNumMines + ")");
        }

        this.numMines = numMines;
    }

    @Override
    public void startGame(int xSize, int ySize, int numMines) {
        setBoardSize(xSize, ySize);
        setNumMine(numMines);

        gameStatus = GameStatus.STARTED;

        numActions = 0;

        hiddenBoard = new int[xSize][];
        visibleBoard = new int[xSize][];

        for (int i = 0; i < xSize; i++) {
            hiddenBoard[i] = new int[ySize];
            visibleBoard[i] = new int[ySize];

            for (int j = 0; j < ySize; j++) {
                hiddenBoard[i][j] = 0;
                visibleBoard[i][j] = -1;
            }
        }

        outputBoard = new GameBoard(visibleBoard);
    }

    @Override
    public void stopGame() {
        checkGameStatus();
        gameStatus = GameStatus.ENDED_WITH_STOP;
    }

    @Override
    public int getGameNumMines() {
        checkGameStatus();
        return numMines;
    }

    @Override
    public GameBoardSize getGameBoardSize() {
        checkGameStatus();
        return new GameBoardSize(xSize, ySize);
    }

    @Override
    public GameBoard linkToGameBoard() {
        checkGameStatus();
        return outputBoard;
    }

    public Instant getStartTime() {
        checkGameStatus();
        return startTime;
    }

    @Override
    public Instant getGameTime() {
        checkGameStatus();
        return Instant.now().minusNanos(startTime.getNano());
    }

    @Override
    public Instant getWinGameTime() {
        if (gameStatus == GameStatus.ENDED_WITH_WIN) {
            return winTime;
        } else {
            throw new IllegalStateException("Calling getWinGameTime() while status != ENDED_WITH_WIN");
        }
    }

    @Override
    public GameInfo getWinGameInfo() {
        if (gameStatus == GameStatus.ENDED_WITH_WIN) {
            return new GameInfo(xSize, ySize, numMines, numActions, winTime);
        } else {
            throw new IllegalStateException("Calling getWinGameInfo() while status != ENDED_WITH_WIN");
        }
    }

    @Override
    public int getNumActions() {
        return numActions;
    }

    @Override
    public GameStatus getGameStatus() {
        return gameStatus;
    }

    private void checkPosition(int xPosition, int yPosition) {
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
    }

    private void checkGameStatus() {
        if (gameStatus.isNoGame()) {
            throw new IllegalStateException("Calling game.method while status == NoGame");
        }
    }

    @Override
    public void setFlag(int xPosition, int yPosition) {
        checkGameStatus();
        checkPosition(xPosition, yPosition);

        if (visibleBoard[xPosition][yPosition] == CellState.CLOSE.getValue()) {
            visibleBoard[xPosition][yPosition] = CellState.FLAG.getValue();
        } else if (visibleBoard[xPosition][yPosition] == CellState.FLAG.getValue()) {
            visibleBoard[xPosition][yPosition] = CellState.CLOSE.getValue();
        } else {
            throw new IllegalArgumentException("FLAG in impossible position!");
        }
    }

    @Override
    public boolean setOpen(int xPosition, int yPosition) {
        checkGameStatus();
        checkPosition(xPosition, yPosition);

        if (gameStatus.isStarted()) {
            initCellsForFirstPosition(xPosition, yPosition);
            gameStatus = GameStatus.CONTINUED;
            startTime = Instant.now();
        }

        return makeStep(xPosition, yPosition);
    }

    private Position[] getPositionOfMines() {
        LocalTime time = LocalTime.now();

        Random random = new Random(time.toNanoOfDay());

        Position[] positions = new Position[numMines];

        for (int i = 0; i < numMines; i++) {
            positions[i].setX(random.nextInt(xSize));
            positions[i].setY(random.nextInt(ySize));
        }

        return positions;
    }

    private int calcNumMinesAround(int xPosition, int yPosition) {
        if (hiddenBoard[xPosition][yPosition] == CellState.MINE.getValue()) {
            return hiddenBoard[xPosition][yPosition];
        }

        int xStart = xPosition - 1;
        int xEnd = xPosition + 1;
        int yStart = yPosition - 1;
        int yEnd = yPosition + 1;

        if (xPosition == 0) {
            xStart = 0;
        }
        if (yPosition == 0) {
            yStart = 0;
        }
        if (xPosition == (xSize - 1)) {
            xEnd = xSize - 1;
        }
        if (yPosition == (ySize - 1)) {
            yEnd = ySize - 1;
        }

        int numMines = 0;
        int mineValue = CellState.MINE.getValue();

        for (int iX = xStart; iX <= xEnd; iX++) {
            for (int iY = yStart; iY <= yEnd; iY++) {
                if (hiddenBoard[iX][iY] == mineValue) {
                    numMines++;
                }
            }
        }

        return numMines;
    }

    private void initCellsForFirstPosition(int xPosition, int yPosition) {
        boolean isNonAdequatePositions;
        Position[] minePositions;

        do {
            minePositions = getPositionOfMines();
            isNonAdequatePositions = false;

            for (Position p : minePositions) {
                if (p.getX() == xPosition && p.getY() == yPosition) {
                    isNonAdequatePositions = true;
                }
            }
        } while (isNonAdequatePositions);

        for (Position p : minePositions) {
            hiddenBoard[p.getX()][p.getY()] = CellState.MINE.getValue();
        }

        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                hiddenBoard[i][j] = calcNumMinesAround(i, j);
            }
        }
    }

    private boolean isMine(int xPosition, int yPosition) {
        return hiddenBoard[xPosition][yPosition] == CellState.MINE.getValue();
    }

    private boolean isFree(int xPosition, int yPosition) {
        return hiddenBoard[xPosition][yPosition] == CellState.FREE.getValue();
    }

    private boolean isNumber(int xPosition, int yPosition) {
        return hiddenBoard[xPosition][yPosition] >= CellState.N1.getValue()
                && hiddenBoard[xPosition][yPosition] <= CellState.N8.getValue();
    }

    private void showHiddenBoard() {
        for (int i = 0; i < xSize; i++) {
            System.arraycopy(hiddenBoard[i], 0, visibleBoard[i], 0, ySize);
        }
    }

    private boolean makeStep(int xPosition, int yPosition) {
        numActions++;

        if (isMine(xPosition, yPosition)) {
            showHiddenBoard();
            visibleBoard[xPosition][yPosition] = CellState.DETONATION.getValue();
            gameStatus = GameStatus.ENDED_WITH_LOSS;
            return false;

        } else if (isNumber(xPosition, yPosition)) {
            visibleBoard[xPosition][yPosition] = hiddenBoard[xPosition][yPosition];

        } else if (isFree(xPosition, yPosition)) {
            showFreeCellsAround(xPosition, yPosition);

        } else {
            throw new IllegalArgumentException("Unknown Cell State: hiddenBoard[ "
                    + xPosition + " ][ " + yPosition + "] = " + hiddenBoard[xPosition][yPosition]);
        }

        if (outputBoard.getNumCloseAndFlagCells() == numMines) {
            gameStatus = GameStatus.ENDED_WITH_WIN;
            winTime = Instant.now().minusNanos(startTime.getNano());
        }

        return true;
    }

    private void showFreeCellsAround(int xPosition, int yPosition) {
        int xStart = xPosition - 1;
        int xEnd = xPosition + 1;
        int yStart = yPosition - 1;
        int yEnd = yPosition + 1;

        if (xPosition == 0) {
            xStart = 0;
        }
        if (yPosition == 0) {
            yStart = 0;
        }
        if (xPosition == (xSize - 1)) {
            xEnd = xSize - 1;
        }
        if (yPosition == (ySize - 1)) {
            yEnd = ySize - 1;
        }

        for (int iX = xStart; iX <= xEnd; iX++) {
            for (int iY = yStart; iY <= yEnd; iY++) {
                if (iX != xPosition && iY != yPosition) {
                    if (isNumber(iX, iY)) {
                        visibleBoard[iX][iY] = hiddenBoard[iX][iY];
                    } else if (isFree(iX, iY)) {
                        showFreeCellsAround(iX, iY);
                    }
                } else {
                    visibleBoard[iX][iY] = CellState.FREE.getValue();
                }
            }
        }
    }
}
