package ru.academit.ilnitsky.huffman;

/**
 * Created by UserLabView on 06.03.17.
 */
public class RepeatCharArray {
    private RepeatCharSymbol[][] symbols;

    public RepeatCharArray() {
        symbols = new RepeatCharSymbol[65536][];
    }

    public void add(char symbol, int length) {
        if (symbols[symbol] == null) {
            symbols[symbol] = new RepeatCharSymbol[2];
            symbols[symbol][0] = new RepeatCharSymbol(symbol, length);
        } else {
            int index = -1;
            int firstNull = -1;

            for (int i = 0; i < symbols[symbol].length; i++) {
                if (symbols[symbol][i] == null) {
                    firstNull = i;
                    break;
                } else if (symbols[symbol][i].is(symbol, length)) {
                    index = i;
                    break;
                }
            }

            if (index == -1) {
                if (firstNull == -1) {
                    int oldArrayLength = symbols[symbol].length;

                    RepeatCharSymbol[] newArray = new RepeatCharSymbol[oldArrayLength * 2];
                    System.arraycopy(symbols[symbol], 0, newArray, 0, oldArrayLength);

                    symbols[symbol] = newArray;
                    symbols[symbol][oldArrayLength] = new RepeatCharSymbol(symbol, length);
                } else {
                    symbols[symbol][firstNull] = new RepeatCharSymbol(symbol, length);
                }
            } else {
                symbols[symbol][index].add(symbol, length);
            }
        }
    }

    public int getRate(char symbol, int length) {
        int index = -1;

        for (int i = 0; i < symbols[symbol].length; i++) {
            if (symbols[symbol][i] == null) {
                break;
            } else if (symbols[symbol][i].is(symbol, length)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return 0;
        } else {
            return symbols[symbol][index].getRate();
        }
    }

    public int getRate(byte symbol) {
        int sum = 0;

        for (RepeatCharSymbol s : symbols[symbol]) {
            if (s == null) {
                break;
            } else {
                sum += s.getRate() * s.getLength();
            }
        }

        return sum;
    }

    public RepeatCharSymbol[] getRepeatSymbol(char symbol) {
        return symbols[symbol];
    }
}
