package com.example.usuario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usuario.business.UsuarioService;
import com.example.usuario.business.dto.UsuarioDTO;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	@PostMapping
	public ResponseEntity<UsuarioDTO> insertUser(@RequestBody UsuarioDTO uDTO) {
		service.insertUsuario(uDTO);
		return ResponseEntity.ok().body(uDTO);
	}

}
