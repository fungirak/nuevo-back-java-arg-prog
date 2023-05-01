
package com.ProyectoFinalArgProg.crud.entity;

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

import com.ProyectoFinalArgProg.crud.security.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 *
 * @author fungirak
 */


@Entity
@JsonIgnoreProperties({"usuario"})
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_proyecto;

    @ManyToOne(/*fetch = FetchType.EAGER, cascade=CascadeType.REMOVE, optional = true*/)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

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
   


    public Proyecto(){
    
    }

    public Proyecto(String titulo, String imagen, String descripcion){
       this.titulo = titulo;
       this.imagen = imagen;
       this.descripcion = descripcion;
    }
    
    public Integer getId() {
        return id_proyecto;
    }

    public void setId(Integer id) {
        this.id_proyecto = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

 

   


}
