package com.ProyectoFinalArgProg.crud.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ProyectoFinalArgProg.crud.security.entity.Usuario;
import java.util.List;


@Repository
public interface SearchRepository extends JpaRepository<Usuario, Long> {
  List<Usuario> findByNombreUsuarioContainingIgnoreCase(String query);
}

