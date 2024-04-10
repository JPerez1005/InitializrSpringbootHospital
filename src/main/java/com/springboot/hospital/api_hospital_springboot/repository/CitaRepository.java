package com.springboot.hospital.api_hospital_springboot.repository;

import com.springboot.hospital.api_hospital_springboot.model.Cita;
import com.springboot.hospital.api_hospital_springboot.model.StatusCita;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author perez
 */

@Repository
public interface CitaRepository extends JpaRepository<Cita,Long> {
    
    List<Cita> findByPacienteId(Long pacienteId);
    
    List<Cita> findByMedicoId(Long medicoId);
    
    List<Cita> findByStatusCita(StatusCita statusCita);
    
}
