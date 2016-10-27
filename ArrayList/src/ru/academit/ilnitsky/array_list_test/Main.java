package ru.academit.ilnitsky.array_list_test;

import ru.academit.ilnitsky.array_list.*;

import java.util.Iterator;

/**
 * Класс для тетирования класса MyArrayList
 * Created by UserLabView on 27.10.16.
 */
public class Main {
    public static void main(String[] args) {
        MyArrayList<String> myArrayList = new MyArrayList<String>(10);

        myArrayList.add("A1");
        myArrayList.add("A2");
        myArrayList.add("A3");
        myArrayList.add("A4");
        myArrayList.add(2, "A2-2");
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
        myArrayList.remove(10);
        myArrayList.remove("A6");

        int length = myArrayList.size();
        for (int i = 0; i < length; i++) {
            System.out.printf("[%2d]: %s %n", i, myArrayList.get(i));
        }

        System.out.println();

        Iterator iterator = myArrayList.iterator();
        for (int i = 0; i < length; i++) {
            System.out.printf("[%2d]: %s %n", i, iterator.next());
        }
    }
}
