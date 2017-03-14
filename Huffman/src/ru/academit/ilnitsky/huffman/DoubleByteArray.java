package ru.academit.ilnitsky.huffman;

/**
 * Created by Mike on 15.03.2017.
 */
public class DoubleByteArray {
    private final int shift = 128;
    private final int byteSize = 256;
    private final int[][] symbols = new int[byteSize][byteSize];
    private final boolean[][] mask = new boolean[byteSize][byteSize];

    public DoubleByteArray(int[] singleByteRates, int threshold) {
        for (int i = 0; i < byteSize; i++) {
            for (int j = 0; j < byteSize; j++) {
                if (singleByteRates[i] > threshold && singleByteRates[j] > threshold) {
                    mask[i][j] = true;
                }
            }
        }
    }

    public void add(byte firstByte, byte secondByte){
        int firstIndex = firstByte + shift;
        int secondIndex = secondByte + shift;

        if(mask[firstIndex][secondIndex]){
            symbols[firstIndex][secondIndex]++;
        }
    }
}
