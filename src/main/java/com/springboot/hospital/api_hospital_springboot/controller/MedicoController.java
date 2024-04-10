package com.springboot.hospital.api_hospital_springboot.controller;

import com.springboot.hospital.api_hospital_springboot.dto.CitaDto;
import com.springboot.hospital.api_hospital_springboot.dto.MedicoDto;
import com.springboot.hospital.api_hospital_springboot.service.MedicoService;
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
@RequestMapping("/api/medicos")
public class MedicoController {
    
    @Autowired
    private MedicoService ms;
    
    @GetMapping
    public ResponseEntity<List<MedicoDto>> listarMedicos(){
        List<MedicoDto> medicos=ms.getAllMedicos();
        return new ResponseEntity<>(medicos, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MedicoDto> listarMedicoPorId(@PathVariable Long id){
        return ms.getMedicoById(id)
                .map(medico -> new ResponseEntity<>(medico,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<MedicoDto> guardarMedico(@RequestBody MedicoDto medicoDto){
        MedicoDto createdMedico=ms.createMedico(medicoDto);
        return new ResponseEntity<>(createdMedico,HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MedicoDto> actualizarMedico
        (@PathVariable Long id,@RequestBody MedicoDto medicoDto){
        
            MedicoDto updateMedico=ms.updateMedico(id, medicoDto);
            
            if (updateMedico!=null) {
                return new ResponseEntity<>(updateMedico,HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }
        
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMedico(@PathVariable Long id){
        ms.deleteMedico(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/{id}/citas")
    public ResponseEntity<Collection<CitaDto>> listarCitasPorMedicoId(@PathVariable Long id){
        Collection<CitaDto> citas=ms.getCitasByMedicoId(id);//optenemos la coleccion de citas por medico
        if(citas!=null){
            return new ResponseEntity<>(citas,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/especialidad/{especialidad}")
    public ResponseEntity<List<MedicoDto>> listarMedicosPorEspecialidad(@PathVariable String especialidad){
        List<MedicoDto> medicos=ms.getMedicosByEspecialidad(especialidad);
        return new ResponseEntity<>(medicos,HttpStatus.OK);
    }
    
}
