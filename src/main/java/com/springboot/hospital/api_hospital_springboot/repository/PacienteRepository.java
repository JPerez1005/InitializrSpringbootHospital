package com.springboot.hospital.api_hospital_springboot.repository;

import com.springboot.hospital.api_hospital_springboot.model.Cita;
import com.springboot.hospital.api_hospital_springboot.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author perez
 */
@Repository
public interface PacienteRepository extends JpaRepository<Paciente,Long> {
    
    Paciente findByNombre(String nombre);
    
}
