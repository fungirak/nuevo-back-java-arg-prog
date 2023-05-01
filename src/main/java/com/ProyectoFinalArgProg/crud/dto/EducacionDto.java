
package com.ProyectoFinalArgProg.crud.dto;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.ProyectoFinalArgProg.crud.security.entity.Usuario;
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
public class EducacionDto {

    
    private Integer id_educacion;


    private Usuario usuario;

    
    @NotBlank
    @Size(min = 2, max = 1024)
    @NotNull(message = "Escribe el nombre de la institución")
    private String institucion;

    @NotBlank
    @Size(min = 2, max = 256)
    @NotNull(message = "Escribe el nombre completo del título que obtuviste o vas a obtener")
    private String titulo;  

    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "La fecha de inicio de tu educación no puede estar vacía")
    private LocalDate fechaInicio;


    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaFinalizacion;

    @NotBlank
    @Size(min = 2, max = 64)
    @NotNull(message = "El estado de la educación que estás cargando. Ej: Estudiante Inicial, Estudiante Avanzado, Graduado o como te identifiques con tus estudios.")
    private String estado;

    
    @NotBlank
    @Size(min = 16, max = 4096)
    @NotNull(message = "¿Podrías compartirme un resumen de tu historial educativo? Cuenta si has estado en algún programa de intercambio ó investigación,  practicado algún deporte ó lo que tu quieras!")
    private String detalles;


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void add(EducacionDto edDto) {
    }
    
}
