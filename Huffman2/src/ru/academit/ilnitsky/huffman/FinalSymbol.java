package ru.academit.ilnitsky.huffman;

/**
 * Created by UserLabView on 29.03.17.
 */
public class FinalSymbol extends NumByteSymbol implements Comparable<FinalSymbol> {
    private int rate;

    public FinalSymbol(byte[] symbol, int rate) {
        super(symbol);
        this.rate = rate;
    }

    public FinalSymbol(NumByteSymbol numByteSymbol, int rate) {
        super(numByteSymbol.symbol);
        this.rate = rate;
    }

    public FinalSymbol(AlphabetSymbol alphabetSymbol) {
        super(alphabetSymbol.symbol);
        this.rate = alphabetSymbol.rate;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (byte b : symbol) {
            sb.append("[").append((char) b).append("]");
        }

        sb.append("  Rate=").append(rate);

        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int result = 127;

        result += symbol.length + rate;
        for (byte b : symbol) {
            result = prime * result;
            result += b;
        }
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        } else if (this == object) {
            return true;
        } else if (this.getClass() == object.getClass()) {
            FinalSymbol other = (FinalSymbol) object;

            if (this.symbol.length != other.symbol.length) {
                return false;
            } else if (this.rate != other.rate) {
                return false;
            } else {
                for (int i = 0; i < symbol.length; i++) {
                    if (this.symbol[i] != other.symbol[i]) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(FinalSymbol symbol) {
        if (rate > symbol.rate) {
            return 1;
        } else if (rate < symbol.rate) {
            return -1;
        } else {
            return 0;
        }
    }
}
