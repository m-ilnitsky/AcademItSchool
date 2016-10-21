package ru.academit.ilnitsky.cash_machine;

/**
 * Created by UserLabView on 21.10.16.
 * Класс "Кредитно-дебетная Карта"
 */
public class Card {
    private final int accountSerial;
    private final int cardSerial;
    private int pinCode;

    public Card(int accountSerial, int cardSerial, int pinCode) {
        this.accountSerial = accountSerial;
        this.cardSerial = cardSerial;
        this.pinCode = pinCode;
    }

    public int getAccountSerial() {
        return accountSerial;
    }

    public int getCardSerial() {
        return cardSerial;
    }

    public boolean checkPIN(int pin) {
        return pinCode == pin;
    }
}
