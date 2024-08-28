package ru.clevertec.parser;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
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

public class EmptyObjectGenerator {

    public static <E> E[] generateArray(Class<E> clazz, int size) {
        return (E[]) Array.newInstance(clazz, size);
    }

    public static Collection generateCollection(Class<?> clazz) throws
            NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Collection collection = null;
        if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
            if (Collection.class.isAssignableFrom(clazz)) collection = new ArrayList();
            else if (Set.class.isAssignableFrom(clazz)) collection = new HashSet();
            else if (SortedSet.class.isAssignableFrom(clazz)) collection = new TreeSet();
            else if (NavigableSet.class.isAssignableFrom(clazz)) collection = new TreeSet();
            else if (List.class.isAssignableFrom(clazz)) collection = new ArrayList();
            else if (Queue.class.isAssignableFrom(clazz)) collection = new LinkedList();
            else if (BlockingQueue.class.isAssignableFrom(clazz)) collection = new LinkedBlockingQueue();
            else if (TransferQueue.class.isAssignableFrom(clazz)) collection = new LinkedTransferQueue();
            else if (Deque.class.isAssignableFrom(clazz)) collection = new ArrayDeque();
            else if (BlockingDeque.class.isAssignableFrom(clazz)) collection = new LinkedBlockingDeque();
        } else {
            collection = (Collection) clazz.getDeclaredConstructor().newInstance();
        }
        return collection;
    }

    public static Map generateMap(Class<?> clazz) throws
            NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Map map = null;
        if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
            if (Map.class.isAssignableFrom(clazz)) map = new HashMap();
            else if (SortedMap.class.isAssignableFrom(clazz)) map = new TreeMap();
            else if (NavigableMap.class.isAssignableFrom(clazz)) map = new TreeMap();
            else if (ConcurrentMap.class.isAssignableFrom(clazz)) map = new ConcurrentHashMap();
            else if (ConcurrentNavigableMap.class.isAssignableFrom(clazz)) map = new ConcurrentSkipListMap();
        } else {
            map = (Map) clazz.getDeclaredConstructor().newInstance();
        }
        return map;
    }
}
