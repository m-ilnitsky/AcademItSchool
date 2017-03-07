package ru.academit.ilnitsky.huffman;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by UserLabView on 06.03.17.
 */
public class Rate {
    RepeatByteArray symbols;

    public Rate() {
        symbols = new RepeatByteArray();
    }

    private void readFromFile(String fileName) throws IOException {
        try (
                Scanner fileScanner = new Scanner(new FileInputStream(fileName), "ASCII")
        ) {
            while (fileScanner.hasNext()) {

                String line = fileScanner.next();
                char[] chars = line.toCharArray();

                if (chars.length == 1) {
                    symbols.add((byte) (chars[0] - 128), 1);
                } else {
                    int count = 1;
                    char oldChar = chars[0];
                    for (int i = 1; i < chars.length; i++) {
                        if (chars[i] != chars[i - 1]) {
                            symbols.add((byte) (oldChar - 128), count);
                            oldChar = chars[i];
                            count = 1;
                        } else {
                            count++;
                        }
                    }
                    if (count > 1) {
                        symbols.add((byte) (oldChar - 128), count);
                    }
                }
            }
        }
    }
}
