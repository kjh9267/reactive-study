package me.jun.reactivestudy.pubsub;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Flow.Publisher;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static me.jun.reactivestudy.pubsub.PublisherSubscriberUtils.iterablePublisher;
import static me.jun.reactivestudy.pubsub.PublisherSubscriberUtils.subscriberOf;

@Slf4j
public class SimplePubSubExample {

    public static void main(String[] args) {
        Publisher<Integer> integerPublisher = iterablePublisher(Stream.iterate(0, integer -> integer + 1)
                .limit(5)
                .collect(toList()));

        integerPublisher.subscribe(subscriberOf(7));

        List<String> iterable = Arrays.asList("Hello", "ABC");
        Publisher<String> stringPublisher = iterablePublisher(iterable);

        stringPublisher.subscribe(subscriberOf(1));
    }
}
