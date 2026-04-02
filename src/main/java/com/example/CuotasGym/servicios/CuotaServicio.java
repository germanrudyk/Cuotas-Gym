/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.CuotasGym.servicios;

import com.example.CuotasGym.entidades.Cliente;
import com.example.CuotasGym.entidades.Cuota;
import com.example.CuotasGym.entidades.CuotasGeneradas;
import com.example.CuotasGym.enumeraciones.Estado;
import com.example.CuotasGym.repositorios.CuotaRepositorio;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.util.StringUtils.capitalize;

/**
 *
 * @author German
 */
@Service
public class CuotaServicio {

    @Autowired
    private CuotaRepositorio cuotaRepositorio;

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private CuotasGeneradasServicio cuotasGeneradasServicio;

    public void crearCuotaInicial(String inicio, Cliente cliente) throws ParseException {

        YearMonth now = YearMonth.now();
        
        YearMonth febrero2025 = YearMonth.of(2025, 2);

        if (!now.equals(febrero2025)) {

            Cuota cuota = new Cuota();

            establecerFechas(cuota, inicio);

            cuota.setCliente(cliente);

            cuota.setEstado(Estado.ADEUDADA);

            cuota.setNotificada(Boolean.FALSE);

            cuota.setVencida(Boolean.FALSE);

            cuotaRepositorio.save(cuota);

        }

    }

    public void establecerFechas(Cuota cuota, String inicio) {

        // Define el patrón de formato de la fecha
        String patron = "yyyy-MM-dd";

        // Crea un objeto DateTimeFormatter con el patrón de formato
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patron);

        // Convierte la cadena de texto a LocalDate utilizando el formatter
        LocalDate fechaInicio = LocalDate.parse(inicio, formatter);

        cuota.setInicio(fechaInicio);

        // Mostrar fecha
        String mes = capitalize(fechaInicio.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()));

        int anio = fechaInicio.getYear();

        cuota.setMostrarFecha(mes + " " + anio);

    }

    public void crearNuevasCuotas() {

        List<Cuota> cuotas = listarCuotas();

        for (Cuota aux : cuotas) {

            LocalDate fechaHoy = LocalDate.now();

            LocalDate fechaCuota = aux.getInicio();

            if (fechaHoy.compareTo(fechaCuota) > 0) {

                if (!aux.getProximaAgregada() && aux.getReplicar()) {

                    Cuota cuota = new Cuota();

                    establecerFechas(cuota, aux.getInicio().plusMonths(1).withDayOfMonth(1).toString());

                    cuota.setCliente(aux.getCliente());

                    cuota.setEstado(Estado.ADEUDADA);

                    cuota.setNotificada(Boolean.FALSE);

                    cuota.setVencida(Boolean.FALSE);

                    cuota.setProximaAgregada(Boolean.FALSE);

                    cuota.setReplicar(Boolean.TRUE);

                    aux.setProximaAgregada(Boolean.TRUE);

                    // Commit de la cuota verificada
                    cuotaRepositorio.save(aux);

                    // Commit de la nueva cuota
                    cuotaRepositorio.save(cuota);

                }

            }

        }

    }

    public void crearCuotas() {

        CuotasGeneradas periodo = cuotasGeneradasServicio.ultimoPeriodo();

        YearMonth now = YearMonth.now();
        
        YearMonth febrero2025 = YearMonth.of(2025, 2);
        
        if (!(now.equals(febrero2025)) && now.compareTo(periodo.getPeriodo()) > 0) {

            generarCuotas();

            cuotasGeneradasServicio.agregarPeriodo();

        }

    }

    public void generarCuotas() {

        List<Cliente> clientes = clienteServicio.listarClientesActivos();

        for (Cliente cliente : clientes) {

            Cuota cuota = new Cuota();

            establecerFechas(cuota, LocalDate.now().withDayOfMonth(1).toString());

            cuota.setCliente(cliente);

            cuota.setEstado(Estado.ADEUDADA);

            cuota.setNotificada(Boolean.FALSE);

            cuota.setVencida(Boolean.FALSE);

            cuotaRepositorio.save(cuota);

        }

    }

    public List<Cuota> listarCuotas() {

        return cuotaRepositorio.listarCuotas();

    }

    public List<Cuota> cuotasCliente(Cliente cliente) {

        return cuotaRepositorio.listarEnFormaDescendente(cliente);

    }

    public List<Cuota> listarPrimerasDiezCuotas(Cliente cliente) {

        List<Cuota> cuotas = cuotaRepositorio.listarEnFormaDescendente(cliente);

        List<Cuota> cuotasAMostrar = new ArrayList();

        int contador = 0;

        for (Cuota aux : cuotas) {
            if (contador < 10) {
                cuotasAMostrar.add(aux);
                contador++;
            } else {
                break;
            }
        }

        return cuotasAMostrar;

    }

    public List<Cuota> CuotasAdeudadas() {

        List<Cuota> cuotasAVerificar = cuotaRepositorio.listarCuotasVencidasAdeudadas();

        List<Cuota> cuotasAdeudadas = new ArrayList();

        for (Cuota aux : cuotasAVerificar) {

            if ("ADEUDADA".equals(aux.getEstado().toString())) {

                cuotasAdeudadas.add(aux);
            }
        }

        return cuotasAdeudadas;

    }

    public List<Cuota> listarCuotasAdeudadas() {

        return cuotaRepositorio.listarCuotasAdeudadas();

    }

    public Cuota getOne(String id) {

        return cuotaRepositorio.getOne(id);

    }

    public void cambiarEstado(String id) {

        Cuota cuota = getOne(id);

        if ("ADEUDADA".equals(cuota.getEstado().toString())) {
            cuota.setEstado(Estado.CANCELADA);
        } else {
            cuota.setEstado(Estado.ADEUDADA);
        }

        cuotaRepositorio.save(cuota);

    }

    public void desReplicar(Cliente cliente) {

        List<Cuota> cuotas = cuotasCliente(cliente);

        for (Cuota cuota : cuotas) {
            cuota.setReplicar(Boolean.FALSE);
            cuotaRepositorio.save(cuota);
        }

    }

}
