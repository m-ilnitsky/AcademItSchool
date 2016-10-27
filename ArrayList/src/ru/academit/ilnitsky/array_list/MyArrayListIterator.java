package ru.academit.ilnitsky.array_list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор для MyArrayList
 * Created by UserLabView on 27.10.16.
 */
public class MyArrayListIterator<E> implements Iterator<E> {
    protected int index = -1;
    protected MyArrayList<E> arrayList;

    public MyArrayListIterator(MyArrayList<E> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public boolean hasNext() {
        return index + 1 < arrayList.size();
    }

    @Override
    public E next() {
        if (hasNext()) {
            index++;
            return arrayList.get(index);
        } else {
            throw new NoSuchElementException("NextIndex > MaxIndex");
        }
    }

    @Override
    public void remove() {
        arrayList.remove(index);
        if (index >= arrayList.size()) {
            index = arrayList.size();
        }
    }
}
