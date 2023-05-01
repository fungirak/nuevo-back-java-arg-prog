
package com.ProyectoFinalArgProg.crud.repository.impl;


import com.ProyectoFinalArgProg.crud.entity.AcercaDe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author fungirak
 */

@Repository
public interface AcercaDeRepositoryImpl extends JpaRepository <AcercaDe, Integer> {
     
    /* 
    @Query("SELECT a FROM AcercaDe a WHERE a.usuario =: IDusuarioLogueado")
    AcercaDe buscarAcercaDePorUsuario(@Param("IDusuarioLogueado") Integer IDusuarioLogueado);

     
    @Query("UPDATE")
    AcercaDe actualizarAcercaDePorUsuario();

    @Query("DELETE")
    AcercaDe borrarAcercaDePorUsuario();
    */


}
