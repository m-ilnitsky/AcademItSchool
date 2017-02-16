package ru.academit.ilnitsky.minesweeper.console;

import ru.academit.ilnitsky.minesweeper.common.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Конслольное представление View для игры "Сапёр"
 * Created by Mike on 06.02.2017.
 */
public class ConsoleView implements View {
    private final ArrayList<ViewListener> listeners = new ArrayList<>();

    private GameStatus gameStatus;
    private GameBoard gameBoard;

    private int xSize;
    private int ySize;
    private int numMines;

    private boolean continued;

    private boolean isWideBoard = true;

    private String warning = "";

    private LastCommands lastCommands = new LastCommands(5);

    private final static String SYMBOL_DETONATION = "X";
    private final static String SYMBOL_MINE = "*";
    private final static String SYMBOL_QUERY = "?";
    private final static String SYMBOL_FLAG = "F";
    private final static String SYMBOL_FREE = " ";
    private final static String SYMBOL_CLOSE = "#";

    private final int topLength;

    private final GameSize[] standardGameSizes = {
            new GameSize(9, 9, 10),
            new GameSize(16, 16, 40),
            new GameSize(30, 16, 99)
    };

    private final String[] standardGameNames = {
            "Новичок (малый размер 9х9 ячеек, 10 мин)",
            "Любитель (средний размер 16х16 ячеек, 40 мин)",
            "Профессионал (большой размер 16х30 ячеек, 99 мин)"
    };

    public ConsoleView(int topLength) {
        gameStatus = GameStatus.NONE;
        continued = true;

        this.topLength = topLength;
    }

