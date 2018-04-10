package uk.org.clayson.push_bullet.controllers;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import uk.org.clayson.push_bullet.exception.PushBulletException;
import uk.org.clayson.push_bullet.exception.UserNotFoundException;
import uk.org.clayson.push_bullet.exception.ValidationFailureException;
import uk.org.clayson.push_bullet.model.dto.NotificationDTO;
import uk.org.clayson.push_bullet.model.entity.User;
import uk.org.clayson.push_bullet.services.PushBulletService;
import uk.org.clayson.push_bullet.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotificationControllerTests {

	@InjectMocks
	private NotificationController notificationController;
	
	@Mock
	private UserService userService;	

	@Mock
	private PushBulletService pbService;
	
	private User user;
	private NotificationDTO notification;
	
	@Before
	public void init() throws PushBulletException, ValidationFailureException {
		MockitoAnnotations.initMocks(this);

		user = new User();
		user.setUsername("test1");
		user.setAccessToken("anAccessToken");
		notification = new NotificationDTO();
		notification.setTitle("Test Message Title");
		notification.setBody("Test Message Body");
		userService.Clear();
		userService.createUser(user);
		when(userService.getUser("test1")).thenReturn(user);
		when(pbService.sendNotification(user, notification)).thenReturn("anIdentity");		
	}
	
	@Test
	public void sendNotificationSucceeds() throws UserNotFoundException, PushBulletException, ValidationFailureException {		
		NotificationDTO result = notificationController.sendNotification("test1", notification);
		Assert.assertEquals("anIdentity", result.getIdentity());
	}
	
	@Test(expected = UserNotFoundException.class)
	public void sendNotificationToInvalidUserFails() throws UserNotFoundException, PushBulletException, ValidationFailureException {
		notificationController.sendNotification("test2", notification);
	}	
	
	@Test(expected = ValidationFailureException.class)
	public void sendNotificationWithNullBodyFails() throws UserNotFoundException, PushBulletException, ValidationFailureException {
		NotificationDTO n1 = new NotificationDTO();
		n1.setTitle("Notification Title");
		n1.setBody(null);
		notificationController.sendNotification("test1", n1);
	}		

	@Test(expected = ValidationFailureException.class)
	public void sendNotificationWithEmptyBodyFails() throws UserNotFoundException, PushBulletException, ValidationFailureException {
		NotificationDTO n1 = new NotificationDTO();
		n1.setTitle("Notification Title");
		n1.setBody("");
		notificationController.sendNotification("test1", n1);
	}		

}
