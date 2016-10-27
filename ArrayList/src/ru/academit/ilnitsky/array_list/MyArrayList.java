package ru.academit.ilnitsky.array_list;

import java.util.*;

/**
 * Моя реализация класса ArrayList
 * Created by UserLabView on 26.10.16.
 */
public class MyArrayList<E> implements List<E> {
    private final static int INITIAL_LENGTH = 64;

    private int numElements;
    private E[] elements;

    public MyArrayList(int length) {
        numElements = 0;

        //noinspection unchecked
        elements = (E[]) new Object[length];
    }

    public MyArrayList() {
        this(INITIAL_LENGTH);
    }

    private boolean resize(int newLength) {
        if (newLength < numElements || elements.length == newLength) {
            return false;
        }

        @SuppressWarnings("unchecked")
        E[] newElements = (E[]) new Object[newLength];
        System.arraycopy(elements, 0, newElements, 0, numElements);

        elements = newElements;
        return true;
    }

    private boolean resizeIfNeedForAdd(int numAdd) {
        if (numElements + numAdd > elements.length) {
            return resize(elements.length + Math.max(numElements, numAdd * 2));
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
        if (o == null) {
            throw new NullPointerException("Object == null");
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
    public Iterator<E> iterator() {
        return new MyArrayListIterator<E>(this);
    }

    @Override
    public Object[] toArray() {
        Objects[] result = new Objects[numElements];
        System.arraycopy(elements, 0, result, 0, numElements);
        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a == null) {
            throw new NullPointerException("Object == null");
        } else if (a.length < numElements) {

            @SuppressWarnings("unchecked")
            T[] a2 = (T[]) new Objects[numElements];

            System.arraycopy(elements, 0, a2, 0, numElements);
            return a2;
        } else {
            System.arraycopy(elements, 0, a, 0, numElements);
            return a;
        }
    }

    @Override
    public boolean add(E e) {
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
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
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
        //noinspection unchecked
        elements = (E[]) new Object[elements.length];
        numElements = 0;
    }

    @Override
    public E get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index < 0");
        } else if (index >= numElements) {
            throw new IndexOutOfBoundsException("index > size()");
        }

        return (E) elements[index];
    }

    @Override
    public E set(int index, E element) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index < 0");
        } else if (index >= numElements) {
            throw new IndexOutOfBoundsException("index > size()");
        }

        elements[index] = element;

        return elements[index];
    }

    @Override
    public void add(int index, E element) {
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
    public E remove(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index < 0");
        } else if (index >= numElements) {
            throw new IndexOutOfBoundsException("index > size()");
        }

        E element = elements[index];

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
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
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

        MyArrayList<?> that = (MyArrayList<?>) o;

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
