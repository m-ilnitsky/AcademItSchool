package ru.academit.ilnitsky.minesweeper.common;

import java.time.Instant;

/**
 * Интерфейс представления View для игры "Сапёр"
 * Created by UserLabView on 02.02.17.
 */
public interface View {

    void addViewListener(ViewListener listener);

    void removeViewListener(ViewListener listener);

    void startApplication();

    void onGameStart(Instant startTime);

    void onGameStep(GameStatus gameStatus);

    void onSaveResult(GameInfo gameInfo);
}
