package com.springboot.hospital.api_hospital_springboot.dto;

import java.util.Date;
import lombok.Data;

/**
 * @author perez
 */
@Data
public class PacienteDto {
    
    private Long id;
    private String nombre;
    private Date fechaNacimiento;
    private boolean situacion;
}
