package me.jun.reactivestudy.iterable;

import java.util.Iterator;

public class IterableExample {

    public static void main(String[] args) {
        Iterable<Integer> iterable = () -> new Iterator<Integer>() {
            private final Integer MAX_VALUE = 5;
            private Integer value = 0;

            @Override
            public boolean hasNext() {
                return value.compareTo(MAX_VALUE) < 0;
            }

            @Override
            public Integer next() {
                return value++;
            }
        };

        for (Iterator<Integer> iter = iterable.iterator(); iter.hasNext();) {
            System.out.println(iter.next());
        }
        
        for (Integer integer: iterable) {
            System.out.println(integer);
        }
    }
}
