package ru.clevertec.session_storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SessionStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(SessionStorageApplication.class, args);
    }

}
