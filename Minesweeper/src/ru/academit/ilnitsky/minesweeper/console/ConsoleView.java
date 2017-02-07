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
    private final static String SYMBOL_CLOSE = "#";

    public ConsoleView() {
        scanner = new Scanner(System.in);

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

                    readCommand();

                    //showMessage("Здесь должен быть выбор команд игрока..");
                    //gameStatus = GameStatus.ENDED_WITH_LOSS;

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
                    showBoard();
                    showMessage("Поздравляем! Вы победили!");
                    gameStatus = GameStatus.NONE;
                    showStartMenu();

                    break;
            }
        } while (continued);
    }

    public void onGameStart(Instant startTime) {

    }

    public void onGameStep(GameStatus gameStatus) {

    }

    public void onSaveResult(GameInfo gameInfo) {

    }

    private void checkNoneGameStatus() {
        if (gameStatus.isNone()) {
            throw new IllegalStateException("Calling game.method while status == None");
        }
    }

    private void clear() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    private void printLine() {

        System.out.print("   ");
        for (int i = 0; i < xSize; i++) {
            if (i > 8) {
                if ((i - 8) % 4 == 0) {
                    System.out.print("---+");

                    if (xSize - 1 - i < 4) {
                        for (int j = i + 1; j < xSize; j++) {
                            System.out.print("-");
                        }
                    }
                }
            } else {
                if (i % 2 == 0) {
                    System.out.print("+");
                } else {
                    System.out.print("-");
                }
            }
        }
        System.out.println();
    }

    private void printLineOfNumbers() {

        System.out.print("   ");
        for (int i = 0; i < xSize; i++) {
            if (i > 8) {
                if ((i - 8) % 4 == 0) {
                    System.out.print("  " + i);
                }
            } else {
                if (i % 2 == 0) {
                    System.out.print(i);
                } else {
                    System.out.print(" ");
                }
            }
        }
        System.out.println();
    }

    private void showBoard() {
        checkNoneGameStatus();

        int xS = xSize + 2;

        clear();
        printLineOfNumbers();
        printLine();

        for (int iY = 0; iY < ySize; iY++) {
            for (int j = 0; j < xS; j++) {
                if (j < 1) {
                    if (iY % 2 == 0) {
                        if (iY > 9) {
                            System.out.print(iY + "+");
                        } else {
                            System.out.print(" " + iY + "+");
                        }
                    } else {
                        System.out.print("  |");
                    }
                } else if (j < xS - 1) {
                    int iX = j - 1;

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
                } else {
                    if (iY % 2 == 0) {
                        if (iY > 9) {
                            System.out.print("+" + iY);
                        } else {
                            System.out.print("+ " + iY);
                        }
                    } else {
                        System.out.print("|");
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
        System.out.println("Выберите сложность (размер) игры:");
        System.out.println("1: Новичёк (малый размер 9х9 ячеек, 10 мин)");
        System.out.println("2: Любитель (средний размер 16х16 ячеек, 40 мин)");
        System.out.println("3: Профессионал (большой размер 16х30 ячеек, 99 мин)");
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
                    numMines = 10;

                    break;
                case 2:
                    xSize = 16;
                    ySize = 16;
                    numMines = 40;
                    System.out.println("2");

                    break;
                case 3:
                    xSize = 30;
                    ySize = 16;
                    numMines = 99;

                    break;
                case 4:
                    showGameSizeMenu();
                    break;

                default:
                    continued = false;
            }

            if (choice >= 1 && choice <= 3) {
                for (ViewListener listener : listeners) {
                    gameBoard = listener.startGame(xSize, ySize, numMines);
                    gameStatus = listener.getGameStatus();
                }
            }
        } catch (Exception e) {
            showMessage("Чтоб выбрать пункт меню, введите цело число!");
        }
    }

    private void showMessage(String message) {
        System.out.println();
        System.out.println(message);
        System.out.println("Для продолжения нажмите Enter");

        scanner.nextLine();
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

    private void readCommand() {
        System.out.println("Доступные команды:");
        System.out.println("stop или exit - останов игры");
        System.out.println("xXyY или yYxX - где X и Y целые числа - открыть ячейку с адресом (X;Y)");
        System.out.println("fyYxX или fxXyY - где X и Y целые числа - установить флаг на ячейку с адресом (X;Y)");

        String line = scanner.nextLine();
        line = line.trim();

        boolean isFlag = false;
        boolean isX = false;
        boolean isY = false;

        String x = "";
        String y = "";

        int xPosition;
        int yPosition;

        if (line.equals("stop") || line.equals("Stop") || line.equals("STOP")
                || line.equals("exit") || line.equals("Exit") || line.equals("EXIT")) {
            for (ViewListener listener : listeners) {
                listener.stopGame();
                gameStatus = listener.getGameStatus();
            }
        } else if (!line.isEmpty()) {
            int iX = 0;
            int iY = 0;

            if (line.substring(0, 1).equals("f") || line.substring(0, 1).equals("F")) {
                isFlag = true;
                System.out.println("isFlag = true");
            }

            if (line.contains("x")) {
                iX = line.indexOf("x");
                isX = true;
            } else if (line.contains("X")) {
                iX = line.indexOf("X");
                isX = true;
            }

            if (line.contains("y")) {
                iY = line.indexOf("y");
                isY = true;
            } else if (line.contains("Y")) {
                iY = line.indexOf("Y");
                isY = true;
            }

            if (isX && isY) {
                iX++;
                while (iX < line.length() && Character.isDigit(line.substring(iX, iX + 1).toCharArray()[0])) {
                    x += line.substring(iX, iX + 1);
                    iX++;
                }

                iY++;
                while (iY < line.length() && Character.isDigit(line.substring(iY, iY + 1).toCharArray()[0])) {
                    y += line.substring(iY, iY + 1);
                    iY++;
                }

                if (!x.isEmpty() && !y.isEmpty()) {
                    try {
                        xPosition = Integer.parseInt(x);
                        yPosition = Integer.parseInt(y);

                        if (isFlag) {
                            for (ViewListener listener : listeners) {
                                listener.setFlag(xPosition, yPosition);
                                gameStatus = listener.getGameStatus();
                            }
                        } else {
                            for (ViewListener listener : listeners) {
                                listener.setOpen(xPosition, yPosition);
                                gameStatus = listener.getGameStatus();
                            }
                        }

                    } catch (NumberFormatException e) {
                        showMessage("Ошибка:" + e);
                    }
                }
            }
        }
    }
}
