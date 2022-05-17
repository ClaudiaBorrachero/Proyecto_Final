package org.iesalixar.servidor.error;

public class CreateJobError extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CreateJobError() {
		super("Error al crear o editar anuncio");
	}
	
}
