
package com.ProyectoFinalArgProg.crud.repository;


import com.ProyectoFinalArgProg.crud.entity.AcercaDe;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author fungirak
 */

@Repository
public interface AcercaDeRepository extends JpaRepository <AcercaDe, Integer> {
       
    @Query(value="SELECT * FROM acerca_de WHERE id_usuario =:idUsuario LIMIT 1" , nativeQuery = true)
    AcercaDe buscarAcercaDePorIdUsuario(@Param("idUsuario") Integer idUsuario);
    
    @Query(value="SELECT * FROM acerca_de WHERE id_usuario =:idUsuario AND id_acerca_de =:idAcercaDe LIMIT 1" , nativeQuery = true)
    AcercaDe buscarAcercaDePorIdUsuarioAndId(@Param("idUsuario") Integer idUsuario, @Param("idAcercaDe") Integer idAcercaDe);

    @Query(value="DELETE FROM acerca_de a WHERE a.id_acerca_de =:idAcercaDe AND a.id_usuario =:idUsuario" , nativeQuery = true)
    void deleteByIdAcercaDeAndIdUsuario(@Param("idAcercaDe") Integer idAcercaDe, @Param("idUsuario") Integer idUsuario);


    @Query(value="UPDATE acerca_de ad SET ad.fullname =:nuevoFullname, "
               + "ad.posicion =:nuevaPosicion,"
               + "ad.descripcion =:nuevaDescripcion " 
               + "WHERE ad.id_acerca_de =:idAcercaDe AND ad.id_usuario =:idUsuario"
     , nativeQuery = true)
    AcercaDe editarByIdAcercaDeAndIdUsuario(
        @Param("idAcercaDe") Integer idAcercaDe, 
        @Param("idUsuario") Integer idUsuario,
        @Param("nuevoFullname") String nuevoFullname,
        @Param("nuevaPosicion") String nuevaPosicion,
        @Param("nuevaDescripcion") String nuevaDescripcion
    );


    @Query(value="SELECT EXISTS("
               + "SELECT 1 FROM acerca_de ac "
               + "WHERE ac.id_acerca_de =:idAcercaDe AND "
               + "ac.id_usuario =:idUsuario)"
        , nativeQuery = true)
    Boolean existsByIdAcercaDeAndIdUsuario(@Param("idAcercaDe") Integer idAcercaDe, @Param("idUsuario") Integer idUsuario);
    
}
