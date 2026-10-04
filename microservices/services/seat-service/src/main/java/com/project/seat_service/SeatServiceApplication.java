package com.project.seat_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
@EnableKafka
public class SeatServiceApplication {

    public static void main(String[] args) {

        System.out.println("########## I AM RUNNING THE NEW SEAT SERVICE CODE ##########");

        SpringApplication.run(SeatServiceApplication.class, args);
    }
}