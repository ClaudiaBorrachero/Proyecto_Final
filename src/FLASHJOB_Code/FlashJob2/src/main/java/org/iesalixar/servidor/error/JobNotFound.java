package org.iesalixar.servidor.error;

public class JobNotFound extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public JobNotFound(Long id) {
		super("El anuncio con id: "+id+" no ha sido encontrado.");
	}
}
