package ru.academit.ilnitsky.huffman.alphabet;

import java.util.Arrays;

/**
 * Выборщик символов произвольной длины
 * Отсеивает лишние символы
 * Created by Mike on 27.03.2017.
 */
class RatedSymbolSelector {
    private RatedSymbol[][] symbols;
    private int threshold;

    RatedSymbolSelector(int maxLength, int threshold) {
        symbols = new RatedSymbol[maxLength][];
        this.threshold = threshold;
    }

    void addSymbols(int length, RatedSymbol[] ratedSymbols) {
        if (ratedSymbols.length > 0) {
            symbols[length] = ratedSymbols;
        }
    }

    void addSymbols(int length, NumByteAccumulator numByteAccumulator) {
        int size = numByteAccumulator.getNumberSymbolsInAlphabet(threshold);

        if (size > 0) {
            symbols[length] = numByteAccumulator.getRatedSymbols(threshold);

            if (symbols[length].length != size) {
                throw new IllegalArgumentException("symbols[" + length + "].length(" + symbols[length].length + ") != size(" + size + ")");
            }
        }
    }

    void sortByDepthAndRate() {
        for (RatedSymbol[] ss : symbols) {
            if (ss != null) {
                Arrays.sort(ss);
            }
        }
    }

    void sortByRate() {
        SortedByRate comparator = new SortedByRate();
        for (RatedSymbol[] ss : symbols) {
            if (ss != null) {
                Arrays.sort(ss, comparator);
            }
        }
    }

    void setDepth() {
        for (int i = symbols.length - 1; i >= 2; i--) {
            if (symbols[i] != null && symbols[i - 1] != null) {
                for (int j = 0; j < symbols[i].length; j++) {
                    for (int k = 0; k < symbols[i - 1].length; k++) {
                        if (symbols[i][j].indexOfContained(symbols[i - 1][k]) > -1) {
                            symbols[i - 1][k].setDepth(symbols[i][j].getDepth() + 1);
                        }
                    }
                }
            }
        }
    }

    void setRates() {
        for (int i = 3; i < symbols.length; i++) {
            if (symbols[i] != null && symbols[i - 1] != null) {
                for (int j = symbols[i].length - 1; j >= 0; j--) {
                    for (int k = symbols[i - 1].length - 1; k >= 0; k--) {

                        if (symbols[i - 1][k].getDepth() == 0) {
                            break;
                        }

                        if (symbols[i][j].indexOfContained(symbols[i - 1][k]) > -1) {
                            if (symbols[i][j].getRate() <= symbols[i - 1][k].getRate()) {
                                symbols[i - 1][k].setRate(symbols[i - 1][k].getRate() - symbols[i][j].getRate());
                            }
                        }
                    }
                }
            }
        }

        for (int i = 2; i < symbols.length; i++) {
            if (symbols[i] != null) {
                int threshold2 = threshold / i;
                for (int j = symbols[i].length - 1; j >= 0; j--) {
                    if (symbols[i][j].getRate() < threshold2) {
                        symbols[i][j].setRate(0);
                    }
                }
            }
        }

        sortByRate();

        int shift = 128;
        RatedSymbol[] cash = new RatedSymbol[256];
        for (int i = 0; i < symbols[1].length; i++) {
            cash[symbols[1][i].getSymbol()[0] + shift] = symbols[1][i];
        }

        for (int i = symbols.length - 1; i >= 2; i--) {
            if (symbols[i] != null) {
                for (int j = symbols[i].length - 1; j >= 0; j--) {
                    byte[] bytes = symbols[i][j].getSymbol();
                    int rate = symbols[i][j].getRate();

                    boolean key = false;
                    for (byte b : bytes) {
                        int index = b + shift;
                        if (cash[index] != null) {
                            if (cash[index].getRate() < rate) {
                                key = true;
                                rate = cash[index].getRate();
                            }
                        } else {
                            throw new IllegalArgumentException("Unknown symbol: [" + (char) b + "]=" + b);
                        }
                    }

                    if (key) {
                        symbols[i][j].setRate(rate);
                    }

                    if (rate > threshold / i) {
                        for (byte b : bytes) {
                            int index = b + shift;
                            cash[index].setRate(cash[index].getRate() - rate);
                        }
                    } else {
                        symbols[i][j].setRate(0);
                    }
                }
            }

        }

        sortByRate();
    }

    private int numSymbols() {
        int count = 0;

        for (RatedSymbol[] ss : symbols) {
            if (ss != null) {
                for (RatedSymbol s : ss) {
                    if (s != null && s.rate > 0) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    AlphabetSymbol[] getAlphabet() {
        AlphabetSymbol[] alphabetSymbol = new AlphabetSymbol[numSymbols()];
        int count = 0;

        for (RatedSymbol[] ss : symbols) {
            if (ss != null) {
                for (RatedSymbol s : ss) {
                    if (s != null && s.rate > 0) {
                        alphabetSymbol[count] = new AlphabetSymbol(s);
                        count++;
                    }
                }
            }
        }

        return alphabetSymbol;
    }

    void print() {
        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i] != null) {
                int count = 0;
                for (int j = 0; j < symbols[i].length; j++) {
                    if (symbols[i][j] != null && symbols[i][j].getRate() > 0) {
                        count++;
                    }
                }
                System.out.println(i + "-byte Symbols: " + count);
                for (int j = 0; j < symbols[i].length; j++) {
                    if (symbols[i][j] != null && symbols[i][j].getRate() > 0) {
                        System.out.println(symbols[i][j]);
                    }
                }
            }
        }
    }
}
