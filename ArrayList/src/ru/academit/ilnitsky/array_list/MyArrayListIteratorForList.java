package ru.academit.ilnitsky.array_list;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Итератор для MyArrayList
 * Created by UserLabView on 27.10.16.
 */
public class MyArrayListIteratorForList<E> extends MyArrayListIterator<E> implements ListIterator<E> {
    private boolean nextOrPrevious;

    public MyArrayListIteratorForList(MyArrayList<E> arrayList) {
        super(arrayList);
        nextOrPrevious = false;
    }

    @Override
    public E next() {
        if (hasNext()) {
            index++;
            nextOrPrevious = true;
            return arrayList.get(index);
        } else {
            throw new NoSuchElementException("NextIndex > MaxIndex");
        }
    }

    @Override
    public boolean hasPrevious() {
        return index - 1 >= 0;
    }

    @Override
    public E previous() {
        if (hasPrevious()) {
            index--;
            nextOrPrevious = true;
            return arrayList.get(index);
        } else {
            throw new NoSuchElementException("PreviousIndex < 0");
        }
    }

    @Override
    public int nextIndex() {
        return index + 1;
    }

    @Override
    public int previousIndex() {
        return index - 1;
    }

    @Override
    public void remove() {
        if (nextOrPrevious) {
            nextOrPrevious = false;
            arrayList.remove(index);
            if (index >= arrayList.size()) {
                index = arrayList.size();
            }
        }
    }

    @Override
    public void set(E e) {
        if (nextOrPrevious) {
            arrayList.set(index, e);
        }
    }

    @Override
    public void add(E e) {
        if (nextOrPrevious) {
            nextOrPrevious = false;
            arrayList.add(index, e);
        } else if (arrayList.size() == 0) {
            arrayList.add(e);
        }
    }
}
