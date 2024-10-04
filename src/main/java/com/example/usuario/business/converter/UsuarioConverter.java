package com.example.usuario.business.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.usuario.business.dto.EnderecoDTO;
import com.example.usuario.business.dto.TelefoneDTO;
import com.example.usuario.business.dto.UsuarioDTO;
import com.example.usuario.infrastructure.entity.Endereco;
import com.example.usuario.infrastructure.entity.Telefone;
import com.example.usuario.infrastructure.entity.Usuario;

@Component
public class UsuarioConverter {
	
	public Usuario forUsuario(UsuarioDTO uDTO) {
		return Usuario.builder()
				.nome(uDTO.getNome())
				.email(uDTO.getEmail())
				.senha(uDTO.getSenha())
				.enderecos(uDTO.getEnderecos() != null ? forListEndereco(uDTO.getEnderecos()) : null)
				.telefones(uDTO.getTelefones() != null ? forListTelefone(uDTO.getTelefones()) : null)
				.build();
	}

	public List<Endereco> forListEndereco(List<EnderecoDTO> list) {
		return list.stream().map(this::forEndereco).toList();
	}
	
	public Endereco forEndereco(EnderecoDTO eDTO) {
		return Endereco.builder()
				.rua(eDTO.getRua())
				.numero(eDTO.getNumero())
				.cidade(eDTO.getCidade())
				.complemento(eDTO.getComplemento())
				.cep(eDTO.getCep())
				.estado(eDTO.getEstado())
				.build();
	}
	
	public List<Telefone> forListTelefone(List<TelefoneDTO> list) {
		return list.stream().map(this::forTelefone).toList();
	}
	
	public Telefone forTelefone(TelefoneDTO uDTO)  {
		return Telefone.builder()
				.numero(uDTO.getNumero())
				.ddd(uDTO.getDdd())
				.build();
	}
	
	public UsuarioDTO forUsuarioDTO(Usuario user) {
		return UsuarioDTO.builder()
				.nome(user.getNome())
				.email(user.getEmail())
				.senha(user.getSenha())
				.enderecos(user.getEnderecos() != null ? forListEnderecoDTO(user.getEnderecos()) : null)
				.telefones(user.getTelefones() != null ? forListTelefoneDTO(user.getTelefones()) : null)
				.build();
	}

	public List<EnderecoDTO> forListEnderecoDTO(List<Endereco> list) {
		return list.stream().map(this::forEnderecoDTO).toList();
	}
	
	public EnderecoDTO forEnderecoDTO(Endereco endereco) {
		return EnderecoDTO.builder()
				.id(endereco.getId())
				.rua(endereco.getRua())
				.numero(endereco.getNumero())
				.cidade(endereco.getCidade())
				.complemento(endereco.getComplemento())
				.cep(endereco.getCep())
				.estado(endereco.getEstado())
				.build();
	}
	
	public List<TelefoneDTO> forListTelefoneDTO(List<Telefone> list) {
		return list.stream().map(this::forTelefoneDTO).toList();
	}
	
	public TelefoneDTO forTelefoneDTO(Telefone telefone)  {
		return TelefoneDTO.builder()
				.id(telefone.getId())
				.numero(telefone.getNumero())
				.ddd(telefone.getDdd())
				.build();
	}
	
	public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario entity){
        return Usuario.builder()
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome())
                .id(entity.getId())
                .senha(usuarioDTO.getSenha() !=null ? usuarioDTO.getSenha() : entity.getSenha())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entity.getEmail())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .build();
    }
	
	public Endereco updateEndereco(EnderecoDTO dto, Endereco entity) {
		return Endereco.builder()
				.id(entity.getId())
				.usuario_id(entity.getUsuario_id())
				.rua(dto.getRua() != null ? dto.getRua() : entity.getRua())
				.numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
				.cidade(dto.getCidade() != null ? dto.getCidade() : entity.getCidade())
				.cep(dto.getCep() != null ? dto.getCep() : entity.getCep())
				.estado(dto.getEstado() != null ? dto.getEstado() : entity.getEstado())
				.complemento(dto.getComplemento() != null ? dto.getComplemento() : entity.getComplemento())
				.build();
	}
	
	public Telefone updateTelefone(TelefoneDTO dto, Telefone entity) {
		return Telefone.builder()
				.id(entity.getId())
				.usuario_id(entity.getUsuario_id())
				.numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
				.ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
				.build();
	}
	
	public Endereco forEnderecoEntity(EnderecoDTO dto, Long id) {
		return Endereco.builder()
				.rua(dto.getRua())
				.numero(dto.getNumero())
				.cidade(dto.getCidade())
				.cep(dto.getCep())
				.estado(dto.getEstado())
				.complemento(dto.getComplemento())
				.usuario_id(id)
				.build();
	}

	public Telefone forTelefoneEntity(TelefoneDTO dto, Long id) {
		return Telefone.builder()
				.ddd(dto.getDdd())
				.numero(dto.getNumero())
				.usuario_id(id)
				.build();
	}
}
