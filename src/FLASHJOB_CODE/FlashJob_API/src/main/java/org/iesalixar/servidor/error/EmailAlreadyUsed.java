package org.iesalixar.servidor.error;

public class EmailAlreadyUsed extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmailAlreadyUsed() {
		super("El email se está usando ya");
	}
}
