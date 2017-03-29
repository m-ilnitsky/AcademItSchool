package ru.academit.ilnitsky.huffman.alphabet;

import java.util.Arrays;

/**
 * Created by UserLabView on 29.03.17.
 */
public class SingleByteAccumulator {
    private int[] rates;

    private final int shift = 128;
    private final int byteSize = 256;

    public SingleByteAccumulator() {
        rates = new int[byteSize];
    }

    public void add(byte newByte) {
        int index = newByte + shift;
        rates[index]++;
    }

    public int getNumberSymbolsInAlphabet(int threshold) {
        int count = 0;

        for (int i = 0; i < rates.length; i++) {
            if (rates[i] >= threshold) {
                count++;
            }
        }

        return count;
    }

    public NumByteSymbol[] getNumByteSymbols(int threshold) {
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

    public AlphabetBuilderSymbol[] getAlphabetBuilderSymbols(int threshold) {
        int size = getNumberSymbolsInAlphabet(threshold);
        AlphabetBuilderSymbol[] result = new AlphabetBuilderSymbol[size];

        int count = 0;
        for (int i = 0; i < rates.length; i++) {
            if (rates[i] >= threshold) {
                byte[] newArray = new byte[]{(byte) (i - shift)};
                result[count] = new AlphabetBuilderSymbol(newArray, rates[i]);
                count++;
            }
        }

        return result;
    }

    public int calcThreshold() {
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

        System.out.println("average=" + average);
        System.out.println("median=" + median);
        System.out.println("center=" + center);

        return Math.max(Math.min(Math.min(average, median), center), 1);
    }

    public void print(int threshold) {
        for (int i = 0; i < rates.length; i++) {
            if (rates[i] >= threshold) {
                System.out.println("[" + (char) (i - shift) + "] Rate = " + rates[i]);
            }
        }
    }
}
