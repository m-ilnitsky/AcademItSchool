package ru.academit.ilnitsky.moneybox;

/**
 * Перечисление "Рублёвые Банкноты"
 * Created by UserLabView on 21.10.16.
 */
public enum RubleBanknote {
    R50(50), R100(100), R500(500), R1000(1000), R5000(5000);

    private int value;

    RubleBanknote(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
