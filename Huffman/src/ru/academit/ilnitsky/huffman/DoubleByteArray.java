package ru.academit.ilnitsky.huffman;

/**
 * Created by Mike on 15.03.2017.
 */
public class DoubleByteArray {
    private final int shift = 128;
    private final int byteSize = 256;
    private final int[][] symbols = new int[byteSize][byteSize];
    private final boolean[][] mask = new boolean[byteSize][byteSize];

    public void initMask(int[] singleByteRates, int threshold) {
        for (int i = 0; i < byteSize; i++) {
            for (int j = 0; j < byteSize; j++) {
                if (i != j && singleByteRates[i] > threshold && singleByteRates[j] > threshold) {
                    mask[i][j] = true;
                }
            }
        }
    }

    public void add(byte firstByte, byte secondByte) {
        int firstIndex = firstByte + shift;
        int secondIndex = secondByte + shift;

        if (mask[firstIndex][secondIndex]) {
            symbols[firstIndex][secondIndex]++;
        }
    }

    public int getRate(byte firstByte, byte secondByte) {
        int firstIndex = firstByte + shift;
        int secondIndex = secondByte + shift;

        if (mask[firstIndex][secondIndex]) {
            return symbols[firstIndex][secondIndex];
        } else {
            return 0;
        }
    }

    public void maskSubThreshold(int threshold) {
        for (int i = 0; i < byteSize; i++) {
            for (int j = 0; j < byteSize; j++) {
                if (mask[i][j] && symbols[i][j] < threshold) {
                    mask[i][j] = false;
                }
            }
        }
    }

    public int getNumSymbolsInAlphabet() {
        int count = 0;

        for (int i = 0; i < byteSize; i++) {
            for (int j = 0; j < byteSize; j++) {
                if (mask[i][j] && symbols[i][j] > 1) {
                    count++;
                }
            }
        }

        return count;
    }

    public void printAll() {
        for (int i = 0; i < byteSize; i++) {
            for (int j = 0; j < byteSize; j++) {
                if (mask[i][j] && symbols[i][j] > 1) {
                    System.out.println("[" + (char) (i - shift) + "][" + (char) (j - shift) + "] Rate = " + symbols[i][j]);
                }
            }
        }
    }
}
