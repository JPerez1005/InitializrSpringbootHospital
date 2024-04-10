package com.springboot.hospital.api_hospital_springboot.dto;

import lombok.Data;

/**
 * @author perez
 */

@Data
public class MedicoDto {

    private Long id;
    private String nombre;
    private String email;
    private String especialidad;
    
}
