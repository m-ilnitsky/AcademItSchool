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
    private int xSize;
    private int ySize;
    private int numMines;

    private boolean[][] mask;
    private int[][] hiddenBoard;
    private int[][] visibleBoard;
    private GameBoard outputBoard;

    private GameStatus gameStatus;

    private int numActions;

    private Instant startTime;
    private long winTime;

    private final static double MINE_COEFFICIENT = 0.3;
    private final static int MIN_SIZE = 5;
    private final static int MAX_SIZE = 64;

    public MinesweeperCore() {
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

        mask = new boolean[xSize][];
        hiddenBoard = new int[xSize][];
        visibleBoard = new int[xSize][];

        for (int i = 0; i < xSize; i++) {
            mask[i] = new boolean[ySize];
            hiddenBoard[i] = new int[ySize];
            visibleBoard[i] = new int[ySize];

            for (int j = 0; j < ySize; j++) {
                mask[i][j] = true;
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

    @Override
    public Instant getStartTime() {
        checkGameStatus();
        return startTime;
    }

    @Override
    public long getGameTime() {
        checkGameStatus();
        return Instant.now().getEpochSecond() - startTime.getEpochSecond();
    }

    @Override
    public long getWinGameTime() {
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
    public void setQuery(int xPosition, int yPosition) {
        checkGameStatus();
        checkPosition(xPosition, yPosition);

        if (visibleBoard[xPosition][yPosition] == CellState.CLOSE.getValue()
                || visibleBoard[xPosition][yPosition] == CellState.FLAG.getValue()) {
            visibleBoard[xPosition][yPosition] = CellState.QUERY.getValue();
        } else if (visibleBoard[xPosition][yPosition] == CellState.QUERY.getValue()) {
            visibleBoard[xPosition][yPosition] = CellState.CLOSE.getValue();
        } else {
            throw new IllegalArgumentException("QUERY in impossible position!");
        }
    }

    @Override
    public void setFlag(int xPosition, int yPosition) {
        checkGameStatus();
        checkPosition(xPosition, yPosition);

        if (visibleBoard[xPosition][yPosition] == CellState.CLOSE.getValue()) {
            visibleBoard[xPosition][yPosition] = CellState.FLAG.getValue();
        } else if (visibleBoard[xPosition][yPosition] == CellState.FLAG.getValue()) {
            visibleBoard[xPosition][yPosition] = CellState.QUERY.getValue();
        } else if (visibleBoard[xPosition][yPosition] == CellState.QUERY.getValue()) {
            visibleBoard[xPosition][yPosition] = CellState.CLOSE.getValue();
        } else {
            throw new IllegalArgumentException("FLAG in impossible position!");
        }
    }

    @Override
    public boolean setOpenAllAround(int xPosition, int yPosition) {
        checkGameStatus();
        checkPosition(xPosition, yPosition);

        if (isNumber(xPosition, yPosition)) {
            if (visibleBoard[xPosition][yPosition] == CellState.CLOSE.getValue()) {
                throw new IllegalArgumentException("OpenAllAround in CLOSE position!");
            } else if (visibleBoard[xPosition][yPosition] == CellState.QUERY.getValue()) {
                throw new IllegalArgumentException("OpenAllAround in QUERY position!");
            } else if (visibleBoard[xPosition][yPosition] == CellState.FLAG.getValue()) {
                throw new IllegalArgumentException("OpenAllAround in FLAG position!");
            } else {

                if (visibleBoard[xPosition][yPosition] == calcNumFlagsAround(xPosition, yPosition)) {

                    Position[] positions = getAllPositionsAround(xPosition, yPosition);

                    int vClose = CellState.CLOSE.getValue();
                    int vQuery = CellState.QUERY.getValue();

                    for (Position p : positions) {
                        int x = p.getX();
                        int y = p.getY();

                        if ((visibleBoard[x][y] == vClose || visibleBoard[x][y] == vQuery) && !makeStep(x, y)) {
                            return false;
                        }
                    }
                }

                return true;
            }
        } else {
            throw new IllegalArgumentException("OpenAllAround in impossible position!");
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

        return (visibleBoard[xPosition][yPosition] == CellState.FLAG.getValue()) || makeStep(xPosition, yPosition);
    }

    private Position[] getPositionOfMines(int xPosition, int yPosition) {
        Position[] positions = new Position[numMines];

        boolean isOldPosition;
        int x;
        int y;

        Random random = new Random(LocalTime.now().toNanoOfDay());

        for (int i = 0; i < numMines; i++) {
            do {
                isOldPosition = false;
                x = random.nextInt(xSize);
                y = random.nextInt(ySize);

                if (x == xPosition && y == yPosition) {
                    isOldPosition = true;
                } else {
                    for (int j = 0; j < i; j++) {
                        if (x == positions[j].getX() && y == positions[j].getY()) {
                            isOldPosition = true;
                            break;
                        }
                    }
                }
            } while (isOldPosition);

            positions[i] = new Position(x, y);
        }

        return positions;
    }

    private Position[] getAllPositionsAround(int xPosition, int yPosition) {
        int xStart;
        int xEnd;
        int yStart;
        int yEnd;

        if (xPosition == 0) {
            xStart = 0;
        } else {
            xStart = xPosition - 1;
        }

        if (yPosition == 0) {
            yStart = 0;
        } else {
            yStart = yPosition - 1;
        }

        if (xPosition == (xSize - 1)) {
            xEnd = xSize - 1;
        } else {
            xEnd = xPosition + 1;
        }

        if (yPosition == (ySize - 1)) {
            yEnd = ySize - 1;
        } else {
            yEnd = yPosition + 1;
        }

        int count = 0;
        Position[] positions = new Position[8];

        for (int iX = xStart; iX <= xEnd; iX++) {
            for (int iY = yStart; iY <= yEnd; iY++) {
                if (iX != xPosition || iY != yPosition) {
                    positions[count] = new Position(iX, iY);
                    count++;
                }
            }
        }

        Position[] result = new Position[count];
        System.arraycopy(positions, 0, result, 0, count);

        return result;
    }

    private int calcNumFlagsAround(int xPosition, int yPosition) {
        int xStart;
        int xEnd;
        int yStart;
        int yEnd;

        if (xPosition == 0) {
            xStart = 0;
        } else {
            xStart = xPosition - 1;
        }

        if (yPosition == 0) {
            yStart = 0;
        } else {
            yStart = yPosition - 1;
        }

        if (xPosition == (xSize - 1)) {
            xEnd = xSize - 1;
        } else {
            xEnd = xPosition + 1;
        }

        if (yPosition == (ySize - 1)) {
            yEnd = ySize - 1;
        } else {
            yEnd = yPosition + 1;
        }

        int numFlags = 0;
        int flagValue = CellState.FLAG.getValue();

        for (int iX = xStart; iX <= xEnd; iX++) {
            for (int iY = yStart; iY <= yEnd; iY++) {
                if ((iX != xPosition || iY != yPosition) && visibleBoard[iX][iY] == flagValue) {
                    numFlags++;
                }
            }
        }

        return numFlags;
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

        int numberMines = 0;
        int mineValue = CellState.MINE.getValue();

        for (int iX = xStart; iX <= xEnd; iX++) {
            for (int iY = yStart; iY <= yEnd; iY++) {
                if (hiddenBoard[iX][iY] == mineValue) {
                    numberMines++;
                }
            }
        }

        return numberMines;
    }

    private void initCellsForFirstPosition(int xPosition, int yPosition) {

        Position[] minePositions = getPositionOfMines(xPosition, yPosition);

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
            showFreeCellsAround(xPosition, yPosition, true);

        } else {
            throw new IllegalArgumentException("Unknown Cell State: hiddenBoard[ "
                    + xPosition + " ][ " + yPosition + "] = " + hiddenBoard[xPosition][yPosition]);
        }

        if (outputBoard.getNumCloseAndFlagCells() == numMines) {
            gameStatus = GameStatus.ENDED_WITH_WIN;
            winTime = Instant.now().getEpochSecond() - startTime.getEpochSecond();
        }

        return true;
    }

    private void showFreeCellsAround(int xPosition, int yPosition, boolean clearMask) {

        if (clearMask) {
            for (int i = 0; i < mask.length; i++) {
                for (int j = 0; j < mask[i].length; j++) {
                    mask[i][j] = true;
                }
            }
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

        for (int iX = xStart; iX <= xEnd; iX++) {
            for (int iY = yStart; iY <= yEnd; iY++) {
                if (mask[iX][iY]) {
                    if (iX == xPosition && iY == yPosition) {
                        visibleBoard[iX][iY] = CellState.FREE.getValue();
                        mask[iX][iY] = false;
                    } else {
                        if (isNumber(iX, iY)) {
                            visibleBoard[iX][iY] = hiddenBoard[iX][iY];
                            mask[iX][iY] = false;
                        } else if (isFree(iX, iY)) {
                            showFreeCellsAround(iX, iY, false);
                        }
                    }
                }
            }
        }
    }
}
