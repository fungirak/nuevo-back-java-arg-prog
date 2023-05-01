
package com.ProyectoFinalArgProg.crud.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author fungirak
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"usuario"})
public class ExperienciaDto {

   
    @NotBlank
    @Size(min = 1, max = 256)
    @NotNull(message = "¿En qué empresa u organización trabajaste?")
    private String empresa;

   
    @NotBlank
    @Size(min = 8, max = 1024)
    @NotNull(message = "Puedes escribir País - Provincia - Ciudad")
    private String ubicacion;  

   
    @NotBlank
    @Size(min = 2, max = 128)
    @NotNull(message = "¿En qué puesto o posición has trabajado?")
    private String puesto;

   
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "La fecha de inicio de tu experiencia no puede estar vacía")
    private LocalDate fechaInicio;


    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaFinalizacion;


    @NotBlank
    @Size(min = 16, max = 1024)
    @NotNull(message = "Cuenta que has hecho o que tareas has realizado, si ha sido de tu agrado, cómo te ha ido.")   
    private String actividades;


    
}
