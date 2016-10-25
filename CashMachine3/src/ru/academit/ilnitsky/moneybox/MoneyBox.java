package ru.academit.ilnitsky.moneybox;

import java.util.Arrays;
import java.util.Collections;

/**
 * Класс "Копилка"
 * Created by UserLabView on 21.10.16.
 */
public class MoneyBox {
    private final int numContainers;

    private final RubleBanknote[] nominals;
    private final int[] setOfBanknotesForRemove;
    private final Container[] containers;

    public MoneyBox(int containerSize, int numBanknotes) {
        RubleBanknote[] banknotes = RubleBanknote.values();
        Arrays.sort(banknotes, Collections.reverseOrder(new SortedByValue()));

        numContainers = Math.min(banknotes.length, 9); //9 - максимальное число в меню

        nominals = new RubleBanknote[numContainers];
        setOfBanknotesForRemove = new int[numContainers];
        containers = new Container[numContainers];

        for (int i = 0; i < numContainers; i++) {
            nominals[i] = banknotes[i];
            containers[i] = new Container(banknotes[i], containerSize, numBanknotes);
        }
    }

    private int getIndexOfContainer(RubleBanknote banknote) {
        for (int i = 0; i < numContainers; i++) {
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
        for (int i = 0; i < numContainers; i++) {
            result += containers[i].getNumBanknotes() * containers[i].getNominal().getValue();
        }
        return result;
    }

    public boolean isAvailable(RubleBanknote banknote) {
        return getAvailableBanknote(banknote) > 0;
    }

    public boolean isAvailable(int value) {
        int sum = 0;
        for (int i = 0; i < numContainers; i++) {
            if (containers[i].getNominal().getValue() <= value) {
                sum += containers[i].getNominal().getValue() * containers[i].getNumBanknotes();
            }
        }
        return sum >= value;
    }

    public boolean isAvailable(int value, RubleBanknote priorityBanknote) {
        return value == valueOfSet(setOfBanknotes(value, priorityBanknote));
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
        int[] numBanknotes = new int[numContainers];

        for (int i = 0; i < numContainers; i++) {
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
        int[] numBanknotes = new int[numContainers];

        int numBanknotesPriority = value / containers[priorityIndex].getNominal().getValue();
        if (numBanknotesPriority > containers[priorityIndex].getNumBanknotes()) {
            numBanknotesPriority = containers[priorityIndex].getNumBanknotes();
        }

        for (int numPriority = numBanknotesPriority; numPriority > 0; numPriority--) {
            int removeValue = value;
            numBanknotes[priorityIndex] = numPriority;
            removeValue -= numBanknotes[priorityIndex] * containers[priorityIndex].getNominal().getValue();

            for (int i = 0; i < numContainers; i++) {
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

        System.arraycopy(numBanknotes, 0, setOfBanknotesForRemove, 0, numContainers);
        return numBanknotes;
    }

    public int[] getSetOfBanknotes() {
        int[] numBanknotes = new int[numContainers];
        System.arraycopy(setOfBanknotesForRemove, 0, numBanknotes, 0, numContainers);
        return numBanknotes;
    }

    public RubleBanknote[] getNominals() {
        RubleBanknote[] banknotes = new RubleBanknote[numContainers];
        System.arraycopy(nominals, 0, banknotes, 0, numContainers);
        return banknotes;
    }

    private int valueOfSet(int[] numBanknotes) {
        int value = 0;

        for (int i = 0; i < numContainers; i++) {
            value += numBanknotes[i] * containers[i].getNominal().getValue();
        }

        return value;
    }

    public int removeMoney(int value, RubleBanknote priorityBanknote) {

        int[] setForRemove = setOfBanknotes(value, priorityBanknote);
        int removeValue = valueOfSet(setForRemove);

        for (int i = 0; i < numContainers; i++) {
            containers[i].removeBanknotes(setForRemove[i]);
        }

        return removeValue;
    }
}
