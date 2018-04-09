package uk.org.clayson.push_bullet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uk.org.clayson.push_bullet.exception.PushBulletException;
import uk.org.clayson.push_bullet.exception.UserNotFoundException;
import uk.org.clayson.push_bullet.exception.ValidationFailureException;
import uk.org.clayson.push_bullet.model.dto.NotificationDTO;
import uk.org.clayson.push_bullet.model.entity.User;
import uk.org.clayson.push_bullet.services.PushBulletService;
import uk.org.clayson.push_bullet.services.UserService;

@RestController
@RequestMapping(value="/notification")
public class NotificationController {

	@Autowired
	private PushBulletService service;

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/{username}", method=RequestMethod.POST)
	public NotificationDTO sendNotification(@PathVariable String username, @RequestBody NotificationDTO notification) throws UserNotFoundException, PushBulletException, ValidationFailureException {
		
		if ( notification.getBody() == null || notification.getBody().trim().isEmpty()) throw new ValidationFailureException("Body is required");

		User user = userService.getUser(username);
		
		if ( user == null ) {
			throw new UserNotFoundException(String.format("User %s does not exist", username));
		}else{
			notification.setIdentity(service.sendNotification(user, notification));
			userService.incrementMessageCount(username);
		}
		return notification;
	}
	
}
