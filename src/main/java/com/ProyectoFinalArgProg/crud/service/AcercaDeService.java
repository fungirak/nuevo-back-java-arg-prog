
package com.ProyectoFinalArgProg.crud.service;

import com.ProyectoFinalArgProg.crud.entity.AcercaDe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.ProyectoFinalArgProg.crud.repository.AcercaDeRepository;
import com.ProyectoFinalArgProg.crud.security.entity.Usuario;
import com.ProyectoFinalArgProg.crud.security.repository.UsuarioRepository;


/**
 *
 * @author fungirak
 */

@Service
public class AcercaDeService  {

    @Autowired
    public AcercaDeRepository acercadeRepo;

    @Autowired
    public UsuarioRepository usuarioRepo;


    public AcercaDe verAcercaDe(String nombre_usuario){
        Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
        Integer idUsuario = usuarioAutenticado.getIdUsuario();

      AcercaDe acercaDeDevuelto =  acercadeRepo.buscarAcercaDePorIdUsuario(idUsuario);
      if(acercaDeDevuelto == null ){
        acercaDeDevuelto = new AcercaDe();
      }

      return acercaDeDevuelto ;
    }

    public AcercaDe verAcercaDe(String nombre_usuario, Integer idAcercaDe){
      Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
      Integer idUsuario = usuarioAutenticado.getIdUsuario();

    AcercaDe acercaDeDevuelto =  acercadeRepo.buscarAcercaDePorIdUsuarioAndId(idUsuario, idAcercaDe);
    if(acercaDeDevuelto == null ){
      acercaDeDevuelto = new AcercaDe();
    }

    return acercaDeDevuelto ;
  }

    

    public void crearAcercaDe(AcercaDe acercade, String nombre_usuario){
      Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
      Integer idUsuarioAutenticado = usuarioAutenticado.getIdUsuario();

        // Se procede a crear el acercaDe.
        acercade.setUsuario(usuarioAutenticado);
        acercadeRepo.save(acercade);
      }
     
     
    

   
    
    public void borrarAcercaDe (String nombre_usuario, Integer idAcercaDe){
      Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
      Integer idUsuario = usuarioAutenticado.getIdUsuario();

      acercadeRepo.deleteByIdAcercaDeAndIdUsuario(idAcercaDe, idUsuario);
    } 

    public AcercaDe actualizarAcercaDe(AcercaDe ad){
      return acercadeRepo.save(ad);
    }

   
    public AcercaDe editarAcercaDe (String nombre_usuario, AcercaDe acercade){
      Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
      Integer idUsuario = usuarioAutenticado.getIdUsuario();

      Integer idAcercaDe = acercade.getId();

      AcercaDe acercaDeUpdate =  acercadeRepo.buscarAcercaDePorIdUsuarioAndId(idUsuario, idAcercaDe);
                     //acercaDeUpdate.setId(acercade.getId());
                     acercaDeUpdate.setFullname(acercade.getFullname());
                     acercaDeUpdate.setPosicion(acercade.getPosicion());
                     acercaDeUpdate.setDescripcion(acercade.getDescripcion());
                     acercaDeUpdate.setUsuario(usuarioAutenticado);

             AcercaDe acercaDeActualizado =  acercadeRepo.save(acercaDeUpdate);

              return acercaDeActualizado;
      };
        //.orElseGet(() -> {
        //  acercade.setId(id);
        //  return acercadeRepo.save(acercade);
        //});
    
    

      
           
           public Boolean existsAcercaDe(String nombre_usuario, Integer idAcercaDe){
            Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
            Integer idUsuario = usuarioAutenticado.getIdUsuario();

                try {
                  return acercadeRepo.existsByIdAcercaDeAndIdUsuario(idAcercaDe, idUsuario);
                 } catch(Exception e){
                    return false;
                 }
           }

          

          

}
