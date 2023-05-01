package com.ProyectoFinalArgProg.crud.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ProyectoFinalArgProg.crud.security.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"usuario"})
public class AcercaDeDto {

    
    private Integer id_acerca_de;

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


    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
