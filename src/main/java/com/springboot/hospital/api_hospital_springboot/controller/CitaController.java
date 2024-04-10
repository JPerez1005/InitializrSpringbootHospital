package com.springboot.hospital.api_hospital_springboot.controller;

import com.springboot.hospital.api_hospital_springboot.dto.CitaDto;
import com.springboot.hospital.api_hospital_springboot.mapper.CitaMapper;
import com.springboot.hospital.api_hospital_springboot.mapper.MedicoMapper;
import com.springboot.hospital.api_hospital_springboot.mapper.PacienteMapper;
import com.springboot.hospital.api_hospital_springboot.model.Cita;
import com.springboot.hospital.api_hospital_springboot.model.StatusCita;
import com.springboot.hospital.api_hospital_springboot.service.CitaService;
import com.springboot.hospital.api_hospital_springboot.service.MedicoService;
import com.springboot.hospital.api_hospital_springboot.service.PacienteService;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author perez
 */

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService cs;
    
    @Autowired
    private MedicoService ms;
    
    @Autowired
    private PacienteService ps;
    
    @Autowired
    private CitaMapper cm;
    
    @Autowired
    private MedicoMapper mm;
    
    @Autowired
    private PacienteMapper pm;
    
    @GetMapping
    public ResponseEntity<List<CitaDto>> listarCitas(){
        List<CitaDto> citas=cs.getAllCitas();
        return new ResponseEntity<>(citas,HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CitaDto> listarCitaPorId(@PathVariable Long id){
        Optional<CitaDto> citaDtoOptional=cs.getCitaById(id);
        return citaDtoOptional.map(cita->new ResponseEntity<>(cita,HttpStatus.OK))
                .orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping("/{idPaciente}/{idMedico}")
    public ResponseEntity<CitaDto> guardarCita
        (@RequestBody CitaDto citaDto,@PathVariable Long idPaciente,@PathVariable Long idMedico) throws ParseException{
        Cita newCita=cs.createCita(citaDto, idPaciente, idMedico);
        
        if(newCita==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        CitaDto newCitaDto=cm.toDto(newCita);//Aquí lo convertimos a Dto
        return new ResponseEntity<>(citaDto,HttpStatus.CREATED);
    }
        
    @PutMapping("/{id}")
    public ResponseEntity<CitaDto> actualizarCita(@PathVariable Long id,@RequestBody CitaDto citaDto)throws ParseException{
        CitaDto citaUpdate=cs.updateCita(id, citaDto);
        if(citaUpdate!=null){
            return ResponseEntity.ok(citaUpdate);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCita(@PathVariable Long id){
        cs.deleteCita(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/paciente/{pacienteId}")
    public List<CitaDto> listarCitasPorPacienteId(@PathVariable Long pacienteId){
        return cs.getCitasByPacienteId(pacienteId);
    }
    
    @GetMapping("/medico/{medicoId}")
    public List<CitaDto> listaCitasPorMedicoId(@PathVariable Long medicoId){
        return cs.getCitasByMedicoId(medicoId);
    }
    
    @GetMapping("/status/{statusCita}")
    public List<CitaDto> listaCitasPorStatus(@PathVariable StatusCita statusCita){
        return cs.getCitasByStatusCita(statusCita);
    }
}
