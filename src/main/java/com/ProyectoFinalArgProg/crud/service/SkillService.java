
package com.ProyectoFinalArgProg.crud.service;

import com.ProyectoFinalArgProg.crud.entity.Skill;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ProyectoFinalArgProg.crud.repository.SkillRepository;
import com.ProyectoFinalArgProg.crud.security.entity.Usuario;
import com.ProyectoFinalArgProg.crud.security.repository.UsuarioRepository;

/**
 *
 * @author fungirak
 */

@Service
public class SkillService  {

    @Autowired
    public SkillRepository skillRepo;

    @Autowired
    public UsuarioRepository usuarioRepo;

    
    public Set<Skill> verSkills (String nombre_usuario){
        Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
        Integer idUsuario = usuarioAutenticado.getIdUsuario();
      return skillRepo.buscarTodasLasSkillsPorIdUsuario(idUsuario);
    }

    public Skill buscarSkill(String nombre_usuario, Integer idSkill){
        Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
        Integer idUsuario = usuarioAutenticado.getIdUsuario();
      return skillRepo.findByIdUsuarioAndIdSkill(idUsuario, idSkill).orElse(null);
    }

  
    public void crearSkill (Skill sk, String nombre_usuario){
        Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
        Integer idUsuario = usuarioAutenticado.getIdUsuario();
      
      sk.setUsuario(usuarioAutenticado);
      skillRepo.save(sk);
    }
   
    
    public void borrarSkill (String nombre_usuario, Integer idSkill){
        Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
        Integer idUsuario = usuarioAutenticado.getIdUsuario();
      skillRepo.deleteByIdUsuarioAndIdSkill(idUsuario, idSkill);
    } 

    
   
    public void editarSkill (String nombre_usuario, Skill sk){
        Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
        Integer idUsuario = usuarioAutenticado.getIdUsuario();

         skillRepo.findByIdUsuarioAndIdSkill(idUsuario, sk.getId())
         .map( editSk -> {
                editSk.setId(sk.getId());
                editSk.setTecnologia(sk.getTecnologia());
                editSk.setImagen(sk.getImagen());

                editSk.setUsuario(usuarioAutenticado);
         return skillRepo.save(editSk);
      });
        //.orElseGet(() -> {
        //  acercade.setId(id);
        //  return acercadeRepo.save(acercade);
        //});
    }

        
            public Boolean existsSkill(String nombre_usuario, Integer idSkill){
                Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
                Integer idUsuario = usuarioAutenticado.getIdUsuario();

                try {
                  return skillRepo.existsByIdUsuarioAndIdSkill(idUsuario, idSkill);
                 } catch(Exception e){
                    return false;
                 }
           }

        
       // Control de existencia de registro por TECNOLOGÍA.

        public Boolean tecnologiaExists(String nombre_usuario, String titulo){
          Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
          Integer idUsuario = usuarioAutenticado.getIdUsuario();

          Boolean tecExsts =  skillRepo.tecnologiaExists(idUsuario, titulo) == 1 ? true : false;
          return tecExsts;
      }
}
