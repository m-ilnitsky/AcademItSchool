package ru.academit.ilnitsky.huffman.alphabet;

import java.util.Comparator;

/**
 * Created by Mike on 27.03.2017.
 */
public class SortedByDepthAndRate implements Comparator<AlphabetBuilderSymbol> {
    public int compare(AlphabetBuilderSymbol symbol1, AlphabetBuilderSymbol symbol2) {

        int rate1 = symbol1.getRate();
        int rate2 = symbol2.getRate();
        int depth1 = symbol1.getDepth();
        int depth2 = symbol2.getDepth();

        if (depth1 > depth2) {
            return 1;
        } else if (depth1 < depth2) {
            return -1;
        } else if (rate1 > rate2) {
            return 1;
        } else if (rate1 < rate2) {
            return -1;
        } else {
            return 0;
        }
    }
}
