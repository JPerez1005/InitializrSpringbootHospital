package com.springboot.hospital.api_hospital_springboot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Collection;
import java.util.Date;
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
public class Paciente {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    
    @Temporal(TemporalType.DATE)
    //cuando se guarde la fecha se guarda en formato fecha
    private Date fechaNacimiento;

    private boolean situacion;
    
    @OneToMany(mappedBy = "paciente", fetch=FetchType.LAZY)
    //el dueño de la relaciión es medico
    private Collection<Cita> citas;
    //se almacenan muchas citas
}
