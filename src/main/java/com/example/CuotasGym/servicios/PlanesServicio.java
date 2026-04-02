/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.CuotasGym.servicios;

import com.example.CuotasGym.entidades.Planes;
import com.example.CuotasGym.repositorios.PlanesRepositorio;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author German
 */

@Service
public class PlanesServicio {
    
    @Autowired
    private PlanesRepositorio planesRepositorio;
    
    /**
     * @param dos
     * @param tres
     * @param cinco 
     */
    @Transactional
    public void agregar(Integer dos, Integer tres, Integer cinco){
        
        Planes planes = new Planes();
        
        planes.setDos(dos);
        planes.setTres(tres);
        planes.setCinco(cinco);
        planes.setPeriodo(new Date());
        
        planesRepositorio.save(planes);
        
        
    }
    
    /**
     * Obtener los planes actuales
     * @return 
     */
    public Planes planesActuales(){
        
        return planesRepositorio.ultimosPlanes();
        
    }
    
}
