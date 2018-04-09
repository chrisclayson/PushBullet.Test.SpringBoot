package uk.org.clayson.push_bullet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationFailureException extends Exception{

	private static final long serialVersionUID = 6414991772084831204L;

	public ValidationFailureException(String message) {
		super(message);
	}
}
