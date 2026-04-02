/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.CuotasGym.servicios;

import com.example.CuotasGym.entidades.CuotasGeneradas;
import com.example.CuotasGym.repositorios.CuotasGeneradasRepositorio;
import java.time.Month;
import java.time.YearMonth;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author German
 */
@Service
public class CuotasGeneradasServicio {

    @Autowired
    private CuotasGeneradasRepositorio cuotasGeneradasRepositorio;

    public CuotasGeneradas ultimoPeriodo() {

        return cuotasGeneradasRepositorio.ultimoPeriodo();

    }

    public void primerPeriodo() {

        CuotasGeneradas primerPeriodo = new CuotasGeneradas();

        primerPeriodo.setPeriodo(YearMonth.of(2024, Month.DECEMBER));

        cuotasGeneradasRepositorio.save(primerPeriodo);

    }

    public void agregarPeriodo() {

        YearMonth now = YearMonth.now();

        CuotasGeneradas nuevoPeriodo = new CuotasGeneradas();

        nuevoPeriodo.setPeriodo(now);

        cuotasGeneradasRepositorio.save(nuevoPeriodo);

    }

}
