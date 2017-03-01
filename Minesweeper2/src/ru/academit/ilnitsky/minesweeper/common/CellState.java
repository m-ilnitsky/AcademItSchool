package ru.academit.ilnitsky.minesweeper.common;

/**
 * Состояние ячейки игровой доски игры "Сапёр"
 * Created by Mike on 02.02.2017.
 */
public enum CellState {
    DETONATION(-7),
    MINE(-5),
    FLAG(-3),
    QUERY(-2),
    CLOSE(-1),
    FREE(0),
    N1(1), N2(2), N3(3), N4(4), N5(5), N6(6), N7(7), N8(8);

    private final int value;

    CellState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isNumber() {
        return (value >= 1 && value <= 8);
    }

    public static CellState state(int value) {
        switch (value) {
            case -7:
                return DETONATION;
            case -5:
                return MINE;
            case -3:
                return FLAG;
            case -2:
                return QUERY;
            case -1:
                return CLOSE;
            case 0:
                return FREE;
            case 1:
                return N1;
            case 2:
                return N2;
            case 3:
                return N3;
            case 4:
                return N4;
            case 5:
                return N5;
            case 6:
                return N6;
            case 7:
                return N7;
            case 8:
                return N8;
            default:
                throw new IllegalArgumentException("Unknown Value");
        }
    }
}
