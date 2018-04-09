package uk.org.clayson.push_bullet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends Exception{

	private static final long serialVersionUID = 5280278261312851452L;

	public UserNotFoundException(String message) {
		super(message);
	}
}
