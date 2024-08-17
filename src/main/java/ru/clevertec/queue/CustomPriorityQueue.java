package ru.clevertec.queue;

import java.util.Arrays;
import java.util.Comparator;

public class CustomPriorityQueue<T> {
    public static final int DEFAULT_CAPACITY = 8;
    private int size = 0;
    private Object[] inner;
    private Comparator<? super T> comparator;

    public CustomPriorityQueue() {
        this.inner = new Object[DEFAULT_CAPACITY];
    }

    public CustomPriorityQueue(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException();
        }
        this.inner = new Object[capacity];
    }

    public CustomPriorityQueue(Comparator<? super T> comparator) {
        this.inner = new Object[DEFAULT_CAPACITY];
        this.comparator = comparator;
    }

    public CustomPriorityQueue(int capacity, Comparator<? super T> comparator) {
        if (capacity < 1) {
            throw new IllegalArgumentException();
        }
        this.inner = new Object[capacity];
        this.comparator = comparator;
    }

    public void add(T t) {
        inner[size] = t;
        size++;
        if (size > inner.length / 2) {
            inner = Arrays.copyOf(inner, inner.length * 2);
        }
        if (comparator == null) {
            siftUpComparable(inner, size - 1);
        } else {
            siftUpWithComparator(inner, size - 1, comparator);
        }
    }

    public T peek() {
        return (T) inner[0];
    }

    public T poll() {
        if (size == 0) {
            return null;
        }
        T swap = (T) inner[0];
        inner[0] = inner[size - 1];
        inner[size - 1] = null;
        size--;
        if (comparator == null) {
            siftDownComparable(inner, 0);
        } else {
            siftDownWithComparator(inner, 0, comparator);
        }
        return swap;
    }

    private <T> void siftUpWithComparator(Object[] inner, int indexOfRaisedElement, Comparator<? super T> comp) {
        int child = indexOfRaisedElement;
        int parent = (child - 1) / 2;
        while (child > 0 && comp.compare((T) inner[parent], (T) inner[child]) > 0) {
            swap(inner, child, parent);
            child = parent;
            parent = (child - 1) / 2;
        }
    }

    private void siftUpComparable(Object[] inner, int indexOfRaisedElement) {
        int child = indexOfRaisedElement;
        int parent = (child - 1) / 2;
        while (child > 0 && ((Comparable) inner[parent]).compareTo(inner[child]) > 0) {
            swap(inner, child, parent);
            child = parent;
            parent = (child - 1) / 2;
        }
    }

    private <T> void siftDownWithComparator(Object[] inner, int indexOfDownedElement, Comparator<? super T> comp) {
        int parent = indexOfDownedElement;
        int leftChild = 2 * parent + 1;
        int rightChild = 2 * parent + 2;
        while (leftChild < size &&
                ((inner[leftChild] != null && comp.compare((T) inner[parent], (T) inner[leftChild]) > 0) ||
                        (inner[rightChild] != null && comp.compare((T) inner[parent], (T) inner[rightChild]) > 0))) {
            if (inner[rightChild] != null && comp.compare((T) inner[leftChild], (T) inner[rightChild]) > 0) {
                if (comp.compare((T) inner[parent], (T) inner[rightChild]) > 0) {
                    swap(inner, parent, rightChild);
                    parent = rightChild;
                }
            } else if (comp.compare((T) inner[parent], (T) inner[leftChild]) > 0) {
                swap(inner, parent, leftChild);
                parent = leftChild;
            }
            rightChild = 2 * parent + 2;
            leftChild = 2 * parent + 1;
        }
    }

    private void siftDownComparable(Object[] inner, int indexOfDownedElement) {
        int parent = indexOfDownedElement;
        int leftChild = 2 * parent + 1;
        int rightChild = 2 * parent + 2;
        while (leftChild < size &&
                ((inner[leftChild] != null && ((Comparable) inner[parent]).compareTo(inner[leftChild]) > 0) ||
                        (inner[rightChild] != null && ((Comparable) inner[parent]).compareTo(inner[rightChild]) > 0))) {
            if (inner[rightChild] != null && ((Comparable) inner[leftChild]).compareTo(inner[rightChild]) > 0) {
                if (((Comparable) inner[parent]).compareTo(inner[rightChild]) > 0) {
                    swap(inner, parent, rightChild);
                    parent = rightChild;
                }
            } else if (((Comparable) inner[parent]).compareTo(inner[leftChild]) > 0) {
                swap(inner, parent, leftChild);
                parent = leftChild;
            }
            rightChild = 2 * parent + 2;
            leftChild = 2 * parent + 1;
        }
    }


    private static void swap(Object[] inner, int firstObject, int secondObject) {
        Object swap = inner[firstObject];
        inner[firstObject] = inner[secondObject];
        inner[secondObject] = swap;
    }
}
