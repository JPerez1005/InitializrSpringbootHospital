package com.springboot.hospital.api_hospital_springboot.service.Impl;

import com.springboot.hospital.api_hospital_springboot.dto.CitaDto;
import com.springboot.hospital.api_hospital_springboot.dto.MedicoDto;
import com.springboot.hospital.api_hospital_springboot.dto.PacienteDto;
import com.springboot.hospital.api_hospital_springboot.mapper.CitaMapper;
import com.springboot.hospital.api_hospital_springboot.mapper.MedicoMapper;
import com.springboot.hospital.api_hospital_springboot.mapper.PacienteMapper;
import com.springboot.hospital.api_hospital_springboot.model.Cita;
import com.springboot.hospital.api_hospital_springboot.model.Consulta;
import com.springboot.hospital.api_hospital_springboot.model.Medico;
import com.springboot.hospital.api_hospital_springboot.model.Paciente;
import com.springboot.hospital.api_hospital_springboot.model.StatusCita;
import com.springboot.hospital.api_hospital_springboot.repository.CitaRepository;
import com.springboot.hospital.api_hospital_springboot.repository.ConsultaRepository;
import com.springboot.hospital.api_hospital_springboot.repository.MedicoRepository;
import com.springboot.hospital.api_hospital_springboot.repository.PacienteRepository;
import com.springboot.hospital.api_hospital_springboot.service.CitaService;
import com.springboot.hospital.api_hospital_springboot.service.MedicoService;
import com.springboot.hospital.api_hospital_springboot.service.PacienteService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author perez
 */

@Service
public class CitaServiceImpl implements CitaService{

    @Autowired
    private PacienteRepository pr;
    
    @Autowired
    private MedicoRepository mr;
    
    @Autowired
    private ConsultaRepository cr;
    
    @Autowired
    private CitaRepository cr2;
    
    @Autowired
    private CitaMapper cm;
    
    @Autowired
    private PacienteMapper pm;
    
    @Autowired
    private MedicoMapper mm;
    
    @Autowired
    private MedicoService ms;
    
    @Autowired
    private PacienteService ps;
    
    /*Metodo para obtener las citas*/
    @Override
    public List<CitaDto> getAllCitas() {
        List<Cita> citas=cr2.findAll();/*recogemos los datos en entidades*/
        return citas.stream()/*Recorremos datos*/
                .map(cm::toDto)/*Lo cambiamos en Dto*/
                .collect(Collectors.toList());/*Lo recolectamos en una lista*/
    }

    @Override
    public Optional<CitaDto> getCitaById(Long id) {
        Optional<Cita> citaOptional=cr2.findById(id);
        return citaOptional.map(cm::toDto);
    }

    @Override
    public Cita createCita(CitaDto citaDto, Long idPaciente, Long idMedico) throws ParseException{
        PacienteDto pacienteDto=ps.getPacienteById(idPaciente).orElse(null);
        MedicoDto medicoDto=ms.getMedicoById(idMedico).orElse(null);
        
        if(pacienteDto==null || medicoDto==null){
            return null;
        }
        
        /*Cambiamos todos los datos de Dto a entidad*/
        Paciente paciente=pm.toEntity(pacienteDto);
        Medico medico=mm.toEntity(medicoDto);
        Cita cita=cm.toEntity(citaDto,paciente,medico);
        /*Guardamos la cita*/
        return cr2.save(cita);
    }

    @Override
    public CitaDto updateCita(Long id, CitaDto citaDto) throws ParseException{
        /*Buscamos la cita*/
        Optional<Cita> citaOptional=cr2.findById(id);
        
        
        /*si es que existe...*/
        if(citaOptional.isPresent()){
            /*La guardamos en la entidad*/
            Cita cita=citaOptional.get();
            
            cita.setId(citaDto.getId());
            /*Obtenemos el siguiente formato de fecha*/
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            Date fecha=dateFormat.parse(citaDto.getFecha());
            
            cita.setCancelado(citaDto.isCancelado());
            cita.setStatusCita(StatusCita.valueOf(citaDto.getStatusCita()));
            
            Optional<Paciente> pacienteOptional =pr.findById(citaDto.getPacienteId());
            Paciente paciente=pacienteOptional.get();
            cita.setPaciente(paciente);
            
            
            Optional<Medico> medicoOptional =mr.findById(citaDto.getMedicoId());
            Medico medico=medicoOptional.get();
            cita.setMedico(medico);
            
            return cm.toDto(cr2.save(cita));
        }
        return null;
    }

    @Override
    public void deleteCita(Long id) {
        Optional<Cita> citaOptional=cr2.findById(id);
        
        if(citaOptional.isPresent()){
            Cita cita=citaOptional.get();
            
            if(cita.getConsulta()!=null){
                Consulta consulta=cita.getConsulta();
                consulta.setCita(null);
                cr.delete(consulta);
            }
            cr2.delete(cita);
        }
    }

    @Override
    public List<CitaDto> getCitasByPacienteId(Long pacienteId) {
        
        List<Cita> citas =cr2.findByPacienteId(pacienteId);
        
        return citas.stream()
                .map(cm::toDto)
                .collect(Collectors.toList());
        
    }

    @Override
    public List<CitaDto> getCitasByMedicoId(Long medicoId) {
        List<Cita> citas=cr2.findByMedicoId(medicoId);
        return citas.stream()
                .map(cm::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaDto> getCitasByStatusCita(StatusCita statusCita) {
        List<Cita> citas=cr2.findByStatusCita(statusCita);
        return citas.stream()
                .map(cm::toDto)
                .collect(Collectors.toList());
    }

}
