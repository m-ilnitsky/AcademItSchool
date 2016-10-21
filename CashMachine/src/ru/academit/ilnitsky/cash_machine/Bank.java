package ru.academit.ilnitsky.cash_machine;

/**
 * Created by UserLabView on 21.10.16.
 * Класс "Банк"
 */
public class Bank {
    private int lastClientSerial;
    private int lastAccountSerial;
    private int lastCardSerial;
    private int lastCashMachines;

    private int numClients;
    private int numAccounts;
    private int numCards;
    private int numCashMachines;

    private Client[] clients;
    private Account[] accounts;
    private Card[] cards;
    private CashMachine[] cashMachines;
    private int[] cashMachinesSecretCode;

    public Bank(int initNumClients, int initNumAccounts, int initNumCards, int initNumCashMachines) {
        if (numClients <= 0) {
            throw new IllegalArgumentException("numClients <= 0");
        }
        if (numAccounts <= 0) {
            throw new IllegalArgumentException("numAccounts <= 0");
        }
        if (numCards <= 0) {
            throw new IllegalArgumentException("numCards <= 0");
        }
        if (numCashMachines <= 0) {
            throw new IllegalArgumentException("numCashMachines <= 0");
        }

        lastClientSerial = 0;
        lastAccountSerial = 0;
        lastCardSerial = 0;
        lastCashMachines = 0;

        numClients = 0;
        numAccounts = 0;
        numCards = 0;
        numCashMachines = 0;

        clients = new Client[initNumClients];
        accounts = new Account[initNumAccounts];
        cards = new Card[initNumCards];
        cashMachines = new CashMachine[initNumCashMachines];
        cashMachinesSecretCode = new int[initNumCashMachines];
    }

    private void resizeArrayOfClients(int newSize) {
        int newLength;
        if (newSize >= clients.length) {
            newLength = newSize;
        } else {
            int count = 0;
            for (int i = 0; i < clients.length; i++) {
                if (clients[i] == null) {
                    count++;
                }
            }
            newLength = clients.length - count;
        }

        Client[] clients2 = new Client[newLength];

        int count = 0;
        for (int i = 0; i < clients.length; i++) {
            if (clients[i] != null) {
                clients2[count] = clients[i];
                count++;
            }
        }

        clients = clients2;
    }

