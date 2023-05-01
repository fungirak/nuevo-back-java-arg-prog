
package com.ProyectoFinalArgProg.crud.controller;

import com.ProyectoFinalArgProg.crud.dto.Mensaje;
import com.ProyectoFinalArgProg.crud.entity.Educacion;
import com.ProyectoFinalArgProg.crud.security.entity.Usuario;
import com.ProyectoFinalArgProg.crud.security.service.UsuarioService;
import com.ProyectoFinalArgProg.crud.dto.EducacionDto;
import com.ProyectoFinalArgProg.crud.service.EducacionService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.security.access.AccessDeniedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author fungirak
 */
@RestController
@RequestMapping("/api/v1/portafolio")
@CrossOrigin(origins = "*")
public class EducacionController {
    
    
//-------------------------- EDUCACION CONTROLLER ----------------------------------
    @Autowired
    private EducacionService eduServ;
    private UsuarioService usrSrv;

    private static final Logger logger = LoggerFactory.getLogger(EducacionController.class);

    //@PreAuthorize("hasRole('USER')")
        @GetMapping("/{nombre_usuario}/educacion")
            public ResponseEntity<List<Educacion>> list(@PathVariable String nombre_usuario){
                
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		        String nombreUsuarioLogueado = authentication.getName();

                logger.info("CARGANDO 'EDUCACION' DE USUARIO. MOSTRANDO DATOS DE OBJETO AUTHENTICATION {}", authentication);

                if(nombre_usuario.equals(nombreUsuarioLogueado)){
                    logger.info("USUARIO AUTENTICADO {}", nombreUsuarioLogueado);

                    Set<Educacion> setEducacion = eduServ.verEducacion(nombre_usuario);
                    return new ResponseEntity(setEducacion, HttpStatus.OK);
                }else{
                    logger.info("USUARIO NO AUTENTICADO {}", nombreUsuarioLogueado);
                    throw new AccessDeniedException("NO TENÉS AUTORIZACIÓN PARA REALIZAR ESTA PETICIÓN.");
                }
            }


    //@PreAuthorize("hasRole('USER')")
        @GetMapping("/{nombre_usuario}/educacion/{id}")
            public ResponseEntity<Educacion> getById(@PathVariable String nombre_usuario, @PathVariable("id") Integer idEducacion){

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String nombreUsuarioLogueado = authentication.getName();

            logger.info("CARGANDO 'UNA EDUCACION' DE USUARIO. MOSTRANDO DATOS DE OBJETO AUTHENTICATION {}", authentication);

            if(nombre_usuario.equals(nombreUsuarioLogueado)){
                logger.info("USUARIO AUTENTICADO {}", nombreUsuarioLogueado);

               Educacion educacion = eduServ.buscarEducacion(nombre_usuario, idEducacion);
                return new ResponseEntity(educacion, HttpStatus.OK);
            }else{
                logger.info("USUARIO NO AUTENTICADO {}", nombreUsuarioLogueado);
                throw new AccessDeniedException("NO TENÉS AUTORIZACIÓN PARA REALIZAR ESTA PETICIÓN.");
            }

            }
    

    @PreAuthorize("hasRole('USER')")
        @PostMapping("/{nombre_usuario}/educacion")
        public ResponseEntity<?> create(@PathVariable String nombre_usuario, @RequestBody EducacionDto eduDto){
            
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String nombreUsuarioLogueado = authentication.getName();


            if(nombre_usuario.equals(nombreUsuarioLogueado)){
                logger.info("USUARIO AUTENTICADO {}", nombreUsuarioLogueado);
                // (título-institución) tiene que ser único para cada nueva educación creada.
                   
                    Boolean tituloExists = eduServ.tituloExists(nombre_usuario, eduDto.getTitulo());

                    if(!tituloExists){
                        // creo uno porque el titulo es nuevo.
                        Educacion eduNuevo = new Educacion(
                            eduDto.getInstitucion(), 
                            eduDto.getTitulo(), 
                            eduDto.getFechaInicio(),
                            eduDto.getFechaFinalizacion(), 
                            eduDto.getEstado(), 
                            eduDto.getDetalles() 
                            );

                        eduServ.crearEducacion(eduNuevo, nombre_usuario);
                        return new ResponseEntity(new Mensaje("Nuevo Item Educacion creado"), HttpStatus.OK);
                        
                    }else{
                        // Si quiere guardar un mismo Título, chequear que institución sea diferente.
                        Boolean institucionAndTituloEquals = eduServ.institucionAndTituloEquals(nombre_usuario, eduDto.getInstitucion(), eduDto.getTitulo());
                      
                        if(!institucionAndTituloEquals){

                            Educacion eduNuevo = new Educacion(
                            eduDto.getInstitucion(), 
                            eduDto.getTitulo(), 
                            eduDto.getFechaInicio(),
                            eduDto.getFechaFinalizacion(), 
                            eduDto.getEstado(), 
                            eduDto.getDetalles() 
                            );

                        eduServ.crearEducacion(eduNuevo, nombre_usuario);
                        return new ResponseEntity(new Mensaje("Item Educacion creado, mismo titulo, institucion diferente."), HttpStatus.OK);

                        }else{
                            // ya tiene un una educación con un título e intitución igual. Invalidar registro.
                            return new ResponseEntity(new Mensaje("No guardado: Item Educacion ya registrado (Titulo-Institucion)."), HttpStatus.OK);
                        }
                      
                       
                    }
                   
             

                
            }else{
                logger.info("USUARIO NO AUTENTICADO {}", nombreUsuarioLogueado);
                throw new AccessDeniedException("NO TENÉS AUTORIZACIÓN PARA REALIZAR ESTA PETICIÓN.");
            }
        }


