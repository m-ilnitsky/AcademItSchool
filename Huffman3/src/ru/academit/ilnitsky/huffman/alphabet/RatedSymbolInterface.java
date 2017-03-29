package ru.academit.ilnitsky.huffman.alphabet;

/**
 * Created by UserLabView on 29.03.17.
 */
public interface RatedSymbolInterface extends SymbolInterface {

    void setRate(int rate);

    int getRate();
}
