package ru.academit.ilnitsky.huffman.alphabet;

import java.util.Comparator;

/**
 * Сортировка символов по частоте встречаемости
 * Created by UserLabView on 07.03.17.
 */
public class SortedByRate implements Comparator<RatedSymbolInterface> {
    public int compare(RatedSymbolInterface symbol1, RatedSymbolInterface symbol2) {

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