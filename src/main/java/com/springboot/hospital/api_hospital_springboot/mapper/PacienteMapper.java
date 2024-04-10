package com.springboot.hospital.api_hospital_springboot.mapper;

import com.springboot.hospital.api_hospital_springboot.dto.PacienteDto;
import com.springboot.hospital.api_hospital_springboot.model.Paciente;
import org.springframework.stereotype.Component;

/**
 * @author perez
 */

@Component
public class PacienteMapper {
    public PacienteDto toDto(Paciente paciente) {
        PacienteDto pacienteDto = new PacienteDto();
        pacienteDto.setId(paciente.getId());
        pacienteDto.setNombre(paciente.getNombre());
        pacienteDto.setFechaNacimiento(paciente.getFechaNacimiento());
        pacienteDto.setSituacion(paciente.isSituacion());
        return pacienteDto;
    }

    public Paciente toEntity(PacienteDto pacienteDto) {
        Paciente paciente = new Paciente();
        paciente.setId(pacienteDto.getId());
        paciente.setNombre(pacienteDto.getNombre());
        paciente.setFechaNacimiento(pacienteDto.getFechaNacimiento());
        paciente.setSituacion(pacienteDto.isSituacion());
        return paciente;
    }
}
