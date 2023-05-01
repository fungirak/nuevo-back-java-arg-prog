
package com.ProyectoFinalArgProg.crud.controller;

import com.ProyectoFinalArgProg.crud.dto.Mensaje;
import com.ProyectoFinalArgProg.crud.entity.Skill;
import com.ProyectoFinalArgProg.crud.security.service.UsuarioService;
import com.ProyectoFinalArgProg.crud.dto.SkillDto;
import com.ProyectoFinalArgProg.crud.service.SkillService;

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
public class SkillController {
    


//-------------------------- SKILL CONTROLLER ----------------------------------
    @Autowired
    private SkillService skServ;
    private UsuarioService usrSrv;

    private static final Logger logger = LoggerFactory.getLogger(SkillController.class);

    //@PreAuthorize("hasRole('USER')")
        @GetMapping("/{nombre_usuario}/skills")
            public ResponseEntity<List<Skill>> list(@PathVariable String nombre_usuario){
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String nombreUsuarioLogueado = authentication.getName();

                logger.info("CARGANDO 'SKILLS' DE USUARIO. MOSTRANDO DATOS DE OBJETO AUTHENTICATION {}", authentication);

                if(nombre_usuario.equals(nombreUsuarioLogueado)){
                    logger.info("USUARIO AUTENTICADO {}", nombreUsuarioLogueado);

                    Set<Skill> setSkills = skServ.verSkills(nombre_usuario);
                    return new ResponseEntity(setSkills, HttpStatus.OK);
                }else{
                    logger.info("USUARIO NO AUTENTICADO {}", nombreUsuarioLogueado);
                    throw new AccessDeniedException("NO TENÉS AUTORIZACIÓN PARA REALIZAR ESTA PETICIÓN.");
                }
            }


    //@PreAuthorize("hasRole('USER')")
        @GetMapping("/{nombre_usuario}/skills/{id}")
            public ResponseEntity<Skill> getById(@PathVariable String nombre_usuario, @PathVariable("id") Integer idSkill){
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String nombreUsuarioLogueado = authentication.getName();

            logger.info("CARGANDO 'UNA SKILL' DE USUARIO. MOSTRANDO DATOS DE OBJETO AUTHENTICATION {}", authentication);

            if(nombre_usuario.equals(nombreUsuarioLogueado)){
                logger.info("USUARIO AUTENTICADO {}", nombreUsuarioLogueado);

                Skill sk = skServ.buscarSkill(nombre_usuario, idSkill);
                return new ResponseEntity(sk, HttpStatus.OK);
            }else{
                logger.info("USUARIO NO AUTENTICADO {}", nombreUsuarioLogueado);
                throw new AccessDeniedException("NO TENÉS AUTORIZACIÓN PARA REALIZAR ESTA PETICIÓN.");
            }
            }
    

    @PreAuthorize("hasRole('USER')")
        @PostMapping("/{nombre_usuario}/skills")
        public ResponseEntity<?> create(@PathVariable String nombre_usuario, @RequestBody SkillDto skDto){
          
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String nombreUsuarioLogueado = authentication.getName();


            if(nombre_usuario.equals(nombreUsuarioLogueado)){
                logger.info("USUARIO AUTENTICADO {}", nombreUsuarioLogueado);
                 // (tecnología) tiene que ser único para cada nuevo skill creado.

                 Boolean tecnologiaExists = skServ.tecnologiaExists(nombre_usuario, skDto.getTecnologia());

                if(!tecnologiaExists){
                    Skill skNuevo = new Skill( 
                        skDto.getTecnologia(), 
                        skDto.getImagen() 
                    );

                    skServ.crearSkill(skNuevo, nombre_usuario);
                    return new ResponseEntity(new Mensaje("Nuevo Skill creado"), HttpStatus.OK);
                }else{
                    // ya tiene un skill con una tecnología igual. Invalidar registro.
                    return new ResponseEntity(new Mensaje("No guardado: Item Skill ya registrado (Tecnología)."), HttpStatus.OK);
                }


                
            }else{
                logger.info("USUARIO NO AUTENTICADO {}", nombreUsuarioLogueado);
                throw new AccessDeniedException("NO TENÉS AUTORIZACIÓN PARA REALIZAR ESTA PETICIÓN.");
            }
        }


   @PreAuthorize("hasRole('USER')")
        @DeleteMapping("/{nombre_usuario}/skills/{id}")
        public ResponseEntity<?> delete(@PathVariable String nombre_usuario, @PathVariable("id") Integer idSkill){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String nombreUsuarioLogueado = authentication.getName();


            if(nombre_usuario.equals(nombreUsuarioLogueado)){
                logger.info("USUARIO AUTENTICADO {}", nombreUsuarioLogueado);
                    Boolean existeSkillBuscado = skServ.existsSkill(nombre_usuario, idSkill);
                    if(existeSkillBuscado){
                        skServ.borrarSkill(nombre_usuario, idSkill);
                        return new ResponseEntity(new Mensaje("Item Skill eliminado."), HttpStatus.OK);
                    }else{
                        return new ResponseEntity(new Mensaje("No existe el Item buscado."), HttpStatus.NOT_FOUND);
                    }

            }else{
                logger.info("USUARIO NO AUTENTICADO {}", nombreUsuarioLogueado);
                throw new AccessDeniedException("NO TENÉS AUTORIZACIÓN PARA REALIZAR ESTA PETICIÓN.");
            }
        }


    @PreAuthorize("hasRole('USER')")
        @PutMapping("/{nombre_usuario}/skills/{id}")
        public ResponseEntity<?> update(@PathVariable String nombre_usuario, @PathVariable("id") Integer idSkill, @RequestBody SkillDto skDto){
           
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String nombreUsuarioLogueado = authentication.getName();

            Boolean existeSkillBuscado = skServ.existsSkill(nombre_usuario, idSkill);
           
            if(!existeSkillBuscado){
                return new ResponseEntity(new Mensaje("No existe el item skill buscado."), HttpStatus.NOT_FOUND);
            } else {
                if(StringUtils.isBlank(skDto.getTecnologia()))
                    return new ResponseEntity(new Mensaje("Ingresar la Tecnología es obligatorio."), HttpStatus.BAD_REQUEST);
                if(StringUtils.isBlank(skDto.getImagen()))
                    return new ResponseEntity(new Mensaje("Ingresar la Imagen es obligatorio."), HttpStatus.BAD_REQUEST);
            }
           

            Skill skEdit = skServ.buscarSkill(nombre_usuario, idSkill);
            skEdit.setTecnologia(skDto.getTecnologia());
            skEdit.setImagen(skDto.getImagen());
            skServ.crearSkill(skEdit, nombre_usuario);
            return new ResponseEntity(new Mensaje("Item Skill actualizado."), HttpStatus.OK);
        }
    
  //-------------------------- FIN SKILL CONTROLLER ----------------------------------
 
  // ************************************************************************************




}
