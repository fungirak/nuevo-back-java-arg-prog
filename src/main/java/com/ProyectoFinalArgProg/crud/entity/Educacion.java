
package com.ProyectoFinalArgProg.crud.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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


/**
 *
 * @author fungirak
 */


@Entity
@JsonIgnoreProperties({"usuario"})
public class Educacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_educacion;



    @ManyToOne(/*fetch = FetchType.EAGER, cascade=CascadeType.REMOVE, optional = true*/)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    
    @NotBlank
    @Size(min = 2, max = 1024)
    @NotNull(message = "Escribe el nombre de la institución")
    private String institucion;

    @NotBlank
    @Size(min = 2, max = 256)
    @NotNull(message = "Escribe el nombre completo del título que obtuviste o vas a obtener")
    private String titulo;  

    @NotNull
    @DateTimeFormat(pattern="dd-MM-yyyy")
    @NotNull(message = "La fecha de inicio de tu educación no puede estar vacía")
    private LocalDate fechaInicio;

    
    @DateTimeFormat(pattern="dd-MM-yyyy")
    private LocalDate fechaFinalizacion;

    @NotBlank
    @Size(min = 2, max = 64)
    @NotNull(message = "El estado de la educación que estás cargando. Ej: Estudiante Inicial, Estudiante Avanzado, Graduado o como te identifiques con tus estudios.")
    private String estado;

    @NotBlank
    @Size(min = 16, max = 4096)
    @NotNull(message = "¿Podrías compartirme un resumen de tu historial educativo? Cuenta si has estado en algún programa de intercambio ó investigación,  practicado algún deporte ó lo que tu quieras!")
    private String detalles;


    public Educacion(){
    
    }

    public Educacion(String institucion, String titulo, LocalDate fechaInicio, LocalDate fechaFinalizacion, String estado, String detalles){
       this.institucion = institucion;
       this.titulo = titulo;
       this.fechaInicio = fechaInicio;
       this.fechaFinalizacion = fechaFinalizacion;
       this.estado = estado;
       this.detalles = detalles;
    }

    public Integer getId() {
        return id_educacion;
    }

    public void setId(Integer id) {
        this.id_educacion = id;
    }



    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(LocalDate fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

   
    
}
