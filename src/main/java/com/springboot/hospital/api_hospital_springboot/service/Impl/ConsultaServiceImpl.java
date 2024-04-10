package com.springboot.hospital.api_hospital_springboot.service.Impl;

import com.springboot.hospital.api_hospital_springboot.dto.CitaDto;
import com.springboot.hospital.api_hospital_springboot.dto.ConsultaDto;
import com.springboot.hospital.api_hospital_springboot.mapper.CitaMapper;
import com.springboot.hospital.api_hospital_springboot.mapper.ConsultaMapper;
import com.springboot.hospital.api_hospital_springboot.model.Cita;
import com.springboot.hospital.api_hospital_springboot.model.Consulta;
import com.springboot.hospital.api_hospital_springboot.model.StatusCita;
import com.springboot.hospital.api_hospital_springboot.repository.CitaRepository;
import com.springboot.hospital.api_hospital_springboot.repository.ConsultaRepository;
import com.springboot.hospital.api_hospital_springboot.service.CitaService;
import com.springboot.hospital.api_hospital_springboot.service.ConsultaService;
import jakarta.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class ConsultaServiceImpl implements ConsultaService {

    @Autowired
    private ConsultaRepository cr;
    
    @Autowired
    private ConsultaMapper cm;
    
    @Autowired
    private CitaMapper cim;
    
    @Autowired
    private CitaService cs;
    
    @Autowired
    private CitaRepository cir;
    
    @Override
    public List<ConsultaDto> getAllConsultas() {
        List<Consulta> consultas=cr.findAll();
        return consultas.stream()
                .map(cm::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ConsultaDto> getConsultaById(Long id) {
        Optional<Consulta> consulta=cr.findById(id);
        return consulta.map(cm::toDto);
    }

    @Override
    public ConsultaDto createConsulta(Long citaId, ConsultaDto consultaDto) throws ParseException {
        try {
            /*Buscamos una cita*/
            CitaDto citaDto=cs.getCitaById(citaId)
                    .orElseThrow(()-> new EntityNotFoundException("Cita no encontrada"));

            Consulta consulta=new Consulta();
            consulta.setCita(cim.toEntity(citaDto));
            consulta.setFechaConsulta(new Date());
            consulta.setInforme(consultaDto.getInforme());

            Consulta createdConsulta=cr.save(consulta);
            return cm.toDto(createdConsulta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ConsultaDto updateConsulta(Long id, ConsultaDto consultaDto) throws ParseException{
        Optional<Consulta> consultaOptional=cr.findById(id);
        if(consultaOptional.isPresent()){
            Consulta consulta=consultaOptional.get();
            
            consulta.setFechaConsulta(consultaDto.getFechaConsultaAsDate());
            consulta.setInforme(consultaDto.getInforme());
            
            Consulta updateConsulta=cr.save(consulta);
            
//            Cita cita=consulta.getCita();
            CitaDto citaDto=consultaDto.getCitaDto();//obtenemos la cita del Dto
            
            if(citaDto!=null){
                Cita cita=consulta.getCita();
                if(cita!=null){
                    cita.setFecha(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(citaDto.getFecha()));
                    cita.setStatusCita(StatusCita.valueOf(citaDto.getStatusCita()));
                    
                    cir.save(cita);
                }
            }
            return cm.toDto(updateConsulta);
        }
        return null;
    }

    @Override
    public void deleteConsulta(Long id) {
        cr.deleteById(id);
    }

    @Override
    public List<ConsultaDto> getConsultasByInformeContaining(String searchTerm) {
        List<Consulta> consultas=cr.findByInformeContainingIgnoreCase(searchTerm);
        return consultas.stream()
                .map(cm::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultaDto> getConsultasByCita(Cita cita) {
        List<Consulta> consultas=cr.findByCita(cita);
        return consultas.stream()
                .map(cm::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultaDto> getConsultasByCitaId(Long citaId) throws ParseException {
        Optional<Cita> citaOptional=cir.findById(citaId);
        
        if(citaOptional.isPresent()){
            Cita cita=citaOptional.get();
            
            if(cita.getId()!=null){
                cir.save(cita);
            }
            
            List<Consulta> consultas=cr.findByCita(cita);
            
            List<ConsultaDto> consultaDtos=new ArrayList<>();
            
            for(Consulta consulta:consultas){
                ConsultaDto consultaDto=new ConsultaDto();
                consultaDto.setId(consulta.getId());
                consultaDto.setFechaConsulta(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(consulta.getFechaConsulta()));
                consultaDto.setInforme(consulta.getInforme());
                
                consultaDtos.add(consultaDto);
            }
            return consultaDtos;
        }else{
            throw new EntityNotFoundException("Cita con el id no encontrada: "+citaId);
        }
        
    }

}
