package ru.academit.ilnitsky.moneybox;

import java.util.Comparator;

/**
 * Created by UserLabView on 25.10.16.
 */
public class SortedByValue implements Comparator<RubleBanknote> {
    public int compare(RubleBanknote banknote1, RubleBanknote banknote2) {

        double value1 = banknote1.getValue();
        double value2 = banknote2.getValue();

        if (value1 > value2) {
            return 1;
        } else if (value1 < value2) {
            return -1;
        } else {
            return 0;
        }
    }
}
