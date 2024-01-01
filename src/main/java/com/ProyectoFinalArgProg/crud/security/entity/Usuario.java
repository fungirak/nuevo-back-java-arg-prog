package com.ProyectoFinalArgProg.crud.security.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.ProyectoFinalArgProg.crud.entity.AcercaDe;
import com.ProyectoFinalArgProg.crud.entity.Educacion;
import com.ProyectoFinalArgProg.crud.entity.Experiencia;
import com.ProyectoFinalArgProg.crud.entity.Proyecto;
import com.ProyectoFinalArgProg.crud.entity.Skill;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Usuario {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_usuario;


    @NotNull
    private String nombre;


    @NotNull
    @Column(unique = true)
    private String nombreUsuario;


    @NotNull
    private String email;

    @JsonIgnore
    @NotNull
    private String password;

   
    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<>();

    // BIDIRECCIONAL
    //@OneToOne(mappedBy="usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //private AcercaDe acercaDe ;

    // UNIDIRECCIONAL 
    //@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "id_acerca_de")
    //private AcercaDe acercaDe;


    //@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //private Set<Educacion> educacion ;


    //@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //private Set<Experiencia> experiencia ;


    //@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //private Set<Skill> skill ;
    

    //@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //private Set<Proyecto> proyecto ;

    public Usuario() {
    }



    public Usuario(@NotNull String nombre, @NotNull String nombreUsuario, @NotNull String email, @NotNull String password) {
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        /* 
        this.acercaDe = new AcercaDe();
        this.educacion = new HashSet<>();
        this.experiencia = new HashSet<>();
        this.skill = new HashSet<>();
        this.proyecto = new HashSet<>();
        */
    }

    public Integer getIdUsuario() {
        return id_usuario;
    }

    public void setIdUsuario(Integer id) {
        this.id_usuario = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }


    // AcercaDe
    /* 
    public AcercaDe getAcercaDe() {
        return acercaDe;
    }

    public void setAcercaDe(AcercaDe acercaDe) {
        this.acercaDe = acercaDe;
    }


    // Educación
    public Set<Educacion> getEducacion() {
        return educacion;
    }

    public void setEducacion(Set<Educacion> educacion) {
        this.educacion = educacion;
    }




    // Experiencia
    public Set<Experiencia> getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(Set<Experiencia> experiencia) {
        this.experiencia = experiencia;
    }

    
    
   

    // Skill
    public Set<Skill> getSkill() {
        return skill;
    }

    public void setSkill(Set<Skill> skill) {
        this.skill = skill;
    }

    


   // Proyecto
    public Set<Proyecto> getProyecto() {
        return proyecto;
    }

    public void setProyecto(Set<Proyecto> proyecto) {
        this.proyecto = proyecto;
    }

   */

   public Usuario get() {
     return this;
    }


}
