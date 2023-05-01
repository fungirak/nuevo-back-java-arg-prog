
package com.ProyectoFinalArgProg.crud.repository.impl;


import com.ProyectoFinalArgProg.crud.entity.Educacion;

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
public interface EducacionRepositoryImpl extends JpaRepository <Educacion, Integer> {
       
    // @Query("SELECT e FROM Educacion e WHERE e.id_usuario =: IDusuarioLogueado")
    // List<Educacion> buscarEducacionPorUsuario(@Param("IDusuarioLogueado") Integer IDusuarioLogueado);

   


}
