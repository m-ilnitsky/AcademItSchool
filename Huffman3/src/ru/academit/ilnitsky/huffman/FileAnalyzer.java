package ru.academit.ilnitsky.huffman;

import ru.academit.ilnitsky.huffman.alphabet.FinalSymbol;
import ru.academit.ilnitsky.huffman.alphabet.AlphabetBuilder;
import ru.academit.ilnitsky.huffman.alphabet.NumByteAccumulator;
import ru.academit.ilnitsky.huffman.alphabet.SingleByteAccumulator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by UserLabView on 29.03.17.
 */
public class FileAnalyzer {
    private SingleByteAccumulator singleByteSymbols;

    private NumByteAccumulator[] numByteSymbols;
    private int minNum = 2;
    private int maxNum = -1;

    private AlphabetBuilder alphabetBuilder;

    private FinalSymbol[] finalSymbols;

    public FileAnalyzer(int maxLength) {
        singleByteSymbols = new SingleByteAccumulator();
        numByteSymbols = new NumByteAccumulator[maxLength];
    }

    public void readFromFile(String fileName) throws IOException {

        byte[] fileBytes;

        try (
                FileInputStream file = new FileInputStream(fileName);
        ) {
            fileBytes = new byte[file.available()];
            file.read(fileBytes, 0, file.available());

            for (int i = 0; i < fileBytes.length; i++) {
                singleByteSymbols.add(fileBytes[i]);
            }

            /////////////////////////////////////
            System.out.println("* File Name = '" + fileName + "'");
            System.out.println("* Bytes in File = " + fileBytes.length);
            System.out.println();

            int threshold = singleByteSymbols.calcThreshold();

            /////////////////////////////////////
            System.out.println("*** Byte ***");
            System.out.println("*** Symbols = " + fileBytes.length);
            System.out.println("*** Symbols in Alphabet = " + singleByteSymbols.getNumberSymbolsInAlphabet(1));
            System.out.println("*** Threshold = " + threshold);
            singleByteSymbols.print(1);

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

                //////////////////////////
                System.out.println("*** " + i + "-Bytes ***");
                System.out.println("*** All Symbols in Alphabet = " + numByteSymbols[i].getNumberSymbolsInAlphabet(1));
                //numByteSymbols[i].print(1);
                System.out.println("*** Above Threshold Symbols in Alphabet = " + numByteSymbols[i].getNumberSymbolsInAlphabet(threshold));
                numByteSymbols[i].print(threshold);
                //////////////////////////

                if (numByteSymbols[i].getNumberSymbolsInAlphabet(threshold) == 0) {
                    maxNum = i - 1;
                    break;
                }
                if (i == numByteSymbols.length - 1) {
                    maxNum = i;
                }
            }

            for (int i = minNum; i <= maxNum; i++) {
                numByteSymbols[i].deleteRepeatBytes();

                //////////////////////////
                System.out.println("*** " + i + "-Bytes ***");
                System.out.println("*** Symbols in Alphabet = " + numByteSymbols[i].getNumberSymbolsInAlphabet(threshold));
                numByteSymbols[i].print(threshold);
                //////////////////////////
            }

            /////////////////////
            System.out.println();
            System.out.println("AlphabetBuilder:");
            System.out.println();

            alphabetBuilder = new AlphabetBuilder(maxNum + 1, threshold);
            alphabetBuilder.addSymbols(1, singleByteSymbols.getAlphabetBuilderSymbols(1));
            for (int i = minNum; i <= maxNum; i++) {
                alphabetBuilder.addSymbols(i, numByteSymbols[i]);
            }
            alphabetBuilder.setDepth();
            alphabetBuilder.sortByDepthAndRate();
            alphabetBuilder.setRates();
            ////////////////////////
            alphabetBuilder.print();

            ////////////////////////////////////
            System.out.println();
            System.out.println("finalSymbols:");
            System.out.println();

            finalSymbols = alphabetBuilder.getAlphabet();

            int sumRate = 0;
            for (FinalSymbol s : finalSymbols) {
                //////////////////////
                System.out.println(s);
                sumRate += s.getRate();
            }

            /////////////////////
            System.out.println();
            System.out.println();

            Arrays.sort(finalSymbols);

            /////////////////////////////////////
            for (FinalSymbol s : finalSymbols) {
                System.out.println(s);
            }

            /////////////////////
            System.out.println();
            System.out.println("*** Num Symbols:  " + finalSymbols.length);

            System.out.println("*** Initial File Size [bytes] = " + fileBytes.length);
            System.out.println("*** Simple Coded File Size [bytes] = " + sumRate);
        }
    }
}
