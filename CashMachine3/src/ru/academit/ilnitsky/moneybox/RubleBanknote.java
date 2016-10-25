package ru.academit.ilnitsky.moneybox;

/**
 * Перечисление "Рублёвые Банкноты"
 * Created by UserLabView on 21.10.16.
 */
public enum RubleBanknote {
    R5000(5000), R1000(1000), R500(500), R100(100), R50(50);

    private int value;

    RubleBanknote(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
