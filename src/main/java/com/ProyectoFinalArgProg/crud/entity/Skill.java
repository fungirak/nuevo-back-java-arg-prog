
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
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_skill;

    @ManyToOne(/*fetch = FetchType.EAGER, cascade=CascadeType.REMOVE, optional = true*/)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    
    @NotBlank
    @Size(min = 2, max = 128)
    @NotNull(message = "Qué tecnología dominas")
    private String tecnologia;

    @NotBlank
    @Size(min = 5, max = 8192)
    @NotNull(message = "Ingresa la URL de alguna imagen que te guste a modo ilustrativo")
    private String imagen;
  
   


    public Skill(){
    
    }

    public Skill(String tecnologia, String imagen){
       this.tecnologia = tecnologia;
       this.imagen = imagen;
    }
    
    public Integer getId() {
        return id_skill;
    }

    public void setId(Integer id) {
        this.id_skill = id;
    }

    public String getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(String tecnologia) {
        this.tecnologia = tecnologia;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

  
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
