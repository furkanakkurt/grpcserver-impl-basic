package com.furkan.grpcserver.config;

import com.furkan.grpcserver.interceptor.AuthInterceptor;
import com.furkan.grpcserver.interceptor.GlobalExceptionInterceptor;
import com.furkan.grpcserver.interceptor.LoggingInterceptor;
import io.grpc.ServerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.grpc.server.GlobalServerInterceptor;
import org.springframework.stereotype.Component;

@Component
public class InterceptorConfig {

    @Bean
    @GlobalServerInterceptor
    @Order(0)
    ServerInterceptor loggingInterceptor() {
        return new LoggingInterceptor();
    }

    @Bean
    @GlobalServerInterceptor
    @Order(10)
    ServerInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Bean
    @GlobalServerInterceptor
    @Order()
    ServerInterceptor exceptionInterceptor() {
        return new GlobalExceptionInterceptor();
    }
}
