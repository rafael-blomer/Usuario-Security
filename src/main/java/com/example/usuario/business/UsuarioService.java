package com.example.usuario.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.usuario.business.converter.UsuarioConverter;
import com.example.usuario.business.dto.UsuarioDTO;
import com.example.usuario.infrastructure.entity.Usuario;
import com.example.usuario.infrastructure.exceptions.ConflictException;
import com.example.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.example.usuario.infrastructure.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repo;
	@Autowired
	private UsuarioConverter converter;
	@Autowired
	private PasswordEncoder encoder;

	public UsuarioDTO insertUsuario(UsuarioDTO uDTO) {
		emailExiste(uDTO.getEmail());
		uDTO.setSenha(encoder.encode(uDTO.getSenha()));
		Usuario user = converter.forUsuario(uDTO);
		return converter.forUsuarioDTO(repo.save(user));
	}

	public void emailExiste(String email) {
		try {
			boolean existe = verificaEmailExistente(email);
			if (existe) {
				throw new ConflictException("Email já cadastrado " + email);
			}
		} catch (ConflictException e) {
			throw new ConflictException("Email já cadastrado ", e.getCause());
		}
	}

	public boolean verificaEmailExistente(String email) {
		return repo.existsByEmail(email);
	}

	public UsuarioDTO buscarUsuarioPorEmail(String email) {
		try {
			return converter.forUsuarioDTO(repo.findByEmail(email)
					.orElseThrow(() -> new ResourceNotFoundException("Email não encontrado " + email)));
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Email não encontrado " + email);
		}
	}

	public void deletaUsuarioPorEmail(String email) {
		repo.deleteByEmail(email);
	}
}