    @Override
    public void addViewListener(ViewListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    @Override
    public void removeViewListener(ViewListener listener) {
        listeners.remove(listener);
    }

    @Override
    public GameSize[] getStandardGameSizes() {
        return standardGameSizes;
    }

    @Override
    public String[] getStandardGameNames() {
        return standardGameNames;
    }

    @Override
    public int getTopLength() {
        return topLength;
    }

    @Override
    public void startApplication() {
        do {
            switch (gameStatus) {
                case NONE:
                    showStartMenu();

                    break;
                case STARTED:
                    showBoard();
                    showWarning();
                    readCommand();

                    break;
                case CONTINUED:
                    showBoard();
                    showStatusBar();
                    showLastCommands();
                    showWarning();
                    readCommand();

                    break;
                case ENDED_WITH_STOP:
                    showMessage("Вы выбрали завершение текущей игры!");

                    gameStatus = GameStatus.NONE;
                    showStartMenu();

                    break;
                case ENDED_WITH_LOSS:
                    showBoard();
                    showLastCommands();

                    showMessage("Вы проиграли!");

                    gameStatus = GameStatus.NONE;
                    showStartMenu();

                    break;
                case ENDED_WITH_WIN:
                    showBoard();
                    showLastCommands();

                    showMessage("Поздравляем! Вы победили!");

                    showSaveResult();

                    gameStatus = GameStatus.NONE;
                    showStartMenu();
            }
        } while (continued);
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
        for (int i = 1; i <= xSize; i++) {
            if (i > 8 && i < 12 && xSize > 8 && xSize < 12) {
                if (i == 10) {
                    System.out.print("+");
                } else {
                    System.out.print("-");
                }
            } else if (i > 8) {
                if ((i - 8) % 4 == 0) {
                    System.out.print("---+");

                    if (xSize - i < 4) {
                        for (int j = i + 1; j <= xSize; j++) {
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
        for (int i = 1; i <= xSize; i++) {
            if (i == 10 && xSize > 9 && xSize < 12) {
                System.out.print(" 10");
            } else if (i > 8) {
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

    private void printWideLine() {

        System.out.print("   +");
        for (int i = 2; i <= xSize; i++) {
            if (i == 11 && xSize == 11) {
                System.out.print("~-");
            } else if (i > 10) {
                if ((i - 8) % 2 == 0) {
                    System.out.print("~-~+");

                    if (xSize - i == 1) {
                        System.out.print("~-");
                    }
                }
            } else {
                System.out.print("~+");
            }
        }
        System.out.println();
    }

    private void printWideLineOfNumbers() {

        System.out.print("   1");
        for (int i = 2; i <= xSize; i++) {
            if (i > 10) {
                if ((i - 8) % 2 == 0) {
                    System.out.print("  " + i);
                }
            } else {
                System.out.print(" " + i);
            }
        }
        System.out.println();
    }

    private void showBoard() {
        checkNoneGameStatus();

        int xS = xSize + 2;

        clear();

        if (isWideBoard) {
            printWideLineOfNumbers();
            printWideLine();
        } else {
            printLineOfNumbers();
            printLine();
        }

        for (int iY = 0; iY < ySize; iY++) {
            for (int j = 0; j < xS; j++) {

                int y = iY + 1;

                if (j < 1) {
                    if (y % 2 == 0) {
                        if (y > 9) {
                            System.out.print(y + "+");
                        } else {
                            System.out.print(" " + y + "+");
                        }
                    } else {
                        System.out.print("  |");
                    }
                } else if (j < xS - 1) {
                    int iX = j - 1;

                    if (isWideBoard && iX != 0) {
                        System.out.print(" ");
                    }

                    if (gameBoard.getCell(iX, iY) == CellState.FREE) {
                        System.out.print(SYMBOL_FREE);
                    } else if (gameBoard.getCell(iX, iY) == CellState.CLOSE) {
                        System.out.print(SYMBOL_CLOSE);
                    } else if (gameBoard.getCell(iX, iY) == CellState.QUERY) {
                        System.out.print(SYMBOL_QUERY);
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
                    if (y % 2 == 0) {
                        if (y > 9) {
                            System.out.print("+" + y);
                        } else {
                            System.out.print("+ " + y);
                        }
                    } else {
                        System.out.print("|");
                    }
                    System.out.println();
                }
            }
        }

        if (isWideBoard) {
            printWideLine();
            printWideLineOfNumbers();
        } else {
            printLine();
            printLineOfNumbers();
        }
    }

    private void showLastCommands() {
        if (lastCommands.getCurrentSize() > 0) {
            System.out.println("Список последних " + lastCommands.getMaxSize() + " выполненных команд: " + lastCommands);
        }
    }

    private void showWarning() {
        if (!warning.isEmpty()) {
            System.out.println(warning);

            warning = "";
        }
    }

    private void showStatusBar() {
        long time = 0;
        int numActions = 0;

        for (ViewListener listener : listeners) {
            time = listener.getGameTime();
            numActions = listener.getNumActions();
        }

        int numFlags = gameBoard.getNumCells(CellState.FLAG);
        System.out.printf("С начала игры прошло: %4ds    Действий: %2d    Флагов: %2d    Мин: %d%n", time, numActions, numFlags, numMines);
    }

    private void showMessage(String message) {
        System.out.println();
        System.out.println(message);
        System.out.println("Для продолжения нажмите Enter");

        Scanner scanner = new Scanner(System.in);

        scanner.nextLine();
    }

    private void showAbout() {
        clear();

        System.out.println("************** О ПРОГРАММЕ ***************");
        System.out.println("*  Консольная версия игры САПЁР          *");
        System.out.println("*  М.Ильницкий, Новосибирск, 2017        *");
        System.out.println("******************************************");
        System.out.println("Для выхода в главное меню нажмите Enter");

        Scanner scanner = new Scanner(System.in);

        scanner.nextLine();
    }

    private void showStartMenu() {
        clear();

        System.out.println("*********************** ИГРА <САПЁР> **********************");
        System.out.println("*                       ГЛАВНОЕ МЕНЮ                      *");
        System.out.println("************** * * * * * * * * * * * * * * * **************");
        System.out.println("* СЛОЖНОСТЬ ИГРЫ:                                         *");
        System.out.println("* 1: Новичок (малый размер 9х9 ячеек, 10 мин)             *");
        System.out.println("* 2: Любитель (средний размер 16х16 ячеек, 40 мин)        *");
        System.out.println("* 3: Профессионал (большой размер 16х30 ячеек, 99 мин)    *");
        System.out.println("* 4: Произвольный размер                                  *");
        System.out.println("************** * * * * * * * * * * * * * * * **************");
        System.out.println("* ТОП ЛУЧШИХ РЕЗУЛЬТАТОВ:                                 *");
        System.out.println("* 5: Новичёк (малый размер 9х9 ячеек, 10 мин)             *");
        System.out.println("* 6: Любитель (средний размер 16х16 ячеек, 40 мин)        *");
        System.out.println("* 7: Профессионал (большой размер 16х30 ячеек, 99 мин)    *");
        System.out.println("************** * * * * * * * * * * * * * * * **************");
        System.out.println("* 9: О ПРОГРАММЕ                                          *");
        System.out.println("* 0: ВЫХОД                                                *");
        System.out.println("*************** Введите номер вашего выбора ***************");

        Scanner scanner = new Scanner(System.in);

        try {
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
                case 5:
                    showTopScores(new GameSize(9, 9, 10));

                    break;
                case 6:
                    showTopScores(new GameSize(16, 16, 40));

                    break;
                case 7:
                    showTopScores(new GameSize(30, 16, 99));

                    break;
                case 9:
                    showAbout();

                    break;
                default:
                    showMessage("Чтоб выбрать пункт меню, введите номер соответствующего пункта!");
            }

            if (choice >= 1 && choice <= 3) {
                for (ViewListener listener : listeners) {
                    gameBoard = listener.startGame(xSize, ySize, numMines);
                    gameStatus = listener.getGameStatus();
                    lastCommands.clear();
                }
            }
        } catch (Exception e) {
            showMessage("Чтоб выбрать пункт меню, введите цело число!");
        }
    }

    private int readGameParameter(String line) {
        int choice = 0;
        boolean notInt;

        Scanner scanner = new Scanner(System.in);

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
            lastCommands.clear();
        }
    }

    private void readCommand() {
        System.out.println("***************************************************************************************************");
        System.out.println("*  stop - остановить текущую игру                                                                 *");
        System.out.println("*  exit - остановить текущую игру и выйти в главное меню                                          *");
        System.out.println("*  new  - остановить текущую игру и начать новую с теми же параметрами                            *");
        System.out.println("*  wide / narrow  - установить режим широкого / узкого поля                                       *");
        System.out.println("*  ?xXyY или ?yYxX или ?X,Y - где X и Y целые числа - установить вопрос на ячейку с адресом (X;Y) *");
        System.out.println("*  fxXyY или fyYxX или fX,Y - где X и Y целые числа - установить флаг на ячейку с адресом (X;Y)   *");
        System.out.println("*  &xXyY или &yYxX или &X,Y - где X и Y целые числа - открыть все ячейки соседние с адресом (X;Y) *");
        System.out.println("*   xXyY или  yYxX или  X,Y - где X и Y целые числа - открыть ячейку с адресом (X;Y)              *");
        System.out.println("**************************************** Введите команду ******************************************");

        Scanner scanner = new Scanner(System.in);

        String line = scanner.nextLine().trim().toLowerCase();

        boolean isFlag = false;
        boolean isQuery = false;
        boolean isAllAround = false;

        boolean isX = false;
        boolean isY = false;
        boolean isComma = false;

        String x = "";
        String y = "";

        int xPosition;
        int yPosition;

        if (line.equals("stop")) {
            for (ViewListener listener : listeners) {
                listener.stopGame();
                gameStatus = listener.getGameStatus();
            }
        } else if (line.equals("exit")) {
            for (ViewListener listener : listeners) {
                listener.stopGame();
            }
            gameStatus = GameStatus.NONE;
        } else if (line.equals("new")) {
            for (ViewListener listener : listeners) {
                gameBoard = listener.startGame(xSize, ySize, numMines);
                gameStatus = listener.getGameStatus();
                lastCommands.clear();
            }
        } else if (line.equals("wide")) {
            isWideBoard = true;
        } else if (line.equals("narrow")) {
            isWideBoard = false;
        } else if (!line.isEmpty()) {
            int iX = 0;
            int iY = 0;
            int iComma = 0;

            if (line.substring(0, 1).equals("f")) {
                isFlag = true;
            } else if (line.substring(0, 1).equals("?")) {
                isQuery = true;
            } else if (line.substring(0, 1).equals("&")) {
                isAllAround = true;
            }

            if (line.contains("x")) {
                iX = line.indexOf("x");
                isX = true;
            }

            if (line.contains("y")) {
                iY = line.indexOf("y");
                isY = true;
            }

            if (line.contains(",")) {
                iComma = line.indexOf(",");
                isComma = true;
            } else if (line.contains(".")) {
                iComma = line.indexOf(".");
                isComma = true;
            }

            if ((isX && isY) || isComma) {

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
                } else {

                    if (isFlag || isQuery || isAllAround) {
                        iX = 1;
                    } else {
                        iX = 0;
                    }

                    while (iX < line.length() && Character.isDigit(line.substring(iX, iX + 1).toCharArray()[0])) {
                        x += line.substring(iX, iX + 1);
                        iX++;
                    }

                    iY = iComma + 1;

                    while (iY < line.length() && Character.isDigit(line.substring(iY, iY + 1).toCharArray()[0])) {
                        y += line.substring(iY, iY + 1);
                        iY++;
                    }
                }

                if (!x.isEmpty() && !y.isEmpty()) {
                    try {
                        xPosition = Integer.parseInt(x);
                        yPosition = Integer.parseInt(y);

                        boolean isError = false;
                        String errorString = "";

                        if (xPosition < 1) {
                            errorString += " x < 1 ";
                            isError = true;
                        } else if (xPosition > xSize) {
                            errorString += " x > xSize ";
                            isError = true;
                        }
                        if (yPosition < 1) {
                            errorString += " y < 1 ";
                            isError = true;
                        } else if (yPosition > xSize) {
                            errorString += " y > ySize ";
                            isError = true;
                        }

                        if (isError) {
                            showMessage("ОШИБКА: координаты выходят за пределы игрового поля (" + errorString + ")!");
                        } else {
                            xPosition--;
                            yPosition--;

                            CellState cellState = gameBoard.getCell(xPosition, yPosition);

                            if (isFlag || isQuery) {
                                if (cellState != CellState.CLOSE && cellState != CellState.FLAG && cellState != CellState.QUERY) {
                                    showMessage("ОШИБКА: нельзя поставить флаг в уже открытую ячейку!");
                                } else {
                                    if (isFlag) {
                                        for (ViewListener listener : listeners) {
                                            listener.setFlag(xPosition, yPosition);
                                            gameStatus = listener.getGameStatus();
                                        }
                                    } else {
                                        for (ViewListener listener : listeners) {
                                            listener.setQuery(xPosition, yPosition);
                                            gameStatus = listener.getGameStatus();
                                        }
                                    }
                                    lastCommands.add(line);
                                }
                            } else if (isAllAround) {
                                if (cellState == CellState.CLOSE || cellState == CellState.FLAG || cellState == CellState.QUERY) {
                                    showMessage("ОШИБКА: нельзя открыть окружение неоткрытой ячейки!");
                                } else if (cellState == CellState.FREE) {
                                    showMessage("ОШИБКА: нельзя открыть окружение ячейки не граничащей с минами!");
                                } else {
                                    for (ViewListener listener : listeners) {
                                        listener.setOpenAllAround(xPosition, yPosition);
                                        gameStatus = listener.getGameStatus();
                                    }
                                    lastCommands.add(line);
                                }
                            } else {
                                if (cellState == CellState.CLOSE || cellState == CellState.QUERY) {
                                    for (ViewListener listener : listeners) {
                                        listener.setOpen(xPosition, yPosition);
                                        gameStatus = listener.getGameStatus();
                                    }
                                    lastCommands.add(line);
                                } else {
                                    if (cellState == CellState.FLAG) {
                                        showMessage("ОШИБКА: нельзя сходить в ячейку занятую флагом!");
                                    } else {
                                        showMessage("ОШИБКА: нельзя сходить в уже открытую ячейку!");
                                    }
                                }
                            }
                        }
                    } catch (NumberFormatException e) {
                        showMessage("ОШИБКА: неправильный формат координат: " + e);
                    }
                } else {
                    warning = "ВНИМАНИЕ: введена неизвестная команда: " + line;
                }
            } else {
                warning = "ВНИМАНИЕ: введена неизвестная команда: " + line;
            }
        }
    }

    private void showSaveResult() {
        GameInfo gameInfo = null;
        boolean isTop = false;

        for (ViewListener listener : listeners) {
            gameInfo = listener.getWinGameInfo();
            isTop = listener.isTopScores(gameInfo);
        }

        if (gameInfo != null && isTop) {
            clear();
            System.out.println("ИГРА *САПЁР*");
            System.out.println("МЕНЮ СОХРАНЕНИЯ РЕЗУЛЬТАТА");
            System.out.println("Параметры выигрышной партии");
            System.out.println("Размер доски (X,Y) = ( " + gameInfo.getXSize() + " , " + gameInfo.getYSize() + " )");
            System.out.println("Число мин = " + gameInfo.getNumMines());
            System.out.println("Число действий = " + gameInfo.getNumActions());
            System.out.println("Время игры = " + gameInfo.getTime() + " сек");
            System.out.println("Введите ваше имя:");

            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();

            if (line.isEmpty() || line.equals("") || line.equals(" ")) {
                gameInfo.setUserName("anonymous");
            } else {
                gameInfo.setUserName(line);
            }

            for (ViewListener listener : listeners) {
                listener.saveWinGameInfo(gameInfo);
            }

            showTopScores(gameInfo);
        }
    }

    private void showTopScores(GameSize gameSize) {
        boolean isTop = false;
        GameInfo[] gameArray = null;
        String gameName = null;

        for (ViewListener listener : listeners) {
            isTop = listener.isTopScores(gameSize);
        }

        if (isTop) {
            for (ViewListener listener : listeners) {
                gameArray = listener.getTopScores(gameSize);
                gameName = listener.getTopScoresName(gameSize);
            }

            if (gameArray != null && gameName != null) {
                int length = 0;
                for (int i = gameArray.length - 1; i >= 0; i--) {
                    if (gameArray[i] != null) {
                        length = i + 1;
                        break;
                    }
                }

                if (length > 0) {
                    clear();
                    System.out.println("Лучшие результаты <Топ " + gameArray.length + ">");
                    System.out.println("ДЛЯ ИГРЫ: " + gameName);
                    System.out.println("******************************** Результаты ******************************");

                    for (int i = 0; i < length; i++) {
                        System.out.printf("* %3d    * Время: %4d сек    * Действий: %3d    * Игрок: %s%n",
                                i + 1, gameArray[i].getTime(), gameArray[i].getNumActions(), gameArray[i].getUserName());
                    }

                    System.out.println("**************************************************************************");

                    showMessage("");
                } else {
                    clear();
                    showMessage("Извините для данных параметров игры ещё нет сохранённых результатов побед!");
                }
            }
        }
    }
}
