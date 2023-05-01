
package com.ProyectoFinalArgProg.crud.controller;

import com.ProyectoFinalArgProg.crud.dto.Mensaje;
import com.ProyectoFinalArgProg.crud.entity.Experiencia;
import com.ProyectoFinalArgProg.crud.security.service.UsuarioService;
import com.ProyectoFinalArgProg.crud.dto.ExperienciaDto;
import com.ProyectoFinalArgProg.crud.service.ExperienciaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author fungirak
 */

@RestController
@RequestMapping("/api/v1/portafolio")
@CrossOrigin(origins = "*")
public class ExperienciaController {
    


//-------------------------- EXPERIENCIA CONTROLLER ----------------------------------
    @Autowired
    private ExperienciaService expeServ;
    private UsuarioService usrSrv;

    private static final Logger logger = LoggerFactory.getLogger(ExperienciaController.class);

    //@PreAuthorize("hasRole('USER')")
        @GetMapping("/{nombre_usuario}/experiencias")
            public ResponseEntity<List<Experiencia>> list(@PathVariable String nombre_usuario){
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		        String nombreUsuarioLogueado = authentication.getName();

                logger.info("CARGANDO 'EXPERIENCIAS' DE USUARIO. MOSTRANDO DATOS DE OBJETO AUTHENTICATION {}", authentication);

                if(nombre_usuario.equals(nombreUsuarioLogueado)){
                    logger.info("USUARIO AUTENTICADO {}", nombreUsuarioLogueado);

                    Set<Experiencia> setExperiencia = expeServ.verExperiencias(nombre_usuario);
                    return new ResponseEntity(setExperiencia, HttpStatus.OK);
                }else{
                    logger.info("USUARIO NO AUTENTICADO {}", nombreUsuarioLogueado);
                    throw new AccessDeniedException("NO TENÉS AUTORIZACIÓN PARA REALIZAR ESTA PETICIÓN.");
                }
            }


    //@PreAuthorize("hasRole('USER')")
        @GetMapping("/{nombre_usuario}/experiencias/{id}")
            public ResponseEntity<Experiencia> getById(@PathVariable String nombre_usuario, @PathVariable("id") Integer idExperiencia){
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String nombreUsuarioLogueado = authentication.getName();

            logger.info("CARGANDO 'UNA EXPERIENCIA' DE USUARIO. MOSTRANDO DATOS DE OBJETO AUTHENTICATION {}", authentication);

            if(nombre_usuario.equals(nombreUsuarioLogueado)){
                logger.info("USUARIO AUTENTICADO {}", nombreUsuarioLogueado);

                Experiencia experiencia = expeServ.buscarExperiencia(nombre_usuario, idExperiencia);
                return new ResponseEntity(experiencia, HttpStatus.OK);
            }else{
                logger.info("USUARIO NO AUTENTICADO {}", nombreUsuarioLogueado);
                throw new AccessDeniedException("NO TENÉS AUTORIZACIÓN PARA REALIZAR ESTA PETICIÓN.");
            }
            }
    

    @PreAuthorize("hasRole('USER')")
        @PostMapping("/{nombre_usuario}/experiencias")
        public ResponseEntity<?> create(@PathVariable String nombre_usuario, @RequestBody ExperienciaDto expeDto){

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String nombreUsuarioLogueado = authentication.getName();


            if(nombre_usuario.equals(nombreUsuarioLogueado)){
                logger.info("USUARIO AUTENTICADO {}", nombreUsuarioLogueado);
                // (empresa-puesto-fechaInicio) tiene que ser único para cada nueva experiencia creada.

                Boolean empresaExists = expeServ.empresaExists(nombre_usuario, expeDto.getEmpresa());

                if(!empresaExists){
                    // creo uno porque la empresa es nueva.
                    Experiencia expeNuevo = new Experiencia( 
                        expeDto.getEmpresa(), 
                        expeDto.getUbicacion(), 
                        expeDto.getPuesto(), 
                        expeDto.getFechaInicio(),
                        expeDto.getFechaFinalizacion(), 
                        expeDto.getActividades() 
                    );

                    expeServ.crearExperiencia(expeNuevo, nombre_usuario);
                    return new ResponseEntity(new Mensaje("Nueva Experiencia creada"), HttpStatus.OK);

                }else{
                    // Si quiere guardar una misma Empresa, chequear que el puesto sea diferente.
                    Boolean puestoAndEmpresaEquals = expeServ.puestoAndEmpresaEquals(nombre_usuario, expeDto.getPuesto(), expeDto.getEmpresa());

                    if(!puestoAndEmpresaEquals){

                        Experiencia expeNuevo = new Experiencia( 
                            expeDto.getEmpresa(), 
                            expeDto.getUbicacion(), 
                            expeDto.getPuesto(), 
                            expeDto.getFechaInicio(),
                            expeDto.getFechaFinalizacion(), 
                            expeDto.getActividades() 
                        );
    
                        expeServ.crearExperiencia(expeNuevo, nombre_usuario);
                        return new ResponseEntity(new Mensaje("Nueva Experiencia creada, misma empresa, puesto diferente."), HttpStatus.OK);

                    }else{
                         // ya tiene un una experiencia con una empresa y puesto igual. Chequear FechaInicio.
                         Boolean puestoAndEmpresaAndFechaInicioEquals = expeServ.puestoAndEmpresaAndFechaInicioEquals(nombre_usuario, expeDto.getPuesto(), expeDto.getEmpresa(), expeDto.getFechaInicio());
                         
                         if(!puestoAndEmpresaAndFechaInicioEquals){
                                Experiencia expeNuevo = new Experiencia( 
                                expeDto.getEmpresa(), 
                                expeDto.getUbicacion(), 
                                expeDto.getPuesto(), 
                                expeDto.getFechaInicio(),
                                expeDto.getFechaFinalizacion(), 
                                expeDto.getActividades() 
                                );
    
                                expeServ.crearExperiencia(expeNuevo, nombre_usuario);
                           
                                return new ResponseEntity(new Mensaje("Nueva Experiencia creada, misma empresa, mismo puesto, fecha de Inicio diferente."), HttpStatus.OK);
                            }else{
                                return new ResponseEntity(new Mensaje("No guardado: Item Experiencia ya registrado (Empresa-Puesto-FechaInicio)."), HttpStatus.OK);
                            }
                            
                        }
                    }

                }else{
                    logger.info("USUARIO NO AUTENTICADO {}", nombreUsuarioLogueado);
                    throw new AccessDeniedException("NO TENÉS AUTORIZACIÓN PARA REALIZAR ESTA PETICIÓN.");
            }   
        }
        
        


