package uk.org.clayson.push_bullet.exception;

public class PushBulletException extends Exception{

	public PushBulletException(String message) {
		super(message);
	}
	
	public PushBulletException(String message, Throwable cause) {
		super(message, cause);
	}	

	private static final long serialVersionUID = -147624043538733822L;

}
