package ru.academit.ilnitsky.cash_machine;

import static ru.academit.ilnitsky.cash_machine.RubleBanknote.*;

/**
 * Created by UserLabView on 21.10.16.
 * Класс "Банкомат"
 */
public class CashMachine {
    // Параметры банкомата
    private Bank bank;
    private int serial;
    private int secretCode;

    // Контейнеры с деньгами
    private Container containerR50;
    private Container containerR100;
    private Container containerR500;
    private Container containerR1000;
    private Container containerR5000;

    // Переменные текущей сессии:
    private Account account;
    private int cardSerial;
    private boolean intoAccount;

    public CashMachine(Bank bank, int serial, int secretCode) {
        this.bank = bank;
        this.serial = serial;
        this.secretCode = secretCode;

        containerR50 = new Container(R50, 300);
        containerR100 = new Container(R100, 300);
        containerR500 = new Container(R500, 300);
        containerR1000 = new Container(R1000, 300);
        containerR5000 = new Container(R5000, 300);

        account = null;
        cardSerial = 0;
        intoAccount = false;
    }

    public int getSerial() {
        return serial;
    }

    public boolean inputToAccount(int pinCode) {
        account = bank.transactionForAccount(serial, secretCode, cardSerial, pinCode);

        if (account != null) {
            intoAccount = true;
            return true;
        } else {
            intoAccount = false;
            cardSerial = 0;
            return false;
        }
    }

    public void resetAccount() {
        account = null;
        cardSerial = 0;
        intoAccount = false;
    }

}
