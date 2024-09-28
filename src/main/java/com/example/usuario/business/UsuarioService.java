package com.example.usuario.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.usuario.business.converter.UsuarioConverter;
import com.example.usuario.business.dto.EnderecoDTO;
import com.example.usuario.business.dto.TelefoneDTO;
import com.example.usuario.business.dto.UsuarioDTO;
import com.example.usuario.infrastructure.entity.Endereco;
import com.example.usuario.infrastructure.entity.Telefone;
import com.example.usuario.infrastructure.entity.Usuario;
import com.example.usuario.infrastructure.exceptions.ConflictException;
import com.example.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.example.usuario.infrastructure.repository.EnderecoRepository;
import com.example.usuario.infrastructure.repository.TelefoneRepository;
import com.example.usuario.infrastructure.repository.UsuarioRepository;
import com.example.usuario.security.JwtUtil;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repo;
	@Autowired
	private UsuarioConverter converter;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private JwtUtil jwt;
	@Autowired
	private EnderecoRepository endRepo;
	@Autowired
	private TelefoneRepository telRepo;

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
	
	public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto){
        String email = jwt.extrairEmailToken(token.substring(7));
        dto.setSenha(dto.getSenha() != null ? encoder.encode(dto.getSenha()) : null);
        Usuario usuarioEntity = repo.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não localizado"));
        Usuario usuario = converter.updateUsuario(dto, usuarioEntity);
        return converter.forUsuarioDTO(repo.save(usuario));
    }
	
	public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO dto) {
		Endereco entity = endRepo.findById(idEndereco).orElseThrow(() -> new ResourceNotFoundException("Endereco não encotrado."));
		Endereco endereco = converter.updateEndereco(dto, entity);
		return converter.forEnderecoDTO(endRepo.save(endereco));
	}
	
	public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO dto) {
		Telefone entity = telRepo.findById(idTelefone).orElseThrow(() -> new ResourceNotFoundException("Telefone não encotrado."));
		Telefone telefone = converter.updateTelefone(dto, entity);
		return converter.forTelefoneDTO(telRepo.save(telefone));
	}
	
	public EnderecoDTO cadastraEndereco(String token, EnderecoDTO dto) {
		String email = jwt.extrairEmailToken(token.substring(7));
		Usuario usuario = repo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não encontrado " + email));
		Endereco endereco = converter.forEnderecoEntity(dto, usuario.getId());
		Endereco enderecoEntity = endRepo.save(endereco);
		return converter.forEnderecoDTO(enderecoEntity);
	}
	
	public TelefoneDTO cadastraTelefone(String token, TelefoneDTO dto) {
		String email = jwt.extrairEmailToken(token.substring(7));
		Usuario usuario = repo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não encontrado " + email));
		Telefone telefone = converter.forTelefoneEntity(dto, usuario.getId());
		Telefone telefoneEntity = telRepo.save(telefone);
		return converter.forTelefoneDTO(telefoneEntity);
	}
}
