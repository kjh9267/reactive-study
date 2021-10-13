package me.jun.reactivestudy.pubsub;

import java.util.Arrays;
import java.util.concurrent.Flow.Publisher;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static me.jun.reactivestudy.pubsub.PublisherSubscriberUtils.*;

public class ReducePubSubExample {

    public static void main(String[] args) {
        Publisher<Integer> integerPublisher = iterablePublisher(Stream.iterate(0, integer -> integer + 1)
                .limit(101)
                .collect(toList()));

        Publisher<Integer> sumPublisher = reducePublisher(integerPublisher, 0, Integer::sum);

        sumPublisher.subscribe(subscriberOf(Integer.MAX_VALUE));

        Publisher<Integer> numberPublisher = iterablePublisher(Arrays.asList(3, 6, 9));

        Publisher<Integer> maxPublisher = reducePublisher(numberPublisher, 0, (x, y) -> x.compareTo(y) > 0 ? x : y);

        maxPublisher.subscribe(subscriberOf(Integer.MAX_VALUE));
    }
}
