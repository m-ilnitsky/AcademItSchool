package ru.academit.ilnitsky.huffman;

import java.util.Arrays;

/**
 * Created by Mike on 27.03.2017.
 */
public class AlphabetBuilder {
    protected AlphabetSymbol[][] symbols;
    protected int threshold;

    public AlphabetBuilder(int maxLength, int threshold) {
        symbols = new AlphabetSymbol[maxLength][];
        this.threshold = threshold;
    }

    public void addSymbols(int length, AlphabetSymbol[] alphabetSymbols) {
        if (alphabetSymbols.length > 0) {
            symbols[length] = alphabetSymbols;
        }
    }

    public void addSymbols(int length, NumByteArray numByteArray) {
        int size = numByteArray.getNumberSymbolsInAlphabet(threshold);

        if (size > 0) {
            symbols[length] = numByteArray.getAlphabetSymbols(threshold);

            if (symbols[length].length != size) {
                throw new IllegalArgumentException("symbols[" + length + "].length(" + symbols[length].length + ") != size(" + size + ")");
            }
        }
    }

    public void sortByDepthAndRate() {
        SortedByDepthAndRate comparator = new SortedByDepthAndRate();
        for (AlphabetSymbol[] ss : symbols) {
            if (ss != null) {
                Arrays.sort(ss, comparator);
            }
        }
    }

    public void sortByRate() {
        for (AlphabetSymbol[] ss : symbols) {
            if (ss != null) {
                Arrays.sort(ss);
            }
        }
    }

    public void setDepth() {
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

    public void setRates() {
        for (int i = 3; i < symbols.length; i++) {
            if (symbols[i] != null && symbols[i - 1] != null) {
                for (int j = symbols[i].length - 1; j >= 0; j--) {
                    for (int k = symbols[i - 1].length - 1; k >= 0; k--) {

                        if (symbols[i - 1][k].getDepth() == 0) {
                            break;
                        }

                        if (symbols[i][j].indexOfContained(symbols[i - 1][k]) > -1) {
                            if (symbols[i][j].getRate() <= symbols[i - 1][k].getRate()) {
                                //System.out.println("v2  i=" + i + " j=" + j + " k=" + k);
                                //System.out.println(symbols[i][j]);
                                //System.out.println(symbols[i - 1][k]);
                                symbols[i - 1][k].setRate(symbols[i - 1][k].getRate() - symbols[i][j].getRate());
                                //System.out.println(symbols[i][j]);
                                //System.out.println(symbols[i - 1][k]);
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
        AlphabetSymbol[] cash = new AlphabetSymbol[256];
        for (int i = 0; i < symbols[1].length; i++) {
            cash[symbols[1][i].getSymbol()[0] + shift] = symbols[1][i];
        }

        for (int i = symbols.length - 1; i >= 2; i--) {
            if (symbols[i] != null) {
                for (int j = symbols[i].length - 1; j >= 0; j--) {
                    byte[] bytes = symbols[i][j].getSymbol();
                    int rate = symbols[i][j].getRate();

                    boolean key = false;
                    for (int k = 0; k < bytes.length; k++) {
                        int index = bytes[k] + shift;
                        if (cash[index] != null) {
                            if (cash[index].getRate() < rate) {
                                key = true;
                                rate = cash[index].getRate();
                            }
                        } else {
                            throw new IllegalArgumentException("Unknown symbol: [" + (char) bytes[k] + "]=" + bytes[k]);
                        }
                    }

                    if (key) {
                        symbols[i][j].setRate(rate);
                    }

                    if (rate > threshold / i) {
                        for (int k = 0; k < bytes.length; k++) {
                            int index = bytes[k] + shift;
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

    public int numSymbols() {
        int count = 0;

        for (AlphabetSymbol[] ss : symbols) {
            if (ss != null) {
                for (AlphabetSymbol s : ss) {
                    if (s != null && s.rate > 0) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public FinalSymbol[] getAlphabet() {
        FinalSymbol[] finalSymbol = new FinalSymbol[numSymbols()];
        int count = 0;

        for (AlphabetSymbol[] ss : symbols) {
            if (ss != null) {
                for (AlphabetSymbol s : ss) {
                    if (s != null && s.rate > 0) {
                        finalSymbol[count] = new FinalSymbol(s);
                        count++;
                    }
                }
            }
        }

        return finalSymbol;
    }

    public void print() {
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
