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
    private final int[] setOfBanknotesForRemove = new int[NUM_CONTAINERS];
    private final Container[] containers = new Container[NUM_CONTAINERS];

    public MoneyBox(int numBanknotes) {
        RubleBanknote[] banknotes = RubleBanknote.values();

        for (int i = 0; i < NUM_CONTAINERS; i++) {
            nominals[i] = banknotes[i];
            containers[i] = new Container(banknotes[i], CONTAINER_SIZE, numBanknotes);
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

    public boolean hasUnoccupiedSpace(RubleBanknote banknote) {
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

    private int[] setOfBanknotesWithoutPriority(int value) {
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

        return numBanknotes;
    }

    private int[] setOfBanknotesWithPriority(int value, RubleBanknote priorityBanknote) {
        int priorityIndex = getIndexOfContainer(priorityBanknote);
        int[] numBanknotes = new int[NUM_CONTAINERS];

        int numBanknotesPriority = value / containers[priorityIndex].getNominal().getValue();
        if (numBanknotesPriority > containers[priorityIndex].getNumBanknotes()) {
            numBanknotesPriority = containers[priorityIndex].getNumBanknotes();
        }

        for (int numPriority = numBanknotesPriority; numPriority > 0; numPriority--) {
            int removeValue = value;
            numBanknotes[priorityIndex] = numPriority;
            removeValue -= numBanknotes[priorityIndex] * containers[priorityIndex].getNominal().getValue();

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

            if (valueOfSet(numBanknotes) == value) {
                return numBanknotes;
            }
        }

        return numBanknotes;
    }

    private int[] setOfBanknotes(int value, RubleBanknote priorityBanknote) {

        int[] numBanknotes = setOfBanknotesWithPriority(value, priorityBanknote);

        if (valueOfSet(numBanknotes) != value) {
            numBanknotes = setOfBanknotesWithoutPriority(value);
        }

        System.arraycopy(numBanknotes, 0, setOfBanknotesForRemove, 0, NUM_CONTAINERS);
        return numBanknotes;
    }

    public int[] getSetOfBanknotes() {
        int[] numBanknotes = new int[NUM_CONTAINERS];
        System.arraycopy(setOfBanknotesForRemove, 0, numBanknotes, 0, NUM_CONTAINERS);
        return numBanknotes;
    }

    public RubleBanknote[] getNominals() {
        RubleBanknote[] banknotes = new RubleBanknote[NUM_CONTAINERS];
        System.arraycopy(nominals, 0, banknotes, 0, NUM_CONTAINERS);
        return banknotes;
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
