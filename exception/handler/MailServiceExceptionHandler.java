package br.com.novio.mailservice.mailservice.exception.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.novio.mailservice.mailservice.exception.BadRequestException;
import br.com.novio.mailservice.mailservice.exception.ConflictException;
import br.com.novio.mailservice.mailservice.exception.NotFoundException;
import br.com.novio.mailservice.mailservice.exception.UnauthorizedException;
import br.com.novio.mailservice.mailservice.exception.error.APIError;
import br.com.novio.mailservice.mailservice.exception.error.ErrorItem;


@SuppressWarnings({"rawtypes", "unchecked"})
@ControllerAdvice(annotations=RestController.class)
@RestControllerAdvice
public class MailServiceExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({BadRequestException.class, NumberFormatException.class})
	public ResponseEntity badRequestException(final BadRequestException e) {
		APIError error = new APIError();

		error.getItems().add(new ErrorItem("Requisição mal formada",
				e.getMessage(), 400));
		return ResponseEntity.status(400).body(error);
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity notFound(final NotFoundException n) {
		APIError error = new APIError();

		error.getItems().add(new ErrorItem("Recurso não encontrado",
				n.getMessage(), 404));
		return ResponseEntity.status(404).body(error);
	}
	
	@ExceptionHandler(ConflictException.class)
	public ResponseEntity conflictException(final ConflictException c) {		
		APIError error = new APIError();

		error.getItems().add(new ErrorItem("Recurso já cadastrado",
				c.getMessage(), 409));
		return ResponseEntity.status(409).body(error);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<APIError> unauthorizedException(final UnauthorizedException u) {	
		APIError error = new APIError();
		error.getItems().add(new ErrorItem("Não autorizado", "Você não possui autorização para acessar este recurso", 401));
		
		return new ResponseEntity(error, HttpStatus.UNAUTHORIZED);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		APIError error = new APIError();

		error.getItems().add(new ErrorItem("Requisição mal formada",
				"Informações de entrada inválidas", 400));
		return ResponseEntity.status(400).body(error);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		APIError error = new APIError();
		Pattern p = Pattern.compile("\\[\"(.*?)\"\\]");
		Matcher m = p.matcher(ex.getMessage());
		String field = " - ";
		
		if (m.find()) {
			field += m.group(0);
			field = field.replaceAll("\\[", "").replaceAll("\\]","");
			field = field.replaceAll("\"", "");
		}
		
		String message = "Formato de entrada inválido" + field;
		error.getItems().add(new ErrorItem("Requisição mal formada",
				message, 400));
		
		return ResponseEntity.status(400).body(error);
	}
}
