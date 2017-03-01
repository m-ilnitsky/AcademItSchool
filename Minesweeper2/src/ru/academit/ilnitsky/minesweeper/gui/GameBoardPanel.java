package ru.academit.ilnitsky.minesweeper.gui;

import ru.academit.ilnitsky.minesweeper.common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Игровая доска для Swing-версии игры "Сапёр"
 * Created by UserLabView on 01.03.17.
 */
class GameBoardPanel extends JPanel {
    private ViewListener core;

    private GameBoard gameBoard;
    private GameInfoPanel gameInfoPanel;
    private FrameView frameView;

    private int xSize;
    private int ySize;

    private GameStatus gameStatus;

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

    GameBoardPanel(FrameView frameView, GameInfoPanel gameInfoPanel) {
        this.frameView = frameView;
        this.gameInfoPanel = gameInfoPanel;
    }

    void addViewListener(ViewListener listener) {
        core = listener;
    }

    void removeViewListener() {
        this.core = null;
    }

    void init(GameBoard gameBoard) {
        this.gameBoard = gameBoard;

        xSize = gameBoard.getSize().getXSize();
        ySize = gameBoard.getSize().getYSize();

        gameStatus = core.getGameStatus();

        lastLeftClick = lastRightClick = System.currentTimeMillis();

        removeAll();

        setLayout(new GridLayout(ySize, xSize));

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

                        gameStatus = core.getGameStatus();

                        if (e.getButton() == MouseEvent.BUTTON2
                                || (e.getButton() == MouseEvent.BUTTON1 && (rightClick < 200))
                                || (e.getButton() == MouseEvent.BUTTON3 && (leftClick < 200))) {

                            if (gameStatus.isContinued() && cellState.isNumber()) {

                                if (e.getButton() == MouseEvent.BUTTON2
                                        || (e.getButton() == MouseEvent.BUTTON1 && (rightClick < 50))
                                        || (e.getButton() == MouseEvent.BUTTON3 && (leftClick < 50))) {

                                    core.setOpenAllAround(xValue, yValue);
                                    gameStatus = core.getGameStatus();
                                    update();
                                }
                            }
                        } else if (e.getButton() == MouseEvent.BUTTON1) {

                            lastLeftClick = System.currentTimeMillis();

                            if (gameStatus.isGame()
                                    && (cellState == CellState.CLOSE || cellState == CellState.QUERY)) {

                                core.setOpen(xValue, yValue);
                                gameStatus = core.getGameStatus();

                                if (core.getNumActions() == 1) {
                                    gameInfoPanel.startTimer();
                                }

                                update();
                            }

                        } else if (e.getButton() == MouseEvent.BUTTON3) {

                            lastRightClick = System.currentTimeMillis();

                            if (gameStatus.isContinued()
                                    && (cellState == CellState.CLOSE
                                    || cellState == CellState.QUERY
                                    || cellState == CellState.FLAG)) {

                                core.setFlag(xValue, yValue);
                                update();
                            }
                        }
                    }
                });
            }
        }

        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                add(cells[j][i]);
            }
        }

        setVisible(true);
    }

    void update() {

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

        gameInfoPanel.update();

        checkGameStatus();

        updateUI();
    }

    private void checkGameStatus() {
        gameStatus = core.getGameStatus();

        if (gameStatus == GameStatus.ENDED_WITH_STOP) {
            showMessageEndWithStop();

        } else if (gameStatus == GameStatus.ENDED_WITH_LOSS) {
            showMessageEndWithLoss();

        } else if (gameStatus == GameStatus.ENDED_WITH_WIN) {
            gameInfoPanel.stopTimer();
            showMessageEndWithWin();
            showSaveDialog();
        }
    }

    private void showMessageEndWithStop() {
        JOptionPane.showMessageDialog(frameView.getFrame(),
                "Игра прервана пользователем!",
                "Игра окончена",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showMessageEndWithLoss() {
        JOptionPane.showMessageDialog(frameView.getFrame(),
                "Вы проиграли!",
                "Игра окончена",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showMessageEndWithWin() {
        JOptionPane.showMessageDialog(frameView.getFrame(),
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

        GameInfo gameInfo = core.getWinGameInfo();

        if (!core.isTopScores(gameInfo)) {
            return;
        }

        JDialog dialog = new JDialog(frameView.getFrame());

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
        panel.add(new JLabel(String.valueOf(gameInfo.getNumMines())));

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

            GameInfo gameInformation = core.getWinGameInfo();

            if (gameInformation == null) {
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

            core.saveWinGameInfo(gameInformation);

            dialog.setVisible(false);

            frameView.showScoresForCurrentSize();
        });

        dialog.add(panel);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    int getIconSize() {
        return iconSize;
    }
}
