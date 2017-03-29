package ru.academit.ilnitsky.huffman;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by UserLabView on 29.03.17.
 */
public class Test {
    public static void main(String[] args) {
        FileAnalyzer fileAnalyzer = new FileAnalyzer(256);

        String file;
        file = "text.fb2";
        //file = "Matrix.java";

        try {
            fileAnalyzer.readFromFile(file);

            Scanner scanner = new Scanner(System.in);
            scanner.next();

        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
    }
}
