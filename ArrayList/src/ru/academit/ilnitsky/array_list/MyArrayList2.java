package ru.academit.ilnitsky.array_list;

import java.util.*;

/**
 * Моя реализация класса ArrayList
 * Created by Mike on 27.10.2016.
 */
public class MyArrayList2<T> implements List<T> {
    private int numElements;
    private int arrayLength;

    private T[] elements;

    public MyArrayList2(int length) {
        numElements = 0;
        arrayLength = length;

        elements = (T[]) new Object[length];
    }

    public MyArrayList2() {
        this(64);
    }

    private boolean resize(int newLength) {
        if (newLength < numElements || arrayLength == newLength) {
            return false;
        }

        T[] newElements = (T[]) new Object[newLength];
        System.arraycopy(elements, 0, newElements, 0, numElements);

        elements = newElements;
        arrayLength = newLength;
        return true;
    }

    private boolean resizeIfNeedForAdd(int numAdd) {
        if (numElements + numAdd > arrayLength) {
            return resize(arrayLength + Math.max(numElements, numAdd * 2));
        }
        return false;
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
        if (a.length < numElements) {
            T[] a2 = (T[]) new Objects[numElements];
            System.arraycopy(elements, 0, a2, 0, numElements);
            return a2;
        } else {
            System.arraycopy(elements, 0, a, 0, numElements);
            return a;
        }
    }

    @Override
    public boolean add(T e) {
        resizeIfNeedForAdd(1);

        elements[numElements] = e;
        numElements++;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index < 0) {
            return false;
        }

        remove(index);

        return true;
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
        elements = (T[]) new Object[arrayLength];
        numElements = 0;
    }

    @Override
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index < 0");
        } else if (index >= numElements) {
            throw new IndexOutOfBoundsException("index > size()");
        }

        return (T) elements[index];
    }

    @Override
    public T set(int index, T element) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index < 0");
        } else if (index >= numElements) {
            throw new IndexOutOfBoundsException("index > size()");
        }

        elements[index] = element;

        return elements[index];
    }

    @Override
    public void add(int index, T element) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index < 0");
        } else if (index >= numElements) {
            throw new IndexOutOfBoundsException("index > size()");
        }

        resizeIfNeedForAdd(1);

        // TODO Проверить на некорректное копирование с затиранием нескопированных элементов
        System.arraycopy(elements, index, elements, index + 1, numElements - index);

        elements[index] = element;
        numElements++;
    }

    @Override
    public T remove(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index < 0");
        } else if (index >= numElements) {
            throw new IndexOutOfBoundsException("index > size()");
        }

        T element = elements[index];

        // TODO Проверить на некорректное копирование с затиранием нескопированных элементов
        System.arraycopy(elements, index + 1, elements, index, numElements - index - 1);

        elements[numElements - 1] = null;
        numElements--;

        return element;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < numElements; i++) {
            if (o.equals(elements[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = numElements - 1; i >= 0; i--) {
            if (o.equals(elements[i])) {
                return i;
            }
        }
        return -1;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MyArrayList2<?> that = (MyArrayList2<?>) o;

        if (numElements != that.numElements) {
            return false;
        }

        for (int i = 0; i < numElements; i++) {
            if (elements[i].equals(that.elements[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = numElements;
        for (int i = 0; i < numElements; i++) {
            result = 31 * result + elements[i].hashCode();
        }
        return result;
    }
}

