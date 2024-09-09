package ru.clevertec.queue;

import java.util.Arrays;
import java.util.Comparator;

@SuppressWarnings("unchecked")
public class CustomPriorityQueue<T> {
    public static final int DEFAULT_CAPACITY = 8;
    private int size = 0;
    private T[] innerArray;
    private Comparator<? super T> comparator;

    public CustomPriorityQueue() {
        this.innerArray = (T[]) new Object[DEFAULT_CAPACITY];
    }

    public CustomPriorityQueue(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException();
        }
        this.innerArray = (T[]) new Object[capacity];
    }

    public CustomPriorityQueue(Comparator<? super T> comparator) {
        this.innerArray = (T[]) new Object[DEFAULT_CAPACITY];
        this.comparator = comparator;
    }

    public CustomPriorityQueue(int capacity, Comparator<? super T> comparator) {
        if (capacity < 1) {
            throw new IllegalArgumentException();
        }
        this.innerArray = (T[]) new Object[capacity];
        this.comparator = comparator;
    }

    public void add(T t) {
        if (t == null) {
            throw new IllegalArgumentException();
        }
        innerArray[size] = t;
        size++;
        if (size > innerArray.length / 2) {
            innerArray = Arrays.copyOf(innerArray, innerArray.length * 2);
        }
        if (comparator == null) {
            siftUp(size - 1, (Comparator<? super T>) Comparator.naturalOrder());
        } else {
            siftUp(size - 1, comparator);
        }
    }

    public T peek() {
        return innerArray[0];
    }

    public T poll() {
        if (size == 0) {
            return null;
        }
        T swap = innerArray[0];
        innerArray[0] = innerArray[size - 1];
        innerArray[size - 1] = null;
        size--;
        if (comparator == null) {
            siftDownFirstElement((Comparator<? super T>) Comparator.naturalOrder());
        } else {
            siftDownFirstElement(comparator);
        }
        return swap;
    }

    private void siftUp(int indexOfRaisedElement, Comparator<? super T> comp) {
        int parent = (indexOfRaisedElement - 1) / 2;
        while (indexOfRaisedElement > 0 && comp.compare(innerArray[parent], innerArray[indexOfRaisedElement]) > 0) {
            swapObjectsFromArray(indexOfRaisedElement, parent);
            indexOfRaisedElement = parent;
            parent = (indexOfRaisedElement - 1) / 2;
        }
    }

    private void siftDownFirstElement(Comparator<? super T> comp) {
        int indexOfDownedElement = 0;
        while (indexOfDownedElement < size / 2) {
            int leftChild = 2 * indexOfDownedElement + 1;
            int rightChild = 2 * indexOfDownedElement + 2;
            if (rightChild < size && comp.compare(innerArray[leftChild], innerArray[rightChild]) > 0 && comp.compare(innerArray[indexOfDownedElement], innerArray[rightChild]) > 0) {
                swapObjectsFromArray(indexOfDownedElement, rightChild);
                indexOfDownedElement = rightChild;
            } else if (comp.compare(innerArray[indexOfDownedElement], innerArray[leftChild]) > 0) {
                swapObjectsFromArray(indexOfDownedElement, leftChild);
                indexOfDownedElement = leftChild;
            } else {
                break;
            }
        }
    }

    private void swapObjectsFromArray(int firstObject, int secondObject) {
        T swap = innerArray[firstObject];
        innerArray[firstObject] = innerArray[secondObject];
        innerArray[secondObject] = swap;
    }
}
