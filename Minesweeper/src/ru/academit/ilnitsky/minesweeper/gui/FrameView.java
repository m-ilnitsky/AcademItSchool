package ru.academit.ilnitsky.minesweeper.gui;

import ru.academit.ilnitsky.minesweeper.common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * View с графическим интерфейсом пользователя (GUI) для игры "Сапёр"
 * Created by UserLabView on 21.02.17.
 */
public class FrameView implements ViewAutoCloseable {
    private final ArrayList<ViewListener> listeners = new ArrayList<>();

    private final JFrame frame = new JFrame("Сапёр");

    private GameStatus gameStatus;
    private GameBoard gameBoard;

    private int xSize;
    private int ySize;
    private int numMines;

    private int numFlags;
    private int numActions;

    private LastCommands lastCommands = new LastCommands(5);

    private final int topLength;

    private final GameSize[] standardGameSizes;
    private final String[] standardGameNames;

    private final JMenuBar menuBar = new JMenuBar();
    private final JPanel topPanel = new JPanel();
    private final JPanel gameBoardPanel = new JPanel();

    private static final String timeStr = " Время: ";
    private static final String flagStr = " Установлено флагов: ";
    private static final String actionStr = " Выполнено действий: ";
    private static final String commandStr = " Последние команды: ";

    private final JLabel timeLabel = new JLabel(timeStr);
    private final JLabel flagLabel = new JLabel(flagStr);
    private final JLabel actionLabel = new JLabel(actionStr);
    private final JLabel commandLabel = new JLabel(commandStr);

    private JButton[][] cells;

    private static final int iconSize = 32;

    private static final String path = ".\\Minesweeper\\src\\ru\\academit\\ilnitsky\\minesweeper\\resources\\";
    private static final ImageIcon iconClose = new ImageIcon(path + "close.png");
    private static final ImageIcon iconQuery = new ImageIcon(path + "query.png");
    private static final ImageIcon iconFlag = new ImageIcon(path + "flag.png");
    private static final ImageIcon icon1 = new ImageIcon(path + "1.png");
    private static final ImageIcon icon2 = new ImageIcon(path + "2.png");
    private static final ImageIcon icon3 = new ImageIcon(path + "3.png");
    private static final ImageIcon icon4 = new ImageIcon(path + "4.png");
    private static final ImageIcon icon5 = new ImageIcon(path + "5.png");
    private static final ImageIcon icon6 = new ImageIcon(path + "6.png");
    private static final ImageIcon icon7 = new ImageIcon(path + "7.png");
    private static final ImageIcon icon8 = new ImageIcon(path + "8.png");
    private static final ImageIcon iconMine = new ImageIcon(path + "mine.png");
    private static final ImageIcon iconDetonation = new ImageIcon(path + "detonation.png");

