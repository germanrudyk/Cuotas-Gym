/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.CuotasGym.repositorios;

import com.example.CuotasGym.entidades.Planes;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author German
 */

@Repository
public interface PlanesRepositorio extends JpaRepository<Planes, String>{
    
    @Query("SELECT p FROM Planes p ORDER BY p.periodo DESC")
    public List<Planes> planes();
    
    default public Planes ultimosPlanes(){
        
        List<Planes> planes = planes();
        
        for (Planes plan : planes) {
            return plan;
        }
        
        return null;
        
    }  
    
}
