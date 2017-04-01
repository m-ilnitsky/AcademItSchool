package ru.academit.ilnitsky.huffman;

import ru.academit.ilnitsky.huffman.alphabet.AlphabetBuilder;

import java.io.IOException;
import java.util.Scanner;

/**
 * Тест создания алфавита (словаря)
 * Created by UserLabView on 29.03.17.
 */
public class Test {
    public static void main(String[] args) {
        String fileName;
        fileName = "text.fb2";
        //fileName = "Matrix.java";

        try {
            AlphabetBuilder alphabetBuilder = new AlphabetBuilder(256, fileName);

            alphabetBuilder.collectSingleBytes();
            alphabetBuilder.calcThreshold();
            alphabetBuilder.collectManyBytes();
            alphabetBuilder.createAlphabet();

            alphabetBuilder.printByteAlphabet();
            System.out.println();
            alphabetBuilder.printSyllableAlphabet();

        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }

        Scanner scanner = new Scanner(System.in);
        scanner.next();
    }
}
