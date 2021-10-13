package me.jun.reactivestudy.pubsub;

import java.util.concurrent.Flow.Publisher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static me.jun.reactivestudy.pubsub.PublisherSubscriberUtils.*;

public class MapPubSubExample {
    public static void main(String[] args) {
        Publisher<Integer> integerPublisher = iterablePublisher(Stream.iterate(0, integer -> integer + 1)
                .limit(5)
                .collect(Collectors.toList()));

        Publisher<Integer> multipleThreePublisher = mapPublisher(integerPublisher, x -> x * 3);

        Publisher<Integer> addTenPublisher = mapPublisher(multipleThreePublisher, x -> x + 10);

        addTenPublisher.subscribe(subscriberOf(3));
    }
}
