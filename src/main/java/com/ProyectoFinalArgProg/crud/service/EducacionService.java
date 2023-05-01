
package com.ProyectoFinalArgProg.crud.service;

import com.ProyectoFinalArgProg.crud.entity.Educacion;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ProyectoFinalArgProg.crud.repository.EducacionRepository;
import com.ProyectoFinalArgProg.crud.security.entity.Usuario;
import com.ProyectoFinalArgProg.crud.security.repository.UsuarioRepository;

/**
 *
 * @author fungirak
 */

@Service
public class EducacionService  {

    @Autowired
    public EducacionRepository educacionRepo;

    @Autowired
    public UsuarioRepository usuarioRepo;

   
    public Set<Educacion> verEducacion(String nombre_usuario){
        Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
        Integer idUsuario = usuarioAutenticado.getIdUsuario();
      return educacionRepo.buscarTodaLaEducacionPorIdUsuario(idUsuario);
    }

    public Educacion buscarEducacion(String nombre_usuario, Integer idEducacion){
        Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
        Integer idUsuario = usuarioAutenticado.getIdUsuario();
      return educacionRepo.BuscarUnaPorIdEducacionAndIdUsuario(idEducacion, idUsuario).orElse(null);
    }

   
    public void crearEducacion(Educacion edu, String nombre_usuario){
        Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
        Integer idUsuario = usuarioAutenticado.getIdUsuario();  
        
      edu.setUsuario(usuarioAutenticado);
      educacionRepo.save(edu);
    }
   
   
    public void borrarEducacion (String nombre_usuario, Integer idEducacion){
        Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
        Integer idUsuario = usuarioAutenticado.getIdUsuario();
      educacionRepo.deleteByIdEducacionAndIdUsuario(idEducacion, idUsuario);
    } 
    
    
    public void editarEducacion (String nombre_usuario, Educacion edu){
        Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
        Integer idUsuario = usuarioAutenticado.getIdUsuario();

         educacionRepo.findAllByIdEducacionAndIdUsuario(edu.getId(), idUsuario)
         .map( editEdu -> {
                  editEdu.setId(edu.getId());
                  editEdu.setInstitucion(edu.getInstitucion());
                  editEdu.setTitulo(edu.getTitulo());
                  editEdu.setFechaInicio(edu.getFechaInicio());
                  editEdu.setFechaFinalizacion(edu.getFechaFinalizacion());
                  editEdu.setEstado(edu.getEstado());
                  editEdu.setDetalles(edu.getDetalles());

                  editEdu.setUsuario(usuarioAutenticado);
         return educacionRepo.save(editEdu);
      });
        //.orElseGet(() -> {
        //  acercade.setId(id);
        //  return acercadeRepo.save(acercade);
        //});
    }

     
            public Boolean existsEducacion(String nombre_usuario, Integer idEducacion){
                Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
                Integer idUsuario = usuarioAutenticado.getIdUsuario();

                try {
                  return educacionRepo.existsByIdEducacionAndIdUsuario( idEducacion, idUsuario);
                 } catch(Exception e){
                     return false;
                 }
            }


        // Control de existencia de registro por TÍTULO e INSTITUCIÓN.

        public Boolean tituloExists(String nombre_usuario, String titulo){
          Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
          Integer idUsuario = usuarioAutenticado.getIdUsuario();

          Boolean titExsts =  educacionRepo.tituloExists(idUsuario, titulo) == 1 ? true : false;
          return titExsts;
      }


        public Boolean institucionAndTituloEquals(String nombre_usuario, String institucion, String titulo){
          Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
          Integer idUsuario = usuarioAutenticado.getIdUsuario();

          Boolean institDifTitEqu = educacionRepo.institucionAndTituloEquals(idUsuario, institucion, titulo) == 1 ? true : false;
          return institDifTitEqu;
        }

        
      
    
}
