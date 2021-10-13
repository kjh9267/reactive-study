package me.jun.reactivestudy.pubsub;

import java.util.concurrent.Flow.Publisher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static me.jun.reactivestudy.pubsub.PublisherSubscriberUtils.*;

public class MapReducePubSubExample {

    public static void main(String[] args) {
        Publisher<String> alphabetPublisher = iterablePublisher(Stream.iterate(65, integer -> integer + 1)
                .map(Character::toString)
                .limit(26)
                .collect(Collectors.toList()));

        Publisher<String> toUpperCasePublisher = mapPublisher(alphabetPublisher, String::toUpperCase);

        Publisher<Character> toCharacterPublisher = mapPublisher(toUpperCasePublisher, x -> x.charAt(0));

        Publisher<Character> maxAlphabetPublisher = reducePublisher(toCharacterPublisher, 'A', (x, y) -> x.compareTo(y) > 0 ? x : y);

        maxAlphabetPublisher.subscribe(subscriberOf(Integer.MAX_VALUE));
    }
}
