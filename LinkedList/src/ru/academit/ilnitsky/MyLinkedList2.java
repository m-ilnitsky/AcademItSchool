package ru.academit.ilnitsky;

import java.util.*;

/**
 * Моя реализация класса LinkedList
 * Created by UserLabView on 17.11.16.
 */
public class MyLinkedList2<E> implements List<E>, Deque<E> {
    private class Entry<E> {
        private E element;
        private Entry<E> next;
        private Entry<E> previous;

        Entry(E element, Entry<E> previous, Entry<E> next) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }

        Entry() {
            this.element = null;
            this.next = this;
            this.previous = this;
        }

        E getElement() {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        Entry<E> getNext() {
            return next;
        }

        void setNext(Entry<E> next) {
            this.next = next;
        }

        Entry<E> getPrevious() {
            return previous;
        }

        void setPrevious(Entry<E> previous) {
            this.previous = previous;
        }
    }

    private Entry<E> first;
    private Entry<E> last;

    private int currentSize = 0;
    private int maxSize = -1;

    @Override
    public void addFirst(E e) {
        Entry<E> newElement;
        if (currentSize == 0) {
            newElement = new Entry<>(e, null, null);
            first = newElement;
            last = newElement;
            currentSize++;
        } else {
            newElement = new Entry<>(e, null, first);
            first.setPrevious(newElement);
            first = newElement;
            currentSize++;
        }
    }

    @Override
    public void addLast(E e) {
        Entry<E> newElement;
        if (currentSize == 0) {
            newElement = new Entry<>(e, null, null);
            first = newElement;
            last = newElement;
            currentSize++;
        } else {
            newElement = new Entry<>(e, last, null);
            last.setNext(newElement);
            last = newElement;
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
            Entry<E> removingElement = first;

            first.getNext().setPrevious(null);
            first = first.getNext();

            return removingElement.getElement();
        }
    }

    @Override
    public E pollLast() {
        if (currentSize == 0) {
            return null;
        } else {
            Entry<E> removingElement = last;

            last.getPrevious().setNext(null);
            last = last.getPrevious();

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
        return first.getElement();
    }

    @Override
    public E peekLast() {
        return last.getElement();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        Entry<E> element = first;

        if (o != null) {
            for (int i = 0; i < currentSize; i++) {
                if (i > 0) {
                    element = element.getNext();
                }
                if (o.equals(element.getElement())) {
                    element.getPrevious().setNext(element.getNext());
                    element.getNext().setPrevious(element.getPrevious());
                    if (element == first) {
                        first = element.getNext();
                    }
                    if (element == last) {
                        last = element.getPrevious();
                    }
                    currentSize--;
                    return true;
                }
            }
        } else {
            for (int i = 0; i < currentSize; i++) {
                if (i > 0) {
                    element = element.getNext();
                }
                if (element.getElement() == null) {
                    element.getPrevious().setNext(element.getNext());
                    element.getNext().setPrevious(element.getPrevious());
                    if (element == first) {
                        first = element.getNext();
                    }
                    if (element == last) {
                        last = element.getPrevious();
                    }
                    currentSize--;
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        Entry<E> element = last;
        int indexLast = currentSize - 1;

        if (o != null) {
            for (int i = indexLast; i >= 0; i--) {
                if (i < indexLast) {
                    element = element.getPrevious();
                }
                if (o.equals(element.getElement())) {
                    element.getPrevious().setNext(element.getNext());
                    element.getNext().setPrevious(element.getPrevious());
                    if (element == first) {
                        first = element.getNext();
                    }
                    if (element == last) {
                        last = element.getPrevious();
                    }
                    currentSize--;
                    return true;
                }
            }
        } else {
            for (int i = indexLast; i >= 0; i--) {
                if (i < indexLast) {
                    element = element.getPrevious();
                }
                if (element.getElement() == null) {
                    element.getPrevious().setNext(element.getNext());
                    element.getNext().setPrevious(element.getPrevious());
                    if (element == first) {
                        first = element.getNext();
                    }
                    if (element == last) {
                        last = element.getPrevious();
                    }
                    currentSize--;
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
        Entry<E> element = first;

        if (o != null) {
            for (int i = 0; i < currentSize; i++) {
                if (i > 0) {
                    element = element.getNext();
                }
                if (o.equals(element.getElement())) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < currentSize; i++) {
                if (i > 0) {
                    element = element.getNext();
                }
                if (element.getElement() == null) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Entry<E> element = last;
        int indexLast = currentSize - 1;

        if (o != null) {
            for (int i = indexLast; i >= 0; i--) {
                if (i < indexLast) {
                    element = element.getPrevious();
                }
                if (o.equals(element.getElement())) {
                    return i;
                }
            }
        } else {
            for (int i = indexLast; i >= 0; i--) {
                if (i < indexLast) {
                    element = element.getPrevious();
                }
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
