package ru.academit.ilnitsky.huffman;

import java.util.Comparator;

/**
 * Created by UserLabView on 07.03.17.
 */
public class SortedByRate implements Comparator<RepeatByteSymbol> {
    public int compare(RepeatByteSymbol symbol1, RepeatByteSymbol symbol2) {

        int rate1 = symbol1.getRate();
        int rate2 = symbol2.getRate();

        if (rate1 > rate2) {
            return 1;
        } else if (rate1 < rate2) {
            return -1;
        } else {
            return 0;
        }
    }
}