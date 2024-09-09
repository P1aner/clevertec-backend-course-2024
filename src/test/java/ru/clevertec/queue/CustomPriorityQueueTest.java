package ru.clevertec.queue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

class CustomPriorityQueueTest {

    public Random random;

    @BeforeEach
    void setUp() {
        random = new Random();
    }

    @Test
    void failedCreatedQueue() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CustomPriorityQueue<>(-1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CustomPriorityQueue<>(-1, Comparator.reverseOrder()));
    }

    @Test
    void addFailObject() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            CustomPriorityQueue<Integer> integerCustomPriorityQueue = new CustomPriorityQueue<>();
            integerCustomPriorityQueue.add(null);
        });
    }

    @Test
    void addComparableObject() {
        CustomPriorityQueue<Integer> integerCustomPriorityQueue = new CustomPriorityQueue<>();
        for (int i = 0; i < 1000; i++) {
            integerCustomPriorityQueue.add(random.nextInt(-1000, 1000));
        }
        CustomPriorityQueue<Double> doubleCustomPriorityQueue = new CustomPriorityQueue<>();
        for (int i = 0; i < 1000; i++) {
            doubleCustomPriorityQueue.add(random.nextDouble(-1000.0, 1000.0));
        }
        CustomPriorityQueue<ComparableStudent> comparableStudentCustomPriorityQueue = new CustomPriorityQueue<>();
        for (int i = 0; i < 1000; i++) {
            comparableStudentCustomPriorityQueue.add(new ComparableStudent(random.nextInt(0, 6)));
        }
    }

    @Test
    void addNotComparableObject() {
        Assertions.assertThrows(Exception.class, () -> {
            CustomPriorityQueue<Student> studentCustomPriorityQueue = new CustomPriorityQueue<>();
            studentCustomPriorityQueue.add(new Student(random.nextInt(0, 6)));
            studentCustomPriorityQueue.add(new Student(random.nextInt(0, 6)));
        });
    }

    @ParameterizedTest
    @CsvSource(value = {
            "12;15;1;0;9;6;22, 0, 22",
            "-9;1;0;76;12;13;14;15;11;-17, -17, 76",
            "-9;12;25;76;11;90;-10, -10, 90"
    })
    void peek(String str, int minVal, int maxVal) {
        CustomPriorityQueue<Integer> integerMinCustomPriorityQueue = new CustomPriorityQueue<>();
        CustomPriorityQueue<Integer> integerMaxCustomPriorityQueue = new CustomPriorityQueue<>(Comparator.reverseOrder());
        Arrays.stream(str.split(";"))
                .map(Integer::parseInt)
                .forEach(t -> {
                    integerMinCustomPriorityQueue.add(t);
                    integerMaxCustomPriorityQueue.add(t);
                });
        Assertions.assertEquals(0, integerMinCustomPriorityQueue.peek().compareTo(minVal));
        Assertions.assertEquals(0, integerMaxCustomPriorityQueue.peek().compareTo(maxVal));
    }

    @Test
    void poll() {
        for (int j = 1; j < 10000; j++) {
            CustomPriorityQueue<Integer> integerMinCustomPriorityQueue = new CustomPriorityQueue<>();
            CustomPriorityQueue<Integer> integerMaxCustomPriorityQueue = new CustomPriorityQueue<>(Comparator.reverseOrder());
            PriorityQueue<Integer> integerMinPriorityQueue = new PriorityQueue<>();
            PriorityQueue<Integer> integerMaxPriorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
            for (int i = 0; i < j; i++) {
                int nextInt = random.nextInt(-1000, 1000);
                integerMinCustomPriorityQueue.add(nextInt);
                integerMaxCustomPriorityQueue.add(nextInt);
                integerMinPriorityQueue.add(nextInt);
                integerMaxPriorityQueue.add(nextInt);
            }
            for (int i = 0; i < j; i++) {
                Assertions.assertEquals(0, integerMinCustomPriorityQueue.poll().compareTo(integerMinPriorityQueue.poll()));
                Assertions.assertEquals(0, integerMaxCustomPriorityQueue.poll().compareTo(integerMaxPriorityQueue.poll()));
            }
            Assertions.assertNull(integerMinCustomPriorityQueue.poll());
            Assertions.assertNull(integerMinPriorityQueue.poll());
            Assertions.assertNull(integerMaxCustomPriorityQueue.poll());
            Assertions.assertNull(integerMaxPriorityQueue.poll());
        }
    }

    class Student {
        private int course;

        public Student(int course) {
            this.course = course;
        }
    }

    record ComparableStudent(int course) implements Comparable<ComparableStudent> {

        @Override
        public int compareTo(ComparableStudent o) {
            return o.course();
        }
    }
}