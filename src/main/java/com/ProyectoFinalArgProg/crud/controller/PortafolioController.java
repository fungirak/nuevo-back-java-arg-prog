package com.ProyectoFinalArgProg.crud.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ProyectoFinalArgProg.crud.dto.AcercaDeDto;
import com.ProyectoFinalArgProg.crud.dto.EducacionDto;
import com.ProyectoFinalArgProg.crud.dto.ExperienciaDto;
import com.ProyectoFinalArgProg.crud.dto.PortafolioDto;
import com.ProyectoFinalArgProg.crud.dto.ProyectoDto;
import com.ProyectoFinalArgProg.crud.dto.SkillDto;
import com.ProyectoFinalArgProg.crud.entity.AcercaDe;
import com.ProyectoFinalArgProg.crud.entity.Educacion;
import com.ProyectoFinalArgProg.crud.entity.Experiencia;
import com.ProyectoFinalArgProg.crud.entity.Proyecto;
import com.ProyectoFinalArgProg.crud.entity.Skill;
import com.ProyectoFinalArgProg.crud.security.entity.Usuario;
import com.ProyectoFinalArgProg.crud.security.service.UsuarioService;
import com.ProyectoFinalArgProg.crud.service.AcercaDeService;
import com.ProyectoFinalArgProg.crud.service.EducacionService;
import com.ProyectoFinalArgProg.crud.service.ExperienciaService;
import com.ProyectoFinalArgProg.crud.service.ProyectoService;
import com.ProyectoFinalArgProg.crud.service.SkillService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/portafolio")
public class PortafolioController {

    @Autowired
    private UsuarioService usuarioService;


    @Autowired
    private AcercaDeService acercaDeService;
    
    @Autowired
    private EducacionService educacionService;
    
    @Autowired
    private ExperienciaService experienciaService;
    
    @Autowired
    private ProyectoService proyectoService;
    
    @Autowired
    private SkillService skillService;

    @GetMapping("/{nombre_usuario}")
    public ResponseEntity<PortafolioDto> getPortafolioByNombreUsuario(@PathVariable("nombre_usuario") String nombreUsuario) {
        // buscar el usuario por nombre de usuario (o por el identificador si es que lo tienes)
        Usuario usuario = usuarioService.getByNombreUsuario(nombreUsuario);
        
        // si el usuario no existe, retornar un error 404
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        Integer idUsuario = usuario.getIdUsuario();
        
        // cargar los datos del usuario desde cada servicio correspondiente
        AcercaDe acercaDe = acercaDeService.verAcercaDe(nombreUsuario);
        Set<Educacion> educacion = educacionService.verEducacion(nombreUsuario);
        Set<Experiencia> experiencias = experienciaService.verExperiencias(nombreUsuario);
        Set<Proyecto> proyectos = proyectoService.verProyectos(nombreUsuario);
        Set<Skill> skills = skillService.verSkills(nombreUsuario);
        
        // crear un objeto PortafolioDto con los datos cargados
        PortafolioDto portafolio = new PortafolioDto();
        portafolio.setAcercaDe(acercaDe);
        portafolio.setEducacion(educacion);
        portafolio.setExperiencias(experiencias);
        portafolio.setProyectos(proyectos);
        portafolio.setSkills(skills);
        
        // retornar la respuesta con el objeto PortafolioDto como cuerpo
        return ResponseEntity.ok(portafolio);
    }



    @GetMapping("/{nombre_usuario}/acerca_de")
    public ResponseEntity<AcercaDeDto> getAcercaDeByNombreUsuario(@PathVariable("nombre_usuario") String nombreUsuario) {
        // buscar el usuario por nombre de usuario (o por el identificador si es que lo tienes)
        Usuario usuario = usuarioService.getByNombreUsuario(nombreUsuario);
        
        // si el usuario no existe, retornar un error 404
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        Integer idUsuario = usuario.getIdUsuario();
        
        // cargar los datos del usuario desde cada servicio correspondiente
        AcercaDe acercaDe = acercaDeService.verAcercaDe(nombreUsuario);
       
        
        // crear un objeto AcercaDeDto con los datos cargados
        AcercaDeDto adDto = new AcercaDeDto();
        adDto.setFullname(acercaDe.getFullname());
        adDto.setPosicion(acercaDe.getPosicion());
        adDto.setDescripcion(acercaDe.getDescripcion());
       
        
        // retornar la respuesta con el objeto AcercaDeDto como cuerpo
        return ResponseEntity.ok(adDto);
    }

