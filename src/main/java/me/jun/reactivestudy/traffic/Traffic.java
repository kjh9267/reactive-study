package me.jun.reactivestudy.traffic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Traffic {

//    private static final String URL = "http://localhost:8080/callable";
    private static final String URL = "http://localhost:8080/async";

    public static void main(String[] args) throws InterruptedException {
        long totalStart = System.currentTimeMillis();

        RestTemplate restTemplate = new RestTemplate();

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int count = 0; count < 100; count++) {
            executorService.execute(
                    () -> {
                        long start = System.currentTimeMillis();
                        restTemplate.getForObject(URL, String.class);
                        long end = System.currentTimeMillis();
                        log.info("seperated time: {}", (end - start) / 1_000);
                    }
            );
        }

        executorService.shutdown();
        executorService.awaitTermination(100, TimeUnit.SECONDS);

        long totalEnd = System.currentTimeMillis();
        log.info("total time: {}", (totalEnd - totalStart) / 1000);
    }
}
