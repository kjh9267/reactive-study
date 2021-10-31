package me.jun.reactivestudy.myserver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequiredArgsConstructor
@SuppressWarnings("deprecation")
@Slf4j
public class AsyncController {

    private final AsyncRestTemplate asyncRestTemplate;

    private final String URL = "http://localhost:9000/api/data";

    @GetMapping("/async")
    public DeferredResult<String> async() {
        DeferredResult<String> deferredResult = new DeferredResult<>();
        ListenableFuture<ResponseEntity<String>> responseFuture = asyncRestTemplate.getForEntity(URL, String.class);

        responseFuture.addCallback(
                value -> {
                    log.info("callback thread check");
                    deferredResult.setResult(value.getBody());
                },
                throwable -> deferredResult.setErrorResult(throwable)
        );

        return deferredResult;
    }
}
