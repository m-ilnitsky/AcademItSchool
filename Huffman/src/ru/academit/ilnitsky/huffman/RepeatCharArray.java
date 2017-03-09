package ru.academit.ilnitsky.huffman;

import java.util.Arrays;
import java.util.Comparator;

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
            symbols[symbol] = new RepeatCharSymbol[4];
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

    public int getRate(char symbol) {
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

    public int getRate() {
        int sum = 0;

        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i] != null) {
                sum += getRate((char) i);
            }
        }

        return sum;
    }

    public RepeatCharSymbol[] getRepeatSymbol(char symbol) {
        return symbols[symbol];
    }

    public int calcThresholdRate() {
        int[] rates = new int[symbols.length];

        int sum;

        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i] != null) {
                sum = 0;
                for (RepeatCharSymbol s : symbols[i]) {
                    if (s == null) {
                        break;
                    } else {
                        sum += s.getRate() * s.getLength();
                    }
                }
                rates[i] = sum;
            }
        }

        Arrays.sort(rates);

        //return (rates[rates.length - 1] + rates[rates.length / 2]) / 2;
        return rates[rates.length / 8 * 7];
    }

    public void removeSubThresholdLength(char symbol, int thresholdRate) {

        if (symbols[symbol] != null && symbols[symbol].length > 1) {
            RepeatCharSymbol[] aboveArray = new RepeatCharSymbol[symbols[symbol].length];
            RepeatCharSymbol[] subArray = new RepeatCharSymbol[symbols[symbol].length];

            int indexLength1 = -1;
            int aboveCount = 0;
            int subCount = 0;

            for (int i = 0; i < symbols[symbol].length; i++) {
                if (symbols[symbol][i] != null) {
                    int length = symbols[symbol][i].getLength();

                    if (length == 1) {
                        indexLength1 = aboveCount;
                        aboveArray[aboveCount] = symbols[symbol][i];
                        aboveCount++;
                    } else if (symbols[symbol][i].getLength() * symbols[symbol][i].getRate() > thresholdRate) {
                        aboveArray[aboveCount] = symbols[symbol][i];
                        aboveCount++;
                    } else {
                        subArray[subCount] = symbols[symbol][i];
                        subCount++;
                    }
                }
            }

            if (subCount > 0) {
                int sum = 0;

                for (RepeatCharSymbol s : subArray) {
                    if (s != null) {
                        sum += s.getLength() * s.getRate();
                    }
                }

                if (indexLength1 == -1) {
                    aboveArray[aboveCount] = new RepeatCharSymbol(symbol, 1, sum);
                    aboveCount++;
                } else {
                    aboveArray[indexLength1].add(sum);
                }
            }

            RepeatCharSymbol[] newArray = new RepeatCharSymbol[aboveCount];
            System.arraycopy(aboveArray, 0, newArray, 0, aboveCount);

            symbols[symbol] = newArray;
        }
    }

    public void removeSubThresholdLength() {
        int thresholdRate = calcThresholdRate();

        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i] != null) {
                removeSubThresholdLength((char) i, thresholdRate);
            }
        }
    }

    public void trim(char symbol) {

        if (symbols[symbol] != null) {
            int count = 0;
            for (int i = 0; i < symbols[symbol].length; i++) {
                if (symbols[symbol][i] != null) {
                    count++;
                } else {
                    break;
                }
            }

            RepeatCharSymbol[] newArray = new RepeatCharSymbol[count];
            System.arraycopy(symbols[symbol], 0, newArray, 0, count);

            symbols[symbol] = newArray;
        }
    }

    public void trim() {

        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i] != null) {
                trim((char) i);
            }
        }
    }

    public void sort(char symbol, Comparator<RepeatSymbol> comparator) {

        if (symbols[symbol] != null && symbols[symbol].length > 1) {
            Arrays.sort(symbols[symbol], comparator);
        }
    }

    public void sort(Comparator<RepeatSymbol> comparator) {

        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i] != null) {
                sort((char) i, comparator);
            }
        }
    }

    public void printAll() {
        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i] != null) {
                for (RepeatCharSymbol s : symbols[i]) {
                    if (s != null) {
                        System.out.println("'" + (char) i + "'=" + i + " L=" + s.getLength() + "  Rate=" + s.getRate());
                    }
                }
            }
        }
    }
}
