package ru.academit.ilnitsky.minesweeper.console;

import ru.academit.ilnitsky.minesweeper.common.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Конслольное представление View для игры "Сапёр"
 * Created by Mike on 06.02.2017.
 */
public class ConsoleView implements View {
    private final ArrayList<ViewListener> listeners = new ArrayList<>();

    private Scanner scanner;

    private GameStatus gameStatus;
    private GameBoard gameBoard;
    private Instant startTime;
    private int xSize;
    private int ySize;

    private boolean continued;

    private final static String SYMBOL_DETONATION = "X";
    private final static String SYMBOL_MINE = "*";
    private final static String SYMBOL_FLAG = "F";
    private final static String SYMBOL_FREE = " ";
    private final static String SYMBOL_CLOSE = "+";

    public ConsoleView() {
        gameStatus = GameStatus.NONE;
        continued = true;
    }

    public void addViewListener(ViewListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeViewListener(ViewListener listener) {
        listeners.remove(listener);
    }

    public void startApplication() {
        do {
            switch (gameStatus) {
                case NONE:
                    showStartMenu();

                    break;
                case STARTED:
                case CONTINUED:
                    showBoard();

                    break;
                case ENDED_WITH_STOP:
                    showMessage("Вы выбрали завершение текущей игры!");
                    gameStatus = GameStatus.NONE;
                    showStartMenu();

                    break;
                case ENDED_WITH_LOSS:
                    showBoard();
                    showMessage("Вы проиграли!");
                    gameStatus = GameStatus.NONE;
                    showStartMenu();

                    break;
                case ENDED_WITH_WIN:

                    break;
            }
            if (gameStatus.isNone()) {
                showStartMenu();
            }
            showStartMenu();
        } while (continued);
    }

    public void onGameStart(Instant startTime) {

    }

    public void onGameStep(GameStatus gameStatus) {

    }

    public void onSaveResult(GameInfo gameInfo) {

    }

    private void checkGameStatus() {
        if (gameStatus.isNoGame()) {
            throw new IllegalStateException("Calling game.method while status == NoGame");
        }
    }

    private void clear() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    private void printLine() {
        int xS = xSize + 6;

        for (int i = 0; i < xS; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    private void printLineOfNumbers() {
        int xS = xSize + 6;

        System.out.print("   ");
        for (int i = 0; i < xS; i++) {
            int iX = i - 3;
            if (iX % 2 == 0) {
                if (iX > 9) {
                    System.out.print(iX);
                } else {
                    System.out.print(iX + " ");
                }
            }
        }
        System.out.println();
    }

    private void showBoard() {
        checkGameStatus();

        int xS = xSize + 6;

        clear();
        printLineOfNumbers();
        printLine();

        for (int iY = 0; iY < ySize; iY++) {
            for (int j = 0; j < xS; j++) {
                if (j < 2) {
                    if (iY % 2 == 0) {
                        if (iY > 9) {
                            System.out.print(iY);
                        } else {
                            System.out.print(" " + iY);
                        }
                    } else {
                        System.out.print("  ");
                    }
                } else if (j == 2) {
                    System.out.print("|");
                } else if (j < xS - 3) {
                    int iX = j - 3;

                    if (gameBoard.getCell(iX, iY) == CellState.FREE) {
                        System.out.print(SYMBOL_FREE);
                    } else if (gameBoard.getCell(iX, iY) == CellState.CLOSE) {
                        System.out.print(SYMBOL_CLOSE);
                    } else if (gameBoard.getCell(iX, iY) == CellState.FLAG) {
                        System.out.print(SYMBOL_FLAG);
                    } else if (gameBoard.getCell(iX, iY) == CellState.MINE) {
                        System.out.print(SYMBOL_MINE);
                    } else if (gameBoard.getCell(iX, iY) == CellState.DETONATION) {
                        System.out.print(SYMBOL_DETONATION);
                    } else if (gameBoard.getCell(iX, iY).getValue() >= 1
                            && gameBoard.getCell(iX, iY).getValue() <= 8) {
                        System.out.print(gameBoard.getCell(iX, iY).getValue());
                    } else {
                        throw new IllegalArgumentException("Unknown Status: " + gameBoard.getCell(iX, iY).getValue());
                    }
                } else if (j == xS - 3) {
                    System.out.print("|");
                } else {
                    if (iY % 2 == 0) {
                        if (iY > 9) {
                            System.out.print(iY);
                        } else {
                            System.out.print(" " + iY);
                        }
                    }
                    System.out.println();
                }
            }
        }

        printLine();
        printLineOfNumbers();
    }

    private void showStartMenu() {
        clear();

        System.out.println("ИГРА *САПЁР*");
        System.out.println("ГЛАВНОЕ МЕНЮ");
        System.out.println("Выберите размер игры:");
        System.out.println("1: Малый размер 9Х9");
        System.out.println("2: Средний размер 16Х16");
        System.out.println("3: Бльшой размер 16Х30");
        System.out.println("4: Произвольный размер");
        System.out.println("0: ВЫХОД");
        System.out.println("Введите номер вашего выбора:");

        try {
            int numMines = 0;
            int choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    continued = false;

                    break;
                case 1:
                    xSize = 9;
                    ySize = 9;
                    numMines = 18;

                    break;
                case 2:
                    xSize = 16;
                    ySize = 16;
                    numMines = 32;

                    break;
                case 3:
                    xSize = 30;
                    ySize = 16;
                    numMines = 99;

                    break;
                case 4:
                    showGameSizeMenu();
                    break;
            }

            if (choice >= 1 && choice <= 3) {
                for (ViewListener listener : listeners) {
                    gameBoard = listener.startGame(xSize, ySize, numMines);
                    gameStatus = listener.getGameStatus();
                }
            }
        } catch (NumberFormatException e) {
            showStartMenu();
        }

        if (gameStatus.isNone()) {
            showStartMenu();
        }
    }

    private void showMessage(String message) {
        System.out.println();
        System.out.println(message);
        System.out.println("Для продолжения нажмите Enter");

        scanner.nextLine();
    }

    private int readGameParameter(String line) {
        int choice = 0;
        boolean notInt;

        do {
            System.out.print(line);
            try {
                choice = scanner.nextInt();
                notInt = false;
            } catch (NumberFormatException e) {
                notInt = true;
            }
        } while (notInt);

        return choice;
    }

    private void showGameSizeMenu() {
        clear();

        System.out.println("ИГРА *САПЁР*");
        System.out.println("МЕНЮ ВЫБОРА ПАРАМЕТРОВ ИГРЫ");
        System.out.println("Для выхода в ГЛАВНОЕ МЕНЮ введите отрицательное или нулевое число!");

        int choice;
        int numMines;

        choice = readGameParameter("Введите число ячеек поля игры по оси X (целое число от 5 до 64): ");
        if (choice < 1) {
            return;
        } else {
            if (choice < 5 || choice > 64) {
                showMessage("ОШИБКА: Число ячеек должно быть от 5 до 64 !");
                showGameSizeMenu();
                return;
            } else {
                xSize = choice;
            }
        }

        choice = readGameParameter("Введите число ячеек поля игры по оси Y (целое число от 5 до 64): ");
        if (choice < 1) {
            return;
        } else {
            if (choice < 5 || choice > 64) {
                showMessage("ОШИБКА: Число ячеек должно быть от 5 до 64 !");
                showGameSizeMenu();
                return;
            } else {
                ySize = choice;
            }
        }

        choice = readGameParameter("Введите число мин на поле (целое число не более 30% от числа ячеек): ");
        if (choice < 1) {
            return;
        } else {
            if (choice > (int) (xSize * ySize * 0.3)) {
                showMessage("ОШИБКА: Число мин должно быть от 1 до 30% от числа ячеек !");
                showGameSizeMenu();
                return;
            } else {
                numMines = choice;
            }
        }

        for (ViewListener listener : listeners) {
            gameBoard = listener.startGame(xSize, ySize, numMines);
            gameStatus = listener.getGameStatus();
        }
    }
}
