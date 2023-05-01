package com.ProyectoFinalArgProg.crud.dto;

import java.util.Set;

import com.ProyectoFinalArgProg.crud.entity.AcercaDe;
import com.ProyectoFinalArgProg.crud.entity.Educacion;
import com.ProyectoFinalArgProg.crud.entity.Experiencia;
import com.ProyectoFinalArgProg.crud.entity.Proyecto;
import com.ProyectoFinalArgProg.crud.entity.Skill;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"usuario"})
public class PortafolioDto {
   
    
        private AcercaDe acercaDe;
        private Set<Educacion> educacion;
        private Set<Experiencia> experiencias;
        private Set<Proyecto> proyectos;
        private Set<Skill> skills;
        
        // getters y setters
        
        public AcercaDe getAcercaDe() {
            return acercaDe;
        }
        
        public void setAcercaDe(AcercaDe acercaDe) {
            this.acercaDe = acercaDe;
        }
        
        public Set<Educacion> getEducacion() {
            return educacion;
        }
        
        public void setEducacion(Set<Educacion> educacion) {
            this.educacion = educacion;
        }
        
        public Set<Experiencia> getExperiencias() {
            return experiencias;
        }
        
        public void setExperiencias(Set<Experiencia> experiencias) {
            this.experiencias = experiencias;
        }
        
        public Set<Proyecto> getProyectos() {
            return proyectos;
        }
        
        public void setProyectos(Set<Proyecto> proyectos) {
            this.proyectos = proyectos;
        }
        
        public Set<Skill> getSkills() {
            return skills;
        }
        
        public void setSkills(Set<Skill> skills) {
            this.skills = skills;
        }
    
}
