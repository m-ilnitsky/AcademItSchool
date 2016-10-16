package ru.academit.ilnitsky.functions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Mike on 16.10.2016.
 */
public class PrimeNumbers {
    private int[] numbers;

    // Создаём массив простых чисел в диапазоне от 2 до maxValue
    public PrimeNumbers(int maxValue) {
        int arraySize;
        // Определяем размер массива простых чисел
        if (maxValue > 4000) {
            arraySize = maxValue / 7;
        } else if (maxValue > 1500) {
            arraySize = maxValue / 6;
        } else if (maxValue > 500) {
            arraySize = maxValue / 5;
        } else if (maxValue > 119) {
            arraySize = maxValue / 4;
        } else if (maxValue >= 32) {
            arraySize = maxValue / 3;
        } else if (maxValue >= 8) {
            arraySize = maxValue / 2;
        } else {
            arraySize = maxValue / 2 + 1;
        }

        int[] primeNumber = new int[arraySize];
        primeNumber[0] = 2;
        int numberOfPrime = 1;

        for (int i = 3; i <= maxValue; i++) {
            if (i % 2 == 0) {
                continue;
            }

            int half = i / 2;
            boolean isNotPrime = false;

            if (numberOfPrime > 0) { // Проверяем делением на числа из заданного диапазона
                for (int j = 0; primeNumber[j] < half; j++) {
                    if (i % primeNumber[j] == 0) {
                        //System.out.printf("B.NonPrime: %d / %d [%d]%n", i, primeNumber[j], j + 1);
                        isNotPrime = true;
                        break;
                    }
                }
            }

            if (isNotPrime) {
                continue;
            }

            primeNumber[numberOfPrime] = i;
            numberOfPrime++;
        }

        /*
        for (int k = 0; k < numberOfPrime; k++) {
            System.out.printf("Prime number[%3d] = %d%n", (k + 1), primeNumber[k]);
        }
        */

        // Урезаем массив до количества простых чисел в нём
        int[] resultArray;
        if (numberOfPrime == primeNumber.length) {
            resultArray = primeNumber;
        } else {
            resultArray = new int[numberOfPrime];

            System.arraycopy(primeNumber, 0, resultArray, 0, numberOfPrime);
        }
        numbers = resultArray;
    }

    // Бинарный поиск в отсортированном массиве
    public int findIndex(int number) {
        int left = 0;
        int right = numbers.length - 1;

        if (number < numbers[left] || number > numbers[right]) {
            return -1;
        }

        while (left <= right) {
            int middle = (left + right) / 2;

            if (number == numbers[middle]) {
                return middle;
            } else if (number > numbers[middle]) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        return -1;
    }

    public void saveToFile(String fileName, String fileCode) throws IOException {

        try (
                PrintWriter writer = new PrintWriter(fileName, fileCode)
        ) {
            for (int i = 0; i < numbers.length; i++) {
                writer.printf("%11d  %11d%n", numbers[i], i + 1);
            }
        }
    }

    public void saveToFile(String fileName) throws IOException {
        String fileCode = "ASCII";
        saveToFile(fileName, fileCode);
    }

    public void print(int numFirstPrimeNumbers) {
        int min = Math.min(numFirstPrimeNumbers, numbers.length);
        for (int i = 0; i < min; i++) {
            System.out.printf("[%d] = %d%n", i + 1, numbers[i]);
        }
    }

    public void print(int begin, int end) {
        int count = 0;
        for (int i = 0; i < numbers.length; i++) {
            int num = numbers[i];
            if (num >= begin && num <= end) {
                count++;
                System.out.printf("%d [%d] = %d%n", count, i + 1, numbers[i]);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        PrimeNumbers prime = new PrimeNumbers(1000000000);
        prime.print(999999900, 1000000000);
        prime.saveToFile("prime_numbers_1e9.txt");
    }
}
