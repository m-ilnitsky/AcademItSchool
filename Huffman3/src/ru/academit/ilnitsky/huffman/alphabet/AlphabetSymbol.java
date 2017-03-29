package ru.academit.ilnitsky.huffman.alphabet;

/**
 * Символ произвольной длины с указанием частоты встречаемости (точнее количества таких символов в тексте)
 * Created by UserLabView on 29.03.17.
 */
public class AlphabetSymbol extends NumByteSymbol implements Comparable<AlphabetSymbol> {
    private int rate;

    public AlphabetSymbol(byte[] symbol, int rate) {
        super(symbol);
        this.rate = rate;
    }

    public AlphabetSymbol(NumByteSymbol numByteSymbol, int rate) {
        super(numByteSymbol.symbol);
        this.rate = rate;
    }

    public AlphabetSymbol(RatedSymbol ratedSymbol) {
        super(ratedSymbol.symbol);
        this.rate = ratedSymbol.rate;
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
            AlphabetSymbol other = (AlphabetSymbol) object;

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
    public int compareTo(AlphabetSymbol symbol) {
        if (rate > symbol.rate) {
            return 1;
        } else if (rate < symbol.rate) {
            return -1;
        } else {
            return 0;
        }
    }
}
