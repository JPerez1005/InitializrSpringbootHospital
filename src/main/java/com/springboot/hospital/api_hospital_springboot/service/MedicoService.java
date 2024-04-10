package com.springboot.hospital.api_hospital_springboot.service;

import com.springboot.hospital.api_hospital_springboot.dto.CitaDto;
import com.springboot.hospital.api_hospital_springboot.dto.MedicoDto;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author perez
 */
public interface MedicoService {
    
    List<MedicoDto> getAllMedicos();
    
    Optional<MedicoDto> getMedicoById(Long id);
    
    MedicoDto createMedico(MedicoDto medicoDto);
    
    MedicoDto updateMedico(Long id,MedicoDto medicoDto);
    
    void deleteMedico(Long id);
    
    Collection<CitaDto> getCitasByMedicoId(Long medicoId);
    
    List<MedicoDto> getMedicosByEspecialidad(String Especialidad);
    
}
