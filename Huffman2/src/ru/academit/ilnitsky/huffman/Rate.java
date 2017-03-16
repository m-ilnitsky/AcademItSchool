package ru.academit.ilnitsky.huffman;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by UserLabView on 06.03.17.
 */
public class Rate {
    private RepeatByteArray byteSymbols;

    private NumByteArray[] numByteArrays;
    private int minNum = 2;
    private int maxNum = -1;

    public Rate(int maxLength) {
        byteSymbols = new RepeatByteArray();
        numByteArrays = new NumByteArray[maxLength];
    }

    private void readFromFile(String fileName) throws IOException {

        byte[] fileBytes;

        try (
                FileInputStream file = new FileInputStream(fileName);
        ) {
            fileBytes = new byte[file.available()];
            file.read(fileBytes, 0, file.available());

            int count = 1;
            byte old = fileBytes[0];
            for (int i = 1; i < fileBytes.length; i++) {
                if (fileBytes[i] != fileBytes[i - 1]) {
                    byteSymbols.add(old, count);
                    old = fileBytes[i];
                    count = 1;
                } else {
                    count++;
                }
            }
            if (count > 1) {
                byteSymbols.add(old, count);
            }

            /////////////////////////////////////
            System.out.println("* File Name = '" + fileName + "'");
            System.out.println("* Bytes in File = " + fileBytes.length);
            System.out.println();

            System.out.println("*** Byte ***");
            byteSymbols.trim();
            byteSymbols.sort(new SortedByLength());
            System.out.println("*** Symbols = " + byteSymbols.getNumInputBytes());
            System.out.println("*** Symbols in Alphabet = " + byteSymbols.getNumberSymbolsInAlphabet());
            System.out.println("*** Symbols in File = " + byteSymbols.getNumSymbolsInFile());
            //byteSymbols.print();
            //byteSymbols.printStatistic();
            System.out.println("*** Threshold = " + byteSymbols.calcThreshold());
            byteSymbols.removeSubThresholdLength();
            byteSymbols.sort(new SortedByLength());
            System.out.println("*** Symbols = " + byteSymbols.getNumInputBytes());
            System.out.println("*** Symbols in Alphabet = " + byteSymbols.getNumberSymbolsInAlphabet());
            System.out.println("*** Symbols in File = " + byteSymbols.getNumSymbolsInFile());
            System.out.println("*** Threshold = " + byteSymbols.calcThreshold());
            byteSymbols.print();
            //byteSymbols.printStatistic();
            /////////////////////////////

            byteSymbols.removeSubThresholdLength();
            int threshold = byteSymbols.calcThreshold();

            numByteArrays[2] = new NumByteArray(1, byteSymbols.getNumByteSymbols(threshold));
            for (int i = 1; i < fileBytes.length; i++) {
                numByteArrays[2].add(new byte[]{fileBytes[i - 1]}, fileBytes[i]);
            }

            //////////////////////////
            System.out.println("*** 2-Bytes ***");
            System.out.println("*** All Symbols in Alphabet = " + numByteArrays[2].getNumberSymbolsInAlphabet(1));
            //numByteArrays[2].print(1);
            System.out.println("*** Above Symbols in Alphabet = " + numByteArrays[2].getNumberSymbolsInAlphabet(threshold));
            numByteArrays[2].print(threshold);
            //////////////////////////

            for (int i = 3; i < numByteArrays.length; i++) {
                int lastSize = i - 1;
                numByteArrays[i] = new NumByteArray(lastSize, numByteArrays[lastSize].getNumByteSymbols(threshold));
                for (int j = lastSize; j < fileBytes.length; j++) {
                    byte[] lastBytes = new byte[lastSize];
                    for (int k = lastSize; k > 0; k--) {
                        lastBytes[lastSize - k] = fileBytes[j - k];
                    }
                    numByteArrays[i].add(lastBytes, fileBytes[j]);
                }

                //////////////////////////
                System.out.println("*** " + i + "-Bytes ***");
                System.out.println("*** All Symbols in Alphabet = " + numByteArrays[i].getNumberSymbolsInAlphabet(1));
                //numByteArrays[i].print(1);
                System.out.println("*** Above Threshold Symbols in Alphabet = " + numByteArrays[i].getNumberSymbolsInAlphabet(threshold));
                numByteArrays[i].print(threshold);
                //////////////////////////

                if (numByteArrays[i].getNumberSymbolsInAlphabet(threshold) == 0) {
                    maxNum = i - 1;
                    break;
                }
                if (i == numByteArrays.length - 1) {
                    maxNum = i;
                }
            }

            for(int i=minNum;i<=maxNum;i++){
                numByteArrays[i].deleteRepeatBytes();

                //////////////////////////
                System.out.println("*** " + i + "-Bytes ***");
                System.out.println("*** Symbols in Alphabet = " + numByteArrays[i].getNumberSymbolsInAlphabet(threshold));
                numByteArrays[i].print(threshold);
                //////////////////////////
            }
        }
    }

    public static void main(String[] args) {
        Rate rate = new Rate(64);

        String file = "text.fb2";
        //String file = "Matrix.java";

        try {
            rate.readFromFile(file);

        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }

    }
}
