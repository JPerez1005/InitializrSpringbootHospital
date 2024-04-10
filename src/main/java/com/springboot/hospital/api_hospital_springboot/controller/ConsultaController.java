package com.springboot.hospital.api_hospital_springboot.controller;

import com.springboot.hospital.api_hospital_springboot.dto.ConsultaDto;
import com.springboot.hospital.api_hospital_springboot.mapper.CitaMapper;
import com.springboot.hospital.api_hospital_springboot.service.CitaService;
import com.springboot.hospital.api_hospital_springboot.service.ConsultaService;
import jakarta.websocket.server.PathParam;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author perez
 */

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {
    
    @Autowired
    private ConsultaService cs;
    
    @Autowired
    private CitaMapper cm;
    
    @Autowired
    private CitaService cis;
    
    @GetMapping
    public ResponseEntity<List<ConsultaDto>> listarConsultas(){
        List<ConsultaDto> consultas=cs.getAllConsultas();
        return new ResponseEntity<>(consultas,HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDto> obtenerConsulta(@PathVariable Long id){
        Optional<ConsultaDto> consulta=cs.getConsultaById(id);
        return consulta.map(dto->new ResponseEntity<>(dto,HttpStatus.OK))
                .orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<ConsultaDto> guardarConsulta(@RequestParam Long citaId,
            @RequestBody ConsultaDto consultaDto) throws ParseException{
        ConsultaDto createdConsulta=cs.createConsulta(citaId, consultaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdConsulta);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ConsultaDto> actualizarConsulta(@PathVariable Long id,
            @RequestBody ConsultaDto consultaDto) throws ParseException{
        ConsultaDto updateConsulta=cs.updateConsulta(id, consultaDto);
        return updateConsulta!=null
                ? new ResponseEntity<>(updateConsulta,HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarConsulta(@PathVariable Long id){
        cs.deleteConsulta(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<ConsultaDto>> listarConsultaPorInforme(@RequestParam String search){
        List<ConsultaDto> consultaDtos=cs.getConsultasByInformeContaining(search);
        return new ResponseEntity<>(consultaDtos,HttpStatus.OK);
    }
    
    @GetMapping("/cita/{citaId}")
    public ResponseEntity<List<ConsultaDto>> listarConsultasPorCita(@PathVariable Long citaId) throws ParseException{
        List<ConsultaDto> consultaDtos=cs.getConsultasByCitaId(citaId);
        return new ResponseEntity<>(consultaDtos,HttpStatus.OK);
    }
    
}
