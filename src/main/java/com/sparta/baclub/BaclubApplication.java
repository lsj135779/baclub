package com.sparta.baclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BaclubApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaclubApplication.class, args);
    }

}
