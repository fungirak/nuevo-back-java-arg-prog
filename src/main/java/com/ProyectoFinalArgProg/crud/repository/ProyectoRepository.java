
package com.ProyectoFinalArgProg.crud.repository;

import com.ProyectoFinalArgProg.crud.entity.Proyecto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author fungirak
 */

@Repository
public interface ProyectoRepository extends JpaRepository <Proyecto, Integer> {
    
    @Query(value="SELECT * FROM proyecto WHERE id_usuario =:idUsuario" , nativeQuery = true)
    Set<Proyecto> buscarTodosLosProyectosPorIdUsuario(@Param("idUsuario") Integer idUsuario);

    @Query(value="SELECT p FROM proyecto p WHERE p.id_proyecto =:idProyecto AND p.id_usuario =:idUsuario" , nativeQuery = true)
    Optional<Proyecto> findByIdUsuarioAndIdProyecto(@Param("idUsuario") Integer idUsuario, @Param("idProyecto") Integer idProyecto);

    @Query(value="DELETE FROM proyecto p WHERE p.id_proyecto =:idProyecto AND p.id_usuario =:idUsuario" , nativeQuery = true)
    void deleteByIdUsuarioAndIdProyecto(@Param("idUsuario") Integer idUsuario, @Param("idProyecto") Integer idProyecto);

    @Query(value="UPDATE proyecto pro SET pro.titulo =:nuevoTitulo, "
    + "pro.imagen =:nuevaImagen,"
    + "pro.descripcipon =:nuevaDescripcion, "
    + "WHERE pro.id_proyecto =:idProyecto AND pro.id_usuario =:idUsuario"
    , nativeQuery = true)
    Optional<Proyecto> editarByIdUsuarioAndIdProyecto(
        @Param("idProyecto")  Integer idProyecto,
        @Param("idUsuario")  Integer idUsuario,
        @Param("nuevoTitulo") String nuevoTitulo, 
        @Param("nuevaImagen") String nuevaImagen,
        @Param("nuevaDescripcion") String nuevaDescripcion
    );

    @Query(value="SELECT EXISTS("
    + "SELECT 1 FROM proyecto p "
    + "WHERE p.id_proyecto =:idProyecto AND "
    + "p.id_usuario =:idUsuario)"
    , nativeQuery = true)
    Boolean existsByIdUsuarioAndIdProyecto(@Param("idUsuario") Integer idUsuario, @Param("idProyecto") Integer idProyecto);
    

    
    // Control de existencia por Título y Descripción.
    @Query(value="SELECT EXISTS (SELECT * FROM proyecto WHERE id_usuario =:idUsuario AND titulo =:titulo)" , nativeQuery = true)
    Integer tituloExists(@Param("idUsuario") Integer idUsuario, @Param("titulo") String titulo);

    @Query(value="SELECT EXISTS (SELECT * FROM proyecto WHERE id_usuario =:idUsuario AND titulo =:titulo AND descripcion =:descripcion)" , nativeQuery = true)
    Integer tituloAndDescripcionEquals(@Param("idUsuario") Integer idUsuario, @Param("titulo") String titulo, @Param("descripcion") String descripcion);
}
