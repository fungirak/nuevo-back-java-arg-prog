
package com.ProyectoFinalArgProg.crud.controller;

import com.ProyectoFinalArgProg.crud.dto.Mensaje;
import com.ProyectoFinalArgProg.crud.entity.AcercaDe;
import com.ProyectoFinalArgProg.crud.security.entity.Usuario;
import com.ProyectoFinalArgProg.crud.security.service.UsuarioService;
import com.ProyectoFinalArgProg.crud.dto.AcercaDeDto;
import com.ProyectoFinalArgProg.crud.service.AcercaDeService;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
public class AcercaDeController {
    


//-------------------------- ACERCA DE CONTROLLER ----------------------------------
    @Autowired
    private AcercaDeService acercadeServ;
    private UsuarioService usrSrv;

    private static final Logger logger = LoggerFactory.getLogger(AcercaDeController.class);

    @PreAuthorize("hasRole('USER')")
        @GetMapping("/{nombre_usuario}/acerca_de")
            public ResponseEntity<AcercaDe> list(@PathVariable String nombre_usuario){

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		        String nombreUsuarioLogueado = authentication.getName();

                logger.info("CARGANDO 'ACERCA DE' DE USUARIO. MOSTRANDO DATOS DE OBJETO AUTHENTICATION {}", authentication);

                if(nombre_usuario.equals(nombreUsuarioLogueado)){
                    logger.info("USUARIO AUTENTICADO {}", nombreUsuarioLogueado);

                    AcercaDe acercaDe = acercadeServ.verAcercaDe(nombre_usuario);
                    return new ResponseEntity(acercaDe, HttpStatus.OK);
                }else{
                    logger.info("USUARIO NO AUTENTICADO {}", nombreUsuarioLogueado);
                    throw new AccessDeniedException("NO TENÉS AUTORIZACIÓN PARA REALIZAR ESTA PETICIÓN.");
                }


            }


    @PreAuthorize("hasRole('USER')")
        @GetMapping("/{nombre_usuario}/acerca_de/{id}")
            public ResponseEntity<AcercaDe> getByUsername(@PathVariable String nombre_usuario, @PathVariable("id") Integer idAcercaDe){
           
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String nombreUsuarioLogueado = authentication.getName();

            logger.info("CARGANDO 'UN ACERCA DE' DE USUARIO. MOSTRANDO DATOS DE OBJETO AUTHENTICATION {}", authentication);

            if(nombre_usuario.equals(nombreUsuarioLogueado)){
                logger.info("USUARIO AUTENTICADO {}", nombreUsuarioLogueado);

                AcercaDe acercaDe = acercadeServ.verAcercaDe(nombre_usuario);
                return new ResponseEntity(acercaDe, HttpStatus.OK);
            }else{
                logger.info("USUARIO NO AUTENTICADO {}", nombreUsuarioLogueado);
                throw new AccessDeniedException("NO TENÉS AUTORIZACIÓN PARA REALIZAR ESTA PETICIÓN.");
        }

    }
    

    @PreAuthorize("hasRole('USER')")
        @PostMapping("/{nombre_usuario}/acerca_de")
        public ResponseEntity<AcercaDe> create(@PathVariable String nombre_usuario, @RequestBody AcercaDeDto acercaDeDto){

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String nombreUsuarioLogueado = authentication.getName();


            if(nombre_usuario.equals(nombreUsuarioLogueado)){
                // usuario logueado

                AcercaDe ad = acercadeServ.verAcercaDe(nombreUsuarioLogueado);

                if(ad.getId() != null){
                        // ya tiene un AcercaDe (Actualizarle el mismo objeto con los datos del dto).
                        ad.setFullname(acercaDeDto.getFullname());
                        ad.setDescripcion(acercaDeDto.getDescripcion());
                        ad.setPosicion(acercaDeDto.getPosicion());

                    AcercaDe adActualizado = acercadeServ.actualizarAcercaDe(ad);
                    return new ResponseEntity(new Mensaje("acerca de actualizado"), HttpStatus.OK);
                
                }else{
                        // no tiene AcercaDe, crearle un nuevo objeto y único para esta entidad.
                
                logger.info("USUARIO AUTENTICADO {}", nombreUsuarioLogueado);
                    AcercaDe acercaDeNuevo = new AcercaDe( 
                        acercaDeDto.getFullname(), 
                        acercaDeDto.getPosicion(),
                        acercaDeDto.getDescripcion() 
                    );
                    acercadeServ.crearAcercaDe(acercaDeNuevo, nombre_usuario);
                    return new ResponseEntity(new Mensaje("acerca de creado"), HttpStatus.OK);
                }
            }else {
                logger.info("USUARIO NO AUTENTICADO {}", nombreUsuarioLogueado);
                throw new AccessDeniedException("NO TENÉS AUTORIZACIÓN PARA REALIZAR ESTA PETICIÓN.");
            }
        }


