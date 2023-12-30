package com.ProyectoFinalArgProg.crud.security.controller;

import com.ProyectoFinalArgProg.crud.dto.Mensaje;
import com.ProyectoFinalArgProg.crud.entity.AcercaDe;
import com.ProyectoFinalArgProg.crud.security.dto.JwtDto;
import com.ProyectoFinalArgProg.crud.security.dto.LoginUsuario;
import com.ProyectoFinalArgProg.crud.security.dto.NuevoUsuario;
import com.ProyectoFinalArgProg.crud.security.entity.Rol;
import com.ProyectoFinalArgProg.crud.security.entity.Usuario;
import com.ProyectoFinalArgProg.crud.security.enums.RolNombre;
import com.ProyectoFinalArgProg.crud.security.jwt.JwtProvider;
import com.ProyectoFinalArgProg.crud.security.service.RolService;
import com.ProyectoFinalArgProg.crud.security.service.UsuarioService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByEmail(nuevoUsuario.getEmail()))
            return new ResponseEntity(new Mensaje("ese email ya existe"), HttpStatus.BAD_REQUEST);
        Usuario usuario =
                new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
                        passwordEncoder.encode(nuevoUsuario.getPassword()));


        Set<Rol> roles = new HashSet<>();
    Rol rolUser = rolService.getByRolNombre(RolNombre.ROLE_USER).orElse(null);
    if (rolUser != null) {
        roles.add(rolUser);
    }

    if(nuevoUsuario.getRoles().contains("admin")) {
        Rol rolAdmin = rolService.getByRolNombre(RolNombre.ROLE_ADMIN).orElse(null);
        if (rolAdmin != null) {
            roles.add(rolAdmin);
        }
    }
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        return new ResponseEntity(new Mensaje("usuario guardado"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);

        logger.info("EL TOKEN DEL USUARIO ES  {}", jwt);

        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());

        logger.info("EL JWTDTO DEL USUARIO ES {}", jwtDto);

        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }

    

}
