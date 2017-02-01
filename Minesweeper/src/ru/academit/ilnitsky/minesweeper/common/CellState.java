package ru.academit.ilnitsky.minesweeper.common;

/**
 * Состояние ячейки игровой доски игры "Минёр"
 * Created by Mike on 02.02.2017.
 */
public enum CellState {
    DETONATION(-10),
    MINE(-5),
    CLOSE(-1),
    FREE(0),
    N1(1), N2(2), N3(3), N4(4), N5(5), N6(6), N7(7), N8(8),
    FLAG(10);

    private int state;

    CellState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public static CellState setState(int state) {
        switch (state) {
            case -10:
                return DETONATION;
            case -5:
                return MINE;
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
            case 10:
                return FLAG;
            default:
                throw new IllegalArgumentException("Unknown Value");
        }
    }
}
