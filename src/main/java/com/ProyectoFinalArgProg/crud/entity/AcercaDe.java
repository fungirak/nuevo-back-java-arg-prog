
package com.ProyectoFinalArgProg.crud.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
public class AcercaDe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_acerca_de;

    // BIDIRECCIONAL
    //@OneToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name="id_usuario", unique=true)
    //private Usuario usuario;

    // UNIDIRECCIONAL
    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    
    //@NotBlank
    //@Size(min = 1, max = 128)
    //@NotNull(message = "Escribe tu nombre completo")
    private String fullname;

    //@NotBlank
    //@Size(min = 3, max = 128)
    //@NotNull(message = "¿Cuál es tu trabajo actual?")
    private String posicion;

    //@NotBlank
    //@Size(min = 16, max = 1024)
    //@NotNull(message = "Cuenta acerca de tu trabajo")
    private String descripcion;


    public AcercaDe(){
    
    }

    public AcercaDe(String fullname, String posicion, String descripcion){
       this.fullname = fullname;
       this.posicion = posicion;
       this.descripcion = descripcion;
    }

    public Integer getId() {
        return id_acerca_de;
    }

    public void setId(Integer id) {
        this.id_acerca_de = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
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
