package ru.academit.ilnitsky.array_list;

import java.util.*;

/**
 * Моя реализация класса ArrayList
 * Created by UserLabView on 26.10.16.
 */
public class MyArrayList<E> implements List<E> {
    private class MyIterator implements Iterator<E> {
        int index = -1;

        private final int initNumElements;
        private boolean isChanged;

        MyIterator() {
            initNumElements = MyArrayList.this.numElements;
            isChanged = false;
        }

        void check() {
            if (isChanged) {
                throw new ConcurrentModificationException("MyArrayList has changed");
            }
            if (initNumElements != MyArrayList.this.numElements) {
                isChanged = true;
                throw new ConcurrentModificationException("MyArrayList has changed");
            }
        }

        void setIndex(int index) {
            if (index > MyArrayList.this.numElements) {
                this.index = MyArrayList.this.numElements;
            } else if (index < 0) {
                this.index = -1;
            } else {
                this.index = index;
            }
        }

        @Override
        public boolean hasNext() {
            check();
            return index + 1 < MyArrayList.this.size();
        }

        @Override
        public E next() {
            if (hasNext()) {
                index++;
                return MyArrayList.this.get(index);
            } else {
                throw new NoSuchElementException("NextIndex > MaxIndex");
            }
        }

        @Override
        public void remove() {
            check();
            MyArrayList.this.remove(index);
            if (index >= MyArrayList.this.size()) {
                index = MyArrayList.this.size();
            }
        }
    }

    private class MyListIterator extends MyIterator implements ListIterator<E> {

        @Override
        public boolean hasPrevious() {
            check();
            return index - 1 >= 0;
        }

        @Override
        public E previous() {
            if (hasPrevious()) {
                index--;
                return MyArrayList.this.get(index);
            } else {
                throw new NoSuchElementException("PreviousIndex < 0");
            }
        }

        @Override
        public int nextIndex() {
            check();
            return index + 1;
        }

        @Override
        public int previousIndex() {
            check();
            return index - 1;
        }

        @Override
        public void remove() {
            check();
            MyArrayList.this.remove(index);
            if (index >= MyArrayList.this.size()) {
                index = MyArrayList.this.size();
            }
        }

        @Override
        public void set(E e) {
            check();
            MyArrayList.this.set(index, e);
        }

        @Override
        public void add(E e) {
            check();
            MyArrayList.this.add(index, e);
        }
    }

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
        return (numElements + numAdd > elements.length) && resize(elements.length + Math.max(numElements, numAdd * 2));
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
        if (o != null) {
            for (int i = 0; i < numElements; i++) {
                if (o.equals(elements[i])) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < numElements; i++) {
                if (elements[i] == null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[numElements];
        System.arraycopy(elements, 0, result, 0, numElements);
        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a == null) {
            throw new NullPointerException("Object == null");
        } else if (a.length < numElements) {

            @SuppressWarnings("unchecked")
            T[] a2 = (T[]) new Object[numElements];

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
        int size = c.size();
        if (size == 0 || numElements == 0) {
            return false;
        }

        Iterator iterator = c.iterator();

        for (int i = 0; i < size; i++) {
            Object o = iterator.next();
            for (int j = 0; j < numElements; j++) {
                if (!o.equals(elements[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        int size = c.size();
        Iterator iterator = c.iterator();

        resizeIfNeedForAdd(size);

        for (int i = 0; i < size; i++) {
            //noinspection unchecked
            elements[numElements] = (E) iterator.next();
            numElements++;
        }

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index < 0");
        } else if (index >= numElements) {
            throw new IndexOutOfBoundsException("index > size()");
        }

        int size = c.size();
        Iterator iterator = c.iterator();

        resizeIfNeedForAdd(size);
        int end = index + size;

        System.arraycopy(elements, index, elements, end, numElements - index);

        for (int i = index; i < end; i++) {
            //noinspection unchecked
            elements[i] = (E) iterator.next();
            numElements++;
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isChange = false;
        int count = 0;

        @SuppressWarnings("unchecked")
        E[] newElements = (E[]) new Object[numElements];

        for (int i = 0; i < numElements; i++) {
            if (!c.contains(elements[i])) {
                newElements[count] = elements[i];
                count++;
            } else {
                isChange = true;
            }
        }

        if (isChange) {
            elements = newElements;
            numElements = count;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isChange = false;
        int count = 0;

        @SuppressWarnings("unchecked")
        E[] newElements = (E[]) new Object[numElements];

        for (int i = 0; i < numElements; i++) {
            if (c.contains(elements[i])) {
                newElements[count] = elements[i];
                count++;
                isChange = true;
            }
        }

        if (isChange) {
            elements = newElements;
            numElements = count;
            return true;
        } else {
            return false;
        }
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

        return elements[index];
    }

    @Override
    public E set(int index, E element) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index < 0");
        } else if (index >= numElements) {
            throw new IndexOutOfBoundsException("index > size()");
        }

        E oldElement = elements[index];

        elements[index] = element;

        return oldElement;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index < 0");
        } else if (index >= numElements) {
            throw new IndexOutOfBoundsException("index > size()");
        }

        resizeIfNeedForAdd(1);

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

        System.arraycopy(elements, index + 1, elements, index, numElements - index - 1);

        elements[numElements - 1] = null;
        numElements--;

        return element;
    }

    @Override
    public int indexOf(Object o) {
        if (o != null) {
            for (int i = 0; i < numElements; i++) {
                if (o.equals(elements[i])) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < numElements; i++) {
                if (elements[i] == null) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o != null) {
            for (int i = numElements - 1; i >= 0; i--) {
                if (o.equals(elements[i])) {
                    return i;
                }
            }
        } else {
            for (int i = numElements - 1; i >= 0; i--) {
                if (elements[i] == null) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new MyListIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        MyListIterator iterator = new MyListIterator();

        iterator.setIndex(index);

        return iterator;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("fromIndex < 0");
        }
        if (toIndex >= numElements) {
            throw new IndexOutOfBoundsException("toIndex > size()");
        }
        if (fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("fromIndex > toIndex");
        }

        MyArrayList<E> list = new MyArrayList<>(toIndex - fromIndex);

        int count = 0;
        for (int i = fromIndex; i < toIndex; i++) {
            list.elements[count] = elements[i];
            count++;
        }

        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        @SuppressWarnings("unchecked")
        MyArrayList<E> that = (MyArrayList<E>) o;

        if (this.numElements != that.numElements) {
            return false;
        }

        for (int i = 0; i < this.numElements; i++) {
            if (this.elements[i].equals(that.elements[i])) {
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

    public void ensureCapacity(int minCapacity) {
        if (elements.length < minCapacity) {

            @SuppressWarnings("unchecked")
            E[] newElements = (E[]) new Object[minCapacity];

            System.arraycopy(elements, 0, newElements, 0, numElements);

            elements = newElements;
        }
    }

    public void trimToSize() {

        @SuppressWarnings("unchecked")
        E[] newElements = (E[]) new Object[numElements];

        System.arraycopy(elements, 0, newElements, 0, numElements);

        elements = newElements;
    }
}
