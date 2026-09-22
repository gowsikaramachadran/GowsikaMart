package com.gowsika.gowsikamart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GowsikaMartApplication {
    public static void main(String[] args) {
        SpringApplication.run(GowsikaMartApplication.class, args);
        System.out.println("=========================================");
        System.out.println(" GowsikaMart Backend is running!");
        System.out.println(" API Base URL: http://localhost:8080/api");
        System.out.println("=========================================");
    }
}
