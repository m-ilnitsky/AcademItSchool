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

    DoubleByteArray doubleByteSymbols1;
    DoubleByteArray doubleByteSymbols2;
    DoubleByteArray doubleByteSymbols;
    ThreeByteArray threeByteSymbols;
    NumByteArray fourByteSymbols;
    NumByteArray fiveByteSymbols;
    NumByteArray sixByteSymbols;

    public Rate() {
        byteSymbols = new RepeatByteArray();
        charSymbols = new RepeatCharArray();
        doubleByteSymbols1 = new DoubleByteArray();
        doubleByteSymbols2 = new DoubleByteArray();
        doubleByteSymbols = new DoubleByteArray();
        threeByteSymbols = new ThreeByteArray();
    }

    private void readFromFile(String fileName) throws IOException {

        byte[] bytes;

        try (
                FileInputStream file = new FileInputStream(fileName);
        ) {
            bytes = new byte[file.available()];
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

            doubleByteSymbols1.initMask(byteSymbols.getSingleByteRates(), byteSymbols.calcThreshold());

            for (int i = 1; i < bytes.length; i += 2) {
                doubleByteSymbols1.add(bytes[i - 1], bytes[i]);
            }

            doubleByteSymbols2.initMask(byteSymbols.getSingleByteRates(), byteSymbols.calcThreshold());

            for (int i = 2; i < bytes.length; i += 2) {
                doubleByteSymbols2.add(bytes[i - 1], bytes[i]);
            }

            doubleByteSymbols.initMask(byteSymbols.getSingleByteRates(), byteSymbols.calcThreshold());

            for (int i = 1; i < bytes.length; i++) {
                doubleByteSymbols.add(bytes[i - 1], bytes[i]);
            }

            threeByteSymbols.initMask(byteSymbols.getSingleByteRates(), byteSymbols.calcThreshold());

            for (int i = 2; i < bytes.length; i++) {
                threeByteSymbols.add(bytes[i - 2], bytes[i - 1], bytes[i]);
            }

            fourByteSymbols = new NumByteArray(3, threeByteSymbols.getNumByteSymbols());

            for (int i = 3; i < bytes.length; i++) {
                fourByteSymbols.add(new byte[]{bytes[i - 3], bytes[i - 2], bytes[i - 1]}, bytes[i]);
            }

            int threshold = byteSymbols.calcThreshold();

            fiveByteSymbols = new NumByteArray(4, fourByteSymbols.getNumByteSymbols(threshold));

            for (int i = 4; i < bytes.length; i++) {
                fiveByteSymbols.add(new byte[]{bytes[i - 4], bytes[i - 3], bytes[i - 2], bytes[i - 1]}, bytes[i]);
            }

            sixByteSymbols = new NumByteArray(5, fiveByteSymbols.getNumByteSymbols(threshold));

            for (int i = 5; i < bytes.length; i++) {
                sixByteSymbols.add(new byte[]{bytes[i - 5], bytes[i - 4], bytes[i - 3], bytes[i - 2], bytes[i - 1]}, bytes[i]);
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
        //byteSymbols.printAll();
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

        int threshold = byteSymbols.calcThreshold();

        System.out.println();
        System.out.println("*** DoubleByte1 ***");
        System.out.println("*** Symbols in Alphabet = " + doubleByteSymbols1.getNumSymbolsInAlphabet());
        //doubleByteSymbols1.printAll();
        doubleByteSymbols1.maskSubThreshold(threshold);
        System.out.println("*** Symbols in Alphabet = " + doubleByteSymbols1.getNumSymbolsInAlphabet());
        doubleByteSymbols1.printAll();

        System.out.println();
        System.out.println("*** DoubleByte2 ***");
        System.out.println("*** Symbols in Alphabet = " + doubleByteSymbols2.getNumSymbolsInAlphabet());
        //doubleByteSymbols2.printAll();
        doubleByteSymbols2.maskSubThreshold(threshold);
        System.out.println("*** Symbols in Alphabet = " + doubleByteSymbols2.getNumSymbolsInAlphabet());
        doubleByteSymbols2.printAll();

        System.out.println();
        System.out.println("*** NumByteSymbol ***");
        System.out.println("*** Symbols in Alphabet = " + doubleByteSymbols.getNumSymbolsInAlphabet());
        //doubleByteSymbols.printAll();
        doubleByteSymbols.maskSubThreshold(threshold);
        System.out.println("*** Symbols in Alphabet = " + doubleByteSymbols.getNumSymbolsInAlphabet());
        doubleByteSymbols.printAll();

        System.out.println();
        System.out.println("*** ThreeByte ***");
        System.out.println("*** Symbols in Alphabet = " + threeByteSymbols.getNumSymbolsInAlphabet());
        //threeByteSymbols.printAll();
        threeByteSymbols.maskSubThreshold(threshold);
        System.out.println("*** Symbols in Alphabet = " + threeByteSymbols.getNumSymbolsInAlphabet());
        threeByteSymbols.printAll();

        System.out.println();
        System.out.println("*** FourByte ***");
        System.out.println("*** Symbols in Alphabet = " + fourByteSymbols.getNumSymbolsInAlphabet(1));
        //fourByteSymbols.print(1);
        System.out.println("*** Symbols in Alphabet = " + fourByteSymbols.getNumSymbolsInAlphabet(threshold));
        fourByteSymbols.print(threshold);

        System.out.println();
        System.out.println("*** FiveByte ***");
        System.out.println("*** Symbols in Alphabet = " + fiveByteSymbols.getNumSymbolsInAlphabet(1));
        //fiveByteSymbols.print(1);
        System.out.println("*** Symbols in Alphabet = " + fiveByteSymbols.getNumSymbolsInAlphabet(threshold));
        fiveByteSymbols.print(threshold);

        System.out.println();
        System.out.println("*** SixByte ***");
        System.out.println("*** Symbols in Alphabet = " + sixByteSymbols.getNumSymbolsInAlphabet(1));
        //sixByteSymbols.print(1);
        System.out.println("*** Symbols in Alphabet = " + sixByteSymbols.getNumSymbolsInAlphabet(threshold));
        sixByteSymbols.print(threshold);

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
