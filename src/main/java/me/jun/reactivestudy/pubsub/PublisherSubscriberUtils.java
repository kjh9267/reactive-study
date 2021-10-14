package me.jun.reactivestudy.pubsub;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.BiFunction;
import java.util.function.Function;

@Slf4j
abstract class PublisherSubscriberUtils {

    public static <T> Publisher<T> iterablePublisher(Iterable<T> iterable) {
        Iterator<T> iterator = iterable.iterator();
        return subscriber -> {
            subscriber.onSubscribe(new Subscription() {
                @Override
                public void request(long n) {
                    log.debug("request() {}", n);
                    try {
//                        if (true) throw new RuntimeException("msg");
                        for (int i = 0; i < n; i++) {
                            if (iterator.hasNext()) {
                                subscriber.onNext(iterator.next());
                            }
                            else {
                                break;
                            }
                        }
                        subscriber.onComplete();
                    } catch (Throwable t) {
                        subscriber.onError(t);
                    }
                }

                @Override
                public void cancel() {
                    log.debug("cancel()");
                }
            });
        };
    }

    public static <T> Subscriber<T> subscriberOf(long request) {
        return new Subscriber<T>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                log.debug("onSubscribe()");
                subscription.request(request);
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

    public static <T, R> Publisher<R> mapPublisher(Publisher<T> publisher, Function<T, R> function) {
        return subscriber -> publisher.subscribe(new Subscriber<T>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscriber.onSubscribe(subscription);
            }

            @Override
            public void onNext(T item) {
                subscriber.onNext(function.apply(item));
            }

            @Override
            public void onError(Throwable throwable) {
                subscriber.onError(throwable);
            }

            @Override
            public void onComplete() {
                subscriber.onComplete();
            }
        });
    }

    public static <T, R> Publisher<R> reducePublisher(Publisher<T> publisher, R seed, BiFunction<R, T, R> biFunction) {
        return subscriber -> publisher.subscribe(new Subscriber<T>() {
            private R result;

            @Override
            public void onSubscribe(Subscription subscription) {
                result = seed;
                subscriber.onSubscribe(subscription);
            }

            @Override
            public void onNext(T item) {
                result = biFunction.apply(result, item);
            }

            @Override
            public void onError(Throwable throwable) {
                subscriber.onError(throwable);
            }

            @Override
            public void onComplete() {
                subscriber.onNext(result);
                subscriber.onComplete();
            }
        });
    }

    public static <T> Publisher<T> subscribeOn(Publisher<T> publisher) {
        return subscriber -> {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> publisher.subscribe(subscriber));
            executorService.shutdown();
        };
    }

    public static <T> Publisher<T> publishOn(Publisher<T> publisher) {
        return subscriber -> {
            ExecutorService executorService = Executors.newSingleThreadExecutor();

            publisher.subscribe(new Subscriber<T>() {
                @Override
                public void onSubscribe(Subscription subscription) {
                    subscriber.onSubscribe(subscription);
                }

                @Override
                public void onNext(T item) {
                    executorService.execute(() -> subscriber.onNext(item));
                }

                @Override
                public void onError(Throwable throwable) {
                    executorService.execute(() -> subscriber.onError(throwable));
                    executorService.shutdown();
                }

                @Override
                public void onComplete() {
                    executorService.execute(() -> subscriber.onComplete());
                    executorService.shutdown();
                }
            });
        };
    }
}
