package com.springboot.hospital.api_hospital_springboot.service.Impl;

import com.springboot.hospital.api_hospital_springboot.dto.CitaDto;
import com.springboot.hospital.api_hospital_springboot.dto.PacienteDto;
import com.springboot.hospital.api_hospital_springboot.mapper.CitaMapper;
import com.springboot.hospital.api_hospital_springboot.mapper.PacienteMapper;
import com.springboot.hospital.api_hospital_springboot.model.Cita;
import com.springboot.hospital.api_hospital_springboot.model.Paciente;
import com.springboot.hospital.api_hospital_springboot.repository.PacienteRepository;
import com.springboot.hospital.api_hospital_springboot.service.CitaService;
import com.springboot.hospital.api_hospital_springboot.service.PacienteService;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author perez
 */

@Service
public class PacienteServiceImpl implements PacienteService{

    @Autowired
    private PacienteRepository pr;
    
    @Autowired
    private CitaService cs;
    
    @Autowired
    private PacienteMapper pm;
    
    @Autowired
    private CitaMapper cm;
    
    
    @Override
    public List<PacienteDto> getAllPacientes() {
        List<Paciente> pacientes=pr.findAll();
        return pacientes.stream()
                .map(pm::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PacienteDto> getPacienteById(Long id) {
        Optional<Paciente> optionalPaciente=pr.findById(id);
        
        return optionalPaciente.map(pm::toDto);
    }

    @Override
    public PacienteDto createPaciente(PacienteDto pacienteDto) {
        Paciente paciente=pm.toEntity(pacienteDto);
        
        paciente=pr.save(paciente);
        return pm.toDto(paciente);
    }

    @Override
    public PacienteDto updatePaciente(Long id, PacienteDto pacienteDto) {
        Optional<Paciente> optionalPaciente=pr.findById(id);
        if(optionalPaciente.isPresent()){
            Paciente paciente=optionalPaciente.get();
            paciente.setNombre(pacienteDto.getNombre());
            paciente.setFechaNacimiento(pacienteDto.getFechaNacimiento());
            paciente.setSituacion(pacienteDto.isSituacion());
            
            paciente=pr.save(paciente);
            return pm.toDto(paciente);
        }
        return null;
    }

    @Override
    public void deletePaciente(Long id) {
        Optional<Paciente> optionalPaciente=pr.findById(id);
        if(optionalPaciente.isPresent()){
            Paciente paciente=optionalPaciente.get();
            
            for(Cita cita: paciente.getCitas()){//eliminamos cada cita en la que estuvo
                cs.deleteCita(cita.getId());
            }
            
            pr.deleteById(id);
        }
    }

    @Override
    public Collection<CitaDto> getCitasByPacienteId(Long pacienteId) {
        Optional<Paciente> optionalPaciente=pr.findById(pacienteId);
        return optionalPaciente.map(paciente->paciente.getCitas().stream()
            .map(cm::toDto)
            .collect(Collectors.toList()))
            .orElse(null);
    }

}
