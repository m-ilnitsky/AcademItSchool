package ru.academit.ilnitsky.huffman;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by UserLabView on 06.03.17.
 */
public class Rate {
    RepeatByteArray byteSymbols;
    RepeatCharArray charSymbols;

    public Rate() {
        byteSymbols = new RepeatByteArray();
        charSymbols = new RepeatCharArray();
    }

    private void readFromFile(String fileName) throws IOException {
        try (
                FileInputStream file = new FileInputStream(fileName);
        ) {
            byte[] bytes = new byte[file.available()];
            file.read(bytes, 0, file.available());

            int count = 1;
            byte old = bytes[0];
            for (int i = 1; i < bytes.length; i++) {
                if (bytes[i] != bytes[i - 1]) {
                    byteSymbols.add(old, count);
                    old = bytes[i];
                    count = 1;
                } else {
                    count++;
                }
            }
            if (count > 1) {
                byteSymbols.add(old, count);
            }
        }

        try (
                Scanner fileScanner = new Scanner(new FileInputStream(fileName))
        ) {
            while (fileScanner.hasNext()) {

                String line = fileScanner.next();
                char[] chars = line.toCharArray();

                if (chars.length == 1) {
                    charSymbols.add(chars[0], 1);
                } else {
                    int count = 1;
                    char oldChar = chars[0];
                    for (int i = 1; i < chars.length; i++) {
                        if (chars[i] != chars[i - 1]) {
                            charSymbols.add(oldChar, count);
                            oldChar = chars[i];
                            count = 1;
                        } else {
                            count++;
                        }
                    }
                    if (count > 1) {
                        charSymbols.add(oldChar, count);
                    }
                }
            }
        }
    }

    public void showSymbolRate() {
        System.out.println("*** Byte ***");
        byteSymbols.trim();
        byteSymbols.sort(new SortedByLength());
        System.out.println("*** Symbols = " + byteSymbols.getNumInputBytes());
        System.out.println("*** Symbols in Alphabet = " + byteSymbols.getNumSymbolsInAlphabet());
        System.out.println("*** Symbols in File = " + byteSymbols.getNumSymbolsInFile());
        byteSymbols.printAll();
        //byteSymbols.printStatistic();

        System.out.println();
        System.out.println("*** Threshold = " + byteSymbols.calcThreshold());
        byteSymbols.removeSubThresholdLength();
        byteSymbols.sort(new SortedByLength());
        System.out.println("*** Symbols = " + byteSymbols.getNumInputBytes());
        System.out.println("*** Symbols in Alphabet = " + byteSymbols.getNumSymbolsInAlphabet());
        System.out.println("*** Symbols in File = " + byteSymbols.getNumSymbolsInFile());
        System.out.println("*** Threshold = " + byteSymbols.calcThreshold());
        byteSymbols.printAll();
        //byteSymbols.printStatistic();

        /*
        System.out.println();
        System.out.println("*** Char ***");
        charSymbols.trim();
        charSymbols.sort(new SortedByLength());
        System.out.println("*** Symbols = " + charSymbols.getRate());
        System.out.println("*** Symbols in Alphabet = " + charSymbols.getNumSymbolsInAlphabet());
        System.out.println("*** Symbols in File = " + charSymbols.getNumSymbolsInFile());
        //charSymbols.printAll();

        System.out.println();
        System.out.println("*** Threshold = " + charSymbols.calcThreshold());
        charSymbols.removeSubThresholdLength();
        charSymbols.sort(new SortedByLength());
        System.out.println("*** Symbols = " + charSymbols.getRate());
        System.out.println("*** Symbols in Alphabet = " + charSymbols.getNumSymbolsInAlphabet());
        System.out.println("*** Symbols in File = " + charSymbols.getNumSymbolsInFile());
        System.out.println("*** Threshold = " + charSymbols.calcThreshold());
        //charSymbols.printAll();
        */
    }

    public static void main(String[] args) {
        Rate rate1 = new Rate();
        Rate rate2 = new Rate();

        String file1 = "text.fb2";
        //String file1 = "Matrix.java";

        try {
            rate1.readFromFile(file1);
            rate1.showSymbolRate();

        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }

    }
}
