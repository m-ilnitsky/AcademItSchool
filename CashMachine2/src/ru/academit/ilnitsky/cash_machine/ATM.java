package ru.academit.ilnitsky.cash_machine;

import static ru.academit.ilnitsky.cash_machine.RubleBanknote.*;

/**
 * Created by UserLabView on 21.10.16.
 * Класс "Банкомат"
 */
public class ATM {
    private final static int NUM_CONTAINERS = 5;
    private Account account;
    private Container[] containers = new Container[NUM_CONTAINERS];
    private int[] setOfBanknotesForRemove = new int[NUM_CONTAINERS];

    public ATM() {
        containers[0] = new Container(R5000, 100, 50);
        containers[1] = new Container(R1000, 100, 50);
        containers[2] = new Container(R500, 100, 50);
        containers[3] = new Container(R100, 100, 50);
        containers[4] = new Container(R50, 100, 50);

        account = new Account(1, 1, 127311);
    }

    public int getBalance() {
        return account.getBalance();
    }

    public int getAvailableCash() {
        int result = 0;
        for (int i = 0; i < NUM_CONTAINERS; i++) {
            result += containers[i].getNumBanknotes() * containers[i].getNominal().getValue();
        }
        return result;
    }

    private int indexOfContainer(RubleBanknote banknote) {
        for (int i = 0; i < NUM_CONTAINERS; i++) {
            if (containers[i].getNominal() == banknote) {
                return i;
            }
        }
        return -1;
    }

    public int unoccupiedSpace(RubleBanknote banknote) {
        int index = indexOfContainer(banknote);
        return containers[index].unoccupiedSpace();
    }

    public boolean isUnoccupiedSpace(RubleBanknote banknote) {
        return unoccupiedSpace(banknote) > 0;
    }

    public int availableBanknote(RubleBanknote banknote) {
        int index = indexOfContainer(banknote);
        return containers[index].getNumBanknotes();
    }

    public int availableMoney(RubleBanknote banknote) {
        int index = indexOfContainer(banknote);
        return containers[index].getNumBanknotes() * containers[index].getNominal().getValue();
    }

    public boolean isAvailable(RubleBanknote banknote) {
        return availableBanknote(banknote) > 0;
    }

    public boolean isAvailable(int value) {
        int sum = 0;
        for (int i = 0; i < NUM_CONTAINERS; i++) {
            if (containers[i].getNominal().getValue() <= value) {
                sum += containers[i].getNominal().getValue() * containers[i].getNumBanknotes();
            }
        }
        return sum >= value;
    }

    public boolean addMoney(RubleBanknote banknote) {
        int index = indexOfContainer(banknote);
        if (containers[index].canAddBanknotes(1)) {
            containers[index].addBanknotes(1);
            account.addToBalance(banknote.getValue());
            return true;
        }
        return false;
    }

    public int[] setOfBanknotes(int value) {
        int removeValue = value;
        int[] numBanknotes = new int[NUM_CONTAINERS];

        for (int i = 0; i < NUM_CONTAINERS; i++) {
            numBanknotes[i] = removeValue / containers[i].getNominal().getValue();

            if (numBanknotes[i] > containers[i].getNumBanknotes()) {
                numBanknotes[i] = containers[i].getNumBanknotes();
                removeValue = removeValue - numBanknotes[i] * containers[i].getNominal().getValue();
            } else {
                removeValue %= containers[i].getNominal().getValue();
            }
        }

        setOfBanknotesForRemove = numBanknotes;

        return numBanknotes;
    }

    public int[] setOfBanknotesWithPriority(int value, RubleBanknote priorityBanknote) {
        int priorityIndex = indexOfContainer(priorityBanknote);
        int removeValue = value;
        int[] numBanknotes = new int[NUM_CONTAINERS];

        numBanknotes[priorityIndex] = removeValue / containers[priorityIndex].getNominal().getValue();
        if (numBanknotes[priorityIndex] > containers[priorityIndex].getNumBanknotes()) {
            numBanknotes[priorityIndex] = containers[priorityIndex].getNumBanknotes();
            removeValue = removeValue - numBanknotes[priorityIndex] * containers[priorityIndex].getNominal().getValue();
        } else {
            removeValue %= containers[priorityIndex].getNominal().getValue();
        }

        for (int i = 0; i < NUM_CONTAINERS; i++) {
            if (i != priorityIndex) {
                numBanknotes[i] = removeValue / containers[i].getNominal().getValue();

                if (numBanknotes[i] > containers[i].getNumBanknotes()) {
                    numBanknotes[i] = containers[i].getNumBanknotes();
                    removeValue = removeValue - numBanknotes[i] * containers[i].getNominal().getValue();
                } else {
                    removeValue %= containers[i].getNominal().getValue();
                }
            }
        }

        setOfBanknotesForRemove = numBanknotes;

        return numBanknotes;
    }

    public int[] getSetOfBanknotesForRemove() {
        return setOfBanknotesForRemove;
    }

    public RubleBanknote[] getSetOfNominal() {
        RubleBanknote[] set = new RubleBanknote[NUM_CONTAINERS];
        for (int i = 0; i < NUM_CONTAINERS; i++) {
            set[i] = containers[i].getNominal();
        }
        return set;
    }

    public int valueOfSet(int[] numBanknotes) {
        int value = 0;

        for (int i = 0; i < NUM_CONTAINERS; i++) {
            value += numBanknotes[i] * containers[i].getNominal().getValue();
        }

        return value;
    }

    public int removeMoney(int value) {
        if (account.getBalance() < value) {
            return 0;
        }

        int[] setForRemove = setOfBanknotes(value);
        int removeValue = valueOfSet(setForRemove);
        account.removeFromBalance(removeValue);

        for (int i = 0; i < NUM_CONTAINERS; i++) {
            containers[i].removeBanknotes(setForRemove[i]);
        }

        return removeValue;
    }

    public int removeMoneyWithPriority(int value, RubleBanknote priorityBanknote) {
        if (account.getBalance() < value) {
            return 0;
        }

        int[] setForRemove = setOfBanknotesWithPriority(value, priorityBanknote);
        int removeValue = valueOfSet(setForRemove);
        account.removeFromBalance(removeValue);

        for (int i = 0; i < NUM_CONTAINERS; i++) {
            containers[i].removeBanknotes(setForRemove[i]);
        }

        return removeValue;
    }
}
