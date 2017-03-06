package ru.academit.ilnitsky.huffman;

/**
 * Created by UserLabView on 06.03.17.
 */
public class CharArray {
    private int[] rates;

    public void add(char symbol) {
        rates[symbol]++;
    }

    public int getRate(char symbol) {
        return rates[symbol];
    }

    public int[] getRates() {
        return rates;
    }
}
