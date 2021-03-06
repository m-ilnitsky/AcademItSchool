package ru.academit.ilnitsky.minesweeper.gui;

import ru.academit.ilnitsky.minesweeper.common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * View с графическим интерфейсом пользователя (GUI) для игры "Сапёр"
 * Created by UserLabView on 21.02.17.
 */
public class FrameView implements ViewAutoCloseable {
    private ViewListener listener;

    private final JFrame frame = new JFrame("Сапёр");

    private GameStatus gameStatus;
    private GameBoard gameBoard;

    private int xSize;
    private int ySize;
    private int numMines;

    private int numFlags;
    private int numActions;

    private final int topLength;

    private final GameSize[] standardGameSizes;
    private final String[] standardGameNames;

    private final JMenuBar menuBar = new JMenuBar();
    private final JPanel topPanel = new JPanel();
    private final JPanel gameBoardPanel = new JPanel();

    private JMenuItem[] menuScoresSize = new JMenuItem[0];

    private static final String timeStr = "Время: ";
    private static final String flagStr = "Флагов: ";
    private static final String actionStr = "Действий: ";

    private final JLabel timeLabel = new JLabel(timeStr);
    private final JLabel flagLabel = new JLabel(flagStr);
    private final JLabel actionLabel = new JLabel(actionStr);

    private Instant startTime;
    private Timer timer = new Timer(100, l -> updateTime());

    private long lastLeftClick;
    private long lastRightClick;

    private JButton[][] cells;

    private static final int iconSize = 32;

    private static final String path = "/ru/academit/ilnitsky/minesweeper/resources/";
    private final ImageIcon iconClose = new ImageIcon(getClass().getResource(path + "close.png"));
    private final ImageIcon iconQuery = new ImageIcon(getClass().getResource(path + "query.png"));
    private final ImageIcon iconFlag = new ImageIcon(getClass().getResource(path + "flag.png"));
    private final ImageIcon icon1 = new ImageIcon(getClass().getResource(path + "1.png"));
    private final ImageIcon icon2 = new ImageIcon(getClass().getResource(path + "2.png"));
    private final ImageIcon icon3 = new ImageIcon(getClass().getResource(path + "3.png"));
    private final ImageIcon icon4 = new ImageIcon(getClass().getResource(path + "4.png"));
    private final ImageIcon icon5 = new ImageIcon(getClass().getResource(path + "5.png"));
    private final ImageIcon icon6 = new ImageIcon(getClass().getResource(path + "6.png"));
    private final ImageIcon icon7 = new ImageIcon(getClass().getResource(path + "7.png"));
    private final ImageIcon icon8 = new ImageIcon(getClass().getResource(path + "8.png"));
    private final ImageIcon iconMine = new ImageIcon(getClass().getResource(path + "mine.png"));
    private final ImageIcon iconDetonation = new ImageIcon(getClass().getResource(path + "detonation.png"));

    public FrameView(int topLength, GameSize[] standardGameSizes, String[] standardGameNames) {

        this.standardGameSizes = standardGameSizes;
        this.standardGameNames = standardGameNames;

        this.topLength = topLength;
    }

    @Override
    public void addViewListener(ViewListener listener) {
        this.listener = listener;
    }

