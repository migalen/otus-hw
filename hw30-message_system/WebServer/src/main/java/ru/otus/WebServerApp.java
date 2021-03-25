package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "ru.otus.config")
public class WebServerApp {

    public static void main(String[] args) {
        SpringApplication.run(WebServerApp.class, args);
    }
}
