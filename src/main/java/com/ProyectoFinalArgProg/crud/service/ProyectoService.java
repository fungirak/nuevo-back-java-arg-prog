
package com.ProyectoFinalArgProg.crud.service;

import com.ProyectoFinalArgProg.crud.entity.Proyecto;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ProyectoFinalArgProg.crud.repository.ProyectoRepository;
import com.ProyectoFinalArgProg.crud.security.entity.Usuario;
import com.ProyectoFinalArgProg.crud.security.repository.UsuarioRepository;

/**
 *
 * @author fungirak
 */

@Service
public class ProyectoService {

    @Autowired
    public ProyectoRepository proyectoRepo;

    @Autowired
    public UsuarioRepository usuarioRepo;

    
    public Set<Proyecto> verProyectos(String nombre_usuario){
        Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
        Integer idUsuario = usuarioAutenticado.getIdUsuario();
      return proyectoRepo.buscarTodosLosProyectosPorIdUsuario(idUsuario);
    }

    public Proyecto buscarProyecto (String nombre_usuario, Integer idProyecto){
        Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
        Integer idUsuario = usuarioAutenticado.getIdUsuario();
      return proyectoRepo.findByIdUsuarioAndIdProyecto(idUsuario, idProyecto).orElse(null);
    }
    
    public void crearProyecto (Proyecto pro, String nombre_usuario){
        Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
        Integer idUsuario = usuarioAutenticado.getIdUsuario();

      pro.setUsuario(usuarioAutenticado);
      proyectoRepo.save(pro);
    }
   
   
    public void borrarProyecto (String nombre_usuario, Integer idProyecto){
        Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
        Integer idUsuario = usuarioAutenticado.getIdUsuario();
      proyectoRepo.deleteByIdUsuarioAndIdProyecto(idUsuario, idProyecto);
    } 

  
    public void editarProyecto (String nombre_usuario, Proyecto pro){
        Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
        Integer idUsuario = usuarioAutenticado.getIdUsuario();

         proyectoRepo.findByIdUsuarioAndIdProyecto(idUsuario, pro.getId())
         .map( editPro -> {
                  editPro.setId(pro.getId());
                  editPro.setTitulo(pro.getTitulo());
                  editPro.setImagen(pro.getImagen());
                  editPro.setDescripcion(pro.getDescripcion());

                  editPro.setUsuario(usuarioAutenticado);
         return proyectoRepo.save(editPro);
      });
        //.orElseGet(() -> {
        //  acercade.setId(id);
        //  return acercadeRepo.save(acercade);
        //});
    }
    
            public Boolean existsProyecto(String nombre_usuario, Integer idProyecto){
              Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
              Integer idUsuario = usuarioAutenticado.getIdUsuario();
              
                try {
                  return proyectoRepo.existsByIdUsuarioAndIdProyecto(idUsuario, idProyecto);
                 } catch(Exception e){
                    return false;
                 }
            }


         // Control de existencia de registro por TÍTULO y DESCRIPCIÓN.

        public Boolean tituloExists(String nombre_usuario, String titulo){
          Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
          Integer idUsuario = usuarioAutenticado.getIdUsuario();

          Boolean titExsts =  proyectoRepo.tituloExists(idUsuario, titulo) == 1 ? true : false;
          return titExsts;
      }


        public Boolean tituloAndDescripcionEquals(String nombre_usuario, String titulo, String descripcion){
          Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
          Integer idUsuario = usuarioAutenticado.getIdUsuario();

          Boolean titAndDesEqu = proyectoRepo.tituloAndDescripcionEquals(idUsuario, titulo, descripcion) == 1 ? true : false;
          return titAndDesEqu;
        }
}
