
package com.ProyectoFinalArgProg.crud.controller;

import com.ProyectoFinalArgProg.crud.dto.Mensaje;
import com.ProyectoFinalArgProg.crud.entity.Proyecto;
import com.ProyectoFinalArgProg.crud.security.service.UsuarioService;
import com.ProyectoFinalArgProg.crud.dto.ProyectoDto;
import com.ProyectoFinalArgProg.crud.service.ProyectoService;

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
public class ProyectoController {
    


//-------------------------- PROYECTO CONTROLLER ----------------------------------
    @Autowired
    private ProyectoService proServ;
    private UsuarioService usrSrv;

    private static final Logger logger = LoggerFactory.getLogger(ProyectoController.class);

    //@PreAuthorize("hasRole('USER')")
        @GetMapping("/{nombre_usuario}/proyectos")
            public ResponseEntity<List<Proyecto>> list(@PathVariable String nombre_usuario){
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String nombreUsuarioLogueado = authentication.getName();
    
                logger.info("CARGANDO 'PROYECTOS' DE USUARIO. MOSTRANDO DATOS DE OBJETO AUTHENTICATION {}", authentication);
    
                if(nombre_usuario.equals(nombreUsuarioLogueado)){
                    logger.info("USUARIO AUTENTICADO {}", nombreUsuarioLogueado);
    
                    Set<Proyecto> setProyectos = proServ.verProyectos(nombre_usuario);
                    return new ResponseEntity(setProyectos, HttpStatus.OK);
                }else{
                    logger.info("USUARIO NO AUTENTICADO {}", nombreUsuarioLogueado);
                    throw new AccessDeniedException("NO TENÉS AUTORIZACIÓN PARA REALIZAR ESTA PETICIÓN.");
                }
            }


    //@PreAuthorize("hasRole('USER')")
        @GetMapping("/{nombre_usuario}/proyectos/{id}")
            public ResponseEntity<Proyecto> getById(@PathVariable String nombre_usuario, @PathVariable("id") Integer idProyecto){
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String nombreUsuarioLogueado = authentication.getName();
    
                logger.info("CARGANDO 'UN PROYECTO' DE USUARIO. MOSTRANDO DATOS DE OBJETO AUTHENTICATION {}", authentication);
    
                if(nombre_usuario.equals(nombreUsuarioLogueado)){
                    logger.info("USUARIO AUTENTICADO {}", nombreUsuarioLogueado);
    
                    Proyecto proyecto = proServ.buscarProyecto(nombre_usuario, idProyecto);
                    return new ResponseEntity(proyecto, HttpStatus.OK);
                }else{
                    logger.info("USUARIO NO AUTENTICADO {}", nombreUsuarioLogueado);
                    throw new AccessDeniedException("NO TENÉS AUTORIZACIÓN PARA REALIZAR ESTA PETICIÓN.");
                }
            }
    

    @PreAuthorize("hasRole('USER')")
        @PostMapping("/{nombre_usuario}/proyectos")
        public ResponseEntity<?> create(@PathVariable String nombre_usuario, @RequestBody ProyectoDto proDto){
            
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String nombreUsuarioLogueado = authentication.getName();

            if(nombre_usuario.equals(nombreUsuarioLogueado)){
                logger.info("USUARIO AUTENTICADO {}", nombreUsuarioLogueado);
                // (título-descripción) tiene que ser único para cada nuevo proyecto creado.

                Boolean tituloExists = proServ.tituloExists(nombre_usuario, proDto.getTitulo());

                if(!tituloExists){
                    Proyecto proyectNuevo = new Proyecto( 
                        proDto.getTitulo(), 
                        proDto.getImagen(), 
                        proDto.getDescripcion() 
                    );
                    proServ.crearProyecto(proyectNuevo, nombre_usuario);
                        return new ResponseEntity(new Mensaje("Nuevo Proyecto creado"), HttpStatus.OK);
                }else{
                    // Si quiere guardar el mismo titulo. Chequear que descripción sea diferente.
                    Boolean tituloAndDescripcionEquals = proServ.tituloAndDescripcionEquals(nombre_usuario, proDto.getTitulo(), proDto.getDescripcion());
               
                    if(!tituloAndDescripcionEquals){
                        Proyecto proyectNuevo = new Proyecto( 
                            proDto.getTitulo(), 
                            proDto.getImagen(), 
                            proDto.getDescripcion() 
                        );
                        proServ.crearProyecto(proyectNuevo, nombre_usuario);
                            return new ResponseEntity(new Mensaje("Nuevo Proyecto creado, mismo titulo, distinta descripción."), HttpStatus.OK);
                    }else{
                        // ya tiene un un proyecto con un título y descripción igual. Invalidar registro.
                        return new ResponseEntity(new Mensaje("No guardado: Item Proyecto ya registrado (Titulo-Descripción)."), HttpStatus.OK);
                    }
                }

               
            }else{
                logger.info("USUARIO NO AUTENTICADO {}", nombreUsuarioLogueado);
                throw new AccessDeniedException("NO TENÉS AUTORIZACIÓN PARA REALIZAR ESTA PETICIÓN.");
            }
        }


