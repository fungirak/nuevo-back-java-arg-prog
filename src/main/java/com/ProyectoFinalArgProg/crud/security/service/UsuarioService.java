/*
package com.ProyectoFinalArgProg.crud.security.service;

import com.ProyectoFinalArgProg.crud.security.entity.Usuario;
import com.ProyectoFinalArgProg.crud.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;
    
    public Integer getIdByNombre(String nombreUsuarioLogueado){
        Usuario usr = usuarioRepository.findByNombreUsuario(nombreUsuarioLogueado);
        return usr.getIdUsuario();
    }

    public Usuario getByNombreUsuario(String nombreUsuario){
        Usuario usr =  usuarioRepository.findByNombreUsuario(nombreUsuario);
        return usr;
    }

    public boolean existsByNombreUsuario(String nombreUsuario){
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    public boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public void save(Usuario usuario){
        usuarioRepository.save(usuario);
    }

    
}
*/

package com.ProyectoFinalArgProg.crud.security.service;

import com.ProyectoFinalArgProg.crud.dto.PortafolioDto;
import com.ProyectoFinalArgProg.crud.entity.AcercaDe;
import com.ProyectoFinalArgProg.crud.entity.Educacion;
import com.ProyectoFinalArgProg.crud.entity.Experiencia;
import com.ProyectoFinalArgProg.crud.entity.Proyecto;
import com.ProyectoFinalArgProg.crud.entity.Skill;
import com.ProyectoFinalArgProg.crud.repository.AcercaDeRepository;
import com.ProyectoFinalArgProg.crud.repository.EducacionRepository;
import com.ProyectoFinalArgProg.crud.repository.ExperienciaRepository;
import com.ProyectoFinalArgProg.crud.repository.ProyectoRepository;
import com.ProyectoFinalArgProg.crud.repository.SkillRepository;
import com.ProyectoFinalArgProg.crud.security.entity.Usuario;
import com.ProyectoFinalArgProg.crud.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private AcercaDeRepository acercaDeRepository;
    
    @Autowired
    private EducacionRepository educacionRepository;
    
    @Autowired
    private ExperienciaRepository experienciaRepository;
    
    @Autowired
    private ProyectoRepository proyectoRepository;
    
    @Autowired
    private SkillRepository skillRepository;

    public Integer getIdUsuarioSegunNombre(String nombre_usuario){
        Usuario usuarioAutenticado = usuarioRepository.findByNombreUsuario(nombre_usuario);
        Integer idUsuario = usuarioAutenticado.getIdUsuario();
        return idUsuario;
    }
    

    public Optional<Usuario> getById(Integer idUsuario){
        return usuarioRepository.findById(idUsuario);
    }

    public Usuario getByNombreUsuario(String nombreUsuario){
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    public boolean existsByNombreUsuario(String nombreUsuario){
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    public boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public void save(Usuario usuario){
        usuarioRepository.save(usuario);
    }


    /*RECUPERACIÓN DE DATOS DE PORTAFOLIO*/

    public AcercaDe cargarAcercaDe(String nombre_usuario){
      return usuarioRepository.cargarAcercaDe(getIdUsuarioSegunNombre(nombre_usuario));
    }

    public Set<Educacion> cargarEducacion(String nombre_usuario){
        return usuarioRepository.cargarEducacion(getIdUsuarioSegunNombre(nombre_usuario));
    }

    public Set<Experiencia> cargarExperiencias(String nombre_usuario){
        return usuarioRepository.cargarExperiencias(getIdUsuarioSegunNombre(nombre_usuario));
    }

    public Set<Proyecto> cargarProyectos(String nombre_usuario){
        return usuarioRepository.cargarProyectos(getIdUsuarioSegunNombre(nombre_usuario));
    }

    public Set<Skill> cargarSkills(String nombre_usuario){
        return usuarioRepository.cargarSkills(getIdUsuarioSegunNombre(nombre_usuario));
    }

    /* PASADO POR CHAT GPT , OTRO MODO */

    public PortafolioDto cargarPortafolio(String nombreUsuario) {
        // Buscar el usuario por nombre de usuario
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario);
        if (usuario == null) {
           // throw new UsuarioNotFoundException("El usuario con nombre de usuario " + nombreUsuario + " no existe.");
        }

        Integer idUsuario = usuario.getIdUsuario();

        // Cargar los objetos de cada entidad relacionada con el portafolio del usuario
        AcercaDe acercaDe = acercaDeRepository.buscarAcercaDePorIdUsuario(idUsuario);
        Set<Educacion> educacion = educacionRepository.buscarTodaLaEducacionPorIdUsuario(idUsuario);
        Set<Experiencia> experiencias = experienciaRepository.buscarTodaLaExperienciaPorIdUsuario(idUsuario);
        Set<Proyecto> proyectos = proyectoRepository.buscarTodosLosProyectosPorIdUsuario(idUsuario);
        Set<Skill> skills = skillRepository.buscarTodasLasSkillsPorIdUsuario(idUsuario);

        // Crear el objeto DTO que contiene todos los objetos relacionados con el portafolio del usuario
        PortafolioDto portafolioDto = new PortafolioDto();
        portafolioDto.setAcercaDe(acercaDe);
        portafolioDto.setEducacion(educacion);
        portafolioDto.setExperiencias(experiencias);
        portafolioDto.setProyectos(proyectos);
        portafolioDto.setSkills(skills);

        return portafolioDto;
    }


}
