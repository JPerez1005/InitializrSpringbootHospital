package com.springboot.hospital.api_hospital_springboot.repository;

import com.springboot.hospital.api_hospital_springboot.model.Medico;
import com.springboot.hospital.api_hospital_springboot.model.Paciente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author perez
 */
@Repository
public interface MedicoRepository extends JpaRepository<Medico,Long>{
    
    Medico findByNombre(String nombre);
    
    List<Medico> findByEspecialidad(String especialidad);
    
}
