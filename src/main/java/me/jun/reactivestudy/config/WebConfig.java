package me.jun.reactivestudy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setCorePoolSize(100);
        threadPoolTaskExecutor.setQueueCapacity(1_000);
        threadPoolTaskExecutor.setMaxPoolSize(200);
        threadPoolTaskExecutor.initialize();

        configurer.setTaskExecutor(threadPoolTaskExecutor);
    }
}
