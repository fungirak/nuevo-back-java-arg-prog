package com.ProyectoFinalArgProg.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.ProyectoFinalArgProg.crud.repository.SearchRepository;
import org.springframework.stereotype.Service;
import com.ProyectoFinalArgProg.crud.security.entity.Usuario;
import java.util.List;



@Service
public class SearchService {

  @Autowired
  private SearchRepository searchRepository; 

  public List<Usuario> searchUsers(String query) {
    return searchRepository.findByNombreUsuarioContainingIgnoreCase(query);
  }
}
