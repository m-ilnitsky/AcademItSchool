package ru.academit.ilnitsky.cash_machine;

/**
 * Created by UserLabView on 21.10.16.
 * Перечисление "Рублёвые Банкноты"
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
