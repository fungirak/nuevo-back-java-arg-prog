
package com.ProyectoFinalArgProg.crud.service;

import com.ProyectoFinalArgProg.crud.entity.Experiencia;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ProyectoFinalArgProg.crud.repository.ExperienciaRepository;
import com.ProyectoFinalArgProg.crud.security.entity.Usuario;
import com.ProyectoFinalArgProg.crud.security.repository.UsuarioRepository;

/**
 *
 * @author fungirak
 */

@Service
public class ExperienciaService  {

    @Autowired
    public ExperienciaRepository experienciaRepo;

    @Autowired
    public UsuarioRepository usuarioRepo;

   
    public Set<Experiencia> verExperiencias(String nombre_usuario){
        Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
        Integer idUsuario = usuarioAutenticado.getIdUsuario();
      return experienciaRepo.buscarTodaLaExperienciaPorIdUsuario(idUsuario);
    }

    public Experiencia buscarExperiencia(String nombre_usuario, Integer idExperiencia){
        Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
        Integer idUsuario = usuarioAutenticado.getIdUsuario();
      return experienciaRepo.buscarPorIdUsuarioAndIdExperiencia(idUsuario, idExperiencia).orElse(null);
    }
   
    public void crearExperiencia(Experiencia expe, String nombre_usuario){
        Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
        Integer idUsuario = usuarioAutenticado.getIdUsuario();

      expe.setUsuario(usuarioAutenticado);
      experienciaRepo.save(expe);
    }
   
   
    public void borrarExperiencia (String nombre_usuario, Integer idExperiencia){
        Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
        Integer idUsuario = usuarioAutenticado.getIdUsuario();
      experienciaRepo.deleteByIdUsuarioAndIdExperiencia(idUsuario, idExperiencia);
    } 
    
   
    public void editarExperiencia (String nombre_usuario, Experiencia expe){
        Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
        Integer idUsuario = usuarioAutenticado.getIdUsuario();

         experienciaRepo.findByIdUsuarioAndIdExperiencia(idUsuario, expe.getId())
         .map( editExpe -> {
                editExpe.setId(expe.getId());
                editExpe.setEmpresa(expe.getEmpresa());
                editExpe.setUbicacion(expe.getUbicacion());
                editExpe.setPuesto(expe.getPuesto());
                editExpe.setFechaInicio(expe.getFechaInicio());
                editExpe.setFechaFinalizacion(expe.getFechaFinalizacion());
                editExpe.setActividades(expe.getActividades());

                editExpe.setUsuario(usuarioAutenticado);
         return experienciaRepo.save(editExpe);
      });
        //.orElseGet(() -> {
        //  acercade.setId(id);
        //  return acercadeRepo.save(acercade);
        //});
    }

       
      public Boolean existsExperiencia(String nombre_usuario, Integer idExperiencia){
          Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
          Integer idUsuario = usuarioAutenticado.getIdUsuario();
          
                try {
                  return experienciaRepo.existsByIdUsuarioAndIdExperiencia(idUsuario, idExperiencia);
                 } catch(Exception e){
                    return false;
                 }
           }


    // Control de existencia de registro por EMPRESA, PUESTO y FECHA INICIO.

    public Boolean empresaExists(String nombre_usuario, String empresa){
      Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
      Integer idUsuario = usuarioAutenticado.getIdUsuario();

      Boolean empExsts =  experienciaRepo.empresaExists(idUsuario, empresa) == 1 ? true : false;
      return empExsts;
  }


    public Boolean puestoAndEmpresaEquals(String nombre_usuario, String puesto, String empresa){
      Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
      Integer idUsuario = usuarioAutenticado.getIdUsuario();

      Boolean puestoAndEmpresaEqu = experienciaRepo.puestoAndEmpresaEquals(idUsuario, puesto, empresa) == 1 ? true : false;
      return puestoAndEmpresaEqu;
    }


    public Boolean puestoAndEmpresaAndFechaInicioEquals(String nombre_usuario, String puesto, String empresa, LocalDate fechaInicio){
      Usuario usuarioAutenticado = usuarioRepo.findByNombreUsuario(nombre_usuario);
      Integer idUsuario = usuarioAutenticado.getIdUsuario();

      Boolean puestoAndEmpresaAndFechaInicioEqu = experienciaRepo.puestoAndEmpresaAndFechaInicioEquals(idUsuario, puesto, empresa, fechaInicio) == 1 ? true : false;
      return puestoAndEmpresaAndFechaInicioEqu;
    }
}
