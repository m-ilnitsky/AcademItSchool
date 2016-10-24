package ru.academit.ilnitsky.moneybox;

/**
 * Класс "Контейнер для денег"
 * Created by UserLabView on 21.10.16.
 */
class Container {
    private final RubleBanknote nominal;
    private final int maxNumBanknotes;
    private int numBanknotes;

    public Container(RubleBanknote nominal, int maxNumBanknotes, int numBanknotes) {
        if (maxNumBanknotes <= 0) {
            throw new IllegalArgumentException("maxNumBanknotes <= 0");
        } else if (numBanknotes < 0) {
            throw new IllegalArgumentException("numBanknotes < 0");
        } else if (maxNumBanknotes < numBanknotes) {
            throw new IllegalArgumentException("maxNumBanknotes < numBanknotes");
        }
        this.nominal = nominal;
        this.maxNumBanknotes = maxNumBanknotes;
        this.numBanknotes = numBanknotes;
    }

    public Container(RubleBanknote nominal, int maxNumBanknotes) {
        this(nominal, maxNumBanknotes, 0);
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
            throw new IllegalArgumentException("numBanknotes < 0");
        }
        return numBanknotes <= unoccupiedSpace();
    }

    public boolean canRemoveBanknotes(int numBanknotes) {
        if (numBanknotes < 0) {
            throw new IllegalArgumentException("numBanknotes < 0");
        }
        return numBanknotes <= this.numBanknotes;
    }

    public boolean addBanknotes(int numBanknotes) {
        if (!canAddBanknotes(numBanknotes)) {
            return false;
        }
        this.numBanknotes += numBanknotes;
        return true;
    }

    public boolean removeBanknotes(int numBanknotes) {
        if (!canRemoveBanknotes(numBanknotes)) {
            return false;
        }
        this.numBanknotes -= numBanknotes;
        return true;
    }
}
