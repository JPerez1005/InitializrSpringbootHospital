package com.springboot.hospital.api_hospital_springboot.service.Impl;

import com.springboot.hospital.api_hospital_springboot.dto.CitaDto;
import com.springboot.hospital.api_hospital_springboot.dto.MedicoDto;
import com.springboot.hospital.api_hospital_springboot.mapper.CitaMapper;
import com.springboot.hospital.api_hospital_springboot.mapper.MedicoMapper;
import com.springboot.hospital.api_hospital_springboot.model.Cita;
import com.springboot.hospital.api_hospital_springboot.model.Medico;
import com.springboot.hospital.api_hospital_springboot.repository.MedicoRepository;
import com.springboot.hospital.api_hospital_springboot.service.CitaService;
import com.springboot.hospital.api_hospital_springboot.service.MedicoService;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author perez
 */

@Service
public class MedicoServiceImpl implements MedicoService{
    
    @Autowired
    private MedicoRepository mr;
    
    @Autowired
    private CitaService cs;
    
    @Autowired
    private CitaMapper cm;
    
    @Autowired
    private MedicoMapper mm;
    
    @Override
    public List<MedicoDto> getAllMedicos() {
        List<Medico> medicos=mr.findAll();
        return medicos.stream()
                .map(mm::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MedicoDto> getMedicoById(Long id) {
        Optional<Medico> optionalMedico=mr.findById(id);
        return optionalMedico.map(mm::toDto);
    }

    @Override
    public MedicoDto createMedico(MedicoDto medicoDto) {
        Medico medico=mm.toEntity(medicoDto);
        medico=mr.save(medico);
        return mm.toDto(medico);
    }

    @Override
    public MedicoDto updateMedico(Long id, MedicoDto medicoDto) {
        Optional<Medico> optionalMedico=mr.findById(id);
        if(optionalMedico.isPresent()){
            Medico medico=optionalMedico.get();
            medico.setNombre(medicoDto.getNombre());
            medico.setEmail(medicoDto.getEmail());
            medico.setEspecialidad(medicoDto.getEspecialidad());
            
            medico=mr.save(medico);
            return mm.toDto(medico);
        }
        return null;
    }

    @Override
    public void deleteMedico(Long id) {
        Optional<Medico> optionalMedico=mr.findById(id);
        
        if(optionalMedico.isPresent()){
            Medico medico=optionalMedico.get();
            
            for(Cita cita:medico.getCitas()){//eliminamos todas las citas en las que está
                cs.deleteCita(cita.getId());
            }
            
            mr.deleteById(id);
        }
    }

    @Override
    public Collection<CitaDto> getCitasByMedicoId(Long medicoId) {
        Optional<Medico> optionalMedico=mr.findById(medicoId);
        return optionalMedico.map(medico->medico.getCitas().stream()
            .map(cm::toDto)
            .collect(Collectors.toList()))
            .orElse(null);
    }

    @Override
    public List<MedicoDto> getMedicosByEspecialidad(String Especialidad) {
        List<Medico> medicos=mr.findByEspecialidad(Especialidad);
        return medicos.stream()
                .map(mm::toDto)
                .collect(Collectors.toList());
    }

}
