package com.springboot.hospital.api_hospital_springboot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author perez
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medico {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    
    private String email;
    
    private String especialidad;
    
    @OneToMany(mappedBy = "medico", fetch=FetchType.LAZY)
    //el dueño de la relaciión es medico
    private Collection<Cita> citas;
    //se almacenan muchas citas
    
}
