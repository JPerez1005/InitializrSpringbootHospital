package com.springboot.hospital.api_hospital_springboot.controller;

import com.springboot.hospital.api_hospital_springboot.dto.CitaDto;
import com.springboot.hospital.api_hospital_springboot.dto.PacienteDto;
import com.springboot.hospital.api_hospital_springboot.service.PacienteService;
import java.util.Collection;
import java.util.List;
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
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService ps;
    
    @GetMapping
    public ResponseEntity<List<PacienteDto>> listarPacientes(){
        List<PacienteDto> pacienteDto=ps.getAllPacientes();
        return new ResponseEntity<>(pacienteDto,HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDto> buscarPacientePorId(@PathVariable Long id){
        return ps.getPacienteById(id)
                .map(pacienteDto->new ResponseEntity<>(pacienteDto,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<PacienteDto> guardarPaciente(@RequestBody PacienteDto pacienteDto){
        PacienteDto createPacienteDto=ps.createPaciente(pacienteDto);
        return new ResponseEntity<>(createPacienteDto,HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PacienteDto> actualizarPaciente(@PathVariable Long id, @RequestBody PacienteDto pacienteDto){
        PacienteDto updatePaciente=ps.updatePaciente(id, pacienteDto);
        
        if(updatePaciente!=null){
            return new ResponseEntity<>(updatePaciente,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id){
        ps.deletePaciente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/{id}/citas")
    public ResponseEntity<Collection<CitaDto>> listarCitasPorPacienteId(@PathVariable Long id){
        Collection<CitaDto> citas=ps.getCitasByPacienteId(id);
        return new ResponseEntity<>(citas,HttpStatus.OK);
    }
    
}