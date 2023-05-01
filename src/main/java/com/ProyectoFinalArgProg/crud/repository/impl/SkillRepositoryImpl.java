
package com.ProyectoFinalArgProg.crud.repository.impl;


import com.ProyectoFinalArgProg.crud.entity.Skill;

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
public interface SkillRepositoryImpl extends JpaRepository <Skill, Integer> {
       
    //@Query("SELECT sk FROM Skill sk WHERE sk.id_usuario =: IDusuarioLogueado")
    //List<Skill> buscarSkillsPorUsuario(@Param("IDusuarioLogueado") Integer IDusuarioLogueado);



}