     @PreAuthorize("hasRole('USER')")
        @DeleteMapping("/{nombre_usuario}/proyectos/{id}")
        public ResponseEntity<?> delete(@PathVariable String nombre_usuario, @PathVariable("id") Integer idProyecto){

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String nombreUsuarioLogueado = authentication.getName();


            if(nombre_usuario.equals(nombreUsuarioLogueado)){
                logger.info("USUARIO AUTENTICADO {}", nombreUsuarioLogueado);
                    Boolean existeProyectoBuscado = proServ.existsProyecto(nombre_usuario, idProyecto);
                    if(existeProyectoBuscado){
                        proServ.borrarProyecto(nombre_usuario, idProyecto);
                        return new ResponseEntity(new Mensaje("Item Proyecto eliminado."), HttpStatus.OK);
                    }else{
                        return new ResponseEntity(new Mensaje("No existe el Item buscado."), HttpStatus.NOT_FOUND);
                    }

            }else{
                logger.info("USUARIO NO AUTENTICADO {}", nombreUsuarioLogueado);
                throw new AccessDeniedException("NO TENÉS AUTORIZACIÓN PARA REALIZAR ESTA PETICIÓN.");
            }
        }


    @PreAuthorize("hasRole('USER')")
        @PutMapping("/{nombre_usuario}/proyectos/{id}")
        public ResponseEntity<?> update(@PathVariable String nombre_usuario, @PathVariable("id") Integer idProyecto, @RequestBody ProyectoDto proyDto){
          
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String nombreUsuarioLogueado = authentication.getName();

            Boolean existeProyectoBuscado = proServ.existsProyecto(nombre_usuario, idProyecto);
          
            if(!existeProyectoBuscado){
                return new ResponseEntity(new Mensaje("No existe el item proyecto buscado."), HttpStatus.NOT_FOUND);
            } else {
                if(StringUtils.isBlank(proyDto.getTitulo()))
                    return new ResponseEntity(new Mensaje("Ingresar el Titulo es obligatorio."), HttpStatus.BAD_REQUEST);
                if(StringUtils.isBlank(proyDto.getImagen()))
                    return new ResponseEntity(new Mensaje("Ingresar la Imagen es obligatorio."), HttpStatus.BAD_REQUEST);
                if(StringUtils.isBlank(proyDto.getDescripcion()))
                    return new ResponseEntity(new Mensaje("Ingresar la Descripcion es obligatorio."), HttpStatus.BAD_REQUEST);
            }
          
            

            Proyecto proyEdit = proServ.buscarProyecto(nombre_usuario, idProyecto);
            proyEdit.setTitulo(proyDto.getTitulo());
            proyEdit.setImagen(proyDto.getImagen());
            proyEdit.setDescripcion(proyDto.getDescripcion());
            proServ.crearProyecto(proyEdit, nombre_usuario);
            return new ResponseEntity(new Mensaje("Item Proyecto actualizado."), HttpStatus.OK);
        }
  //-------------------------- FIN PROYECTO CONTROLLER ----------------------------------
 
  // ************************************************************************************




}