    @PreAuthorize("hasRole('USER')")
        @DeleteMapping("/{nombre_usuario}/educacion/{id}")
        public ResponseEntity<?> delete(@PathVariable String nombre_usuario, @PathVariable("id") Integer idEducacion){

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String nombreUsuarioLogueado = authentication.getName();


            if(nombre_usuario.equals(nombreUsuarioLogueado)){
                logger.info("USUARIO AUTENTICADO {}", nombreUsuarioLogueado);
                    Boolean existeEducacionBuscado = eduServ.existsEducacion(nombre_usuario, idEducacion);
                    if(existeEducacionBuscado){
                        eduServ.borrarEducacion(nombre_usuario, idEducacion);
                        return new ResponseEntity(new Mensaje("Item Educacion eliminado."), HttpStatus.OK);
                    }else{
                        return new ResponseEntity(new Mensaje("No existe el Item buscado."), HttpStatus.NOT_FOUND);
                    }

            }else{
                logger.info("USUARIO NO AUTENTICADO {}", nombreUsuarioLogueado);
                throw new AccessDeniedException("NO TENÉS AUTORIZACIÓN PARA REALIZAR ESTA PETICIÓN.");
            }
        }


    @PreAuthorize("hasRole('USER')")
        @PutMapping("/{nombre_usuario}/educacion/{id}")
        public ResponseEntity<?> update(@PathVariable String nombre_usuario, @PathVariable("id") Integer idEducacion, @RequestBody EducacionDto eduDto){

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String nombreUsuarioLogueado = authentication.getName();

            Boolean existeEducacionBuscado = eduServ.existsEducacion(nombre_usuario, idEducacion);


            if(!existeEducacionBuscado){
                return new ResponseEntity(new Mensaje("No existe el item educacion buscado."), HttpStatus.NOT_FOUND);
            }else{
                if(StringUtils.isBlank(eduDto.getInstitucion()))
                    return new ResponseEntity(new Mensaje("Ingresar la Institucion es obligatorio."), HttpStatus.BAD_REQUEST);
                if(StringUtils.isBlank(eduDto.getTitulo()))
                    return new ResponseEntity(new Mensaje("Ingresar el Titulo es obligatorio."), HttpStatus.BAD_REQUEST);
                if(eduDto.getFechaInicio() == null)
                    return new ResponseEntity(new Mensaje("Ingresar la Fecha de Inicio es obligatorio."), HttpStatus.BAD_REQUEST);
                if(StringUtils.isBlank(eduDto.getEstado()))
                    return new ResponseEntity(new Mensaje("Ingresar el Estado  es obligatorio."), HttpStatus.BAD_REQUEST);
                if(StringUtils.isBlank(eduDto.getDetalles()))
                    return new ResponseEntity(new Mensaje("Ingresar Detalles es obligatorio."), HttpStatus.BAD_REQUEST);
            }
            
            

            Educacion eduEdit = eduServ.buscarEducacion(nombre_usuario, idEducacion);
            eduEdit.setInstitucion(eduDto.getInstitucion());
            eduEdit.setTitulo(eduDto.getTitulo());
            eduEdit.setFechaInicio(eduDto.getFechaInicio());
            eduEdit.setFechaFinalizacion(eduDto.getFechaFinalizacion());
            eduEdit.setEstado(eduDto.getEstado());
            eduEdit.setDetalles(eduDto.getDetalles());
            eduServ.crearEducacion(eduEdit, nombre_usuario);
            return new ResponseEntity(new Mensaje("Item Educacion actualizado."), HttpStatus.OK);
        }
    
  //-------------------------- FIN EDUCACION CONTROLLER ----------------------------------
 
  // ************************************************************************************




}
