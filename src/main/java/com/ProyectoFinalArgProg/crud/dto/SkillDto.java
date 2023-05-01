
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
public class SkillDto {

    @NotBlank
    @Size(min = 2, max = 128)
    @NotNull(message = "Qué tecnología dominas")
    private String tecnologia;

    @NotBlank
    @Size(min = 5, max = 8192)
    @NotNull(message = "Ingresa la URL de alguna imagen que te guste a modo ilustrativo")
    private String imagen;
  

}
