package com.springboot.hospital.api_hospital_springboot.repository;

import com.springboot.hospital.api_hospital_springboot.model.Cita;
import com.springboot.hospital.api_hospital_springboot.model.Consulta;
import com.springboot.hospital.api_hospital_springboot.model.Paciente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author perez
 */

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta,Long>{

    //buscamos una consulta por medio de la cita
    List<Consulta> findByCita(Cita cita);
    
    //buscamos un informe sin importar que contenge ahí en el parametro
    List<Consulta> findByInformeContainingIgnoreCase(String searchTerm);
    
}
