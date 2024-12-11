package org.example.users.service;

import org.example.users.dto.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class OrderClient {
    private final WebClient webClient;

    public OrderClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
    }

    public Flux<Order> getOrdersByUserId(Long userId) {
        return webClient.get()
                .uri("/orders/user/{userId}", userId)
                .retrieve()
                .bodyToFlux(Order.class);
    }
}

