package ru.academit.ilnitsky.cash_machine;

/**
 * Created by UserLabView on 21.10.16.
 * Класс "Клиент банка"
 */
public class Client {
    private final int clientSerial;
    private final String name;
    private final String surname;

    Client(int clientSerial, String name, String surname) {
        this.clientSerial = clientSerial;
        this.name = name;
        this.surname = surname;
    }

    public int getClientSerial() {
        return clientSerial;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
