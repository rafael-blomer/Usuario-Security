package com.example.usuario.infrastructure.exceptions;

public class ConflictException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ConflictException(String mensagem){
        super(mensagem);
    }

    public ConflictException(String mensagem, Throwable throwable){
        super(mensagem);
    }

}