    public FrameView(int topLength, GameSize[] standardGameSizes, String[] standardGameNames) {

        this.standardGameSizes = standardGameSizes;
        this.standardGameNames = standardGameNames;

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

            for (ViewListener listener : listeners) {
                gameBoard = listener.startGame(xSize, ySize, numMines);
                gameStatus = listener.getGameStatus();
            }

            initMenuBar();
            initTopPanel();
            initGameBoard();
            initFrame();

            updateTopPanel();
        });
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
        JMenuItem menuCustom = new JMenuItem("Произвольные параметры");
        buttonGroup.add(menuCustom);

        menuSize[0].doClick();

        for (int i = 0; i < standardGameNames.length; i++) {
            Integer index = i;
            menuSize[i].addActionListener(e -> {
                xSize = standardGameSizes[index].getXSize();
                ySize = standardGameSizes[index].getYSize();
                numMines = standardGameSizes[index].getNumMines();

                for (ViewListener listener : listeners) {
                    gameBoard = listener.startGame(xSize, ySize, numMines);
                    gameStatus = listener.getGameStatus();
                }

                lastCommands.clear();
                numActions = 0;
                numFlags = 0;

                updateTopPanel();
                initGameBoard();
                setFrameSize();

                gameBoardPanel.updateUI();
                frame.repaint();
            });
        }

        menuNew.addActionListener(e -> {
            for (ViewListener listener : listeners) {
                gameBoard = listener.startGame(xSize, ySize, numMines);
                gameStatus = listener.getGameStatus();
            }

            lastCommands.clear();
            numActions = 0;
            numFlags = 0;

            updateTopPanel();
            initGameBoard();
            setFrameSize();

            gameBoardPanel.updateUI();
            frame.repaint();
        });

        menuStop.addActionListener(e -> {
            for (ViewListener listener : listeners) {
                listener.stopGame();
                gameStatus = listener.getGameStatus();
            }
        });

        menuExit.addActionListener(e -> {
            try {
                close();
            } catch (Exception exception) {
                exception.getStackTrace();
            }
        });

        menuFile.add(menuNew);
        for (JRadioButtonMenuItem menu : menuSize) {
            menuFile.add(menu);
        }
        menuFile.add(menuCustom);
        menuFile.add(menuStop);
        menuFile.add(menuExit);

        return menuFile;
    }

    private JMenu createMenuScores() {
        JMenu menuScores = new JMenu("Рекорды");

        JMenuItem menuSize[] = new JMenuItem[standardGameNames.length];

        for (int i = 0; i < standardGameNames.length; i++) {
            menuSize[i] = new JMenuItem(standardGameNames[i]);
            menuScores.add(menuSize[i]);

            menuSize[i].addActionListener(e -> {

            });
        }

        return menuScores;
    }

    private JMenu createMenuAbout() {
        JMenu menuAbout = new JMenu("О программе");

        menuAbout.addActionListener(e -> {

        });

        return menuAbout;
    }

    private void initTopPanel() {
        topPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.weightx = 0.5;
        constraints.gridy = 0;    // нулевая ячейка по вертикали

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;      // нулевая ячейка таблицы по горизонтали
        topPanel.add(timeLabel, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;      // первая ячейка таблицы по горизонтали
        topPanel.add(actionLabel, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 2;      // вторая ячейка таблицы по горизонтали
        topPanel.add(flagLabel, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridwidth = 3;    // размер элемента в три ячейки
        constraints.gridx = 0;    // нулевая ячейка по горизонтали
        constraints.gridy = 1;    // первая ячейка по вертикали
        topPanel.add(commandLabel, constraints);

        topPanel.setVisible(true);
    }

    private void initFrame() {
        frame.setJMenuBar(menuBar);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(gameBoardPanel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setFrameSize();

        frame.setVisible(true);
    }

    private void setFrameSize() {
        final int minStartWidth = 560;

        int width = xSize * (iconSize + 2);
        int height = ySize * (iconSize + 2) + (int) topPanel.getSize().getHeight();

        if (width < minStartWidth) {
            width = minStartWidth;

            if (ySize * minStartWidth / xSize < 640) {
                height = (int) (ySize * minStartWidth / xSize + topPanel.getSize().getHeight());
            }
        }

        frame.setMinimumSize(new Dimension(xSize * (iconSize + 2), ySize * (iconSize + 2) + 48));

        frame.setSize(width, height);

        // заставляет фрейм располагаться по центру экрана при запуске
        frame.setLocationRelativeTo(null);
    }

    private void initGameBoard() {
        gameBoardPanel.removeAll();

        gameBoardPanel.setLayout(new GridLayout(ySize, xSize));

        cells = new JButton[xSize][ySize];

        for (int i = 0; i < xSize; i++) {
            cells[i] = new JButton[ySize];

            for (int j = 0; j < ySize; j++) {
                cells[i][j] = new JButton();
                cells[i][j].setSize(iconSize, iconSize);
                cells[i][j].setMaximumSize(cells[i][j].getSize());
                cells[i][j].setMinimumSize(cells[i][j].getSize());
                cells[i][j].setIconTextGap(0);
                cells[i][j].setText("");
                cells[i][j].setHorizontalTextPosition(SwingConstants.LEFT);
                cells[i][j].setVerticalTextPosition(SwingConstants.TOP);
                cells[i][j].setVisible(true);

                final Integer xValue = i;
                final Integer yValue = j;

                cells[i][j].addActionListener((e) -> {
                    CellState cellState = gameBoard.getCell(xValue, yValue);

                    if (gameStatus.isGame() && cellState == CellState.CLOSE || cellState == CellState.QUERY) {
                        try {
                            for (ViewListener listener : listeners) {
                                listener.setOpen(xValue, yValue);
                                gameStatus = listener.getGameStatus();
                                numActions = listener.getNumActions();
                            }
                            if (gameStatus == GameStatus.STARTED) {
                                Thread.sleep(5000);
                            }
                            lastCommands.add("Open(" + (xValue + 1) + "," + (yValue + 1) + ")");
                            numFlags = gameBoard.getNumCells(CellState.FLAG);
                            updateGameBoard();
                        } catch (InterruptedException exception) {
                            exception.printStackTrace();
                        }
                    }
                });

                cells[i][j].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        CellState cellState = gameBoard.getCell(xValue, yValue);

                        if (gameStatus.isContinued() && e.getButton() == MouseEvent.BUTTON3) {
                            if (cellState == CellState.CLOSE
                                    || cellState == CellState.QUERY
                                    || cellState == CellState.FLAG) {
                                for (ViewListener listener : listeners) {
                                    listener.setFlag(xValue, yValue);
                                }
                                lastCommands.add("Flag(" + (xValue + 1) + "," + (yValue + 1) + ")");
                                numFlags = gameBoard.getNumCells(CellState.FLAG);
                                updateGameBoard();
                            }
                        } else if (gameStatus.isGame() && e.getButton() == MouseEvent.BUTTON2) {
                            if (cellState.isNumber()) {
                                for (ViewListener listener : listeners) {
                                    listener.setOpenAllAround(xValue, yValue);
                                    gameStatus = listener.getGameStatus();
                                    numActions = listener.getNumActions();
                                }
                                lastCommands.add("AllAround(" + (xValue + 1) + "," + (yValue + 1) + ")");
                                numFlags = gameBoard.getNumCells(CellState.FLAG);
                                updateGameBoard();
                            }
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
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
    }

    private void updateTopPanel() {
        flagLabel.setText(flagStr + numFlags + " / " + numMines);
        actionLabel.setText(actionStr + numActions);
        commandLabel.setText(commandStr + lastCommands.toString());
    }
}
