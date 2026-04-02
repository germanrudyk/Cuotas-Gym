/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.CuotasGym.repositorios;

import com.example.CuotasGym.entidades.CuotasGeneradas;
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
public interface CuotasGeneradasRepositorio extends JpaRepository<CuotasGeneradas, String>{
    
    @Query("SELECT c FROM CuotasGeneradas c ORDER BY c.periodo DESC")
    public List<CuotasGeneradas> periodos();
    
    default CuotasGeneradas ultimoPeriodo(){
        
        List<CuotasGeneradas> periodos = periodos();
        
        for (CuotasGeneradas periodo : periodos) {
            
            return periodo;
            
        }
        
        return null;
        
    }    
}