    @Override
    public void removeViewListener(ViewListener listener) {
        if (this.listener == listener) {
            this.listener = null;
        }
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
    public void close() throws Exception {
        frame.setVisible(false);
    }

    @Override
    public void startApplication() {
        SwingUtilities.invokeLater(() -> {

            xSize = standardGameSizes[0].getXSize();
            ySize = standardGameSizes[0].getYSize();
            numMines = standardGameSizes[0].getNumMines();

            numActions = 0;
            numFlags = 0;

            gameBoard = listener.startGame(xSize, ySize, numMines);
            gameStatus = listener.getGameStatus();

            initMenuBar();
            initTopPanel();
            initGameBoard();
            initFrame();

            updateTopPanel();
        });
    }

    private void exitDialog() {
        try {
            Object options[] = {"Да", "Нет"};
            int result = JOptionPane.showOptionDialog(frame,
                    "Завершить программу?",
                    "Подтверждение завершения",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (result == 0) {
                close();
                System.exit(0);
            }
        } catch (Exception exception) {
            exception.getStackTrace();
        }
    }

    private void initMenuBar() {
        JMenu menuFile = createMenuFile();
        JMenu menuScores = createMenuScores();
        JMenu menuAbout = createMenuAbout();

        menuBar.add(menuFile);
        menuBar.add(menuScores);
        menuBar.add(menuAbout);
    }

    private JMenu createMenuFile() {
        JMenu menuFile = new JMenu("Файл");

        JMenuItem menuNew = new JMenuItem("Начать заново");
        JMenuItem menuStop = new JMenuItem("Завершить");
        JMenuItem menuExit = new JMenuItem("Закрыть");

        JRadioButtonMenuItem menuSize[] = new JRadioButtonMenuItem[standardGameNames.length];
        ButtonGroup buttonGroup = new ButtonGroup();

        for (int i = 0; i < standardGameNames.length; i++) {
            menuSize[i] = new JRadioButtonMenuItem(standardGameNames[i]);
            buttonGroup.add(menuSize[i]);
        }
        JRadioButtonMenuItem menuCustom = new JRadioButtonMenuItem("Произвольные параметры");
        buttonGroup.add(menuCustom);

        menuSize[0].doClick();

        for (int i = 0; i < standardGameNames.length; i++) {
            Integer index = i;
            menuSize[i].addActionListener(e -> {
                xSize = standardGameSizes[index].getXSize();
                ySize = standardGameSizes[index].getYSize();
                numMines = standardGameSizes[index].getNumMines();

                setNewGame();
            });
        }

        menuNew.addActionListener(e -> setNewGame());

        menuCustom.addActionListener(e -> {
            JDialog dialog = new JDialog(frame);

            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dialog.setTitle("Выбор произвольных параметров игры");
            dialog.setModal(true);
            dialog.setSize(640, 160);
            dialog.setMinimumSize(dialog.getSize());

            JPanel panel = new JPanel();

            JLabel labelXSize = new JLabel("Введите число ячеек по оси X (от 5 до 64):");
            JLabel labelYSize = new JLabel("Введите число ячеек по оси Y (от 5 до 64):");
            JLabel labelNumMines = new JLabel("Введите число мин (максимум 30% от числа ячеек):");

            JTextField inputXSize = new JTextField();
            JTextField inputYSize = new JTextField();
            JTextField inputNumMines = new JTextField();

            JButton buttonCancel = new JButton("Отмена");
            JButton buttonOk = new JButton("Применить");

            buttonCancel.addActionListener(actionEvent -> {
                dialog.setVisible(false);

                int index = indexOfGameSize();
                if (index == -1) {
                    buttonGroup.setSelected(menuCustom.getModel(), true);
                } else {
                    buttonGroup.setSelected(menuSize[index].getModel(), true);
                }
            });

            buttonOk.addActionListener(actionEvent -> {
                try {
                    int newXSize = Integer.parseInt(inputXSize.getText());
                    int newYSize = Integer.parseInt(inputYSize.getText());
                    int newNumMines = Integer.parseInt(inputNumMines.getText());

                    int maxNumMines = (int) (newXSize * newYSize * 0.3);

                    if (newXSize < 5 || newYSize < 5) {
                        showErrorMessage(dialog, "Размер должен быть больше 4");
                    } else if (newXSize > 64 || newYSize > 64) {
                        showErrorMessage(dialog, "Размер должен быть меньше 65");
                    } else if (newNumMines < 1) {
                        showErrorMessage(dialog, "Число бомб должно быть больше 0");
                    } else if (newNumMines >= maxNumMines) {
                        showErrorMessage(dialog, "Число бомб должно быть меньше 30% от числа ячеек, т.е. меньше " + maxNumMines + " штук");
                    } else {
                        xSize = newXSize;
                        ySize = newYSize;
                        numMines = newNumMines;

                        setNewGame();

                        dialog.setVisible(false);
                    }
                } catch (NumberFormatException ex) {
                    showErrorMessage(dialog, "Вводить нужно целые положительные числа");
                }
            });

            panel.setLayout(new GridLayout(4, 2));
            panel.add(labelXSize);
            panel.add(inputXSize);
            panel.add(labelYSize);
            panel.add(inputYSize);
            panel.add(labelNumMines);
            panel.add(inputNumMines);
            panel.add(buttonCancel);
            panel.add(buttonOk);

            dialog.add(panel);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });

        menuStop.addActionListener(e -> {
            if (gameStatus.isGame()) {
                listener.stopGame();
                gameStatus = listener.getGameStatus();
                updateGameBoard();
            }
        });

        menuExit.addActionListener(e -> exitDialog());

        menuFile.add(menuNew);
        menuFile.addSeparator();
        for (JRadioButtonMenuItem menu : menuSize) {
            menuFile.add(menu);
        }
        menuFile.add(menuCustom);
        menuFile.addSeparator();
        menuFile.add(menuStop);
        menuFile.addSeparator();
        menuFile.add(menuExit);

        return menuFile;
    }

    private JMenu createMenuScores() {
        JMenu menuScores = new JMenu("Рекорды");

        menuScoresSize = new JMenuItem[standardGameNames.length];

        for (int i = 0; i < standardGameNames.length; i++) {
            menuScoresSize[i] = new JMenuItem(standardGameNames[i]);

            Integer index = i;

            menuScoresSize[i].addActionListener(e -> {
                GameInfo[] gameArray = null;
                String gameName = null;

                gameArray = listener.getTopScores(standardGameSizes[index]);
                gameName = standardGameNames[index];

                if (gameArray != null && gameName != null) {

                    int length = 0;
                    for (int ii = gameArray.length - 1; ii >= 0; ii--) {
                        if (gameArray[ii] != null) {
                            length = ii + 1;
                            break;
                        }
                    }

                    String[] strings;

                    if (length > 0) {
                        strings = new String[length];

                        for (int ii = 0; ii < length; ii++) {
                            strings[ii] = String.format(" %3d   Время: %4d сек   Действий: %3d   Игрок: %s%n",
                                    ii + 1, gameArray[ii].getTime(), gameArray[ii].getNumActions(), gameArray[ii].getUserName());
                        }
                    } else {
                        strings = new String[1];
                        strings[0] = "Извините для данных параметров игры ещё нет сохранённых результатов побед!";
                    }

                    String title = "Топ " + gameArray.length + " для игры: " + gameName;

                    JOptionPane.showMessageDialog(frame, strings, title, JOptionPane.INFORMATION_MESSAGE);
                }
            });

            menuScores.add(menuScoresSize[i]);
        }

        return menuScores;
    }

    private JMenu createMenuAbout() {
        JMenu menuAbout = new JMenu("О программе");

        JMenuItem menuAbout2 = new JMenuItem("О программе");

        menuAbout2.addActionListener(e ->
                JOptionPane.showMessageDialog(frame,
                        new String[]{"Игра САПЁР с графическим интерфейсом",
                                "Графический интерфейс на основе Swing",
                                "М.Ильницкий, Новосибирск, 2017"},
                        "О программе",
                        JOptionPane.INFORMATION_MESSAGE)
        );

        menuAbout.add(menuAbout2);

        return menuAbout;
    }

    private void initTopPanel() {
        topPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 16, 8));

        Font groupFont = new Font("TimesRoman", Font.BOLD, 14);

        timeLabel.setFont(groupFont);
        flagLabel.setFont(groupFont);
        actionLabel.setFont(groupFont);

        topPanel.add(timeLabel);
        topPanel.add(flagLabel);
        topPanel.add(actionLabel);

        topPanel.setVisible(true);
    }

