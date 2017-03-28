package ru.academit.ilnitsky.huffman;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by UserLabView on 06.03.17.
 */
public class RepeatByteArray {
    private RepeatByteSymbol[][] symbols;

    private final int shift = 128;
    private final int byteSize = 256;

    public RepeatByteArray() {
        symbols = new RepeatByteSymbol[byteSize][];
    }

    public void add(byte symbol, int length) {
        int symbolIndex = symbol + shift;

        if (symbols[symbolIndex] == null) {
            symbols[symbolIndex] = new RepeatByteSymbol[4];
            symbols[symbolIndex][0] = new RepeatByteSymbol(symbol, length);
        } else {
            int index = -1;
            int firstNull = -1;

            for (int i = 0; i < symbols[symbolIndex].length; i++) {
                if (symbols[symbolIndex][i] == null) {
                    firstNull = i;
                    break;
                } else if (symbols[symbolIndex][i].is(symbol, length)) {
                    index = i;
                    break;
                }
            }

            if (index == -1) {
                if (firstNull == -1) {
                    int oldArrayLength = symbols[symbolIndex].length;

                    RepeatByteSymbol[] newArray = new RepeatByteSymbol[oldArrayLength * 2];
                    System.arraycopy(symbols[symbolIndex], 0, newArray, 0, oldArrayLength);

                    symbols[symbolIndex] = newArray;
                    symbols[symbolIndex][oldArrayLength] = new RepeatByteSymbol(symbol, length);
                } else {
                    symbols[symbolIndex][firstNull] = new RepeatByteSymbol(symbol, length);
                }
            } else {
                symbols[symbolIndex][index].add(symbol, length);
            }
        }
    }

