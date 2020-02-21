package br.com.novio.mailservice.mailservice.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.novio.mailservice.mailservice.exception.BadRequestException;
import br.com.novio.mailservice.mailservice.model.Mail;
import br.com.novio.mailservice.mailservice.service.IMail;

@RestController
@RequestMapping(value = "/mails")
public class MailController {

	private final IMail mail;
	
	public MailController(IMail mail) {
		this.mail = mail;
	}

	@PostMapping()
	public ResponseEntity<Mail> send(@Valid @RequestBody Mail email) {
			Mail sendMail = mail.send(email);		
			return new ResponseEntity<Mail>(sendMail, HttpStatus.OK);
	}
}
