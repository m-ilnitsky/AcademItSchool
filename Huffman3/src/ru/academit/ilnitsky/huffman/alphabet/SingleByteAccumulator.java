package ru.academit.ilnitsky.huffman.alphabet;

import java.util.Arrays;

/**
 * Накопитель однобайтовых символов
 * Подсчитывает частоту встречаемости каждого байта в заданном наборе байт
 * Created by UserLabView on 29.03.17.
 */
class SingleByteAccumulator {
    private int[] rates;

    private final int shift = 128;

    SingleByteAccumulator() {
        rates = new int[256];
    }

    void add(byte newByte) {
        int index = newByte + shift;
        rates[index]++;
    }

    int getNumberSymbolsInAlphabet() {
        return getNumberSymbolsInAlphabet(1);
    }

    int getNumberSymbolsInAlphabet(int threshold) {
        int count = 0;

        for (int r : rates) {
            if (r >= threshold) {
                count++;
            }
        }

        return count;
    }

    NumByteSymbol[] getNumByteSymbols() {
        return getNumByteSymbols(1);
    }

    NumByteSymbol[] getNumByteSymbols(int threshold) {
        int size = getNumberSymbolsInAlphabet(threshold);
        NumByteSymbol[] result = new NumByteSymbol[size];

        int count = 0;
        for (int i = 0; i < rates.length; i++) {
            if (rates[i] >= threshold) {
                byte[] newArray = new byte[]{(byte) (i - shift)};
                result[count] = new NumByteSymbol(newArray);
                count++;
            }
        }

        return result;
    }

    RatedSymbol[] getRatedSymbols() {
        return getRatedSymbols(1);
    }

    RatedSymbol[] getRatedSymbols(int threshold) {
        int size = getNumberSymbolsInAlphabet(threshold);
        RatedSymbol[] result = new RatedSymbol[size];

        int count = 0;
        for (int i = 0; i < rates.length; i++) {
            if (rates[i] >= threshold) {
                byte[] newArray = new byte[]{(byte) (i - shift)};
                result[count] = new RatedSymbol(newArray, rates[i]);
                count++;
            }
        }

        return result;
    }

    AlphabetSymbol[] getAlphabetSymbols() {
        int size = getNumberSymbolsInAlphabet();
        AlphabetSymbol[] result = new AlphabetSymbol[size];

        int count = 0;
        for (int i = 0; i < rates.length; i++) {
            if (rates[i] > 0) {
                byte[] newArray = new byte[]{(byte) (i - shift)};
                result[count] = new AlphabetSymbol(newArray, rates[i]);
                count++;
            }
        }

        return result;
    }

    int calcThreshold() {
        int sum = 0;
        int count = 0;
        for (int r : rates) {
            sum += r;
            if (r > 0) {
                count++;
            }
        }
        int average = sum / count;

        int[] array = new int[count];
        int index = 0;
        for (int r : rates) {
            if (r > 0) {
                array[index] = r;
                index++;
            }
        }
        Arrays.sort(array);
        int median = array[array.length / 2];

        int center = (array[array.length - 1] + array[0]) / 2;

        int min = Math.min(Math.min(average, median), center);
        min = (min + array[0]) / 2;

        //System.out.println("average=" + average);
        //System.out.println("median=" + median);
        //System.out.println("center=" + center);
        //System.out.println("min=" + min);

        return Math.max(min, 2);
    }

    void print() {
        for (int i = 0; i < rates.length; i++) {
            if (rates[i] > 0) {
                System.out.println("[" + (char) (i - shift) + "] Rate = " + rates[i]);
            }
        }
    }
}
