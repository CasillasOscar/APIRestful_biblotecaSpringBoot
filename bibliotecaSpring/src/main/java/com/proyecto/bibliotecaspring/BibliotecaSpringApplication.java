package com.proyecto.bibliotecaspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class BibliotecaSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibliotecaSpringApplication.class, args);
    }

}
