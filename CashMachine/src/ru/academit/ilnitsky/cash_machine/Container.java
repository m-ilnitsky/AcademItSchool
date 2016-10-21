package ru.academit.ilnitsky.cash_machine;

/**
 * Created by UserLabView on 21.10.16.
 * Класс "Контейнер для денег"
 */
public class Container {
    private final RubleBanknote nominal;
    private final int maxNumBanknotes;
    private int numBanknotes;

    public Container(RubleBanknote nominal, int maxNumBanknotes, int numBanknotes) {
        this.nominal = nominal;
        this.maxNumBanknotes = maxNumBanknotes;
        this.numBanknotes = numBanknotes;
    }

    public Container(RubleBanknote nominal, int maxNumBanknotes) {
        this.nominal = nominal;
        this.maxNumBanknotes = maxNumBanknotes;
        this.numBanknotes = 0;
    }

    public RubleBanknote getNominal() {
        return nominal;
    }

    public int getNumBanknotes() {
        return numBanknotes;
    }

    public int getMaxNumBanknotes() {
        return maxNumBanknotes;
    }

    public int unoccupiedSpace() {
        return maxNumBanknotes - numBanknotes;
    }

    public boolean canAddBanknotes(int numBanknotes) {
        if (numBanknotes < 0) {
            return false;
        }
        return numBanknotes <= unoccupiedSpace();
    }

    public boolean canRemoveBanknotes(int numBanknotes) {
        if (numBanknotes < 0) {
            return false;
        }
        return numBanknotes <= this.numBanknotes;
    }

    public int addBanknotes(int numBanknotes) {
        if (numBanknotes < 0) {
            return 0;
        }

        int change = unoccupiedSpace() - numBanknotes;

        if (change < 0) {
            this.numBanknotes = maxNumBanknotes;
            return -change;
        } else {
            this.numBanknotes += numBanknotes;
            return 0;
        }
    }

    public int removeBanknotes(int numBanknotes) {
        if (numBanknotes < 0) {
            return 0;
        }

        if (this.numBanknotes < numBanknotes) {
            int result = this.numBanknotes;
            this.numBanknotes = 0;
            return result;
        } else {
            this.numBanknotes -= numBanknotes;
            return numBanknotes;
        }
    }
}
