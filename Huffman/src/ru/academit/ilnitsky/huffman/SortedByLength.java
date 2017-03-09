package ru.academit.ilnitsky.huffman;

import java.util.Comparator;

/**
 * Created by UserLabView on 07.03.17.
 */
public class SortedByLength implements Comparator<RepeatSymbol> {
    public int compare(RepeatSymbol symbol1, RepeatSymbol symbol2) {

        int length1 = symbol1.getLength();
        int length2 = symbol2.getLength();

        if (length1 > length2) {
            return 1;
        } else if (length1 < length2) {
            return -1;
        } else {
            return 0;
        }
    }
}
