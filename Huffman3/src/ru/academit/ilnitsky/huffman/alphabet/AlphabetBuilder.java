package ru.academit.ilnitsky.huffman.alphabet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Создатель словаря (алфавита) для заданного файла
 * Created by UserLabView on 29.03.17.
 */
public class AlphabetBuilder {
    private final String fileName;
    private byte[] fileBytes;
    private SingleByteAccumulator singleByteSymbols;
    private NumByteAccumulator[] numByteSymbols;
    private AlphabetSymbol[] alphabetSymbols;
    private int minNum = 2;
    private int maxNum = -1;
    private int threshold = 1;

    public AlphabetBuilder(int maxLength, String fileName) throws IOException {
        this.fileName = fileName;
        singleByteSymbols = new SingleByteAccumulator();
        numByteSymbols = new NumByteAccumulator[maxLength];

        try (
                FileInputStream file = new FileInputStream(fileName)
        ) {
            fileBytes = new byte[file.available()];
            file.read(fileBytes, 0, file.available());
        }
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void collectSingleBytes() {
        for (byte b : fileBytes) {
            singleByteSymbols.add(b);
        }
    }

    public void calcThreshold() {
        threshold = singleByteSymbols.calcThreshold();
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public void collectManyBytes() {
        for (int i = 2; i < numByteSymbols.length; i++) {

            if (i == 2) {
                numByteSymbols[2] = new NumByteAccumulator(1, singleByteSymbols.getNumByteSymbols(threshold));
                for (int j = 1; j < fileBytes.length; j++) {
                    numByteSymbols[2].add(new byte[]{fileBytes[j - 1]}, fileBytes[j]);
                }
            } else {
                int lastSize = i - 1;
                numByteSymbols[i] = new NumByteAccumulator(lastSize, numByteSymbols[lastSize].getNumByteSymbols(threshold));
                for (int j = lastSize; j < fileBytes.length; j++) {
                    byte[] lastBytes = new byte[lastSize];
                    for (int k = lastSize; k > 0; k--) {
                        lastBytes[lastSize - k] = fileBytes[j - k];
                    }
                    numByteSymbols[i].add(lastBytes, fileBytes[j]);
                }
            }

            if (numByteSymbols[i].getNumberSymbolsInAlphabet(threshold) == 0) {
                maxNum = i - 1;
                break;
            }

            if (i == numByteSymbols.length - 1) {
                maxNum = i;
            }
        }
    }

    public void createAlphabet() {
        RatedSymbolSelector ratedSymbolSelector;

        ratedSymbolSelector = new RatedSymbolSelector(maxNum + 1, threshold);
        ratedSymbolSelector.addSymbols(1, singleByteSymbols.getRatedSymbols());
        for (int i = minNum; i <= maxNum; i++) {
            ratedSymbolSelector.addSymbols(i, numByteSymbols[i]);
        }
        ratedSymbolSelector.setDepth();
        ratedSymbolSelector.sortByDepthAndRate();
        ratedSymbolSelector.setRates();

        alphabetSymbols = ratedSymbolSelector.getAlphabet();

        Arrays.sort(alphabetSymbols);
    }

    public AlphabetSymbol[] getByteAlphabet() {
        return singleByteSymbols.getAlphabetSymbols();
    }

    public AlphabetSymbol[] getSyllableAlphabet() {
        return alphabetSymbols;
    }

    public int getFileSizeWithBytes() {
        return fileBytes.length;
    }

    public int getFileSizeWithAlphabet() {
        int sumRate = 0;
        for (AlphabetSymbol s : alphabetSymbols) {
            sumRate += s.getRate();
        }
        return sumRate;
    }

    public void printByteAlphabet() {
        int numSymbols = singleByteSymbols.getNumberSymbolsInAlphabet();

        System.out.println("****************************");
        System.out.println("*** Single Byte Alphabet ***");
        System.out.println("*** File Name = '" + fileName + "'");
        System.out.println("*** Bytes in File = " + fileBytes.length);
        System.out.println("*** Number Symbols = " + numSymbols);
        System.out.println();

        singleByteSymbols.print();

        System.out.println();
        System.out.println("*** Number Symbols = " + numSymbols);
        System.out.println("******");
    }

    public void printSyllableAlphabet() {
        System.out.println("*************************");
        System.out.println("*** Syllable Alphabet ***");
        System.out.println("*** File Name = '" + fileName + "'");
        System.out.println("*** Number Symbols = " + alphabetSymbols.length);
        System.out.println("*** Initial File Size [bytes] = " + getFileSizeWithBytes());
        System.out.println("*** Simple Coded File Size [bytes] = " + getFileSizeWithAlphabet());
        System.out.println();

        for (AlphabetSymbol s : alphabetSymbols) {
            System.out.println(s);
        }

        System.out.println();
        System.out.println("*** Number Symbols = " + alphabetSymbols.length);
        System.out.println("*** Initial File Size [bytes] = " + getFileSizeWithBytes());
        System.out.println("*** Simple Coded File Size [bytes] = " + getFileSizeWithAlphabet());
        System.out.println("******");
    }
}
