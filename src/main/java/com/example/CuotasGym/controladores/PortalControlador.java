/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.CuotasGym.controladores;

import com.example.CuotasGym.entidades.Cuota;
import com.example.CuotasGym.entidades.Planes;
import com.example.CuotasGym.excepciones.MiException;
import com.example.CuotasGym.servicios.ClienteServicio;
import com.example.CuotasGym.servicios.CuotaServicio;
import com.example.CuotasGym.servicios.CuotasGeneradasServicio;
import com.example.CuotasGym.servicios.PlanesServicio;
import java.text.ParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author German
 */
@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private CuotaServicio cuotaServicio;
    
    @Autowired
    private PlanesServicio planesServicio;
  
    @GetMapping("/")
    public String index(ModelMap modelo) {      
        
        cuotaServicio.crearCuotas();

        Planes planes = planesServicio.planesActuales();
        
        modelo.put("planes", planes);
        
        return "index";

    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String telefono, @RequestParam String domicilio,
            @RequestParam String inicio, ModelMap modelo) throws ParseException, MiException, Exception {

        try {
            clienteServicio.crearCliente(nombre, telefono, domicilio, inicio);
        } catch (MiException e) {

            modelo.put("error", e.getMessage());

            return "index";

        }

        return "index";

    }
    
    @PostMapping("/planes")
    public String planes(@RequestParam Integer dos, @RequestParam Integer tres, Integer cinco){
        
        planesServicio.agregar(dos, tres, cinco);
        
        return "redirect:/";
        
    }

    @GetMapping("/reporte")
    public String reporte(ModelMap modelo) {

        List<Cuota> cuotas = cuotaServicio.listarCuotasAdeudadas();

        modelo.addAttribute("cuotas", cuotas);

        return "reporte";

    }

}
