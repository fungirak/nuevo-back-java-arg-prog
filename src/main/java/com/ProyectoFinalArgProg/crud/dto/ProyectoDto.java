
package com.ProyectoFinalArgProg.crud.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
public class ProyectoDto {

   
    @NotBlank
    @Size(min = 2, max = 1024)
    @NotNull(message = "Escribe el título de tu proyecto o del cuál has participado")
    private String titulo;

  
    @NotBlank
    @Size(min = 5, max = 8192)
    @NotNull(message = "Ingresa la URL de alguna imagen que te guste a modo ilustrativo")
    private String imagen;

    
    @NotBlank
    @Size(min = 32, max = 4096)
    @NotNull(message = "Cuenta todo lo que quieras acerca de este proyecto")
    private String descripcion;  
   
   
}
