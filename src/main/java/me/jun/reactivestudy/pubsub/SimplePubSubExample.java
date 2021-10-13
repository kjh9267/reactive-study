package me.jun.reactivestudy.pubsub;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.stream.Stream;

import static java.util.concurrent.Flow.Subscription;
import static java.util.stream.Collectors.toList;

@Slf4j
public class SimplePubSubExample {

    public static void main(String[] args) {
        Publisher<Integer> integerPublisher = iterablePublisher(Stream.iterate(0, integer -> integer + 1)
                .limit(5)
                .collect(toList()));

        integerPublisher.subscribe(subscriber());

        List<String> iterable = Arrays.asList("Hello", "ABC");
        Publisher<String> stringPublisher = iterablePublisher(iterable);

        stringPublisher.subscribe(subscriber());
    }

    private static <T> Publisher<T> iterablePublisher(Iterable<T> iterable) {
        return subscriber -> {
            subscriber.onSubscribe(new Subscription() {
                @Override
                public void request(long n) {
                    try {
//                        if (true) throw new RuntimeException("msg");
                        iterable.forEach(subscriber::onNext);
                        subscriber.onComplete();
                    } catch (Throwable t) {
                        subscriber.onError(t);
                    }
                }

                @Override
                public void cancel() {

                }
            });
        };
    }

    private static <T> Subscriber<T> subscriber() {
        return new Subscriber<T>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                log.debug("onSubscribe()");
                subscription.request(100_000);
            }

            @Override
            public void onNext(T item) {
                log.debug("onNext() {}", item);
            }

            @Override
            public void onError(Throwable throwable) {
                log.debug("onError() {}", throwable.toString());
            }

            @Override
            public void onComplete() {
                log.debug("onComplete()");
            }
        };
    }
}
