package ru.academit.ilnitsky.moneybox;

import static ru.academit.ilnitsky.moneybox.RubleBanknote.*;

/**
 * Класс "Копилка"
 * Created by UserLabView on 21.10.16.
 */
public class MoneyBox {
    private final static int NUM_CONTAINERS = 5;
    private final static int CONTAINER_SIZE = 100;

    private final RubleBanknote[] nominals = new RubleBanknote[NUM_CONTAINERS];

    private int[] setOfBanknotesForRemove = new int[NUM_CONTAINERS];
    private Container[] containers = new Container[NUM_CONTAINERS];

    public MoneyBox() {
        containers[0] = new Container(R5000, CONTAINER_SIZE, 10);
        containers[1] = new Container(R1000, CONTAINER_SIZE, 20);
        containers[2] = new Container(R500, CONTAINER_SIZE, 30);
        containers[3] = new Container(R100, CONTAINER_SIZE, 40);
        containers[4] = new Container(R50, CONTAINER_SIZE, 50);

        for (int i = 0; i < NUM_CONTAINERS; i++) {
            nominals[i] = containers[i].getNominal();
        }
    }

    public MoneyBox(int numBanknotes) {
        containers[0] = new Container(R5000, CONTAINER_SIZE, numBanknotes);
        containers[1] = new Container(R1000, CONTAINER_SIZE, numBanknotes);
        containers[2] = new Container(R500, CONTAINER_SIZE, numBanknotes);
        containers[3] = new Container(R100, CONTAINER_SIZE, numBanknotes);
        containers[4] = new Container(R50, CONTAINER_SIZE, numBanknotes);

        for (int i = 0; i < NUM_CONTAINERS; i++) {
            nominals[i] = containers[i].getNominal();
        }
    }

    private int getIndexOfContainer(RubleBanknote banknote) {
        for (int i = 0; i < NUM_CONTAINERS; i++) {
            if (containers[i].getNominal() == banknote) {
                return i;
            }
        }
        return -1;
    }

    private int getUnoccupiedSpace(RubleBanknote banknote) {
        int index = getIndexOfContainer(banknote);
        return containers[index].unoccupiedSpace();
    }

    public boolean isUnoccupiedSpace(RubleBanknote banknote) {
        return getUnoccupiedSpace(banknote) > 0;
    }

    public int getAvailableBanknote(RubleBanknote banknote) {
        int index = getIndexOfContainer(banknote);
        return containers[index].getNumBanknotes();
    }

    public int getAvailableMoney(RubleBanknote banknote) {
        int index = getIndexOfContainer(banknote);
        return containers[index].getNumBanknotes() * containers[index].getNominal().getValue();
    }

    public int getAvailableMoney() {
        int result = 0;
        for (int i = 0; i < NUM_CONTAINERS; i++) {
            result += containers[i].getNumBanknotes() * containers[i].getNominal().getValue();
        }
        return result;
    }

    public boolean isAvailable(RubleBanknote banknote) {
        return getAvailableBanknote(banknote) > 0;
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

    public boolean isAvailable(int value, RubleBanknote priorityBanknote) {
        setOfBanknotes(value, priorityBanknote);
        return value == valueOfSet(setOfBanknotesForRemove);
    }

    public boolean addMoney(RubleBanknote banknote) {
        int index = getIndexOfContainer(banknote);
        if (containers[index].canAddBanknotes(1)) {
            containers[index].addBanknotes(1);
            return true;
        }
        return false;
    }

    private int[] setOfBanknotes(int value, RubleBanknote priorityBanknote) {
        int priorityIndex = getIndexOfContainer(priorityBanknote);
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

        return setOfBanknotesForRemove;
    }

    public int[] getSetOfBanknotes() {
        return setOfBanknotesForRemove;
    }

    public RubleBanknote[] getNominals() {
        return nominals;
    }

    private int valueOfSet(int[] numBanknotes) {
        int value = 0;

        for (int i = 0; i < NUM_CONTAINERS; i++) {
            value += numBanknotes[i] * containers[i].getNominal().getValue();
        }

        return value;
    }

    public int removeMoney(int value, RubleBanknote priorityBanknote) {

        int[] setForRemove = setOfBanknotes(value, priorityBanknote);
        int removeValue = valueOfSet(setForRemove);

        for (int i = 0; i < NUM_CONTAINERS; i++) {
            containers[i].removeBanknotes(setForRemove[i]);
        }

        return removeValue;
    }
}
