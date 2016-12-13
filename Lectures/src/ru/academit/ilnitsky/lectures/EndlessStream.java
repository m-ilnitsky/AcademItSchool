package ru.academit.ilnitsky.lectures;

import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Задача №2 с предпоследней лекции
 * Created by Mike on 13.12.2016.
 */
class Pair {
    long previous;
    long current;

    Pair(long previous, long current) {
        this.previous = previous;
        this.current = current;
    }
}

public class EndlessStream {


    public static void main(String[] args) {

        LongStream squares = LongStream
                .iterate(1, x -> x + 1)
                .map(x -> x * x);

        // Используется формула Бине
        final double fi = (Math.sqrt(5) + 1) / 2.0;
        LongStream fibonacciBinet = LongStream
                .iterate(1, n -> n + 1)
                .map(n -> (long) ((Math.pow(fi, n) - Math.pow(-fi, -n)) / (2 * fi - 1)));

        Stream<Pair> fibonacci = Stream
                .iterate(new Pair(0, 1), p -> new Pair(p.current, p.current + p.previous));

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите число выводимых числел: ");
        int number = scanner.nextInt();

        System.out.printf("Квадраты первых %d целых чисел:%n", number);
        squares.limit(number).forEach(System.out::println);

        System.out.printf("%nПервые %d чисел Фибоначчи по формуле Бине:%n", number);
        fibonacciBinet.limit(number).forEach(System.out::println);

        System.out.printf("%nПервые %d чисел Фибоначчи:%n", number);
        fibonacci.limit(number).forEach(x -> System.out.println(x.current));
    }
}
