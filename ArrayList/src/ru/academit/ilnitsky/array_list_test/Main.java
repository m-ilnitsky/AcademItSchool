package ru.academit.ilnitsky.array_list_test;

import ru.academit.ilnitsky.array_list.*;

import java.util.Iterator;
import java.util.ListIterator;

/**
 * Класс для тетирования класса MyArrayList
 * Created by UserLabView on 27.10.16.
 */
public class Main {
    public static void main(String[] args) {
        MyArrayList<String> myArrayList = new MyArrayList<>(2);

        myArrayList.add("A1");
        myArrayList.add("A2");
        myArrayList.add("A3");
        myArrayList.add("A4");
        myArrayList.add("A5");
        myArrayList.add("A6");
        myArrayList.add("A7");
        myArrayList.add("A8");
        myArrayList.add("A9");
        myArrayList.add("A10");
        myArrayList.add("A11");
        myArrayList.add("A12");
        myArrayList.add("A13");
        myArrayList.add("A14");

        myArrayList.set(3, null);
        myArrayList.set(6, null);
        myArrayList.remove(10);
        myArrayList.remove("A6");
        myArrayList.add(2, "A2-2");

        int length = myArrayList.size();
        for (int i = 0; i < length; i++) {
            System.out.printf("[%2d]: %s %n", i, myArrayList.get(i));
        }

        System.out.println();

        Iterator iterator = myArrayList.iterator();
        for (int i = 0; i < length; i++) {
            System.out.printf("[%2d]: %s %n", i, iterator.next());
        }

        System.out.println();

        Iterator listIterator = myArrayList.listIterator();
        for (int i = 0; i < length; i++) {
            System.out.printf("[%2d]: %s %n", i, listIterator.next());
        }

        // Тест myArrayList.removeAll(myArrayList2)
        MyArrayList<String> myArrayList2 = new MyArrayList<>(2);

        myArrayList2.add("A8");
        myArrayList2.add("A9");
        myArrayList2.add("A10");

        myArrayList.removeAll(myArrayList2);

        /*
        // Тест исключения при изменении списка
        for (int i = 0; i < length; i++) {
            System.out.printf("[%2d]: %s %n", i, iterator.next());
        }
        */

        listIterator = myArrayList.listIterator();
        length = myArrayList.size();
        System.out.println();
        System.out.println("myArrayList.removeAll(myArrayList2)");
        for (int i = 0; i < length; i++) {
            System.out.printf("[%2d]: %s %n", i, listIterator.next());
        }

        // Тест myArrayList.retainAll(myArrayList3)
        MyArrayList<String> myArrayList3 = new MyArrayList<>(2);

        myArrayList3.add("A1");
        myArrayList3.add("A2");
        myArrayList3.add("A3");
        myArrayList3.add("A4");
        myArrayList3.add("A5");
        myArrayList3.add("A6");
        myArrayList3.add("A7");

        myArrayList.retainAll(myArrayList3);

        listIterator = myArrayList.listIterator();
        length = myArrayList.size();
        System.out.println();
        System.out.println("myArrayList.retainAll(myArrayList3)");
        for (int i = 0; i < length; i++) {
            System.out.printf("[%2d]: %s %n", i, listIterator.next());
        }

        System.out.println();

        ListIterator listIterator2 = myArrayList.listIterator();
        listIterator2.next();
        listIterator2.remove();
        while (listIterator2.hasNext()) {
            System.out.printf("[%2d]: %s %n", listIterator2.nextIndex(), listIterator2.next());
        }
    }
}
