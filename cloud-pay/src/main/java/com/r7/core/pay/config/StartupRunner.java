package com.r7.core.pay.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

@Order(1)
@Slf4j
public class StartupRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        log.info("startup runner");
    }

}