    private void resizeArrayOfAccounts(int newSize) {
        int newLength;
        if (newSize >= accounts.length) {
            newLength = newSize;
        } else {
            int count = 0;
            for (int i = 0; i < accounts.length; i++) {
                if (accounts[i] == null) {
                    count++;
                }
            }
            newLength = accounts.length - count;
        }

        Account[] accounts2 = new Account[newLength];

        int count = 0;
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] != null) {
                accounts2[count] = accounts[i];
                count++;
            }
        }

        accounts = accounts2;
    }

    private void resizeArrayOfCards(int newSize) {
        int newLength;
        if (newSize >= cards.length) {
            newLength = newSize;
        } else {
            int count = 0;
            for (int i = 0; i < cards.length; i++) {
                if (cards[i] == null) {
                    count++;
                }
            }
            newLength = cards.length - count;
        }

        Card[] cards2 = new Card[newLength];

        int count = 0;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] != null) {
                cards2[count] = cards[i];
                count++;
            }
        }

        cards = cards2;
    }

    private void resizeArrayOfCashMachines(int newSize) {
        int newLength;
        if (newSize >= cashMachines.length) {
            newLength = newSize;
        } else {
            int count = 0;
            for (int i = 0; i < cashMachines.length; i++) {
                if (cashMachines[i] == null) {
                    count++;
                }
            }
            newLength = cashMachines.length - count;
        }

        CashMachine[] cashMachines2 = new CashMachine[newLength];
        int[] cashMachinesSecretCode2 = new int[newLength];

        int count = 0;
        for (int i = 0; i < cashMachines.length; i++) {
            if (cashMachines[i] != null) {
                cashMachines2[count] = cashMachines[i];
                cashMachinesSecretCode2[count] = cashMachinesSecretCode[i];
                count++;
            }
        }

        cashMachines = cashMachines2;
        cashMachinesSecretCode = cashMachinesSecretCode2;
    }

    private int getNumClients() {
        return numClients;
    }

    private int getNumAccounts() {
        return numAccounts;
    }

    private int getNumCards() {
        return numCards;
    }

    private int getNumCashMachines() {
        return numCashMachines;
    }

    public void addClient(String name, String surname) {
        if (numClients >= clients.length) {
            resizeArrayOfClients(clients.length * 2);
        }

        lastClientSerial++;
        clients[numClients] = new Client(lastClientSerial, name, surname);
        numClients++;
    }

    public void addAccount(int clientSerial, int balance) {
        if (numAccounts >= accounts.length) {
            resizeArrayOfAccounts(accounts.length * 2);
        }

        lastAccountSerial++;
        accounts[numAccounts] = new Account(clientSerial, lastAccountSerial, balance);
        numAccounts++;
    }

    public void addCard(int accountSerial, int pinCode) {
        if (numCards >= cards.length) {
            resizeArrayOfCards(cards.length * 2);
        }

        lastCardSerial++;
        cards[numCards] = new Card(accountSerial, lastCardSerial, pinCode);
        numCards++;
    }

    public void addCashMachine(int secretCode) {
        if (numCashMachines >= cashMachines.length) {
            resizeArrayOfCashMachines(cashMachines.length * 2);
        }

        lastCashMachines++;
        cashMachines[numCashMachines] = new CashMachine(this, lastCashMachines, secretCode);
        cashMachinesSecretCode[numCashMachines] = secretCode;
        numCashMachines++;
    }

    private int findClient(int serial) {
        for (int i = 0; i < clients.length; i++) {
            if (serial == clients[i].getClientSerial()) {
                return i;
            }
        }
        return -1;
    }

    private int findAccount(int serial) {
        for (int i = 0; i < accounts.length; i++) {
            if (serial == accounts[i].getAccountSerial()) {
                return i;
            }
        }
        return -1;
    }

    private int findAccountOfClient(int from, int clientSerial) {
        for (int i = from; i < accounts.length; i++) {
            if (clientSerial == accounts[i].getClientSerial()) {
                return i;
            }
        }
        return -1;
    }

    private int findAccountOfClient(int clientSerial) {
        return findAccountOfClient(0, clientSerial);
    }

    private int findCard(int serial) {
        for (int i = 0; i < cards.length; i++) {
            if (serial == cards[i].getCardSerial()) {
                return i;
            }
        }
        return -1;
    }

    private int findCardOfAccount(int from, int accountSerial) {
        for (int i = from; i < cards.length; i++) {
            if (accountSerial == cards[i].getAccountSerial()) {
                return i;
            }
        }
        return -1;
    }

    private int findCardOfAccount(int accountSerial) {
        return findCardOfAccount(0, accountSerial);
    }

    private int findCashMachine(int serial) {
        for (int i = 0; i < cashMachines.length; i++) {
            if (serial == cashMachines[i].getSerial()) {
                return i;
            }
        }
        return -1;
    }

    public void deleteCashMachine(int serial) {
        int index = findCashMachine(serial);
        if (index < 0) {
            return;
        }
        cashMachines[index] = null;
        numCashMachines--;
        resizeArrayOfCashMachines(cashMachines.length);
    }

    public void deleteCard(int serial) {
        int index = findCard(serial);
        if (index < 0) {
            return;
        }
        cards[index] = null;
        numCards--;
        resizeArrayOfCashMachines(cards.length);
    }

    public boolean deleteAccount(int serial) {
        int index = findAccount(serial);
        if (index < 0) {
            return true;
        }

        if (accounts[index].getBalance() == 0 && findCardOfAccount(serial) == -1) {
            accounts[index] = null;
            numAccounts--;
            resizeArrayOfCashMachines(accounts.length);
            return true;
        } else {
            return false;
        }
    }

    public boolean transactionForRegistrationCashMachine(int cashMachineSerial, int cashMachineSecretCode) {
        int index = findCashMachine(cashMachineSerial);
        if (index < 0) {
            return false;
        }

        return cashMachinesSecretCode[index] == cashMachineSecretCode;
    }

    public boolean transactionForRegistrationCard(int cashMachineSerial, int cashMachineSecretCode, int cardSerial, int pinCode) {
        if (!transactionForRegistrationCashMachine(cashMachineSerial, cashMachineSecretCode)) {
            return false;
        }

        int index = findCard(cardSerial);
        if (index < 0) {
            return false;
        }

        return cards[index].checkPIN(pinCode);
    }

    public Account transactionForAccount(int cashMachineSerial, int cashMachineSecretCode, int cardSerial, int pinCode) {
        if (!transactionForRegistrationCard(cashMachineSerial, cashMachineSecretCode, cardSerial, pinCode)) {
            return null;
        }

        int indexCard = findCard(cardSerial);
        int indexAccount = findAccount(cards[indexCard].getAccountSerial());

        return accounts[indexAccount];
    }
}
