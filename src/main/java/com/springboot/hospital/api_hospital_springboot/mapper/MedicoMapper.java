package com.springboot.hospital.api_hospital_springboot.mapper;

import com.springboot.hospital.api_hospital_springboot.dto.MedicoDto;
import com.springboot.hospital.api_hospital_springboot.model.Medico;
import org.springframework.stereotype.Component;

/**
 * @author perez
 */

@Component
public class MedicoMapper {

    public MedicoDto toDto(Medico medico){
        MedicoDto medicoDto=new MedicoDto();
        medicoDto.setId(medico.getId());
        medicoDto.setNombre(medico.getNombre());
        medicoDto.setEmail(medico.getEmail());
        medicoDto.setEspecialidad(medico.getEspecialidad());
        return medicoDto;
    }
    
    public Medico toEntity(MedicoDto medicoDto){
        Medico medico=new Medico();
        
        medico.setId(medicoDto.getId());
        medico.setNombre(medicoDto.getNombre());
        medico.setEmail(medicoDto.getEmail());
        medico.setEspecialidad(medicoDto.getEspecialidad());
        return medico;
    }
    
}
