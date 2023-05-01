
package com.ProyectoFinalArgProg.crud.repository;

import com.ProyectoFinalArgProg.crud.entity.Skill;

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
public interface SkillRepository extends JpaRepository <Skill, Integer> {
    
    @Query(value="SELECT * FROM skill WHERE id_usuario =:idUsuario" , nativeQuery = true)
    Set<Skill> buscarTodasLasSkillsPorIdUsuario(@Param("idUsuario") Integer idUsuario);

    @Query(value="SELECT s FROM skill s WHERE s.id_skill =:idSkill AND a.id_usuario =:idUsuario" , nativeQuery = true)
    Optional<Skill> findByIdUsuarioAndIdSkill(@Param("idUsuario") Integer idUsuario, @Param("idSkill") Integer idSkill);

    @Query(value="DELETE FROM skill s WHERE s.id_skill =:idSkill AND s.id_usuario =:idUsuario" , nativeQuery = true)
    void deleteByIdUsuarioAndIdSkill(@Param("idUsuario") Integer idUsuario, @Param("idSkill") Integer idSkill);

    @Query(value="UPDATE skill sk SET sk.tecnologia =:nuevaTecnologia, "
    + "sk.imagen =:nuevaImagen,"
    + "WHERE sk.id_skill =:idSkill AND sk.id_usuario =:idUsuario"
    , nativeQuery = true)
    Optional<Skill> editarByIdUsuarioAndIdSkill(
        @Param("idSkill")  Integer idSkill,
        @Param("idUsuario")  Integer idUsuario,
        @Param("nuevaTecnologia") String nuevaTecnologia, 
        @Param("nuevaImagen") String nuevaImagen
    );

    @Query(value="SELECT EXISTS("
    + "SELECT 1 FROM skill s "
    + "WHERE s.id_skill =:idSkill AND "
    + "s.id_usuario =:idUsuario)"
    , nativeQuery = true)
    Boolean existsByIdUsuarioAndIdSkill(@Param("idUsuario") Integer idUsuario, @Param("idSkill") Integer idSkill);


    // Control de existencia por Tecnología.
    @Query(value="SELECT EXISTS (SELECT * FROM skill WHERE id_usuario =:idUsuario AND tecnologia =:tecnologia)" , nativeQuery = true)
    Integer tecnologiaExists(@Param("idUsuario") Integer idUsuario, @Param("tecnologia") String tecnologia);


}
