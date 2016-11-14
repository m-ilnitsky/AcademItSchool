package ru.academit.ilnitsky.array_list_test;

import ru.academit.ilnitsky.array_list.MyArrayList;

import java.util.ListIterator;
import java.util.Scanner;

/**
 * Created by UserLabView on 14.11.16.
 */
public class Main1 {
    public static void main(String[] args) {
        MyArrayList<String> list = new MyArrayList<>(10);

        list.add("A1");
        list.add("A2");
        list.add("A3");
        list.add("A4");
        list.add(2, "A2-2");
        list.add("A5");
        list.add("A6");
        list.add("A7");
        list.add("A8");
        list.add("A9");
        list.add("A10");
        list.add("A11");
        list.add("A12");
        list.remove(7);
        list.remove("A6");
        list.set(8, null);
        list.set(9, "B1");
        list.add(null);
        list.add("C1");

        System.out.println("Initial state:");

        int length = list.size();
        for (int i = 0; i < length; i++) {
            System.out.printf("[%2d]: %s %n", i, list.get(i));
        }

        System.out.println();
        System.out.println("listIterator.next, next, remove, next, next, set, next, next, add:");

        ListIterator listIterator = list.listIterator();

        listIterator.next();
        listIterator.next();
        listIterator.remove();
        //listIterator.remove();
        listIterator.next();
        listIterator.next();
        listIterator.set("listIterator.set");
        listIterator.next();
        listIterator.next();
        listIterator.add("listIterator.add");
        while (listIterator.hasNext()) {
            System.out.printf("[%2d]: %s %n", listIterator.nextIndex(), listIterator.next());
        }

        System.out.println();
        System.out.println("End state:");

        while (listIterator.hasPrevious()) {
            listIterator.previous();
        }
        while (listIterator.hasNext()) {
            System.out.printf("[%2d]: %s %n", listIterator.nextIndex(), listIterator.next());
        }

        System.out.println();
        while (listIterator.hasPrevious()) {
            System.out.printf("[%2d]: %s %n", listIterator.previousIndex(), listIterator.previous());
        }

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}
