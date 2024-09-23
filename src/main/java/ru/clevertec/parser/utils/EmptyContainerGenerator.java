package ru.clevertec.parser.utils;

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
import java.util.Optional;
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
import java.util.function.Supplier;

public class EmptyContainerGenerator {
    private static final Map<Class<?>, Supplier<Collection<?>>> SUPPLIER_EMPTY_COLLECTION = new HashMap<>();
    private static final Map<Class<?>, Supplier<Map<?, ?>>> SUPPLIER_EMPTY_MAP = new HashMap<>();

    static {
        SUPPLIER_EMPTY_COLLECTION.put(Collection.class, ArrayList::new);
        SUPPLIER_EMPTY_COLLECTION.put(Set.class, HashSet::new);
        SUPPLIER_EMPTY_COLLECTION.put(SortedSet.class, TreeSet::new);
        SUPPLIER_EMPTY_COLLECTION.put(NavigableSet.class, TreeSet::new);
        SUPPLIER_EMPTY_COLLECTION.put(List.class, ArrayList::new);
        SUPPLIER_EMPTY_COLLECTION.put(Queue.class, LinkedList::new);
        SUPPLIER_EMPTY_COLLECTION.put(BlockingQueue.class, LinkedBlockingQueue::new);
        SUPPLIER_EMPTY_COLLECTION.put(TransferQueue.class, LinkedTransferQueue::new);
        SUPPLIER_EMPTY_COLLECTION.put(Deque.class, ArrayDeque::new);
        SUPPLIER_EMPTY_COLLECTION.put(BlockingDeque.class, LinkedBlockingDeque::new);

        SUPPLIER_EMPTY_MAP.put(Map.class, HashMap::new);
        SUPPLIER_EMPTY_MAP.put(SortedMap.class, TreeMap::new);
        SUPPLIER_EMPTY_MAP.put(NavigableMap.class, TreeMap::new);
        SUPPLIER_EMPTY_MAP.put(ConcurrentMap.class, ConcurrentHashMap::new);
        SUPPLIER_EMPTY_MAP.put(ConcurrentNavigableMap.class, ConcurrentSkipListMap::new);
    }

    private EmptyContainerGenerator() {
    }

    public static Object generateArray(Class<?> clazz, int size) {
        return Array.newInstance(clazz, size);
    }

    @SneakyThrows
    public static Collection<?> generateCollection(Class<?> clazz) {
        if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
            return Optional.ofNullable(SUPPLIER_EMPTY_COLLECTION.get(clazz))
                    .orElseThrow(() -> new ImplementationNotFoundException("Collection not found"))
                    .get();
        } else {
            return (Collection<?>) clazz.getDeclaredConstructor().newInstance();
        }
    }

    @SneakyThrows
    public static Map<?, ?> generateMap(Class<?> clazz) {
        if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
            return Optional.ofNullable(SUPPLIER_EMPTY_MAP.get(clazz))
                    .orElseThrow(() -> new ImplementationNotFoundException("Map not found"))
                    .get();
        } else {
            return (Map<?, ?>) clazz.getDeclaredConstructor().newInstance();
        }
    }
}