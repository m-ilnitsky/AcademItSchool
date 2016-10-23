package ru.academit.ilnitsky.cash_machine;

/**
 * Created by UserLabView on 21.10.16.
 * Класс "Счёт"
 */
public class Account {
    private final int clientSerial;
    private final int accountSerial;
    private int balance;

    public Account(int clientSerial, int accountSerial, int balance) {
        this.clientSerial = clientSerial;
        this.accountSerial = accountSerial;
        this.balance = balance;
    }

    public int getClientSerial() {
        return clientSerial;
    }

    public int getAccountSerial() {
        return accountSerial;
    }

    public int getBalance() {
        return balance;
    }

    public void addToBalance(int money) {
        if (money <= 0) {
            return;
        }

        balance += money;
    }

    public boolean removeFromBalance(int money) {
        if (money <= 0 || money > balance) {
            return false;
        }

        balance -= money;

        return true;
    }
}
