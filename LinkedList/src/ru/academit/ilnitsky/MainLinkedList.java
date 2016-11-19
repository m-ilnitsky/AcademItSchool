package ru.academit.ilnitsky;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

/**
 * Класс для тестирования стандартного LinkedList
 * Created by Mike on 17.11.2016.
 */
public class MainLinkedList {
    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();

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
        ListIterator listIterator2 = list.listIterator();

        listIterator.next();
        listIterator.next();
        listIterator.remove();
        //listIterator.remove();
        //listIterator2.next();
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

        System.out.println();
        System.out.println("DescendingIterator:");
        Iterator descendingIterator = list.descendingIterator();
        int count2 = 0;
        while (descendingIterator.hasNext()) {
            System.out.printf("[%2d]: %s %n", count2, descendingIterator.next());
            count2++;
        }

        System.out.println();
        System.out.println("Iterator:");
        Iterator iterator = list.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            System.out.printf("[%2d]: %s %n", count, iterator.next());
            count++;
        }

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}
