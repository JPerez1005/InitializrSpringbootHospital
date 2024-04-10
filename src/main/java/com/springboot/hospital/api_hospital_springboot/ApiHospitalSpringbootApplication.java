package com.springboot.hospital.api_hospital_springboot;

import com.springboot.hospital.api_hospital_springboot.model.Cita;
import com.springboot.hospital.api_hospital_springboot.model.Consulta;
import com.springboot.hospital.api_hospital_springboot.model.Medico;
import com.springboot.hospital.api_hospital_springboot.model.Paciente;
import com.springboot.hospital.api_hospital_springboot.model.StatusCita;
import com.springboot.hospital.api_hospital_springboot.repository.CitaRepository;
import com.springboot.hospital.api_hospital_springboot.repository.ConsultaRepository;
import com.springboot.hospital.api_hospital_springboot.repository.MedicoRepository;
import com.springboot.hospital.api_hospital_springboot.repository.PacienteRepository;
import java.util.Date;
import java.util.stream.Stream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiHospitalSpringbootApplication {

    public static void main(String[] args) {
            SpringApplication.run(ApiHospitalSpringbootApplication.class, args);
    }
    
    //@Bean
    CommandLineRunner start
        (PacienteRepository pr,
        MedicoRepository mr,
        CitaRepository cr,
        ConsultaRepository cor){
        
        return args->{
            Stream.of("Tatiana","Sofia","Samanta")
                    .forEach(nombre->{
                        Paciente p=new Paciente();
                        p.setNombre(nombre);
                        p.setFechaNacimiento(new Date());
                        p.setSituacion(false);
                        pr.save(p);
                    });
            
            Stream.of("Julian","Ginna","Angela")
                    .forEach(nombre->{
                        Medico m= new Medico();
                        m.setNombre(nombre);
                        m.setEmail(nombre+((int)Math.random()*9)+"@gmail.com");
                        m.setEspecialidad(Math.random()>0.5?"Cardiología":"Obstetricia");
                        mr.save(m);
                    });
            
            Paciente p1=pr.findById(1L).orElse(null);
            
            Medico medico = mr.findByNombre("Julian");
            
            Cita c1=new Cita();
            
            c1.setFecha(new Date());
            c1.setStatusCita(StatusCita.PENDIENTE);
            c1.setMedico(medico);
            c1.setPaciente(p1);
            cr.save(c1);
            
            Cita citaBBDD1=cr.findById(1L).orElse(null);
            
            Consulta consulta=new Consulta();
            consulta.setFechaConsulta(new Date());
            consulta.setCita(citaBBDD1);
            consulta.setInforme("Informe de la consulta");
            
            cor.save(consulta);
        };
        
    }

}
