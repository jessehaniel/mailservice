package br.com.novio.mailservice.mailservice.exception.handler.mail;

public class SendFailedException extends RuntimeException {

private static final long serialVersionUID = 1L;
	
	public SendFailedException() {
		super();
	}
	
	public SendFailedException(String campo) {
		super(campo);
	}
}
