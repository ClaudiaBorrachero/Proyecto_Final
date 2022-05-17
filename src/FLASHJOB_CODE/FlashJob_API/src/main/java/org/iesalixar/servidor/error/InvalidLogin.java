package org.iesalixar.servidor.error;

public class InvalidLogin extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidLogin() {
		super("Credenciales erróneas");
	}
}
