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
        int arraySize = 1 + (int) (maxValue / Math.log10(maxValue) / Math.sqrt(Math.log10(maxValue) / 2));
        // Определяем размер массива простых чисел
        /*
        if (maxValue > 2e7) {
            arraySize = maxValue / 14;
        } else if (maxValue > 2e6) {
            arraySize = maxValue / 12;
        } else if (maxValue > 2e5) {
            arraySize = maxValue / 10;
        } else if (maxValue > 2e4) {
            arraySize = maxValue / 8;
        } else if (maxValue > 4000) {
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
        */

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

    // Расширяем массив простых чисел до maxValue
    public void expandTo(int maxValue) {
        if (maxValue <= numbers[numbers.length - 1]) {
            System.out.println("Из имеющихся [" + numbers.length + "] чисел максимальное число (" + numbers[numbers.length - 1] + ") уже больше заданного (" + maxValue + ")");
            return;
        }

        int oldSize = numbers.length;
        int arraySize = 1 + (int) (maxValue / Math.log10(maxValue) / Math.sqrt(Math.log10(maxValue) / 2));

        System.out.println("Old Size=" + oldSize);
        System.out.println("New Size=" + arraySize);

        int[] primeNumber = new int[arraySize];
        System.arraycopy(numbers, 0, primeNumber, 0, oldSize);

        numbers = primeNumber;

        int numberOfPrime = oldSize;

        for (int i = numbers[oldSize - 1] + 1; i <= maxValue; i++) {
            if (i % 2 == 0) {
                continue;
            }

            int half = i / 2;
            boolean isNotPrime = false;

            if (numberOfPrime > 0) { // Проверяем делением на числа из заданного диапазона
                for (int j = 1; primeNumber[j] < half; j++) {
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

        // Урезаем массив до количества простых чисел в нём
        int[] resultArray;
        if (numberOfPrime == primeNumber.length) {
            resultArray = primeNumber;
        } else {
            resultArray = new int[numberOfPrime];

            System.arraycopy(primeNumber, 0, resultArray, 0, numberOfPrime);
        }
        numbers = resultArray;

        System.out.println("Result Size=" + numbers.length);
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

    public int findEqualOrLarger(int number) {
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

        if (numbers[left] > number) {
            return left;
        } else {
            return left + 1;
        }

    }

    public int findEqualOrSmaller(int number) {
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

        if (numbers[right] < number) {
            return right;
        } else {
            return right - 1;
        }

    }

    public void saveToFileFormat(String fileName, String fileCode) throws IOException {

        try (
                PrintWriter writer = new PrintWriter(fileName, fileCode)
        ) {
            for (int i = 0; i < numbers.length; i++) {
                writer.printf("%11d  %11d%n", numbers[i], i + 1);
            }
        }
    }

    public void saveToFileFormat(String fileName) throws IOException {
        String fileCode = "ASCII";
        saveToFileFormat(fileName, fileCode);
    }

    public void saveToFile(String fileName) throws IOException {

        try (
                PrintWriter writer = new PrintWriter(fileName, "ASCII")
        ) {
            for (int i = 0; i < numbers.length; i++) {
                writer.println(numbers[i] + " " + (i + 1));
            }
        }
    }

    public void load(String fileName) throws IOException {
        int numLines = 0;

        try (
                Scanner fileScanner = new Scanner(new FileInputStream(fileName), "ASCII")
        ) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                numLines++;
            }
        }

        try (
                Scanner fileScanner = new Scanner(new FileInputStream(fileName), "ASCII")
        ) {
            int[] primeNumber = new int[numLines];
            int count = 0;

            while (fileScanner.hasNextLine()) {
                String[] line = fileScanner.nextLine().trim().split(" ");

                if (!line[0].isEmpty()) {
                    primeNumber[count] = Integer.parseInt(line[0]);
                    count++;
                }
            }

            numbers = primeNumber;

            System.out.println("Result Size=" + count);

            if (count < numbers.length) {
                int[] resultArray = new int[count];

                System.arraycopy(numbers, 0, resultArray, 0, count);

                numbers = resultArray;
            }
        }
    }

    public void print(int numFirstPrimeNumbers) {
        int min = Math.min(numFirstPrimeNumbers, numbers.length);
        for (int i = 0; i < min; i++) {
            System.out.printf("[%d] = %d%n", i + 1, numbers[i]);
        }
    }

    public void print(int begin, int end) {
        int count = 0;
        int iBegin = findEqualOrLarger(begin);
        int iEnd = findEqualOrSmaller(end);

        for (int i = iBegin; i <= iEnd; i++) {
            count++;
            System.out.printf("%d [%d] = %d%n", count, i + 1, numbers[i]);
        }
    }

    public static void main(String[] args) throws IOException {
        PrimeNumbers prime = new PrimeNumbers(2000);
        prime.load("prime_numbers_1e3_expand_from_100.txt");
        prime.expandTo(100);
        prime.expandTo(2000);
        prime.saveToFile("prime_numbers_2e3_expand_from 1e3.txt");
    }
}