    @PreAuthorize("hasRole('USER')") 
        @DeleteMapping("/{nombre_usuario}/acerca_de/{id}")
        public ResponseEntity<?> delete(@PathVariable String nombre_usuario, @PathVariable("id") Integer idAcercaDe){
            // Authentication authentication = Security ContextHolder.getContext().getAuthentication
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String nombreUsuarioLogueado = authentication.getName();
           
        

            if(nombre_usuario.equals(nombreUsuarioLogueado)){
                logger.info("USUARIO AUTENTICADO {}", nombreUsuarioLogueado);
                    Boolean existeAcercaDeBuscado = acercadeServ.existsAcercaDe(nombre_usuario, idAcercaDe);
                    if(existeAcercaDeBuscado){
                        acercadeServ.borrarAcercaDe(nombre_usuario, idAcercaDe);
                        return new ResponseEntity(new Mensaje("ITEM ACERCA_DE ELIMINADO."), HttpStatus.OK);
                    }else{
                        return new ResponseEntity(new Mensaje("No existe el Item buscado."), HttpStatus.NOT_FOUND);
                    }

            }else{
                logger.info("USUARIO NO AUTENTICADO {}", nombreUsuarioLogueado);
                throw new AccessDeniedException("NO TENÉS AUTORIZACIÓN PARA REALIZAR ESTA PETICIÓN.");
            }

        }

     
    @PreAuthorize("hasRole('USER')")
        @PutMapping("/{nombre_usuario}/acerca_de/{id}")
        public ResponseEntity<AcercaDe> update(@PathVariable String nombre_usuario, @PathVariable("id") Integer idAcercaDe, @RequestBody AcercaDeDto acercDto){
            
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String nombreUsuarioLogueado = authentication.getName();

            Boolean existeAcercaDeBuscado = acercadeServ.existsAcercaDe(nombre_usuario, idAcercaDe);

            if(!existeAcercaDeBuscado){
                return new ResponseEntity(new Mensaje("No existe el item buscado."), HttpStatus.NOT_FOUND);
            }else{
                if(StringUtils.isBlank(acercDto.getFullname()))
                return new ResponseEntity(new Mensaje("Ingresar el Fullname es obligatorio."), HttpStatus.BAD_REQUEST);
            if(StringUtils.isBlank(acercDto.getPosicion()))
                return new ResponseEntity(new Mensaje("Ingresar la Posicion es obligatorio."), HttpStatus.BAD_REQUEST);
            if(StringUtils.isBlank(acercDto.getDescripcion()))
                return new ResponseEntity(new Mensaje("Ingresar la Descripcion es obligatorio."), HttpStatus.BAD_REQUEST);
            }
           
            

            AcercaDe acercEdit = acercadeServ.verAcercaDe(nombre_usuario, idAcercaDe);
            acercEdit.setFullname(acercDto.getFullname());
            acercEdit.setPosicion(acercDto.getPosicion());
            acercEdit.setDescripcion(acercDto.getDescripcion());

            acercadeServ.crearAcercaDe(acercEdit, nombre_usuario);
            return new ResponseEntity(new Mensaje("Item AcercaDe actualizado."), HttpStatus.OK);
        }

    
  //-------------------------- FIN ACERCA DE CONTROLLER ----------------------------------
 
  // ************************************************************************************




}
