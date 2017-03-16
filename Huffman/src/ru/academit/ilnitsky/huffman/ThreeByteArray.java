package ru.academit.ilnitsky.huffman;

/**
 * Created by UserLabView on 15.03.17.
 */
public class ThreeByteArray {
    private final int shift = 128;
    private final int byteSize = 256;
    private final int[][][] symbols = new int[byteSize][byteSize][byteSize];
    private final boolean[][][] mask = new boolean[byteSize][byteSize][byteSize];

    public void initMask(int[] singleByteRates, int threshold) {
        for (int i = 0; i < byteSize; i++) {
            for (int j = 0; j < byteSize; j++) {
                for (int k = 0; k < byteSize; k++) {
                    if (i != j && j != k && k != i && singleByteRates[i] > threshold && singleByteRates[j] > threshold) {
                        mask[i][j][k] = true;
                    }
                }
            }
        }
    }

    public void add(byte firstByte, byte secondByte, byte thirdByte) {
        int firstIndex = firstByte + shift;
        int secondIndex = secondByte + shift;
        int thirdIndex = thirdByte + shift;

        if (mask[firstIndex][secondIndex][thirdIndex]) {
            symbols[firstIndex][secondIndex][thirdIndex]++;
        }
    }

    public int getRate(byte firstByte, byte secondByte, byte thirdByte) {
        int firstIndex = firstByte + shift;
        int secondIndex = secondByte + shift;
        int thirdIndex = thirdByte + shift;

        if (mask[firstIndex][secondIndex][thirdIndex]) {
            return symbols[firstIndex][secondIndex][thirdIndex];
        } else {
            return 0;
        }
    }

    public void maskSubThreshold(int threshold) {
        for (int i = 0; i < byteSize; i++) {
            for (int j = 0; j < byteSize; j++) {
                for (int k = 0; k < byteSize; k++) {
                    if (mask[i][j][k] && symbols[i][j][k] < threshold) {
                        mask[i][j][k] = false;
                    }
                }
            }
        }
    }

    public int getNumSymbolsInAlphabet() {
        int count = 0;

        for (int i = 0; i < byteSize; i++) {
            for (int j = 0; j < byteSize; j++) {
                for (int k = 0; k < byteSize; k++) {
                    if (mask[i][j][k] && symbols[i][j][k] > 1) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public NumByteSymbol[] getNumByteSymbols() {
        int size = getNumSymbolsInAlphabet();
        NumByteSymbol[] result = new NumByteSymbol[size];

        int count = 0;
        for (int i = 0; i < byteSize; i++) {
            for (int j = 0; j < byteSize; j++) {
                for (int k = 0; k < byteSize; k++) {
                    if (mask[i][j][k] && symbols[i][j][k] > 1) {
                        result[count] = new NumByteSymbol(new byte[]{(byte) (i - shift), (byte) (j - shift), (byte) (k - shift)});
                        count++;
                    }
                }
            }
        }

        return result;
    }

    public void printAll() {
        for (int i = 0; i < byteSize; i++) {
            for (int j = 0; j < byteSize; j++) {
                for (int k = 0; k < byteSize; k++) {
                    if (mask[i][j][k] && symbols[i][j][k] > 1) {
                        System.out.println("[" + (char) (i - shift) + "][" + (char) (j - shift) + "][" + (char) (k - shift) + "] Rate = " + symbols[i][j][k]);
                    }
                }
            }
        }
    }
}
