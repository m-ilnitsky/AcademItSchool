package ru.academit.ilnitsky.huffman;

/**
 * Created by UserLabView on 06.03.17.
 */
public class ByteArray {
    private int[] rates;

    private final int shift = 128;

    public void add(byte symbol) {
        rates[symbol + shift]++;
    }

    public int getRate(byte symbol) {
        return rates[symbol + shift];
    }

    public int[] getRates() {
        return rates;
    }
}
