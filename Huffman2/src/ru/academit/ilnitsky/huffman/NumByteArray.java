package ru.academit.ilnitsky.huffman;

import java.util.HashMap;

/**
 * Created by UserLabView on 16.03.17.
 */
public class NumByteArray {
    private HashMap<NumByteSymbol, Integer> hashMap;
    private NumByteSymbol[] numByteSymbols;
    private int[][] rates;
    private int numBytes;

    private final int shift = 128;
    private final int byteSize = 256;

    public NumByteArray(int numBytes, NumByteSymbol[] numByteSymbols) {
        this.numBytes = numBytes;
        this.numByteSymbols = numByteSymbols;

        hashMap = new HashMap<>(numByteSymbols.length);
        rates = new int[numByteSymbols.length][byteSize];

        for (int i = 0; i < numByteSymbols.length; i++) {
            if (numByteSymbols[i] == null) {
                throw new IllegalArgumentException("numByteSymbols[" + i + "] == null");
            }
            if (numBytes != numByteSymbols[i].getNumBytes()) {
                throw new IllegalArgumentException("numBytes(" + numBytes + ") != numByteSymbols[" + i + "] (" + numByteSymbols[i].getNumBytes() + ")");
            }
            hashMap.put(numByteSymbols[i], i);
        }
    }

    public void add(byte[] bytes, byte newByte) {
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

    public int getNumberSymbolsInAlphabet(int threshold) {
        int count = 0;

        for (int i = 0; i < rates.length; i++) {
            for (int j = 0; j < byteSize; j++) {
                if (rates[i][j] >= threshold) {
                    count++;
                }
            }
        }

        return count;
    }

    public NumByteSymbol[] getNumByteSymbols(int threshold) {
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

    public AlphabetSymbol[] getAlphabetSymbols(int threshold) {
        int size = getNumberSymbolsInAlphabet(threshold);
        AlphabetSymbol[] result = new AlphabetSymbol[size];

        int count = 0;
        for (int i = 0; i < rates.length; i++) {
            for (int j = 0; j < byteSize; j++) {
                if (rates[i][j] >= threshold) {
                    byte[] oldArray = numByteSymbols[i].getSymbol();
                    byte[] newArray = new byte[numBytes + 1];
                    System.arraycopy(oldArray, 0, newArray, 0, numBytes);
                    newArray[numBytes] = (byte) (j - shift);

                    result[count] = new AlphabetSymbol(newArray,rates[i][j]);
                    count++;
                }
            }
        }

        return result;
    }

    public void deleteRepeatBytes() {
        for (int i = 0; i < rates.length; i++) {
            for (int j = 0; j < byteSize; j++) {
                if (numByteSymbols[i].getNumBytes() == 1 || numByteSymbols[i].isRepeatByte()) {
                    if (numByteSymbols[i].getSymbol()[0] == (byte) (j - shift)) {
                        rates[i][j] = 0;
                    }
                }
            }
        }
    }

    public void print(int threshold) {
        for (int i = 0; i < rates.length; i++) {
            for (int j = 0; j < byteSize; j++) {
                if (rates[i][j] >= threshold) {
                    System.out.println(numByteSymbols[i] + "[" + (char) (j - shift) + "] Rate = " + rates[i][j]);
                }
            }
        }
    }
}
