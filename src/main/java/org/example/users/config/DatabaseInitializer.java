package org.example.users.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DatabaseInitializer {

    private final DatabaseClient databaseClient;

    @PostConstruct
    public void initialize() {
        databaseClient.sql("""
            CREATE TABLE IF NOT EXISTS users (
                id SERIAL PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL
            );
        """).then().subscribe();
    }
}
