package com.springboot.hospital.api_hospital_springboot.service;

import com.springboot.hospital.api_hospital_springboot.dto.CitaDto;
import com.springboot.hospital.api_hospital_springboot.dto.PacienteDto;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author perez
 */
public interface PacienteService {

    List<PacienteDto> getAllPacientes();
    
    Optional<PacienteDto> getPacienteById(Long id);
    
    PacienteDto createPaciente(PacienteDto pacienteDto);
    
    PacienteDto updatePaciente(Long id, PacienteDto pacienteDto);
    
    void deletePaciente(Long id);
    
    Collection<CitaDto> getCitasByPacienteId(Long pacienteId);
    
}
