package ru.academit.ilnitsky.huffman.alphabet;

/**
 * Интерфейс символа переменной длины
 * Created by UserLabView on 09.03.17.
 */
public interface SymbolInterface {

    int getLength();

    byte[] getSymbol();
}
