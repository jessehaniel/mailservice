package br.com.novio.mailservice.mailservice.exception.handler.mail;

public class AddressException extends RuntimeException {

private static final long serialVersionUID = 1L;
	
	public AddressException() {
		super();
	}
	
	public AddressException(String campo) {
		super(campo);
	}
}