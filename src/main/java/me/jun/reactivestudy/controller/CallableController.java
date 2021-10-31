package me.jun.reactivestudy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
@Slf4j
public class CallableController {

    private int ONE_SECOND = 1_000;

    // AsyncContext

    @GetMapping("/callable")
    public Callable<String> async() {
        return () -> {
            log.info("callable");
            Thread.sleep(ONE_SECOND);
            return "callable end";
        };
    }
}
