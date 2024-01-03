package com.ProyectoFinalArgProg.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ProyectoFinalArgProg.crud.service.SearchService;
import com.ProyectoFinalArgProg.crud.security.entity.Usuario;

import java.util.List;

@RestController
@RequestMapping("/api/v1/portafolio/usuarios")
@CrossOrigin(origins = "*")
public class SearchController {

  @Autowired
  private SearchService searchService;

  @GetMapping("/search")
  public List<Usuario> searchUsers(@RequestParam String query) {
    return searchService.searchUsers(query);
  }
}
