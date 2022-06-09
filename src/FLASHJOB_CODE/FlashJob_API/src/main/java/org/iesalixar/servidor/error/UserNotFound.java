package org.iesalixar.servidor.error;

public class UserNotFound extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UserNotFound(Long id) {
		super("El usuario con id: "+id+" no ha sido encontrado.");
	}
	
}
