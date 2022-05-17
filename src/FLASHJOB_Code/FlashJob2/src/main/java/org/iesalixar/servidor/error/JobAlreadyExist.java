package org.iesalixar.servidor.error;

public class JobAlreadyExist extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public JobAlreadyExist(Long id) {
		super("El anuncio con id: "+id+" ya existe.");
	}
}
