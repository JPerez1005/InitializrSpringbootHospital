package com.springboot.hospital.api_hospital_springboot.service;

import com.springboot.hospital.api_hospital_springboot.dto.CitaDto;
import com.springboot.hospital.api_hospital_springboot.model.Cita;
import com.springboot.hospital.api_hospital_springboot.model.StatusCita;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

/**
 * @author perez
 */
public interface CitaService {

    List<CitaDto> getAllCitas();
    
    Optional<CitaDto> getCitaById(Long id);
    
    Cita createCita(CitaDto citaDto,Long idPaciente,Long idMedico) throws ParseException;
    
    CitaDto updateCita(Long id, CitaDto citaDto) throws ParseException;
    
    void deleteCita(Long id);
    
    List<CitaDto> getCitasByPacienteId(Long pacienteId);
    
    List<CitaDto> getCitasByMedicoId(Long medicoId);
    
    List<CitaDto> getCitasByStatusCita(StatusCita statusCita);
}
