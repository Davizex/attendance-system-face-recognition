package br.com.rekome.handle;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.rekome.abstracts.AbstractExceptionHandler;

@RestControllerAdvice
public class ExceptionHandle extends AbstractExceptionHandler{

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Exception> handleException(Exception exception, HttpHeaders headers) {
		return new ResponseEntity<Exception>(exception, headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
