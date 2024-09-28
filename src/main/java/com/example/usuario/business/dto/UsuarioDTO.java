package com.example.usuario.business.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {
	private String nome, email, senha;
	private List<EnderecoDTO> enderecos;
	private List<TelefoneDTO> telefones;

}
