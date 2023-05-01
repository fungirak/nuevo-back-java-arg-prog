
package com.ProyectoFinalArgProg.crud.repository.impl;


import com.ProyectoFinalArgProg.crud.entity.Proyecto;

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
public interface ProyectoRepositoryImpl extends JpaRepository <Proyecto, Integer> {
       
    //@Query("SELECT p FROM Proyecto p WHERE p.id_usuario =: IDusuarioLogueado")
    //List<Proyecto> buscarProyectosPorUsuario(@Param("IDusuarioLogueado") Integer IDusuarioLogueado);



}
