package com.dddang.shortlinkpro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ShortlinkProApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortlinkProApplication.class, args);
    }

}
