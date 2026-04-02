/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.CuotasGym.entidades;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author German
 */

@Entity
@Table(name = "planes")
@Getter
@Setter
@NoArgsConstructor
public class Planes {
    
    @Column
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date periodo;
    
    @Column
    private Integer dos;
    
    @Column
    private Integer tres;
    
    @Column
    private Integer cinco;
    
}
