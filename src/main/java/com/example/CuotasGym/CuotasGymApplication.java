package com.example.CuotasGym;

import com.example.CuotasGym.servicios.CuotaServicio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class CuotasGymApplication {

    @Autowired
    private CuotaServicio cuotaServicio;

    public static void main(String[] args) {

        SpringApplication.run(CuotasGymApplication.class, args);

    }

}
