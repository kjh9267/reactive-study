package me.jun.reactivestudy.observable;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@SuppressWarnings("deprecation")
public class ObservableExample {

    private static class IntegerSource extends Observable implements Runnable {

        @Override
        public void run() {
            for (int num = 0; num < 5; num++) {
                setChanged();
                notifyObservers(num);
            }
        }
    }

    private static class Listener implements Observer {

        @Override
        public void update(Observable o, Object arg) {
            log.debug(arg.toString());
        }
    }

    public static void main(String[] args) {
        List<Observer> listeners = Arrays.asList(new Listener(),
                new Listener(),
                new Listener()
        );

        IntegerSource integerSource = new IntegerSource();

        listeners.forEach(integerSource::addObserver);

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(integerSource);

        executorService.shutdown();

        log.debug("main end");
    }
}
