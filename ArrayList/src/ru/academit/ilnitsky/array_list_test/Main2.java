package ru.academit.ilnitsky.array_list_test;

import java.util.ArrayList;

/**
 * Класс для тестирования стандартного ArrayList
 * Created by UserLabView on 28.10.16.
 */
public class Main2 {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>(10);

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
        list.add("A13");
        list.add("A14");
        list.remove(10);
        list.remove("A6");
        list.set(11, null);
        list.set(12, "B1");
        list.add(null);
        list.add("C1");

        int length = list.size();
        for (int i = 0; i < length; i++) {
            System.out.printf("[%2d]: %s %n", i, list.get(i));
        }
    }
}
