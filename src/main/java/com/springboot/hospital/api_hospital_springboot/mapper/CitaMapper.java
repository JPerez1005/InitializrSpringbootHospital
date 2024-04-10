package com.springboot.hospital.api_hospital_springboot.mapper;

import com.springboot.hospital.api_hospital_springboot.dto.CitaDto;
import com.springboot.hospital.api_hospital_springboot.model.Cita;
import com.springboot.hospital.api_hospital_springboot.model.Medico;
import com.springboot.hospital.api_hospital_springboot.model.Paciente;
import com.springboot.hospital.api_hospital_springboot.model.StatusCita;
import com.springboot.hospital.api_hospital_springboot.repository.MedicoRepository;
import com.springboot.hospital.api_hospital_springboot.repository.PacienteRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author perez
 */

@Component
public class CitaMapper {

    @Autowired
    private MedicoRepository mr;
    
    @Autowired
    private PacienteRepository pr;
    
    /*El siguiente metodo es para convertir datos de entidad
    a DTO*/
    public CitaDto toDto(Cita cita){
        CitaDto citaDto= new CitaDto();
        
        citaDto.setId(citaDto.getId());
        
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String formatedFecha=sdf.format(cita.getFecha());
        
        citaDto.setFecha(formatedFecha);
        
        citaDto.setCancelado(cita.isCancelado());
        citaDto.setStatusCita(cita.getStatusCita().name());
        citaDto.setPacienteId(cita.getPaciente().getId());
        citaDto.setMedicoId(cita.getMedico().getId());
        
        return citaDto;
    }
    
    /*El siguiente metodo es para convertir datos de DTO
    a Entity*/
    public Cita toEntity(CitaDto citaDto,Paciente paciente,Medico medico) throws ParseException{
        
        Cita cita=new Cita();
        
        cita.setId(cita.getId());
        
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date fecha=sdf.parse(citaDto.getFecha());
        cita.setFecha(fecha);
        
        cita.setCancelado(citaDto.isCancelado());
        cita.setStatusCita(StatusCita.valueOf(citaDto.getStatusCita()));
        cita.setPaciente(paciente);
        cita.setMedico(medico);
        return cita;
    }
    
    /*El siguiente metodo es para convertir datos de DTO
    a Entity*/
    public Cita toEntity(CitaDto citaDto) throws ParseException{
        
        Cita cita=new Cita();
        
        cita.setId(citaDto.getId());
        
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date fecha=sdf.parse(citaDto.getFecha());
        cita.setFecha(fecha);
        
        cita.setCancelado(citaDto.isCancelado());
        cita.setStatusCita(StatusCita.valueOf(citaDto.getStatusCita()));
        
        Optional<Paciente> paciente=pr.findById(citaDto.getPacienteId());
        Paciente pacienteBBDD=paciente.get();
        cita.setPaciente(pacienteBBDD);
        
        Optional<Medico> medico=mr.findById(citaDto.getMedicoId());
        Medico medicoBBDD=medico.get();
        cita.setMedico(medicoBBDD);
        return cita;
    }
    
}
