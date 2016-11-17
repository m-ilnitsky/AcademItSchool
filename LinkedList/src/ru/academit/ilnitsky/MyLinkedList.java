package ru.academit.ilnitsky;

import java.util.*;

/**
 * Моя реализация класса LinkedList
 * Created by Mike on 17.11.2016.
 */
public class MyLinkedList<E> implements List<E>, Deque<E> {
    private class Entry<E2> {
        private E2 element;
        private Entry<E2> next;
        private Entry<E2> previous;

        Entry(E2 element, Entry<E2> next, Entry<E2> previous) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }

        Entry() {
            this.element = null;
            this.next = this;
            this.previous = this;
        }

        E2 getElement() {
            return element;
        }

        public void setElement(E2 element) {
            this.element = element;
        }

        Entry<E2> getNext() {
            return next;
        }

        void setNext(Entry<E2> next) {
            this.next = next;
        }

        Entry<E2> getPrevious() {
            return previous;
        }

        void setPrevious(Entry<E2> previous) {
            this.previous = previous;
        }
    }

    private final Entry<E> header = new Entry<E>();
    private int currentSize = 0;
    private int maxSize = -1;

    @Override
    public void addFirst(E e) {
        Entry<E> newElement;
        if (currentSize == 0) {
            newElement = new Entry<>(e, header, header);
            header.setNext(newElement);
            header.setPrevious(newElement);
            currentSize++;
        } else {
            newElement = new Entry<>(e, header.getNext(), header);
            header.setNext(newElement);
            newElement.getNext().setPrevious(newElement);
            currentSize++;
        }
    }

    @Override
    public void addLast(E e) {
        Entry<E> newElement;
        if (currentSize == 0) {
            newElement = new Entry<>(e, header, header);
            header.setNext(newElement);
            header.setPrevious(newElement);
            currentSize++;
        } else {
            newElement = new Entry<>(e, header, header.getPrevious());
            header.setPrevious(newElement);
            newElement.getPrevious().setNext(newElement);
            currentSize++;
        }
    }

    @Override
    public boolean offerFirst(E e) {
        if (maxSize >= 0 && currentSize == maxSize) {
            return false;
        } else {
            addFirst(e);
            return true;
        }
    }

    @Override
    public boolean offerLast(E e) {
        if (maxSize >= 0 && currentSize == maxSize) {
            return false;
        } else {
            addLast(e);
            return true;
        }
    }

    @Override
    public E removeFirst() {
        if (currentSize == 0) {
            throw new NoSuchElementException("MyLinkedList is empty!");
        } else {
            return pollFirst();
        }
    }

    @Override
    public E removeLast() {
        if (currentSize == 0) {
            throw new NoSuchElementException("MyLinkedList is empty!");
        } else {
            return pollLast();
        }
    }

    @Override
    public E pollFirst() {
        if (currentSize == 0) {
            return null;
        } else {
            Entry<E> removingElement = header.getNext();

            header.setNext(removingElement.getNext());
            removingElement.getNext().setPrevious(header);

            return removingElement.getElement();
        }
    }

    @Override
    public E pollLast() {
        if (currentSize == 0) {
            return null;
        } else {
            Entry<E> removingElement = header.getPrevious();

            header.setPrevious(removingElement.getPrevious());
            removingElement.getPrevious().setNext(header);

            return removingElement.getElement();
        }
    }

    @Override
    public E getFirst() {
        if (currentSize == 0) {
            throw new NoSuchElementException("MyLinkedList is empty!");
        } else {
            return peekFirst();
        }
    }

    @Override
    public E getLast() {
        if (currentSize == 0) {
            throw new NoSuchElementException("MyLinkedList is empty!");
        } else {
            return peekLast();
        }
    }

    @Override
    public E peekFirst() {
        return header.getNext().getElement();
    }

    @Override
    public E peekLast() {
        return header.getPrevious().getElement();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        Entry<E> element = header;

        if (o != null) {
            for (int i = 0; i < currentSize; i++) {
                element = element.getNext();
                if (o.equals(element.getElement())) {
                    element.getPrevious().setNext(element.getNext());
                    element.getNext().setPrevious(element.getPrevious());
                    return true;
                }
            }
        } else {
            for (int i = 0; i < currentSize; i++) {
                element = element.getNext();
                if (element.getElement() == null) {
                    element.getPrevious().setNext(element.getNext());
                    element.getNext().setPrevious(element.getPrevious());
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        Entry<E> element = header;

        if (o != null) {
            for (int i = currentSize - 1; i >= 0; i--) {
                element = element.getPrevious();
                if (o.equals(element.getElement())) {
                    element.getPrevious().setNext(element.getNext());
                    element.getNext().setPrevious(element.getPrevious());
                    return true;
                }
            }
        } else {
            for (int i = currentSize - 1; i >= 0; i--) {
                element = element.getPrevious();
                if (element.getElement() == null) {
                    element.getPrevious().setNext(element.getNext());
                    element.getNext().setPrevious(element.getPrevious());
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean offer(E e) {
        return offerLast(e);
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public void push(E e) {
        addFirst(e);
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        addLast(e);
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

    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        Entry<E> element = header;

        if (o != null) {
            for (int i = 0; i < currentSize; i++) {
                element = element.getNext();
                if (o.equals(element.getElement())) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < currentSize; i++) {
                element = element.getNext();
                if (element.getElement() == null) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Entry<E> element = header;

        if (o != null) {
            for (int i = currentSize - 1; i >= 0; i--) {
                element = element.getPrevious();
                if (o.equals(element.getElement())) {
                    return i;
                }
            }
        } else {
            for (int i = currentSize - 1; i >= 0; i--) {
                element = element.getPrevious();
                if (element.getElement() == null) {
                    return i;
                }
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
}
