package com.springboot.hospital.api_hospital_springboot.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;

/**
 * @author perez
 */

@Data
public class ConsultaDto {
    
    private Long id;
    
    private String fechaConsulta;
    
    private String informe;
    
    private CitaDto citaDto;
    
    public Date getFechaConsultaAsDate() throws ParseException{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        return sdf.parse(this.fechaConsulta);//pasamos el string a formato de fecha
    }
    
    public void setFechaConsultaFromDate(Date fechaConsulta){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        this.fechaConsulta=sdf.format(this.fechaConsulta);//establecemos la consulta
    }
    
}
