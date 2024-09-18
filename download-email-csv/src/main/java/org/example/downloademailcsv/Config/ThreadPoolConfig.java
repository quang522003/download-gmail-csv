package org.example.downloademailcsv.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig {
    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // Số lượng luồng chính
        executor.setMaxPoolSize(25);  // Số lượng luồng tối đa
        executor.setQueueCapacity(100); // Kích thước của hàng đợi
        executor.setThreadNamePrefix("TaskExecutor-"); // tien to cho ten cac luong
        executor.initialize();
        return executor;
    }
}

