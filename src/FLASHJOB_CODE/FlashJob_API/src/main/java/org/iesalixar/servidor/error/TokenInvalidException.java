package org.iesalixar.servidor.error;

public class TokenInvalidException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public TokenInvalidException() {
		super("El token no es válido");
	}
}
