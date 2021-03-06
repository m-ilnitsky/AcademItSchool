package ru.academit.ilnitsky.huffman;

/**
 * Created by UserLabView on 06.03.17.
 */
public class RepeatCharSymbol implements RepeatSymbol {
    private char symbol;
    private int length;
    private int rate;

    public RepeatCharSymbol(char symbol, int length) {
        this(symbol, length, 1);
    }

    public RepeatCharSymbol(char symbol, int length, int rate) {
        checkLength(length);
        this.symbol = symbol;

        this.length = length;
        this.rate = rate;
    }

    public void add(char symbol, int length) {
        checkLength(length);
        if ((this.symbol == symbol) && (this.length == length)) {
            rate++;
        }
    }

    public void add() {
        rate++;
    }

    public void add(int number) {
        rate += number;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getLength() {
        return length;
    }

    public int getRate() {
        return rate;
    }

    public boolean is(char symbol, int length) {
        checkLength(length);
        return (this.symbol == symbol) && (this.length == length);
    }

    private void checkLength(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("length < 1");
        }
    }
}