    //Educacion
    @GetMapping("/{nombre_usuario}/educacion")
    public ResponseEntity<List<EducacionDto>> getEducacionByNombreUsuario(@PathVariable("nombre_usuario") String nombreUsuario) {
        // buscar el usuario por nombre de usuario (o por el identificador si es que lo tienes)
        Usuario usuario = usuarioService.getByNombreUsuario(nombreUsuario);
        
        // si el usuario no existe, retornar un error 404
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        Integer idUsuario = usuario.getIdUsuario();
        
        // cargar los datos del usuario desde cada servicio correspondiente
        Set<Educacion> educacion = educacionService.verEducacion(nombreUsuario);
       
        // crear una lista de objetos EducacionDto
        List<EducacionDto> edDtoList = new ArrayList<>();

        for(Educacion educ : educacion){
            EducacionDto edDto = new EducacionDto();
            edDto.setTitulo(educ.getTitulo());
            edDto.setInstitucion(educ.getInstitucion());
            edDto.setEstado(educ.getEstado());
            edDto.setDetalles(educ.getDetalles());
            edDto.setFechaInicio(educ.getFechaInicio());
            edDto.setFechaFinalizacion(educ.getFechaFinalizacion());

            //agregar el objeto mapeado a la lista de objetos EducacionDto
            edDtoList.add(edDto);
        } 
        
        // retornar la respuesta con el objeto AcercaDeDto como cuerpo
        return ResponseEntity.ok(edDtoList);
    }

    //Experiencia
    @GetMapping("/{nombre_usuario}/experiencia")
    public ResponseEntity<List<ExperienciaDto>> getExperienciaByNombreUsuario(@PathVariable("nombre_usuario") String nombreUsuario) {
         // buscar el usuario por nombre de usuario (o por el identificador si es que lo tienes)
         Usuario usuario = usuarioService.getByNombreUsuario(nombreUsuario);
         
         // si el usuario no existe, retornar un error 404
         if (usuario == null) {
             return ResponseEntity.notFound().build();
         }
 
         Integer idUsuario = usuario.getIdUsuario();
         
         // cargar los datos del usuario desde cada servicio correspondiente
         Set<Experiencia> experiencia = experienciaService.verExperiencias(nombreUsuario);
        
         // crear una lista de objetos ExperienciaDto
         List<ExperienciaDto> expeDtoList = new ArrayList<>();
 
         for(Experiencia expe : experiencia){
             ExperienciaDto expeDto = new ExperienciaDto();
             expeDto.setEmpresa(expe.getEmpresa());
             expeDto.setPuesto(expe.getPuesto());
             expeDto.setUbicacion(expe.getUbicacion());
             expeDto.setActividades(expe.getActividades());
             expeDto.setFechaInicio(expe.getFechaInicio());
             expeDto.setFechaFinalizacion(expe.getFechaFinalizacion());
 
             //agregar el objeto mapeado a la lista de objetos EducacionDto
             expeDtoList.add(expeDto);
         } 
         
         // retornar la respuesta con el objeto AcercaDeDto como cuerpo
         return ResponseEntity.ok(expeDtoList);
     }

    //Proyectos
    @GetMapping("/{nombre_usuario}/proyectos")
    public ResponseEntity<List<ProyectoDto>> getProyectosByNombreUsuario(@PathVariable("nombre_usuario") String nombreUsuario) {
         // buscar el usuario por nombre de usuario (o por el identificador si es que lo tienes)
         Usuario usuario = usuarioService.getByNombreUsuario(nombreUsuario);
         
         // si el usuario no existe, retornar un error 404
         if (usuario == null) {
             return ResponseEntity.notFound().build();
         }
 
         Integer idUsuario = usuario.getIdUsuario();
         
         // cargar los datos del usuario desde cada servicio correspondiente
         Set<Proyecto> proyecto = proyectoService.verProyectos(nombreUsuario);
        
         // crear una lista de objetos ExperienciaDto
         List<ProyectoDto> proDtoList = new ArrayList<>();
 
         for(Proyecto pro : proyecto){
             ProyectoDto proDto = new ProyectoDto();
             proDto.setTitulo(pro.getTitulo());
             proDto.setDescripcion(pro.getDescripcion());
             proDto.setImagen(pro.getImagen());

             //agregar el objeto mapeado a la lista de objetos EducacionDto
             proDtoList.add(proDto);
         } 
         
         // retornar la respuesta con el objeto AcercaDeDto como cuerpo
         return ResponseEntity.ok(proDtoList);
     }

    //Skills
    @GetMapping("/{nombre_usuario}/skills")
    public ResponseEntity<List<SkillDto>> getSkillsByNombreUsuario(@PathVariable("nombre_usuario") String nombreUsuario) {
         // buscar el usuario por nombre de usuario (o por el identificador si es que lo tienes)
         Usuario usuario = usuarioService.getByNombreUsuario(nombreUsuario);
         
         // si el usuario no existe, retornar un error 404
         if (usuario == null) {
             return ResponseEntity.notFound().build();
         }
 
         Integer idUsuario = usuario.getIdUsuario();
         
         // cargar los datos del usuario desde cada servicio correspondiente
         Set<Skill> skills = skillService.verSkills(nombreUsuario);
        
         // crear una lista de objetos ExperienciaDto
         List<SkillDto> skDtoList = new ArrayList<>();
 
         for(Skill sk : skills){
             SkillDto skDto = new SkillDto();
             skDto.setTecnologia(sk.getTecnologia());
             skDto.setImagen(sk.getImagen());

             //agregar el objeto mapeado a la lista de objetos EducacionDto
             skDtoList.add(skDto);
         } 
         
         // retornar la respuesta con el objeto AcercaDeDto como cuerpo
         return ResponseEntity.ok(skDtoList);
     }
}
