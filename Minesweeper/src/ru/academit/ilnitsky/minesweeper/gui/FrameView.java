package ru.academit.ilnitsky.minesweeper.gui;

import ru.academit.ilnitsky.minesweeper.common.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

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

    private String warning = "";

    private LastCommands lastCommands = new LastCommands(5);

    private final int topLength;

    private final GameSize[] standardGameSizes;
    private final String[] standardGameNames;

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

            gameStatus = GameStatus.NONE;

            initPanels();
            initFrame();
        });
    }

    private void initPanels() {

    }

    private void initFrame() {

    }

    private void setView() {

    }
}
