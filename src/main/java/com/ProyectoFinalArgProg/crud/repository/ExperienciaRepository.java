
package com.ProyectoFinalArgProg.crud.repository;

import com.ProyectoFinalArgProg.crud.entity.Experiencia;

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
public interface ExperienciaRepository extends JpaRepository <Experiencia, Integer> {
    
    @Query(value="SELECT * FROM experiencia WHERE id_usuario =:idUsuario" , nativeQuery = true)
    Set<Experiencia> buscarTodaLaExperienciaPorIdUsuario(Integer idUsuario);

    @Query(value="SELECT e FROM experiencia e WHERE e.id_experiencia =:idExperiencia AND e.id_usuario =:idUsuario" , nativeQuery = true)
    Optional<Experiencia> findByIdUsuarioAndIdExperiencia(Integer idUsuario, @Param("idExperiencia") Integer idExperiencia);

    @Query(value="SELECT * FROM experiencia WHERE id_experiencia =:idExperiencia AND id_usuario =:idUsuario LIMIT 1" , nativeQuery = true)
    Optional<Experiencia> buscarPorIdUsuarioAndIdExperiencia(Integer idUsuario, @Param("idExperiencia") Integer idExperiencia);

    @Query(value="DELETE FROM experiencia e WHERE e.id_experiencia =:idExperiencia AND e.id_usuario =:idUsuario" , nativeQuery = true)
    void deleteByIdUsuarioAndIdExperiencia(Integer idUsuario, @Param("idExperiencia") Integer idExperiencia);

    @Query(value="UPDATE experiencia expe SET expe.empresa =:nuevaEmpresa, "
    + "expe.ubicacion =:nuevaUbicacion,"
    + "expe.puesto =:nuevoPuesto, " 
    + "expe.fechaInicio =:nuevaFechaInicio, " 
    + "expe.fechaFinalizacion =:nuevaFechaFinalizacion, " 
    + "expe.actividades =:nuevoActividades " 
    + "WHERE expe.id_experiencia =:idExperiencia AND expe.id_usuario =:idUsuario"
    , nativeQuery = true)
    Optional<Experiencia> editarByIdUsuarioAndIdExperiencia(
        @Param("idExperiencia")  Integer idExperiencia,
        @Param("idUsuario")  Integer idUsuario,
        @Param("nuevaEmpresa") String nuevaEmpresa, 
        @Param("nuevaUbicacion") String nuevaUbicacion,
        @Param("nuevoPuesto") String nuevoPuesto,
        @Param("nuevaFechaInicio") LocalDate nuevaFechaInicio,
        @Param("nuevaFechaFinalizacion") LocalDate nuevaFechaFinalizacion,
        @Param("nuevoActividades") String nuevoActividades
    );

    @Query(value="SELECT EXISTS("
    + "SELECT 1 FROM experiencia e "
    + "WHERE e.id_experiencia =:idExperiencia AND "
    + "e.id_usuario =:idUsuario)"
    , nativeQuery = true)
    Boolean existsByIdUsuarioAndIdExperiencia(@Param("idUsuario")  Integer idUsuario, @Param("idExperiencia")  Integer idExperiencia);
   

    // Control de existencia por EMPRESA , PUESTO Y FECHA INICIO.
    @Query(value="SELECT EXISTS (SELECT * FROM experiencia WHERE id_usuario =:idUsuario AND empresa =:empresa)" , nativeQuery = true)
    Integer empresaExists(@Param("idUsuario") Integer idUsuario, @Param("empresa") String empresa);

    @Query(value="SELECT EXISTS (SELECT * FROM experiencia WHERE id_usuario =:idUsuario AND puesto =:puesto AND empresa =:empresa)" , nativeQuery = true)
    Integer puestoAndEmpresaEquals(@Param("idUsuario") Integer idUsuario, @Param("puesto") String puesto, @Param("empresa") String empresa);

    @Query(value="SELECT EXISTS (SELECT * FROM experiencia WHERE id_usuario =:idUsuario AND puesto =:puesto AND empresa =:empresa AND fecha_inicio =:fechaInicio)" , nativeQuery = true)
    Integer puestoAndEmpresaAndFechaInicioEquals(@Param("idUsuario") Integer idUsuario, @Param("puesto") String puesto, @Param("empresa") String empresa,  @Param("fechaInicio") LocalDate fechaInicio);
}
