package com.springboot.hospital.api_hospital_springboot.mapper;

import com.springboot.hospital.api_hospital_springboot.dto.CitaDto;
import com.springboot.hospital.api_hospital_springboot.dto.ConsultaDto;
import com.springboot.hospital.api_hospital_springboot.model.Cita;
import com.springboot.hospital.api_hospital_springboot.model.Consulta;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.stereotype.Component;

/**
 * @author perez
 */

@Component
public class ConsultaMapper {

    private static final SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
    
    public ConsultaDto toDto(Consulta consulta){
        ConsultaDto consultaDto=new ConsultaDto();
        consultaDto.setId(consulta.getId());
        consultaDto.setFechaConsulta(dateFormat.format(consulta.getFechaConsulta()));
        consultaDto.setInforme(consulta.getInforme());
        
        if(consulta.getCita()!=null){
            Cita cita=consulta.getCita();
            CitaDto citaDto= new CitaDto();
            
            citaDto.setId(cita.getId());
            citaDto.setFecha(dateFormat.format(cita.getFecha()));
            citaDto.setCancelado(cita.isCancelado());
            citaDto.setStatusCita(cita.getStatusCita().toString());
            citaDto.setPacienteId(citaDto.getPacienteId());
            citaDto.setMedicoId(citaDto.getMedicoId());
            consultaDto.setCitaDto(citaDto);
        }
        return consultaDto;
    }
    
    public Consulta toEntity(ConsultaDto consultaDto) throws ParseException{
        Consulta consulta=new Consulta();
        
        consulta.setId(consultaDto.getId());
        consulta.setFechaConsulta((dateFormat.parse(consultaDto.getFechaConsulta())));
        consulta.setInforme(consultaDto.getInforme());
        
        if(consultaDto.getCitaDto()!=null){
            CitaDto citaDto=consultaDto.getCitaDto();
            Cita cita=new Cita();
            cita.setId(citaDto.getId());
            consulta.setCita(cita);
        }
        return consulta;
    }
    
}
