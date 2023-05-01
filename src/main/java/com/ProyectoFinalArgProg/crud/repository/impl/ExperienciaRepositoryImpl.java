
package com.ProyectoFinalArgProg.crud.repository.impl;


import com.ProyectoFinalArgProg.crud.entity.Experiencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author fungirak
 */

@Repository
public interface ExperienciaRepositoryImpl extends JpaRepository <Experiencia, Integer> {
       
    //@Query("SELECT e FROM Experiencia e WHERE e.id_usuario =: IDusuarioLogueado")
    //List<Experiencia> buscarExperienciasPorUsuario(@Param("IDusuarioLogueado") Integer IDusuarioLogueado);



}
