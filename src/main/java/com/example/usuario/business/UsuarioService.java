package com.example.usuario.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.usuario.business.converter.UsuarioConverter;
import com.example.usuario.business.dto.UsuarioDTO;
import com.example.usuario.infrastructure.entity.Usuario;
import com.example.usuario.infrastructure.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repo;
	@Autowired
	private UsuarioConverter converter;
	
	public UsuarioDTO insertUsuario(UsuarioDTO uDTO) {
		Usuario user = converter.forUsuario(uDTO);
		return converter.forUsuarioDTO(repo.save(user));
	}
}
