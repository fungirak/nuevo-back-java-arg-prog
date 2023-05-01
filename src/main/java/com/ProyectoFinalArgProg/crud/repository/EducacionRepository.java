
package com.ProyectoFinalArgProg.crud.repository;

import com.ProyectoFinalArgProg.crud.entity.Educacion;

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
public interface EducacionRepository extends JpaRepository<Educacion, Long> {

    @Query(value="SELECT * FROM educacion WHERE id_usuario =:idUsuario" , nativeQuery = true)
    Set<Educacion> buscarTodaLaEducacionPorIdUsuario(@Param("idUsuario") Integer idUsuario);

    @Query(value="SELECT e FROM educacion e WHERE e.educacion =:idEducacion AND e.id_usuario =:idUsuario" , nativeQuery = true)
    Optional<Educacion> findAllByIdEducacionAndIdUsuario(@Param("idEducacion")  Integer idEducacion, @Param("idUsuario")  Integer idUsuario);

    @Query(value="SELECT * FROM educacion WHERE id_educacion =:idEducacion AND id_usuario =:idUsuario LIMIT 1" , nativeQuery = true)
    Optional<Educacion> BuscarUnaPorIdEducacionAndIdUsuario(@Param("idEducacion")  Integer idEducacion, @Param("idUsuario")  Integer idUsuario);

    @Query(value="DELETE FROM educacion e WHERE e.id_educacion =:idEducacion AND e.id_usuario =:idUsuario" , nativeQuery = true)
    Educacion deleteByIdEducacionAndIdUsuario(@Param("idEducacion")  Integer idEducacion, @Param("idUsuario")  Integer idUsuario);

    
    @Query(value="UPDATE educacion edu SET edu.institucion =:nuevaInstitucion, "
    + "edu.titulo =:nuevoTitulo,"
    + "edu.fechaInicio =:nuevaFechaInicio, " 
    + "edu.fechaFinalizacion =:nuevaFechaFinalizacion, " 
    + "edu.estado =:nuevoEstado, " 
    + "edu.detalles =:nuevoDetalle " 
    + "WHERE edu.id_educacion =:idEducacion AND edu.id_usuario =:idUsuario"
    , nativeQuery = true)
    Educacion editarByIdEducacionAndIdUsuario(
        @Param("idEducacion")  Integer idEducacion,
        @Param("idUsuario")  Integer idUsuario,
        @Param("nuevoTitulo") String nuevoTitulo, 
        @Param("nuevaFechaInicio") LocalDate nuevaFechaInicio,
        @Param("nuevaFechaFinalizacion") LocalDate nuevaFechaFinalizacion,
        @Param("nuevoEstado") String nuevoEstado,
        @Param("nuevoDetalle") String nuevoDetalle
    );

    @Query(value="SELECT EXISTS("
    + "SELECT 1 FROM educacion e "
    + "WHERE e.id_educacion =:idEducacion AND "
    + "e.id_usuario =:idUsuario)"
    , nativeQuery = true)
    Boolean existsByIdEducacionAndIdUsuario(@Param("idEducacion")  Integer idEducacion, @Param("idUsuario")  Integer idUsuario);
    

    // Control de existencia por Título e Institución.
    @Query(value="SELECT EXISTS (SELECT * FROM educacion WHERE id_usuario =:idUsuario AND titulo =:titulo)" , nativeQuery = true)
    Integer tituloExists(@Param("idUsuario") Integer idUsuario, @Param("titulo") String titulo);

    @Query(value="SELECT EXISTS (SELECT * FROM educacion WHERE id_usuario =:idUsuario AND titulo =:titulo AND institucion =:institucion)" , nativeQuery = true)
    Integer institucionAndTituloEquals(@Param("idUsuario") Integer idUsuario, @Param("institucion") String institucion, @Param("titulo") String titulo);
}
