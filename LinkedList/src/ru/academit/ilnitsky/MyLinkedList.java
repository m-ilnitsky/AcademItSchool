package ru.academit.ilnitsky;

import java.util.*;

/**
 * Моя реализация класса LinkedList
 * Created by UserLabView on 17.11.16.
 */
public class MyLinkedList<E> implements List<E>, Deque<E> {
    private class Entry<T> {
        private T element;
        private Entry<T> next;
        private Entry<T> previous;

        Entry(T element, Entry<T> previous, Entry<T> next) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }

        T getElement() {
            return element;
        }

        void setElement(T element) {
            this.element = element;
        }

        Entry<T> getNext() {
            return next;
        }

        void setNext(Entry<T> next) {
            this.next = next;
        }

        Entry<T> getPrevious() {
            return previous;
        }

        void setPrevious(Entry<T> previous) {
            this.previous = previous;
        }
    }

    private Entry<E> first;
    private Entry<E> last;

    private int currentSize = 0;

    private long numChanges = 0;

    @Override
    public void addFirst(E e) {
        Entry<E> newElement;
        if (currentSize == 0) {
            newElement = new Entry<>(e, null, null);
            first = newElement;
            last = newElement;
            currentSize++;
            numChanges++;
        } else {
            newElement = new Entry<>(e, null, first);
            first.setPrevious(newElement);
            first = newElement;
            currentSize++;
            numChanges++;
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
            numChanges++;
        } else {
            newElement = new Entry<>(e, last, null);
            last.setNext(newElement);
            last = newElement;
            currentSize++;
            numChanges++;
        }
    }

    @Override
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        addLast(e);
        return true;
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

            currentSize--;
            numChanges++;

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

            currentSize--;
            numChanges++;

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

    private void removeEntry(Entry<E> element){
        if (element == first && element == last) {
            first = null;
            last = null;
        } else if (element == first) {
            first = element.getNext();
            element.getNext().setPrevious(null);
        } else if (element == last) {
            last = element.getPrevious();
            element.getPrevious().setNext(null);
        } else {
            element.getPrevious().setNext(element.getNext());
            element.getNext().setPrevious(element.getPrevious());
        }
        currentSize--;
        numChanges++;
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
                    removeEntry(element);
                    return true;
                }
            }
        } else {
            for (int i = 0; i < currentSize; i++) {
                if (i > 0) {
                    element = element.getNext();
                }
                if (element.getElement() == null) {
                    removeEntry(element);
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
                    removeEntry(element);
                    return true;
                }
            }
        } else {
            for (int i = indexLast; i >= 0; i--) {
                if (i < indexLast) {
                    element = element.getPrevious();
                }
                if (element.getElement() == null) {
                    removeEntry(element);
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
        Entry<E> element = first;

        if (o != null) {
            for (int i = 0; i < currentSize; i++) {
                if (i > 0) {
                    element = element.getNext();
                }
                if (o.equals(element.getElement())) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < currentSize; i++) {
                if (i > 0) {
                    element = element.getNext();
                }
                if (element.getElement() == null) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        Entry<E> element = first;

        Object[] array = new Object[currentSize];

        for (int i = 0; i < currentSize; i++) {
            if (i > 0) {
                element = element.getNext();
            }
            array[i] = element.getElement();
        }

        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a == null) {
            throw new NullPointerException("Object == null");
        } else if (a.length < currentSize) {

            @SuppressWarnings("unchecked")
            T[] a2 = (T[]) new Object[currentSize];

            Entry<E> element = first;
            for (int i = 0; i < currentSize; i++) {
                if (i > 0) {
                    element = element.getNext();
                }
                //noinspection unchecked
                a2[i] = (T) element.getElement();
            }

            return a2;
        } else {
            Entry<E> element = first;
            for (int i = 0; i < currentSize; i++) {
                if (i > 0) {
                    element = element.getNext();
                }
                //noinspection unchecked
                a[i] = (T) element.getElement();
            }

            if (a.length > currentSize) {
                a[currentSize] = null;
            }

            return a;
        }
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return removeFirstOccurrence(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        int size = c.size();
        if (size == 0 || currentSize == 0) {
            return false;
        }

        for (Object o : c) {
            boolean hasInThis = false;

            Entry<E> element = first;

            if (o != null) {
                for (int i = 0; i < currentSize; i++) {
                    if (i > 0) {
                        element = element.getNext();
                    }
                    if (o.equals(element.getElement())) {
                        hasInThis = true;
                        break;
                    }
                }
            } else {
                for (int i = 0; i < currentSize; i++) {
                    if (i > 0) {
                        element = element.getNext();
                    }
                    if (element.getElement() == null) {
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
        if (c == null) {
            throw new NullPointerException();
        }

        for (Object o : c) {
            //noinspection unchecked
            addLast((E) o);
            currentSize++;
        }
        numChanges++;

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index < 0");
        } else if (index > currentSize) {
            throw new IndexOutOfBoundsException("index > size");
        } else if (c == null) {
            throw new NullPointerException();
        }

        Entry<E> newElement;

        if (index == currentSize) {
            for (Object o : c) {
                //noinspection unchecked
                addLast((E) o);
                currentSize++;
            }
        } else {
            Entry<E> elementAtIndex = entry(index);

            for (Object o : c) {
                //noinspection unchecked
                newElement = new Entry<>((E) o, elementAtIndex.getPrevious(), elementAtIndex);
                elementAtIndex.getPrevious().setNext(newElement);
                elementAtIndex.setPrevious(newElement);
                if (elementAtIndex == first) {
                    first = newElement;
                }
                currentSize++;
            }
        }

        numChanges++;

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException();
        }

        boolean isChange = false;

        int size = currentSize;
        Entry<E> element = first;
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                element = element.getNext();
            }
            if (c.contains(element.getElement())) {
                isChange = true;
                removeEntry(element);
            }
        }

        return isChange;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException();
        }

        boolean isChange = false;

        int size = currentSize;
        Entry<E> element = first;
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                element = element.getNext();
            }
            if (!c.contains(element.getElement())) {
                isChange = true;
                removeEntry(element);
            }
        }

        return isChange;
    }

    @Override
    public void clear() {
        /*
        // Возможная логика для ускорения работы сборщика мусора
        // Замедлит формальное удаление списка
        Entry<T> element = first;
        element.setNext(null);
        for (int i = 1; i <= currentSize; i++) {
            element = element.getNext();
            element.setNext(null);
            element.setPrevious(null);
        }
        */

        first = null;
        last = null;

        currentSize = 0;
        numChanges++;
    }

    private Entry<E> entry(int index) {
        Entry<E> element = first;
        for (int i = 1; i <= index; i++) {
            element = element.getNext();
        }
        return element;
    }

    @Override
    public E get(int index) {
        if (index >= currentSize) {
            throw new IndexOutOfBoundsException("index >= size");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("index < 0");
        }

        Entry<E> element = entry(index);

        return element.getElement();
    }

    @Override
    public E set(int index, E element) {
        if (index >= currentSize) {
            throw new IndexOutOfBoundsException("index >= size");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("index < 0");
        }

        Entry<E> elementAtIndex = entry(index);

        E oldElement = elementAtIndex.getElement();

        elementAtIndex.setElement(element);
        numChanges++;

        return oldElement;
    }

    @Override
    public void add(int index, E element) {
        if (index > currentSize) {
            throw new IndexOutOfBoundsException("index > size");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("index < 0");
        }

        Entry<E> newElement;
        if (index == currentSize) {
            addLast(element);
        } else {
            Entry<E> elementAtIndex = entry(index);

            newElement = new Entry<>(element, elementAtIndex.getPrevious(), elementAtIndex);
            elementAtIndex.getPrevious().setNext(newElement);
            elementAtIndex.setPrevious(newElement);
            if (elementAtIndex == first) {
                first = newElement;
            }

            currentSize++;
            numChanges++;
        }
    }

    @Override
    public E remove(int index) {
        if (index >= currentSize) {
            throw new IndexOutOfBoundsException("index >= size");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("index < 0");
        }

        Entry<E> element = entry(index);
        E removed = element.getElement();

        removeEntry(element);

        return removed;
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
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("fromIndex < 0");
        }
        if (toIndex >= currentSize) {
            throw new IndexOutOfBoundsException("toIndex >= size");
        }
        if (fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("fromIndex > toIndex");
        }

        MyLinkedList<E> list = new MyLinkedList<>();

        Entry<E> element = first;
        for (int i = 0; i < currentSize; i++) {
            if (i > 0) {
                element = element.getNext();
            }
            if (i >= fromIndex && i <= toIndex) {
                list.addLast(element.getElement());
            }
        }

        return list;
    }
}
