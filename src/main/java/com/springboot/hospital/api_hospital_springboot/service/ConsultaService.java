package com.springboot.hospital.api_hospital_springboot.service;

import com.springboot.hospital.api_hospital_springboot.dto.ConsultaDto;
import com.springboot.hospital.api_hospital_springboot.model.Cita;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

/**
 * @author perez
 */
public interface ConsultaService {
    
    List<ConsultaDto> getAllConsultas();
    
    Optional<ConsultaDto> getConsultaById(Long id);
    
    ConsultaDto createConsulta(Long citaId,ConsultaDto consultaDto) throws ParseException;
    
    ConsultaDto updateConsulta(Long id, ConsultaDto consultaDto) throws ParseException;
    
    void deleteConsulta(Long id);
    
    List<ConsultaDto> getConsultasByInformeContaining(String searchTerm);
    
    List<ConsultaDto> getConsultasByCita(Cita cita);
    
    List<ConsultaDto> getConsultasByCitaId(Long citaId) throws ParseException;
}
