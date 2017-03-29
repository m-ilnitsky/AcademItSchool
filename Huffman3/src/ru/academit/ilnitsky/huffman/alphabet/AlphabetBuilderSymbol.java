package ru.academit.ilnitsky.huffman.alphabet;

/**
 * Created by Mike on 27.03.2017.
 */
public class AlphabetBuilderSymbol extends NumByteSymbol implements RatedSymbolInterface, Comparable<AlphabetBuilderSymbol> {
    protected int depth;
    protected int rate;

    public AlphabetBuilderSymbol(byte[] symbol, int rate) {
        super(symbol);
        this.rate = rate;
    }

    public AlphabetBuilderSymbol(NumByteSymbol numByteSymbol, int rate) {
        super(numByteSymbol.symbol);
        this.rate = rate;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public int getRate() {
        return rate;
    }

    @Override
    public void setRate(int rate) {
        this.rate = rate;
    }

    public int indexOfContained(AlphabetBuilderSymbol alphabetBuilderSymbol) {
        if (symbol.length < alphabetBuilderSymbol.symbol.length) {
            return -1;
        } else {
            int diff = symbol.length - alphabetBuilderSymbol.symbol.length;
            for (int i = 0; i <= diff; i++) {
                boolean key = true;
                for (int j = 0; j < alphabetBuilderSymbol.symbol.length; j++) {
                    if (alphabetBuilderSymbol.symbol[j] != symbol[i + j]) {
                        key = false;
                        break;
                    }
                }
                if (key) {
                    return i;
                }
            }
            return -1;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (byte b : symbol) {
            sb.append("[").append((char) b).append("]");
        }

        sb.append("  Depth=").append(depth).append("  Rate=").append(rate);

        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int result = 127;

        result += symbol.length + depth + rate;
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
            AlphabetBuilderSymbol other = (AlphabetBuilderSymbol) object;

            if (this.symbol.length != other.symbol.length) {
                return false;
            } else if (this.rate != other.rate) {
                return false;
            } else if (this.depth != other.depth) {
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
    public int compareTo(AlphabetBuilderSymbol symbol) {
        if (rate > symbol.rate) {
            return 1;
        } else if (rate < symbol.rate) {
            return -1;
        } else {
            return 0;
        }
    }
}