    @PreAuthorize("hasRole('USER')")
        @DeleteMapping("/{nombre_usuario}/experiencias/{id}")
        public ResponseEntity<?> delete(@PathVariable String nombre_usuario, @PathVariable("id") Integer idExperiencia){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String nombreUsuarioLogueado = authentication.getName();


            if(nombre_usuario.equals(nombreUsuarioLogueado)){
                logger.info("USUARIO AUTENTICADO {}", nombreUsuarioLogueado);
                    Boolean existeExperienciaBuscado = expeServ.existsExperiencia(nombre_usuario, idExperiencia);
                    if(existeExperienciaBuscado){
                        expeServ.borrarExperiencia(nombre_usuario, idExperiencia);
                        return new ResponseEntity(new Mensaje("Item Experiencia eliminado."), HttpStatus.OK);
                    }else{
                        return new ResponseEntity(new Mensaje("No existe el Item buscado."), HttpStatus.NOT_FOUND);
                    }

            }else{
                logger.info("USUARIO NO AUTENTICADO {}", nombreUsuarioLogueado);
                throw new AccessDeniedException("NO TENÉS AUTORIZACIÓN PARA REALIZAR ESTA PETICIÓN.");
            }
        }


    @PreAuthorize("hasRole('USER')")
        @PutMapping("/{nombre_usuario}/experiencias/{id}")
        public ResponseEntity<?> update(@PathVariable String nombre_usuario, @PathVariable("id") Integer idExperiencia, @RequestBody ExperienciaDto expeDto){

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String nombreUsuarioLogueado = authentication.getName();

            Boolean existeExperienciaBuscado = expeServ.existsExperiencia(nombre_usuario, idExperiencia);

            if(!existeExperienciaBuscado){
                return new ResponseEntity(new Mensaje("No existe el item experiencia buscado."), HttpStatus.NOT_FOUND);
            }else{
                if(StringUtils.isBlank(expeDto.getEmpresa()))
                    return new ResponseEntity(new Mensaje("Ingresar la Empresa es obligatorio."), HttpStatus.BAD_REQUEST);
                if(StringUtils.isBlank(expeDto.getUbicacion()))
                    return new ResponseEntity(new Mensaje("Ingresar la Ubicacion es obligatorio."), HttpStatus.BAD_REQUEST);
                if(StringUtils.isBlank(expeDto.getPuesto()))
                    return new ResponseEntity(new Mensaje("Ingresar el Puesto es obligatorio."), HttpStatus.BAD_REQUEST);
                if(expeDto.getFechaInicio() == null)
                    return new ResponseEntity(new Mensaje("Ingresar la Fecha de Inicio es obligatorio."), HttpStatus.BAD_REQUEST);
                if(StringUtils.isBlank(expeDto.getActividades()))
                    return new ResponseEntity(new Mensaje("Ingresar Actividades  es obligatorio."), HttpStatus.BAD_REQUEST);
            }
            
            

            Experiencia expeEdit = expeServ.buscarExperiencia(nombre_usuario, idExperiencia);
            expeEdit.setEmpresa(expeDto.getEmpresa());
            expeEdit.setUbicacion(expeDto.getUbicacion());
            expeEdit.setPuesto(expeDto.getPuesto());
            expeEdit.setFechaInicio(expeDto.getFechaInicio());
            expeEdit.setFechaFinalizacion(expeDto.getFechaFinalizacion());
            expeEdit.setActividades(expeDto.getActividades());
            expeServ.crearExperiencia(expeEdit, nombre_usuario);
            return new ResponseEntity(new Mensaje("Item Experiencia actualizado."), HttpStatus.OK);
        }
    
  //-------------------------- FIN EXPERIENCIA CONTROLLER ----------------------------------
 
  // ************************************************************************************




}
