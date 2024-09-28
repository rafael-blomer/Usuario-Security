package com.example.usuario.infrastructure.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(String msg) {
		super(msg);
	}

	public ResourceNotFoundException(String mensagem, Throwable throwable){
        super(mensagem);
    }
}
