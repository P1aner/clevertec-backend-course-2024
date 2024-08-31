package ru.clevertec.parser.service;

import lombok.SneakyThrows;
import ru.clevertec.parser.exception.ImplementationNotFoundException;

import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public class EmptyContainerGenerator {

    private EmptyContainerGenerator() {
    }

    public static <E> E[] generateArray(Class<E> clazz, int size) {
        return (E[]) Array.newInstance(clazz, size);
    }

    @SneakyThrows
    public static Collection generateCollection(Class<?> clazz) {
        Collection collection;
        if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
            if (Collection.class.equals(clazz)) collection = new ArrayList();
            else if (Set.class.equals(clazz)) collection = new HashSet();
            else if (SortedSet.class.equals(clazz)) collection = new TreeSet();
            else if (NavigableSet.class.equals(clazz)) collection = new TreeSet();
            else if (List.class.equals(clazz)) collection = new ArrayList();
            else if (Queue.class.equals(clazz)) collection = new LinkedList();
            else if (BlockingQueue.class.equals(clazz)) collection = new LinkedBlockingQueue();
            else if (TransferQueue.class.equals(clazz)) collection = new LinkedTransferQueue();
            else if (Deque.class.equals(clazz)) collection = new ArrayDeque();
            else if (BlockingDeque.class.equals(clazz)) collection = new LinkedBlockingDeque();
            else throw new ImplementationNotFoundException("Collection not find");
        } else {
            collection = (Collection) clazz.getDeclaredConstructor().newInstance();
        }
        return collection;
    }

    @SneakyThrows
    public static Map generateMap(Class<?> clazz) {
        Map map;
        if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
            if (Map.class.equals(clazz)) map = new HashMap();
            else if (SortedMap.class.equals(clazz)) map = new TreeMap();
            else if (NavigableMap.class.equals(clazz)) map = new TreeMap();
            else if (ConcurrentMap.class.equals(clazz)) map = new ConcurrentHashMap();
            else if (ConcurrentNavigableMap.class.equals(clazz)) map = new ConcurrentSkipListMap();
            else throw new ImplementationNotFoundException("Map not find");
        } else {
            map = (Map) clazz.getDeclaredConstructor().newInstance();
        }
        return map;
    }
}