    public int getRate(byte symbol, int length) {
        int symbolIndex = symbol + shift;
        int index = -1;

        for (int i = 0; i < symbols[symbolIndex].length; i++) {
            if (symbols[symbolIndex][i] == null) {
                break;
            } else if (symbols[symbolIndex][i].is(symbol, length)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return 0;
        } else {
            return symbols[symbolIndex][index].getRate();
        }
    }

    public int getNumInputBytes(byte symbol) {
        int symbolIndex = symbol + shift;
        int sum = 0;

        for (RepeatByteSymbol s : symbols[symbolIndex]) {
            if (s == null) {
                break;
            } else {
                sum += s.getRate() * s.getLength();
            }
        }

        return sum;
    }

    public int getNumInputBytes() {
        int sum = 0;

        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i] != null) {
                sum += getNumInputBytes((byte) (i - shift));
            }
        }

        return sum;
    }

    public int getNumSymbolsInFile() {
        int sum = 0;

        for (RepeatByteSymbol[] ss : symbols) {
            if (ss != null) {
                for (RepeatByteSymbol s : ss) {
                    if (s != null) {
                        sum += s.getRate();
                    }
                }
            }
        }

        return sum;
    }

    public int getNumberSymbolsInAlphabet() {
        int count = 0;

        for (RepeatByteSymbol[] ss : symbols) {
            if (ss != null) {
                for (RepeatByteSymbol s : ss) {
                    if (s != null) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public int getNumberSingleBytesInAlphabet() {
        int count = 0;

        for (RepeatByteSymbol[] ss : symbols) {
            if (ss != null) {
                for (RepeatByteSymbol s : ss) {
                    if (s != null && s.getLength() == 1) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public RepeatByteSymbol[] getRepeatSymbol(byte symbol) {
        return symbols[symbol + shift];
    }

    public int calcThreshold() {
        int count = 0;

        for (RepeatByteSymbol[] ss : symbols) {
            if (ss != null) {
                count++;
            }
        }

        int[] rates = new int[count];

        int sum;
        int totalSum = 0;
        count = 0;

        for (RepeatByteSymbol[] ss : symbols) {
            if (ss != null) {
                sum = 0;
                for (RepeatByteSymbol s : ss) {
                    if (s == null) {
                        break;
                    } else {
                        sum += s.getRate() * s.getLength();
                    }
                }
                rates[count] = sum;
                totalSum += sum;
                count++;
            }
        }

        Arrays.sort(rates);

        int average = totalSum / count;
        int median = rates[rates.length / 2];

        return Math.min(average, median);
    }

    public void removeSubThresholdLength(byte symbol, int thresholdRate) {
        int symbolIndex = symbol + shift;

        if (symbols[symbolIndex] != null && symbols[symbolIndex].length > 1) {
            RepeatByteSymbol[] aboveArray = new RepeatByteSymbol[symbols[symbolIndex].length];
            RepeatByteSymbol[] subArray = new RepeatByteSymbol[symbols[symbolIndex].length];

            int indexLength1 = -1;
            int count = 0;

            for (int i = 0; i < symbols[symbolIndex].length; i++) {
                if (symbols[symbolIndex][i] != null) {
                    count++;
                    if (symbols[symbolIndex][i].getLength() == 1) {
                        indexLength1 = i;
                    }
                }
            }

            if (!((indexLength1 == -1 && count < 4) || count < 2)) {
                int aboveCount = 0;
                int subCount = 0;

                for (int i = 0; i < symbols[symbolIndex].length; i++) {
                    if (symbols[symbolIndex][i] != null) {
                        int length = symbols[symbolIndex][i].getLength();

                        if (length == 1) {
                            indexLength1 = aboveCount;
                            aboveArray[aboveCount] = symbols[symbolIndex][i];
                            aboveCount++;
                        } else if (symbols[symbolIndex][i].getLength() * symbols[symbolIndex][i].getRate() > thresholdRate) {
                            aboveArray[aboveCount] = symbols[symbolIndex][i];
                            aboveCount++;
                        } else {
                            subArray[subCount] = symbols[symbolIndex][i];
                            subCount++;
                        }
                    }
                }

                if (subCount > 0) {
                    int sum = 0;

                    for (RepeatByteSymbol s : subArray) {
                        if (s != null) {
                            sum += s.getLength() * s.getRate();
                        }
                    }

                    if (indexLength1 == -1) {
                        aboveArray[aboveCount] = new RepeatByteSymbol(symbol, 1, sum);
                        aboveCount++;
                    } else {
                        aboveArray[indexLength1].add(sum);
                    }
                }

                RepeatByteSymbol[] newArray = new RepeatByteSymbol[aboveCount];
                System.arraycopy(aboveArray, 0, newArray, 0, aboveCount);

                symbols[symbolIndex] = newArray;
            }
        }
    }

    public void removeSubThresholdLength() {
        int thresholdRate = calcThreshold();

        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i] != null) {
                removeSubThresholdLength((byte) (i - shift), thresholdRate);
            }
        }
    }

    public void trim(byte symbol) {
        int symbolIndex = symbol + shift;

        if (symbols[symbolIndex] != null) {
            int count = 0;
            for (int i = 0; i < symbols[symbolIndex].length; i++) {
                if (symbols[symbolIndex][i] != null) {
                    count++;
                } else {
                    break;
                }
            }

            RepeatByteSymbol[] newArray = new RepeatByteSymbol[count];
            System.arraycopy(symbols[symbolIndex], 0, newArray, 0, count);

            symbols[symbolIndex] = newArray;
        }
    }

    public void trim() {
        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i] != null) {
                trim((byte) (i - shift));
            }
        }
    }

    public void sort(byte symbol, Comparator<RepeatSymbol> comparator) {
        int symbolIndex = symbol + shift;

        if (symbols[symbolIndex] != null && symbols[symbolIndex].length > 1) {
            Arrays.sort(symbols[symbolIndex], comparator);
        }
    }

    public void sort(Comparator<RepeatSymbol> comparator) {
        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i] != null) {
                sort((byte) (i - shift), comparator);
            }
        }
    }

    public int[] getSingleByteRates() {
        int[] rates = new int[byteSize];

        for (int i = 0; i < symbols.length; i++) {
            rates[i] = 0;
            if (symbols[i] != null) {
                for (RepeatByteSymbol s : symbols[i]) {
                    if (s != null && s.getLength() == 1) {
                        rates[i] = s.getRate();
                    }
                }
            }
        }

        return rates;
    }

    public NumByteSymbol[] getNumByteSymbols(int threshold) {
        int size = getNumberSingleBytesInAlphabet();
        NumByteSymbol[] result = new NumByteSymbol[size];

        int count = 0;
        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i] != null) {
                for (RepeatByteSymbol s : symbols[i]) {
                    if (s != null && s.getLength() == 1) {
                        result[count] = new NumByteSymbol(new byte[]{(byte) (i - shift)});
                        count++;
                        break;
                    }
                }
            }
        }

        return result;
    }

    public AlphabetSymbol[] getAlphabetSymbols(boolean onlySingleBytes, int threshold) {
        int size;
        if (onlySingleBytes) {
            size = getNumberSingleBytesInAlphabet();
        } else {
            size = getNumberSymbolsInAlphabet();
        }

        AlphabetSymbol[] result = new AlphabetSymbol[size];

        int count = 0;
        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i] != null) {
                for (RepeatByteSymbol s : symbols[i]) {
                    if (s != null) {
                        if (onlySingleBytes) {
                            if (s.getLength() == 1) {
                                result[count] = new AlphabetSymbol(new byte[]{(byte) (i - shift)}, s.getRate());
                                count++;
                                break;
                            }
                        } else {
                            byte[] bytes = new byte[s.getLength()];
                            byte repeated = (byte) (i - shift);
                            for (int k = 0; k < bytes.length; k++) {
                                bytes[i] = repeated;
                            }
                            result[count] = new AlphabetSymbol(bytes, s.getRate());
                            count++;
                        }
                    }
                }
            }
        }

        return result;
    }

    public void print() {
        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i] != null) {
                for (RepeatByteSymbol s : symbols[i]) {
                    if (s != null) {
                        System.out.println("[" + (char) (i - shift) + "]=" + i + " L=" + s.getLength() + "  Rate=" + s.getRate());
                    }
                }
            }
        }
    }

    public void printStatistic() {
        HashMap<Integer, Integer> numSymbolsForRate = new HashMap<>(byteSize);

        int maxRate = 0;

        for (RepeatByteSymbol[] ss : symbols) {
            if (ss != null) {
                for (RepeatByteSymbol s : ss) {
                    if (s != null) {
                        int rate = s.getRate();
                        if (numSymbolsForRate.containsKey(rate)) {
                            numSymbolsForRate.put(rate, numSymbolsForRate.get(rate) + 1);
                        } else {
                            numSymbolsForRate.put(rate, 1);
                        }
                        if (rate > maxRate) {
                            maxRate = rate;
                        }
                    }
                }
            }
        }

        for (int i = maxRate; i > 0; i--) {
            if (numSymbolsForRate.containsKey(i)) {
                System.out.println("Rate = " + i + " NumSymbols = " + numSymbolsForRate.get(i));
            }
        }
    }
}
