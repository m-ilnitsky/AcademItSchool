package ru.academit.ilnitsky.minesweeper.gui;

import ru.academit.ilnitsky.minesweeper.common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Игровая доска для Swing-версии игры "Сапёр"
 * Created by UserLabView on 01.03.17.
 */
class GameBoardPanel extends JPanel {
    private class CellData {
        private ImageIcon icon;
        private boolean isEnabled;

        CellData(ImageIcon icon, boolean isEnabled) {
            this.icon = icon;
            this.isEnabled = isEnabled;
        }

        ImageIcon getIcon() {
            return icon;
        }

        boolean isEnabled() {
            return isEnabled;
        }
    }

    private HashMap<CellState, CellData> cellMap;

    private ViewListener core;

    private GameBoard gameBoard;
    private GameInfoPanel gameInfoPanel;
    private FrameView frameView;

    private int xSize;
    private int ySize;

    private GameStatus gameStatus;

    private long lastLeftPressed;
    private long lastRightPressed;

    private JButton[][] cells;

    private static final int iconSize = 32;

    private static final String path = "/ru/academit/ilnitsky/minesweeper/resources/";

    private ImageIcon getIcon(String namePng) {
        return new ImageIcon(getClass().getResource(path + namePng));
    }

    GameBoardPanel(FrameView frameView, GameInfoPanel gameInfoPanel) {
        this.frameView = frameView;
        this.gameInfoPanel = gameInfoPanel;

        ImageIcon iconClose = getIcon("close.png");
        ImageIcon iconQuery = getIcon("query.png");
        ImageIcon iconFlag = getIcon("flag.png");
        ImageIcon icon1 = getIcon("1.png");
        ImageIcon icon2 = getIcon("2.png");
        ImageIcon icon3 = getIcon("3.png");
        ImageIcon icon4 = getIcon("4.png");
        ImageIcon icon5 = getIcon("5.png");
        ImageIcon icon6 = getIcon("6.png");
        ImageIcon icon7 = getIcon("7.png");
        ImageIcon icon8 = getIcon("8.png");
        ImageIcon iconMine = getIcon("mine.png");
        ImageIcon iconDetonation = getIcon("detonation.png");

        cellMap = new HashMap<>(16);

        cellMap.put(CellState.CLOSE, new CellData(iconClose, true));
        cellMap.put(CellState.FLAG, new CellData(iconFlag, true));
        cellMap.put(CellState.QUERY, new CellData(iconQuery, true));

        cellMap.put(CellState.MINE, new CellData(iconMine, false));
        cellMap.put(CellState.DETONATION, new CellData(iconDetonation, false));
        cellMap.put(CellState.FREE, new CellData(iconClose, false));

        cellMap.put(CellState.N1, new CellData(icon1, false));
        cellMap.put(CellState.N2, new CellData(icon2, false));
        cellMap.put(CellState.N3, new CellData(icon3, false));
        cellMap.put(CellState.N4, new CellData(icon4, false));
        cellMap.put(CellState.N5, new CellData(icon5, false));
        cellMap.put(CellState.N6, new CellData(icon6, false));
        cellMap.put(CellState.N7, new CellData(icon7, false));
        cellMap.put(CellState.N8, new CellData(icon8, false));
    }

    void addViewListener(ViewListener listener) {
        core = listener;
    }

    void removeViewListener() {
        core = null;
    }

    void init(GameBoard gameBoard) {
        this.gameBoard = gameBoard;

        xSize = gameBoard.getSize().getXSize();
        ySize = gameBoard.getSize().getYSize();

        gameStatus = core.getGameStatus();

        lastLeftPressed = lastRightPressed = System.currentTimeMillis();

        removeAll();

        setLayout(new GridLayout(ySize, xSize));

        cells = new JButton[xSize][ySize];

        ImageIcon iconClose = cellMap.get(CellState.CLOSE).getIcon();

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
                    public void mousePressed(MouseEvent e) {
                        CellState cellState = gameBoard.getCell(xValue, yValue);

                        long now = System.currentTimeMillis();
                        long rightPressed = now - lastRightPressed;
                        long leftPressed = now - lastLeftPressed;

                        gameStatus = core.getGameStatus();

                        if (e.getButton() == MouseEvent.BUTTON2
                                || (e.getButton() == MouseEvent.BUTTON1 && (rightPressed < 200))
                                || (e.getButton() == MouseEvent.BUTTON3 && (leftPressed < 200))) {

                            if (gameStatus.isContinued() && cellState.isNumber()) {

                                if (e.getButton() == MouseEvent.BUTTON2
                                        || (e.getButton() == MouseEvent.BUTTON1 && (rightPressed < 50))
                                        || (e.getButton() == MouseEvent.BUTTON3 && (leftPressed < 50))) {

                                    core.setOpenAllAround(xValue, yValue);
                                    gameStatus = core.getGameStatus();
                                    update();
                                }
                            }
                        } else if (e.getButton() == MouseEvent.BUTTON1) {

                            lastLeftPressed = System.currentTimeMillis();

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

                            lastRightPressed = System.currentTimeMillis();

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
                CellData cellData = cellMap.get(gameBoard.getCell(i, j));

                cells[i][j].setEnabled(cellData.isEnabled());

                if (cellData.isEnabled()) {
                    cells[i][j].setIcon(cellData.getIcon());
                } else {
                    cells[i][j].setDisabledIcon(cellData.getIcon());
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
                    JOptionPane.showMessageDialog(dialog,
                            "Имя может состоять только из латинских букв, чисел, знаков трие и подчёркивания!",
                            "Ошибка",
                            JOptionPane.INFORMATION_MESSAGE);
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
