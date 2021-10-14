package me.jun.reactivestudy.pubsub;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow.Publisher;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static me.jun.reactivestudy.pubsub.PublisherSubscriberUtils.*;

@Slf4j
public class SubscribeOnExample {

    public static void main(String[] args) {
        Publisher<Integer> integerPublisher = iterablePublisher(Stream.iterate(0, integer -> integer + 1)
                .limit(5)
                .collect(toList()));

        Publisher<Integer> subscribeOn = subscribeOn(integerPublisher);

        subscribeOn.subscribe(subscriberOf(3));

        log.debug("main end");
    }
}
