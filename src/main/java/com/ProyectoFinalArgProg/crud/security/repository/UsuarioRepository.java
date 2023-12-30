package com.ProyectoFinalArgProg.crud.security.repository;

import com.ProyectoFinalArgProg.crud.entity.AcercaDe;
import com.ProyectoFinalArgProg.crud.entity.Educacion;
import com.ProyectoFinalArgProg.crud.entity.Experiencia;
import com.ProyectoFinalArgProg.crud.entity.Proyecto;
import com.ProyectoFinalArgProg.crud.entity.Skill;
import com.ProyectoFinalArgProg.crud.security.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
   
   
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByEmail(String email);
   
    //@Query(value="SELECT u FROM usuario u WHERE u.id_usuario =: id", nativeQuery = true)
    //Optional<Usuario> findById(@Param("id") Integer id);
    Optional<Usuario> findById(Integer id);

    
    Usuario findByNombreUsuario(String nombreUsuario);
    Set<Educacion> findEducacionByNombreUsuario(String usuarioLogueado);
    Set<Experiencia> findExperienciaByNombreUsuario(String usuarioLogueado);
    Set<Skill> findSkillByNombre(String usuarioLogueado);
    Set<Proyecto> findProyectoByNombreUsuario(String usuarioLogueado);


    /*RECUPERAR DATOS DE PORTAFOLIO POR USUARIO*/
    @Query(value="SELECT a FROM acerca_de a WHERE a.id_usuario =: idUsuario LIMIT 1", nativeQuery = true)
    AcercaDe cargarAcercaDe(@Param("idUsuario") Integer idUsuario);

    @Query(value="SELECT edu FROM educacion edu WHERE edu.id_usuario =: idUsuario", nativeQuery = true)
    Set<Educacion> cargarEducacion(@Param("idUsuario") Integer idUsuario);

    @Query(value="SELECT exp FROM experiencia exp WHERE exp.id_usuario =: idUsuario", nativeQuery = true)
    Set<Experiencia> cargarExperiencias(@Param("idUsuario") Integer idUsuario);

    @Query(value="SELECT p FROM proyecto p WHERE p.id_usuario =: idUsuario", nativeQuery = true)
    Set<Proyecto> cargarProyectos(@Param("idUsuario") Integer idUsuario);

    @Query(value="SELECT s FROM skill s WHERE s.id_usuario =: idUsuario", nativeQuery = true)
    Set<Skill> cargarSkills(@Param("idUsuario") Integer idUsuario);


}
