package ru.academit.ilnitsky.minesweeper.gui;

import ru.academit.ilnitsky.minesweeper.common.CellState;
import ru.academit.ilnitsky.minesweeper.common.GameBoard;
import ru.academit.ilnitsky.minesweeper.common.ViewListener;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;

/**
 * Панель информации для Swing-версии игры "Сапёр"
 * Created by UserLabView on 01.03.17.
 */
class GameInfoPanel extends JPanel {
    private ViewListener core;

    private GameBoard gameBoard;

    private static final String timeStr = "Время: ";
    private static final String flagStr = "Флагов: ";
    private static final String actionStr = "Действий: ";

    private final JLabel timeLabel = new JLabel(timeStr);
    private final JLabel flagLabel = new JLabel(flagStr);
    private final JLabel actionLabel = new JLabel(actionStr);

    private Instant startTime;
    private Timer timer = new Timer(100, l -> updateTime());

    GameInfoPanel() {
        setLayout(new FlowLayout(FlowLayout.LEADING, 16, 8));

        Font groupFont = new Font("TimesRoman", Font.BOLD, 14);

        timeLabel.setFont(groupFont);
        flagLabel.setFont(groupFont);
        actionLabel.setFont(groupFont);

        add(timeLabel);
        add(flagLabel);
        add(actionLabel);

        setVisible(true);
    }

    void addViewListener(ViewListener listener) {
        core = listener;
    }

    void removeViewListener() {
        this.core = null;
    }

    void init(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    void update() {
        actionLabel.setText(actionStr + core.getNumActions());
        flagLabel.setText(flagStr + gameBoard.getNumCells(CellState.FLAG) + " / " + core.getGameNumMines());
    }

    private void updateTime() {
        if (core.getGameStatus().isGame()) {
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

    void startTimer() {
        startTime = core.getStartTime();

        timer.start();
        updateTime();
    }

    void stopTimer() {
        timer.stop();
    }

    void clearTime() {
        timeLabel.setText(timeStr);
    }
}
