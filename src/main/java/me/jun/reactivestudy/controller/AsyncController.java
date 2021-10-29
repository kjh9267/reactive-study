package me.jun.reactivestudy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
@Slf4j
public class AsyncController {

    private int ONE_SECOND = 1_000;

    // AsyncContext

    @GetMapping("/async")
    public Callable<String> async() {
        return () -> {
            log.info("async");
            Thread.sleep(ONE_SECOND);
            return "async end";
        };
    }
}
