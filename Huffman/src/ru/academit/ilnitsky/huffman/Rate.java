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
                Scanner fileScanner = new Scanner(new FileInputStream(fileName), "ASCII")
        ) {
            while (fileScanner.hasNext()) {

                String line = fileScanner.next();
                char[] chars = line.toCharArray();

                if (chars.length == 1) {
                    byteSymbols.add((byte) (chars[0] - 128), 1);
                    charSymbols.add(chars[0], 1);
                } else {
                    int count = 1;
                    char oldChar = chars[0];
                    for (int i = 1; i < chars.length; i++) {
                        if (chars[i] != chars[i - 1]) {
                            byteSymbols.add((byte) (oldChar - 128), count);
                            charSymbols.add(oldChar, count);
                            oldChar = chars[i];
                            count = 1;
                        } else {
                            count++;
                        }
                    }
                    if (count > 1) {
                        byteSymbols.add((byte) (oldChar - 128), count);
                        charSymbols.add(oldChar, count);
                    }
                }
            }
        }
    }

    public void showSymbolRate() {
        System.out.println("*** ByteStep1 ***");
        byteSymbols.trim();
        byteSymbols.sort(new SortedByLength());
        System.out.println("*** Symbols = " + byteSymbols.getRate());
        System.out.println("*** Threshold = " + byteSymbols.calcThresholdRate());
        byteSymbols.printAll();

        System.out.println();
        System.out.println("*** ByteStep2 ***");
        byteSymbols.removeSubThresholdLength();
        byteSymbols.sort(new SortedByLength());
        System.out.println("*** Symbols = " + byteSymbols.getRate());
        System.out.println("*** Threshold = " + byteSymbols.calcThresholdRate());
        byteSymbols.printAll();

        System.out.println();
        System.out.println("*** CharStep1 ***");
        charSymbols.trim();
        charSymbols.sort(new SortedByLength());
        System.out.println("*** Symbols = " + charSymbols.getRate());
        System.out.println("*** Threshold = " + charSymbols.calcThresholdRate());
        charSymbols.printAll();

        System.out.println();
        System.out.println("*** CharStep2 ***");
        charSymbols.removeSubThresholdLength();
        charSymbols.sort(new SortedByLength());
        System.out.println("*** Symbols = " + charSymbols.getRate());
        System.out.println("*** Threshold = " + charSymbols.calcThresholdRate());
        charSymbols.printAll();
    }

    public static void main(String[] args) {
        Rate rate1 = new Rate();
        Rate rate2 = new Rate();

        String file1 = "LICENSE.txt";
        //String file1 = "Matrix.java";

        try {
            rate1.readFromFile(file1);
            rate1.showSymbolRate();

        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }

    }
}
