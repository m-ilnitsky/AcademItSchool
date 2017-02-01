package ru.academit.ilnitsky.minesweeper.common;

/**
 * Позиция на доске
 * Created by Mike on 02.02.2017.
 */
public class Position {
    private int x;
    private int y;

    public Position(Position position) {
        this(position.x, position.y);
    }

    public Position(int x, int y) {
        set(x, y);
    }

    public void set(int x, int y) {
        if (x < 0) {
            throw new IllegalArgumentException("xPosition < 0");
        }
        if (y < 0) {
            throw new IllegalArgumentException("yPosition < 0");
        }

        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        if (x < 0) {
            throw new IllegalArgumentException("xPosition < 0");
        }

        this.x = x;
    }

    public void setY(int y) {
        if (y < 0) {
            throw new IllegalArgumentException("yPosition < 0");
        }

        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
