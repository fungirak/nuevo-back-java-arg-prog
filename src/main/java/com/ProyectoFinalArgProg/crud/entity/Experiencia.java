
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
public class Experiencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_experiencia;

    @ManyToOne(/*fetch = FetchType.EAGER, cascade=CascadeType.REMOVE, optional = true*/)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    
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


    public Experiencia(){
    
    }

    public Experiencia(String empresa, String ubicacion, String puesto,  LocalDate fechaInicio, LocalDate fechaFinalizacion, String actividades){
       this.empresa = empresa;
       this.ubicacion = ubicacion;
       this.puesto = puesto;
       this.fechaInicio = fechaInicio;
       this.fechaFinalizacion = fechaFinalizacion;
       this.actividades = actividades;
    }
 
    public Integer getId() {
        return id_experiencia;
    }

    public void setId(Integer id) {
        this.id_experiencia = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
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

    public String getActividades() {
        return actividades;
    }

    public void setActividades(String actividades) {
        this.actividades = actividades;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


   
}
