package com.springboot.hospital.api_hospital_springboot.dto;

import lombok.Data;

/**
 * @author perez
 */

@Data
public class CitaDto {

    private Long id;
    private String fecha;
    private String statusCita;
    private boolean cancelado;
    private Long pacienteId;
    private Long medicoId;
    
}
