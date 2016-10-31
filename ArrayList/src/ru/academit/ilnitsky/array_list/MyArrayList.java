package ru.academit.ilnitsky.array_list;

import java.util.*;

/**
 * Моя реализация класса ArrayList
 * Created by UserLabView on 26.10.16.
 */
public class MyArrayList<E> implements List<E> {
    private class MyIterator implements Iterator<E> {
        protected int index = -1;

        private final long initNumChanges;

        protected MyIterator() {
            initNumChanges = MyArrayList.this.numChanges;
        }

        protected void check() {
            if (initNumChanges != MyArrayList.this.numChanges) {
                throw new ConcurrentModificationException("MyArrayList has changed");
            }
        }

        protected void setIndex(int index) {
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

    private long numChanges;

    private int numElements;
    private E[] elements;

    public MyArrayList(int length) {
        numElements = 0;
        numChanges = 0;

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
            if (a.length > numElements) {
                a[numElements] = null;
            }

            return a;
        }
    }

    @Override
    public boolean add(E e) {
        resizeIfNeedForAdd(1);

        elements[numElements] = e;
        numElements++;
        numChanges++;

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

        for (Object o : c) {
            boolean hasInThis = false;

            if (o != null) {
                for (int i = 0; i < numElements; i++) {
                    if (o.equals(elements[i])) {
                        hasInThis = true;
                        break;
                    }
                }
            } else {
                for (int i = 0; i < numElements; i++) {
                    if (elements[i] == null) {
                        hasInThis = true;
                        break;
                    }
                }
            }

            if (!hasInThis) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        int size = c.size();

        resizeIfNeedForAdd(size);

        for (Object o : c) {
            //noinspection unchecked
            elements[numElements] = (E) o;
            numElements++;
        }

        numChanges++;

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
        resizeIfNeedForAdd(size);

        System.arraycopy(elements, index, elements, index + size, numElements - index);

        int i = index;
        for (Object o : c) {
            //noinspection unchecked
            elements[i] = (E) o;
            i++;
        }

        numElements += size;
        numChanges++;

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
            numChanges++;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int count = 0;

        @SuppressWarnings("unchecked")
        E[] newElements = (E[]) new Object[numElements];

        for (int i = 0; i < numElements; i++) {
            if (c.contains(elements[i])) {
                newElements[count] = elements[i];
                count++;
            }
        }

        if (count != numElements) {
            elements = newElements;
            numElements = count;
            numChanges++;
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
        numChanges++;
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
        numChanges++;

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
        numChanges++;
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
        numChanges++;

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
            if (this.elements[i] != null) {
                if (!this.elements[i].equals(that.elements[i])) {
                    return false;
                }
            } else {
                if (that.elements[i] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = numElements;
        for (int i = 0; i < numElements; i++) {
            int hash = (elements[i] != null) ? elements[i].hashCode() : 37;
            result = 31 * result + hash;

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
        if (numElements < elements.length) {

            @SuppressWarnings("unchecked")
            E[] newElements = (E[]) new Object[numElements];

            System.arraycopy(elements, 0, newElements, 0, numElements);

            elements = newElements;
        }
    }
}
