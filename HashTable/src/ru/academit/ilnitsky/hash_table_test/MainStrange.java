package ru.academit.ilnitsky.hash_table_test;

import ru.academit.ilnitsky.hash_table.MyStrangeHashTable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Программа для тестирования класса MyStrangeHashTable
 * Created by Mike on 21.11.2016.
 */
public class MainStrange {
    public static void main(String[] args) {
        MyStrangeHashTable<String> list = new MyStrangeHashTable<>(256);

        list.add("A0");
        list.add("A1");
        list.add("A2");
        list.add("A3");
        list.add("A4");
        list.add("A5");
        list.add("A6");
        list.add("A7");
        list.add(null);
        list.add("B1");
        list.add("B2");
        list.add(null);
        list.add("C1");

        System.out.println("Initial state:");
        Iterator iterator0 = list.iterator();
        int count = 0;
        while (iterator0.hasNext()) {
            System.out.printf("[%2d]: %s %n", count, iterator0.next());
            count++;
        }

        Iterator iterator1 = list.iterator();
        Iterator iterator2 = list.iterator();
        System.out.println();
        System.out.println("Iterator1.next: " + iterator1.next());
        System.out.println("Iterator1.next: " + iterator1.next());
        System.out.println("Iterator1.remove");
        iterator1.remove();
        //System.out.println("Iterator1.remove");
        //iterator1.remove();
        //System.out.println("Iterator2.next: " + iterator2.next());
        System.out.println("Iterator1.next: " + iterator1.next());
        System.out.println("Iterator1.remove");
        iterator1.remove();
        System.out.println("Iterator1.next: " + iterator1.next());
        System.out.println("Iterator1.next: " + iterator1.next());
        System.out.println("Iterator1.remove");
        iterator1.remove();
        System.out.println("Iterator1.next: " + iterator1.next());

        System.out.println();
        System.out.println("End state:");
        Iterator iterator3 = list.iterator();
        count = 0;
        while (iterator3.hasNext()) {
            System.out.printf("[%2d]: %s %n", count, iterator3.next());
            count++;
        }

        System.out.println();
        System.out.println("list.retainAll(A0, A1, A2, A3, A4, A5, A6, A7, A8)");
        System.out.println("list.removeAll(A2, A4, A6, A8)");

        list.retainAll(Arrays.asList("A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8"));
        list.removeAll(Arrays.asList("A2", "A4", "A6", "A8"));

        System.out.println();
        System.out.println("End2 state:");
        Iterator iterator4 = list.iterator();
        count = 0;
        while (iterator4.hasNext()) {
            System.out.printf("[%2d]: %s %n", count, iterator4.next());
            count++;
        }

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}
