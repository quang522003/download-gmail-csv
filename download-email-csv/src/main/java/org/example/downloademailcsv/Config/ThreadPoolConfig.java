package org.example.downloademailcsv.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig {
    @Bean(name = "taskExecutorConfig")
    @Primary
    public ThreadPoolTaskExecutor  taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(25); // Số lượng luồng chính
        executor.setMaxPoolSize(50);  // Số lượng luồng tối đa
        //executor.setQueueCapacity(250); // Kích thước của hàng đợi
        executor.setThreadNamePrefix("TaskExecutor-"); // tien to cho ten cac luong
        executor.initialize();
        return executor;
    }
}

