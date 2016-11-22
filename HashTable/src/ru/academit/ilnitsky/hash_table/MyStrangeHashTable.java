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
public class MyStrangeHashTable<E> implements Collection<E> {
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

            if (numElements == 0) {
                nextListIndex = -1;
                nextListElementIndex = -1;
                nextTotalIndex = 0;
                nextElement = null;
            } else {
                nextListIndex = firstList;
                nextListElementIndex = -1;
                nextTotalIndex = -1;

                listIterator = hashList[nextListIndex].iterator();
            }
        }

        private int findNextList(int listIndex) {
            for (int i = listIndex + 1; i <= lastList; i++) {
                if (listSize[i] > 0) {
                    return i;
                }
            }
            return -1;
        }

        private void setList() {
            if (nextTotalIndex < numElements && nextListElementIndex == listSize[nextListIndex]) {
                nextListIndex = findNextList(nextListIndex);
                nextListElementIndex = 0;

                listIterator = hashList[nextListIndex].iterator();
            }
        }

        private void check() {
            if (initNumChanges != numChanges) {
                throw new ConcurrentModificationException("MyStrangeHashTable has changed");
            }
        }

        @Override
        public boolean hasNext() {
            check();
            return numElements != 0 && nextTotalIndex < numElements;
        }

        @Override
        public E next() {
            if (hasNext()) {
                returnedElement = nextElement;

                returnedListIndex = nextListIndex;
                returnedListElementIndex = nextListElementIndex;
                returnedTotalIndex = nextTotalIndex;

                nextTotalIndex++;

                if (nextTotalIndex < numElements) {
                    nextListElementIndex++;
                    setList();
                    nextElement = listIterator.next();
                }

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
            setSizes(returnedListIndex);
            initNumChanges = numChanges;

            nextTotalIndex = returnedTotalIndex;
            nextListIndex = returnedListIndex;
            nextListElementIndex = returnedListElementIndex;
            setList();

            returnedListElementIndex = -1;
            returnedListIndex = -1;
            returnedTotalIndex = -1;
        }
    }

    private LinkedList<E>[] hashList;
    private int[] listSize;

    private int firstList;
    private int lastList;

    private int numElements;

    private long numChanges;

    private final static int HASH_SIZE = 65536;

    public MyStrangeHashTable() {
        this(HASH_SIZE);
    }

    public MyStrangeHashTable(int hashSize) {
        firstList = hashSize;
        lastList = -1;

        numElements = 0;
        numChanges = 0;

        listSize = new int[hashSize];
        //noinspection unchecked
        hashList = new LinkedList[hashSize];
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

    private boolean setFirstLast() {
        boolean changed = false;
        if (numElements == 0) {
            if (firstList != hashList.length) {
                firstList = hashList.length;
                changed = true;
            }
            if (lastList != -1) {
                lastList = -1;
                changed = true;
            }
        } else {
            for (int i = 0; i < hashList.length; i++) {
                if (listSize[i] > 0) {
                    firstList = i;
                    changed = true;
                    break;
                }
            }
            for (int i = hashList.length - 1; i >= 0; i++) {
                if (listSize[i] > 0) {
                    lastList = i;
                    changed = true;
                    break;
                }
            }
        }
        return changed;
    }

    private boolean setFirstLastAfterRemove(int hashIndex) {
        boolean changed = false;
        if (numElements == 0) {
            if (firstList != hashList.length) {
                firstList = hashList.length;
                changed = true;
            }
            if (lastList != -1) {
                lastList = -1;
                changed = true;
            }
        } else {
            if (firstList == hashIndex && listSize[hashIndex] == 0) {
                int start = firstList + 1;
                firstList = hashList.length;
                for (int i = start; i <= lastList; i++) {
                    if (listSize[i] > 0) {
                        firstList = i;
                        changed = true;
                        break;
                    }
                }
            }
            if (lastList == hashIndex && listSize[hashIndex] == 0) {
                int start = lastList - 1;
                lastList = -1;
                for (int i = start; i >= firstList; i--) {
                    if (listSize[i] > 0) {
                        lastList = i;
                        changed = true;
                        break;
                    }
                }
            }
        }
        return changed;
    }

    private boolean setFirstLast(int hashIndex) {
        boolean changed = false;
        if (numElements == 0) {
            firstList = hashIndex;
            lastList = hashIndex;
            changed = true;
        } else {
            if (hashIndex > lastList) {
                lastList = hashIndex;
                changed = true;
            }
            if (hashIndex < firstList) {
                firstList = hashIndex;
                changed = true;
            }
        }
        return changed;
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

    private boolean setParameters(int hashIndex) {
        setFirstLast(hashIndex);
        return setSizes(hashIndex);
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
        for (E e : this) {
            array[count] = e;
            count++;
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
        for (E e : this) {
            //noinspection unchecked
            array[count] = (T) e;
            count++;
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

        return setParameters(hashIndex);
    }

    @Override
    public boolean remove(Object o) {
        //noinspection unchecked
        int hashIndex = hashIndex((E) o);

        if (listSize[hashIndex] == 0) {
            return false;
        } else {
            hashList[hashIndex].remove(o);
            boolean changed = setSizes(hashIndex);
            setFirstLastAfterRemove(hashIndex);
            return changed;
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
        for (E o : c) {
            if (add(o)) {
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
        for (int i = firstList; i <= lastList; i++) {
            if (listSize[i] != 0) {
                hashList[i].retainAll(c);
                if (setSizes(i)) {
                    changed = true;
                }
            }
        }

        setFirstLast();

        return changed;
    }

    @Override
    public void clear() {
        for (int i = firstList; i <= lastList; i++) {
            if (hashList[i] != null) {
                hashList[i].clear();
            }
            listSize[i] = 0;
        }
        numElements = 0;
        firstList = hashList.length;
        lastList = -1;
        numChanges++;
    }
}
