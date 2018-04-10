package uk.org.clayson.push_bullet.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import uk.org.clayson.push_bullet.exception.ValidationFailureException;
import uk.org.clayson.push_bullet.model.dto.UserDTO;
import uk.org.clayson.push_bullet.model.entity.User;
import uk.org.clayson.push_bullet.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTests {

	@Autowired
	private UserController userController;
	
	@Autowired
	private UserService userService;	
	
	@Before
	public void init() {
		userService.Clear();
	}
	
	@Test
	public void addUserSucceeds() throws ValidationFailureException {
		UserDTO u1 = new UserDTO();
		
		u1.setUsername("Test1");
		u1.setAccessToken("accessToken");
		
		userController.register(u1);
		
		User u2 = userService.getUser("Test1");
		
		Assert.assertNotNull(u2);
		Assert.assertEquals(u1.getUsername(), u2.getUsername());
		Assert.assertEquals(u1.getAccessToken(), u1.getAccessToken());
		Assert.assertNotNull(u2.getCreateTime());
		Assert.assertEquals(0,  u2.getNumOfNotificationsPushed());
	}

	@Test(expected = ValidationFailureException.class)
	public void addDuplicateUserFails() throws ValidationFailureException {
		UserDTO u1 = new UserDTO();
		
		u1.setUsername("Test1");
		u1.setAccessToken("accessToken");
		
		userController.register(u1);
		
		UserDTO u2 = new UserDTO();
		u2.setUsername("Test1");
		u2.setAccessToken("anotherAccessToken");
		
		userController.register(u1);
	}	

	@Test
	public void addTenUsersSucceed() throws ValidationFailureException {

		for( int i = 1; i <= 10; i++) {
			UserDTO u = new UserDTO();
			
			u.setUsername(String.format("Test%d", i));
			u.setAccessToken("accessToken");
			
			userController.register(u);
		}
		Assert.assertEquals(userService.Count(), 10);
		
	}		

	@Test(expected = ValidationFailureException.class)
	public void addUserWithNullUsernameFails() throws ValidationFailureException {
		UserDTO u1 = new UserDTO();
		
		u1.setUsername(null);
		u1.setAccessToken("accessToken");
		
		userController.register(u1);
	}		

	@Test(expected = ValidationFailureException.class)
	public void addUserWithEmptyNullFails() throws ValidationFailureException {
		UserDTO u1 = new UserDTO();
		
		u1.setUsername("Test");
		u1.setAccessToken(null);
		
		userController.register(u1);
	}	
	
	@Test(expected = ValidationFailureException.class)
	public void addUserWithEmptyUsernameFails() throws ValidationFailureException {
		UserDTO u1 = new UserDTO();
		
		u1.setUsername("");
		u1.setAccessToken("accessToken");
		
		userController.register(u1);
	}		

	@Test(expected = ValidationFailureException.class)
	public void addUserWithEmptyAccessTokenFails() throws ValidationFailureException {
		UserDTO u1 = new UserDTO();
		
		u1.setUsername("Test");
		u1.setAccessToken("");
		
		userController.register(u1);
	}	

	@Test(expected = ValidationFailureException.class)
	public void addUserWithOnlySpacesUsernameFails() throws ValidationFailureException {
		UserDTO u1 = new UserDTO();
		
		u1.setUsername("   ");
		u1.setAccessToken("accessToken");
		
		userController.register(u1);
	}		

	@Test(expected = ValidationFailureException.class)
	public void addUserWithOnlySpacesAccessTokenFails() throws ValidationFailureException {
		UserDTO u1 = new UserDTO();
		
		u1.setUsername("Test");
		u1.setAccessToken("   ");
		
		userController.register(u1);
	}	
}
