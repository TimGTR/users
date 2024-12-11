package org.example.users.service;

import lombok.extern.slf4j.Slf4j;
import org.example.users.dto.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class OrderClient {
    private final WebClient webClient;

    public OrderClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
    }

    public Flux<Order> getOrdersByUserId(Long userId) {
        log.info("Preparing request to orders service for userId: {}", userId);
        return webClient.get()
                .uri("/orders/user/{userId}", userId)
                .retrieve()
                .bodyToFlux(Order.class)
                .doOnSubscribe(subscription -> log.info("Request sent to orders service for userId: {}", userId))
                .doOnNext(order -> log.info("Received order: {}", order))
                .doOnError(error -> log.error("Error fetching orders: ", error));
    }
}

