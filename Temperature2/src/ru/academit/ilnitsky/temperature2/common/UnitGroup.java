package ru.academit.ilnitsky.temperature2.common;

/**
 * Класс для группировки единиц измерения
 * Содержит название группы и индекс единицы с которой начинается группа
 * Created by UserLabView on 27.01.17.
 */
public class UnitGroup {
    private String name;
    private int startIndex;

    public UnitGroup(String name, int startIndex) {
        this.name = name;
        this.startIndex = startIndex;
    }

    public String getName() {
        return name;
    }

    public int getStartIndex() {
        return startIndex;
    }
}
