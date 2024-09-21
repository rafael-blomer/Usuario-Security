package com.example.usuario.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.usuario.infrastructure.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	boolean existsByEmail(String email);

	Optional<Usuario> findByEmail(String email);

	@Transactional
	void deleteByEmail(String email);
}
