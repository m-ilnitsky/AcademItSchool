package ru.academit.ilnitsky.huffman.alphabet;

/**
 * Символ произвольной длины без указания частоты
 * Created by UserLabView on 15.03.17.
 */
public class NumByteSymbol implements SymbolInterface {
    protected byte[] symbol;

    public NumByteSymbol(byte[] symbol) {
        this.symbol = symbol;
    }

    @Override
    public byte[] getSymbol() {
        return symbol;
    }

    @Override
    public int getLength() {
        return symbol.length;
    }

    public boolean isRepeatByte() {
        if (symbol.length == 1) {
            return false;
        }

        boolean isRepeat = true;
        for (int i = 1; i < symbol.length; i++) {
            isRepeat = isRepeat && symbol[i - 1] == symbol[i];
        }
        return isRepeat;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (byte b : symbol) {
            sb.append("[").append((char) b).append("]");
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int result = 127;

        result += symbol.length;
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
            NumByteSymbol other = (NumByteSymbol) object;

            if (this.symbol.length != other.symbol.length) {
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
}
