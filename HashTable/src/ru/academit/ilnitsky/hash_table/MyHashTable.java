package ru.academit.ilnitsky.hash_table;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Iterator;

import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

/**
 * Моя реализация класса HashTable
 * Created by Mike on 19.11.2016.
 */
public class MyHashTable<E> implements Collection<E> {
    private class MyIterator implements Iterator<E> {
        private Iterator<E> listIterator;

        private E nextElement;
        private E returnedElement;

        private int nextListIndex;
        private int returnedListIndex;

        private int nextListElementIndex;
        private int returnedListElementIndex;

        private int nextTotalIndex;
        private int returnedTotalIndex;

        private long initNumChanges;

        MyIterator() {
            initNumChanges = numChanges;

            returnedElement = null;
            returnedListIndex = -1;
            returnedListElementIndex = -1;
            returnedTotalIndex = -1;

            if (isEmpty()) {
                nextListIndex = -1;
                nextListElementIndex = -1;
                nextTotalIndex = 0;
                nextElement = null;
            } else {
                nextListIndex = findNextList();
                nextListElementIndex = 0;
                nextTotalIndex = 0;
                nextElement = hashList[nextListIndex].getFirst();

                listIterator = hashList[nextListIndex].iterator();
            }
        }

        private int findNextList() {
            if (numElements == 0) {
                return -1;
            } else {
                for (int i = 0; i < hashList.length; i++) {
                    if (listSize[i] > 0) {
                        return i;
                    }
                }
            }
            return -1;
        }

        private void check() {
            if (initNumChanges != numChanges) {
                throw new ConcurrentModificationException("MyHashTable has changed");
            }
        }

        @Override
        public boolean hasNext() {
            check();
            return nextTotalIndex < numElements;
        }

        @Override
        public E next() {
            if (hasNext()) {
                returnedElement = nextElement;
                returnedListIndex = nextListIndex;
                returnedListElementIndex = nextListElementIndex;
                returnedTotalIndex = nextTotalIndex;

                if (nextListElementIndex >= listSize[nextListIndex]) {
                    for (int i = nextListIndex; i < hashList.length; i++) {
                        if (listSize[i] > 0) {
                            nextListIndex = i;
                            listIterator = hashList[nextListIndex].iterator();
                            nextListElementIndex = 0;
                            break;
                        }
                    }
                }

                nextElement = listIterator.next();
                nextListElementIndex++;
                nextTotalIndex++;

                return returnedElement;
            } else {
                throw new NoSuchElementException("NextIndex > MaxIndex");
            }
        }

        @Override
        public void remove() {
            check();
            if (returnedListIndex < 0) {
                throw new IllegalStateException();
            }
            hashList[returnedListIndex].remove(returnedListElementIndex);
            numChanges++;
            initNumChanges = numChanges;
            nextTotalIndex = returnedTotalIndex;
            returnedTotalIndex = -1;
        }
    }

    private LinkedList<E>[] hashList;
    private int[] listSize;

    private int numElements;

    private long numChanges;

    private final static int HASH_SIZE = 1024;

    public MyHashTable() {
        this(HASH_SIZE);
    }

    public MyHashTable(int hashSize) {
        numElements = 0;
        numChanges = 0;

        listSize = new int[hashSize];
        //noinspection unchecked
        hashList = (LinkedList<E>[]) new Object[hashSize];
    }

    private int hashIndex(E e) {
        int hashIndex;
        if (e == null) {
            hashIndex = 0;
        } else {
            hashIndex = Math.abs(e.hashCode() % hashList.length);
        }
        return hashIndex;
    }

    private boolean setSizes(int hashIndex) {
        int oldSize = listSize[hashIndex];
        listSize[hashIndex] = hashList[hashIndex].size();
        int newSize = listSize[hashIndex];

        if (newSize != oldSize) {
            numElements += newSize - oldSize;
            numChanges++;
            return true;
        } else {
            return false;
        }
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
        //noinspection unchecked
        int hashIndex = hashIndex((E) o);

        return listSize[hashIndex] != 0 && hashList[hashIndex].contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    @Override
    public Object[] toArray() {
        if (numElements == 0) {
            return new Object[0];
        }

        Object[] array = new Object[numElements];

        int count = 0;
        for (LinkedList<E> list : hashList) {
            for (E e : list) {
                array[count] = e;
                count++;
            }
        }

        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        T[] array;

        //noinspection ConstantConditions
        if (a == null) {
            throw new NullPointerException("Object == null");
        } else if (a.length < numElements) {
            //noinspection unchecked
            array = (T[]) new Object[numElements];
        } else {
            array = a;
        }

        int count = 0;
        for (LinkedList<E> list : hashList) {
            for (E e : list) {
                //noinspection unchecked
                array[count] = (T) e;
                count++;
            }
        }

        if (array.length > numElements) {
            array[numElements] = null;
        }

        return array;
    }

    @Override
    public boolean add(E e) {
        int hashIndex = hashIndex(e);

        if (hashList[hashIndex] == null) {
            hashList[hashIndex] = new LinkedList<>();
        }
        hashList[hashIndex].add(e);

        return setSizes(hashIndex);
    }

    @Override
    public boolean remove(Object o) {
        //noinspection unchecked
        int hashIndex = hashIndex((E) o);

        if (listSize[hashIndex] == 0) {
            return false;
        } else {
            hashList[hashIndex].remove(o);

            return setSizes(hashIndex);
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        //noinspection ConstantConditions
        if (c == null) {
            throw new NullPointerException();
        } else if (c.size() == 0) {
            return true;
        } else if (numElements == 0) {
            return false;
        }

        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        //noinspection ConstantConditions
        if (c == null) {
            throw new NullPointerException();
        }

        boolean changed = false;
        for (Object o : c) {
            //noinspection unchecked
            if (add((E) o)) {
                changed = true;
            }
        }

        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        //noinspection ConstantConditions
        if (c == null) {
            throw new NullPointerException();
        }

        boolean changed = false;
        for (Object o : c) {
            //noinspection unchecked
            if (remove(o)) {
                changed = true;
            }
        }

        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        //noinspection ConstantConditions
        if (c == null) {
            throw new NullPointerException();
        }

        boolean changed = false;
        for (int i = 0; i <= hashList.length; i++) {
            if (listSize[i] != 0) {
                hashList[i].retainAll(c);
                if (setSizes(i)) {
                    changed = true;
                }
            }
        }

        return changed;
    }

    @Override
    public void clear() {
        for (int i = 0; i <= hashList.length; i++) {
            hashList[i].clear();
            listSize[i] = 0;
        }
        numElements = 0;
        numChanges++;
    }
}