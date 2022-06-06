package org.iesalixar.servidor.error;

public class NotFound extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public NotFound() {
		super("Faltan parámetros para realizar la busqueda");
	}

}
