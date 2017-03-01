package ru.academit.ilnitsky.minesweeper.gui;

import ru.academit.ilnitsky.minesweeper.common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Графический интерфейс для Swing-версии игры "Сапёр"
 * Created by UserLabView on 1.03.17.
 */
public class FrameView implements ViewAutoCloseable {
    private ViewListener core;

    private final JFrame frame = new JFrame("Сапёр");

    private GameBoard gameBoard;

    private int xSize;
    private int ySize;
    private int numMines;

    private final int topLength;

    private final GameSize[] standardGameSizes;
    private final String[] standardGameNames;

    private final JMenuBar menuBar = new JMenuBar();
    private JMenuItem[] menuScoresSize = new JMenuItem[0];

    private CustomSize customSize;

    private GameInfoPanel gameInfoPanel;
    private GameBoardPanel gameBoardPanel;

    public FrameView(int topLength, GameSize[] standardGameSizes, String[] standardGameNames) {

        this.standardGameSizes = standardGameSizes;
        this.standardGameNames = standardGameNames;

        this.topLength = topLength;

        customSize = new CustomSize(frame);

        gameInfoPanel = new GameInfoPanel();
        gameBoardPanel = new GameBoardPanel(this, gameInfoPanel);
    }

    @Override
    public void addViewListener(ViewListener listener) {
        core = listener;

        gameInfoPanel.addViewListener(listener);
        gameBoardPanel.addViewListener(listener);
    }

    @Override
    public void removeViewListener(ViewListener listener) {
        if (core == listener) {
            core = null;

            gameInfoPanel.removeViewListener();
            gameBoardPanel.removeViewListener();
        }
    }

    @Override
    public void removeViewListener() {
        core = null;

        gameInfoPanel.removeViewListener();
        gameBoardPanel.removeViewListener();
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

            gameBoard = core.startGame(xSize, ySize, numMines);

            initMenuBar();
            initGameBoard();
            initFrame();

            gameInfoPanel.update();
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
            boolean isNewGameSize = customSize.showDialog(new GameSize(xSize, ySize, numMines));

            if (isNewGameSize) {
                xSize = customSize.getGameSize().getXSize();
                ySize = customSize.getGameSize().getYSize();
                numMines = customSize.getGameSize().getNumMines();

                setNewGame();
            }

            int index = core.getTopScoresIndex(new GameSize(xSize, ySize, numMines));

            if (index == -1) {
                buttonGroup.setSelected(menuCustom.getModel(), true);
            } else {
                buttonGroup.setSelected(menuSize[index].getModel(), true);
            }
        });

        menuStop.addActionListener(e -> {
            if (core.getGameStatus().isGame()) {
                core.stopGame();
                gameBoardPanel.update();
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

                gameArray = core.getTopScores(standardGameSizes[index]);
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

    private void initFrame() {
        frame.setJMenuBar(menuBar);

        frame.add(gameInfoPanel, BorderLayout.NORTH);
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

        int iconSize = gameBoardPanel.getIconSize() + 1;

        int width = xSize * iconSize;
        int height = ySize * iconSize + (int) gameInfoPanel.getSize().getHeight() + (int) menuBar.getSize().getHeight();

        frame.setMinimumSize(new Dimension(width, height));

        if (width < minStartWidth) {
            width = minStartWidth;

            if (ySize * minStartWidth / xSize < 640) {
                height = ySize * minStartWidth / xSize + 64;
            }
        }

        frame.setSize(width, height);

        frame.setLocationRelativeTo(null);
    }

    private void initGameBoard() {
        gameBoardPanel.init(gameBoard);
        gameInfoPanel.init(gameBoard);
    }

    void showScoresForCurrentSize() {
        final int index = core.getTopScoresIndex(new GameSize(xSize, ySize, numMines));

        if (index > -1) {
            menuScoresSize[index].doClick();
        }
    }

    JFrame getFrame() {
        return frame;
    }

    private void setNewGame() {
        gameInfoPanel.stopTimer();
        gameInfoPanel.clearTime();

        gameBoard = core.startGame(xSize, ySize, numMines);

        initGameBoard();
        setFrameSize();

        gameInfoPanel.update();

        gameBoardPanel.updateUI();
        frame.repaint();
    }
}
