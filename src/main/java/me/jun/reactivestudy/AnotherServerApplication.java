package me.jun.reactivestudy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Slf4j
public class AnotherServerApplication {

    @RestController
    public static class SimpleController {
        @GetMapping("/api/data")
        public String retrieveComment() throws InterruptedException {
            log.info("another server");
            Thread.sleep(1_500);
            return "data";
        }
    }

    public static void main(String[] args) {
        System.setProperty("server.port", "9000");
        System.setProperty("server.tomcat.threads.max", "300");
        SpringApplication.run(AnotherServerApplication.class, args);
    }
}
