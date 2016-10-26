package ru.academit.ilnitsky.array_list;

import java.util.*;

/**
 * Моя реализация класса ArrayList
 * Created by UserLabView on 26.10.16.
 */
public class MyArrayList<T> implements List<T> {
    private int numElements;
    private int arrayLength;

    private Object[] elements;

    public MyArrayList(int length) {
        numElements = 0;
        arrayLength = length;

        elements = new Object[length];
    }

    public MyArrayList() {
        this(64);
    }

    private boolean resize(int newLength) {
        if (newLength < numElements || arrayLength == newLength) {
            return false;
        }

        Object[] newElements = new Object[newLength];
        System.arraycopy(elements, 0, newElements, 0, numElements);

        elements = newElements;
        arrayLength = newLength;
        return true;
    }

    @Override
    public int size() {
        return numElements;
    }

    @Override
    public boolean isEmpty() {
        return numElements == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (elements[0].getClass() != o.getClass()) {
            return false;
        } else {
            for (Object e : elements) {
                if (e.equals(o)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        Objects[] result = new Objects[numElements];
        System.arraycopy(elements, 0, result, 0, numElements);
        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(T e) {
        if (numElements >= arrayLength) {
            if (!resize(numElements + arrayLength)) {
                return false;
            }
        }

        elements[numElements] = e;
        numElements++;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public T get(int index) {
        return (T) elements[index];
    }

    @Override
    public T set(int index, T element) {
        if (index >= numElements) {
            return null;
        }

        elements[index] = element;

        return (T) elements[index];
    }

    @Override
    public void add(int index, T element) {

    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return numElements - 1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }
}
