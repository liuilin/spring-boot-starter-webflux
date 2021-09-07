package com.liumulin.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories //开启 MongoDB 的 Spring--Data-JPA
public class SpringBootStarterWebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStarterWebfluxApplication.class, args);
    }

}
