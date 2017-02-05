package ru.academit.ilnitsky.minesweeper.core;

/**
 * Статус игры "Сапёр"
 * Created by Mike on 05.02.2017.
 */
public enum GameStatus {
    NONE(0),
    STARTED(1),
    CONTINUED(2),
    ENDED_WITH_WIN(3),
    ENDED_WITH_STOP(4),
    ENDED_WITH_LOSS(5);

    private final int level;

    GameStatus(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public boolean isNone() {
        return this == NONE;
    }

    public boolean isEnded() {
        return this == ENDED_WITH_LOSS || this == ENDED_WITH_STOP || this == ENDED_WITH_WIN;
    }

    public boolean isStarted() {
        return this == STARTED;
    }

    public boolean isContinued() {
        return this == CONTINUED;
    }

    public boolean isGame() {
        return this == STARTED || this == CONTINUED;
    }

    public boolean isNoGame() {
        return this == NONE || this == ENDED_WITH_LOSS || this == ENDED_WITH_STOP || this == ENDED_WITH_WIN;
    }
}
