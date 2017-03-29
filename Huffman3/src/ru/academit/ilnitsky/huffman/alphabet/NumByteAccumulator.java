package ru.academit.ilnitsky.huffman.alphabet;

import java.util.HashMap;

/**
 * Накопитель символов произвольной длины
 * Подсчитывает частоту встречаемости каждого сивола (последовательности байт) в заданном наборе байт
 * Created by UserLabView on 16.03.17.
 */
class NumByteAccumulator {
    private HashMap<NumByteSymbol, Integer> hashMap;
    private NumByteSymbol[] numByteSymbols;
    private int[][] rates;
    private int numBytes;

    private final int shift = 128;
    private final int byteSize = 256;

    NumByteAccumulator(int numBytes, NumByteSymbol[] numByteSymbols) {
        this.numBytes = numBytes;
        this.numByteSymbols = numByteSymbols;

        hashMap = new HashMap<>(numByteSymbols.length);
        rates = new int[numByteSymbols.length][byteSize];

        for (int i = 0; i < numByteSymbols.length; i++) {
            if (numByteSymbols[i] == null) {
                throw new IllegalArgumentException("numByteSymbols[" + i + "] == null");
            }
            if (numBytes != numByteSymbols[i].getLength()) {
                throw new IllegalArgumentException("numBytes(" + numBytes + ") != numByteSymbols[" + i + "] (" + numByteSymbols[i].getLength() + ")");
            }
            hashMap.put(numByteSymbols[i], i);
        }
    }

    void add(byte[] bytes, byte newByte) {
        if (numBytes != bytes.length) {
            throw new IllegalArgumentException("numBytes(" + numBytes + ") != bytes.length(" + bytes.length + ")");
        }

        NumByteSymbol byteSymbol = new NumByteSymbol(bytes);
        if (hashMap.containsKey(byteSymbol)) {
            int index = hashMap.get(byteSymbol);
            int lastIndex = newByte + shift;
            rates[index][lastIndex]++;
        }
    }

    int getNumberSymbolsInAlphabet(int threshold) {
        int count = 0;

        for (int[] rr : rates) {
            for (int r : rr) {
                if (r >= threshold) {
                    count++;
                }
            }
        }

        return count;
    }

    NumByteSymbol[] getNumByteSymbols(int threshold) {
        int size = getNumberSymbolsInAlphabet(threshold);
        NumByteSymbol[] result = new NumByteSymbol[size];

        int count = 0;
        for (int i = 0; i < rates.length; i++) {
            for (int j = 0; j < byteSize; j++) {
                if (rates[i][j] >= threshold) {
                    byte[] oldArray = numByteSymbols[i].getSymbol();
                    byte[] newArray = new byte[numBytes + 1];
                    System.arraycopy(oldArray, 0, newArray, 0, numBytes);
                    newArray[numBytes] = (byte) (j - shift);

                    result[count] = new NumByteSymbol(newArray);
                    count++;
                }
            }
        }

        return result;
    }

    RatedSymbol[] getRatedSymbols(int threshold) {
        int size = getNumberSymbolsInAlphabet(threshold);
        RatedSymbol[] result = new RatedSymbol[size];

        int count = 0;
        for (int i = 0; i < rates.length; i++) {
            for (int j = 0; j < byteSize; j++) {
                if (rates[i][j] >= threshold) {
                    byte[] oldArray = numByteSymbols[i].getSymbol();
                    byte[] newArray = new byte[numBytes + 1];
                    System.arraycopy(oldArray, 0, newArray, 0, numBytes);
                    newArray[numBytes] = (byte) (j - shift);

                    result[count] = new RatedSymbol(newArray, rates[i][j]);
                    count++;
                }
            }
        }

        return result;
    }

    void print(int threshold) {
        for (int i = 0; i < rates.length; i++) {
            for (int j = 0; j < byteSize; j++) {
                if (rates[i][j] >= threshold) {
                    System.out.println(numByteSymbols[i] + "[" + (char) (j - shift) + "] Rate = " + rates[i][j]);
                }
            }
        }
    }
}