    private void initFrame() {
        frame.setJMenuBar(menuBar);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(gameBoardPanel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitDialog();
            }
        });

        setFrameSize();

        frame.setVisible(true);
    }

    private void setFrameSize() {
        final int minStartWidth = 400;

        int width = xSize * (iconSize + 1);
        int height = ySize * (iconSize + 1) + (int) topPanel.getSize().getHeight();

        if (width < minStartWidth) {
            width = minStartWidth;

            if (ySize * minStartWidth / xSize < 640) {
                height = ySize * minStartWidth / xSize + 64;
            }
        }

        frame.setMinimumSize(new Dimension(xSize * (iconSize + 1), ySize * (iconSize + 1) + 64));

        frame.setSize(width, height);

        // заставляет фрейм располагаться по центру экрана при запуске
        frame.setLocationRelativeTo(null);
    }

    private void initGameBoard() {
        lastLeftClick = lastRightClick = System.currentTimeMillis();

        gameBoardPanel.removeAll();

        gameBoardPanel.setLayout(new GridLayout(ySize, xSize));

        cells = new JButton[xSize][ySize];

        for (int i = 0; i < xSize; i++) {
            cells[i] = new JButton[ySize];

            for (int j = 0; j < ySize; j++) {
                cells[i][j] = new JButton();
                cells[i][j].setSize(iconSize, iconSize);
                cells[i][j].setMinimumSize(cells[i][j].getSize());
                cells[i][j].setIconTextGap(0);
                cells[i][j].setText("");
                cells[i][j].setHorizontalTextPosition(SwingConstants.LEFT);
                cells[i][j].setVerticalTextPosition(SwingConstants.TOP);
                cells[i][j].setIcon(iconClose);
                cells[i][j].setVisible(true);

                final Integer xValue = i;
                final Integer yValue = j;

                cells[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        CellState cellState = gameBoard.getCell(xValue, yValue);

                        long now = System.currentTimeMillis();
                        long rightClick = now - lastRightClick;
                        long leftClick = now - lastLeftClick;

                        if (e.getButton() == MouseEvent.BUTTON2
                                || (e.getButton() == MouseEvent.BUTTON1 && (rightClick < 200))
                                || (e.getButton() == MouseEvent.BUTTON3 && (leftClick < 200))) {

                            if (gameStatus.isContinued() && cellState.isNumber()) {

                                if (e.getButton() == MouseEvent.BUTTON2
                                        || (e.getButton() == MouseEvent.BUTTON1 && (rightClick < 50))
                                        || (e.getButton() == MouseEvent.BUTTON3 && (leftClick < 50))) {

                                    listener.setOpenAllAround(xValue, yValue);
                                    gameStatus = listener.getGameStatus();
                                    numActions = listener.getNumActions();
                                    numFlags = gameBoard.getNumCells(CellState.FLAG);
                                    updateGameBoard();
                                }
                            }
                        } else if (e.getButton() == MouseEvent.BUTTON1) {

                            lastLeftClick = System.currentTimeMillis();

                            if (gameStatus.isGame()
                                    && (cellState == CellState.CLOSE || cellState == CellState.QUERY)) {

                                listener.setOpen(xValue, yValue);
                                gameStatus = listener.getGameStatus();
                                numActions = listener.getNumActions();

                                if (numActions == 1) {
                                    startTimer();
                                }
                                numFlags = gameBoard.getNumCells(CellState.FLAG);
                                updateGameBoard();
                            }

                        } else if (e.getButton() == MouseEvent.BUTTON3) {

                            lastRightClick = System.currentTimeMillis();

                            if (gameStatus.isContinued()
                                    && (cellState == CellState.CLOSE
                                    || cellState == CellState.QUERY
                                    || cellState == CellState.FLAG)) {

                                listener.setFlag(xValue, yValue);
                                numFlags = gameBoard.getNumCells(CellState.FLAG);
                                updateGameBoard();
                            }
                        }
                    }
                });
            }
        }

        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                gameBoardPanel.add(cells[j][i]);
            }
        }

        gameBoardPanel.setVisible(true);
    }

    private void updateGameBoard() {

        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {

                CellState cellState = gameBoard.getCell(i, j);

                if (cellState == CellState.CLOSE) {
                    cells[i][j].setIcon(iconClose);
                    cells[i][j].setEnabled(true);
                } else if (cellState == CellState.FLAG) {
                    cells[i][j].setIcon(iconFlag);
                    cells[i][j].setEnabled(true);
                } else if (cellState == CellState.QUERY) {
                    cells[i][j].setIcon(iconQuery);
                    cells[i][j].setEnabled(true);
                } else if (cellState == CellState.FREE) {
                    cells[i][j].setDisabledIcon(iconClose);
                    cells[i][j].setEnabled(false);
                } else if (cellState == CellState.N1) {
                    cells[i][j].setDisabledIcon(icon1);
                    cells[i][j].setEnabled(false);
                } else if (cellState == CellState.N2) {
                    cells[i][j].setDisabledIcon(icon2);
                    cells[i][j].setEnabled(false);
                } else if (cellState == CellState.N3) {
                    cells[i][j].setDisabledIcon(icon3);
                    cells[i][j].setEnabled(false);
                } else if (cellState == CellState.N4) {
                    cells[i][j].setDisabledIcon(icon4);
                    cells[i][j].setEnabled(false);
                } else if (cellState == CellState.N5) {
                    cells[i][j].setDisabledIcon(icon5);
                    cells[i][j].setEnabled(false);
                } else if (cellState == CellState.N6) {
                    cells[i][j].setDisabledIcon(icon6);
                    cells[i][j].setEnabled(false);
                } else if (cellState == CellState.N7) {
                    cells[i][j].setDisabledIcon(icon7);
                    cells[i][j].setEnabled(false);
                } else if (cellState == CellState.N8) {
                    cells[i][j].setDisabledIcon(icon8);
                    cells[i][j].setEnabled(false);
                } else if (cellState == CellState.MINE) {
                    cells[i][j].setDisabledIcon(iconMine);
                    cells[i][j].setEnabled(false);
                } else if (cellState == CellState.DETONATION) {
                    cells[i][j].setDisabledIcon(iconDetonation);
                    cells[i][j].setEnabled(false);
                }
            }
        }

        updateTopPanel();

        gameBoardPanel.updateUI();

        checkGameStatus();
    }

    private void updateTopPanel() {
        flagLabel.setText(flagStr + numFlags + " / " + numMines);
        actionLabel.setText(actionStr + numActions);
    }

    private void updateTime() {
        if (gameStatus.isGame()) {
            long time = Instant.now().getEpochSecond() - startTime.getEpochSecond();

            int min = (int) (time / 60);
            int sec = (int) (time % 60);

            if (min > 0) {
                timeLabel.setText(timeStr + min + " мин " + sec + " сек");
            } else {
                timeLabel.setText(timeStr + sec + " сек");
            }
        }
    }

    private void startTimer() {
        startTime = listener.getStartTime();

        timer.start();
        updateTime();
    }

    private void checkGameStatus() {
        if (gameStatus == GameStatus.ENDED_WITH_STOP) {
            showMessageEndWithStop();
        } else if (gameStatus == GameStatus.ENDED_WITH_LOSS) {
            showMessageEndWithLoss();
        } else if (gameStatus == GameStatus.ENDED_WITH_WIN) {
            timer.stop();
            showMessageEndWithWin();
            showSaveDialog();
        }
    }

    private void showMessageEndWithStop() {
        JOptionPane.showMessageDialog(frame,
                "Игра прервана пользователем!",
                "Игра окончена",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showMessageEndWithLoss() {
        JOptionPane.showMessageDialog(frame,
                "Вы проиграли!",
                "Игра окончена",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showMessageEndWithWin() {
        JOptionPane.showMessageDialog(frame,
                "Поздравляем! Вы победили!",
                "Игра окончена",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showErrorMessage(Window fromFrame, String message) {
        JOptionPane.showMessageDialog(fromFrame,
                message,
                "Ошибка",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showSaveDialog() {

        final int index = indexOfGameSize();

        GameInfo gameInfo = listener.getWinGameInfo();

        if (index == -1 || gameInfo == null) {
            return;
        }

        JDialog dialog = new JDialog(frame);

        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setTitle("Сохранение результата игры");
        dialog.setModal(true);
        dialog.setSize(320, 240);
        dialog.setMinimumSize(dialog.getSize());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        panel.add(new JLabel("Размер доски X*Y :"));
        panel.add(new JLabel(xSize + "*" + ySize));

        panel.add(new JLabel("Число мин :"));
        panel.add(new JLabel(String.valueOf(numMines)));

        panel.add(new JLabel("Число действий :"));
        panel.add(new JLabel(String.valueOf(gameInfo.getNumActions())));

        panel.add(new JLabel("Время игры :"));
        panel.add(new JLabel(String.valueOf(gameInfo.getTime()) + " сек"));

        JTextField inputName = new JTextField();
        JButton buttonCancel = new JButton("Отмена");
        JButton buttonOk = new JButton("Применить");

        panel.add(new JLabel("Введите имя :"));
        panel.add(inputName);

        panel.add(buttonCancel);
        panel.add(buttonOk);

        Pattern pattern = Pattern.compile("[a-zA-Z0-9_-]+");

        buttonCancel.addActionListener(e -> dialog.setVisible(false));

        buttonOk.addActionListener(e -> {

            String userName = inputName.getText();

            Matcher matcher = pattern.matcher(userName);

            GameInfo gameInformation = listener.getWinGameInfo();

            if (gameInformation == null) {
                System.out.println("gameInformation == null");
                return;
            }

            if (userName.isEmpty() || userName.equals(" ")) {
                gameInformation.setUserName("anonymous");
            } else {
                if (matcher.matches()) {
                    gameInformation.setUserName(userName);
                } else {
                    showErrorMessage(dialog, "Имя может состоять только из букв, чисел, знаков трие и подчёркивания!");
                    return;
                }
            }

            listener.saveWinGameInfo(gameInformation);

            dialog.setVisible(false);

            menuScoresSize[index].doClick();
        });

        dialog.add(panel);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private int indexOfGameSize() {
        int index = -1;

        for (int i = 0; i < standardGameSizes.length; i++) {
            if ((xSize == standardGameSizes[i].getXSize())
                    && (ySize == standardGameSizes[i].getYSize())
                    && (numMines == standardGameSizes[i].getNumMines())) {
                index = i;
                break;
            }
        }

        return index;
    }

    private void setNewGame() {
        timer.stop();
        timeLabel.setText(timeStr);

        gameBoard = listener.startGame(xSize, ySize, numMines);
        gameStatus = listener.getGameStatus();

        numActions = 0;
        numFlags = 0;

        updateTopPanel();
        initGameBoard();
        setFrameSize();

        gameBoardPanel.updateUI();
        frame.repaint();
    }
}
