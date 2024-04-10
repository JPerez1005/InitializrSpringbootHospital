package com.springboot.hospital.api_hospital_springboot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
public class Cita {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private Date fecha;
    
    private boolean cancelado;
    
    @Enumerated(EnumType.STRING)
    private StatusCita statusCita;
    
    @ManyToOne//un paciente puede ir a muchas citas
    private Paciente paciente;
    
    @ManyToOne//un medico puede atender muchas citas
    private Medico medico;
    
    @OneToOne(mappedBy = "cita")
    private Consulta consulta;
    
}
