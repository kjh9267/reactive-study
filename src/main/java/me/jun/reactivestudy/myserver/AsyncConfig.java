package me.jun.reactivestudy.myserver;

import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;

@Configuration
@SuppressWarnings("deprecation")
public class AsyncConfig {

    @Bean
    public AsyncRestTemplate asyncRestTemplate() {
        return new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));
    }
}
