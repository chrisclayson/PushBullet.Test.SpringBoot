package uk.org.clayson.push_bullet.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import uk.org.clayson.push_bullet.exception.ValidationFailureException;
import uk.org.clayson.push_bullet.model.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

	@Autowired
	private UserService userService;
	
	@Before
	public void init() {
		userService.Clear();
	}
	
	@Test
	public void addUserSucceeds() throws ValidationFailureException {
		User u1 = new User();
		
		u1.setUsername("Test1");
		u1.setAccessToken("accessToken");
		
		userService.createUser(u1);
		
		User u2 = userService.getUser("Test1");
		
		Assert.assertNotNull(u2);
		Assert.assertEquals(u1.getUsername(), u2.getUsername());
		Assert.assertEquals(u1.getAccessToken(), u1.getAccessToken());
		Assert.assertNotNull(u2.getCreateTime());
		Assert.assertEquals(0,  u2.getNumOfNotificationsPushed());
	}

	@Test(expected = ValidationFailureException.class)
	public void addDuplicateUserFails() throws ValidationFailureException {
		User u1 = new User();
		
		u1.setUsername("Test1");
		u1.setAccessToken("accessToken");
		
		userService.createUser(u1);
		
		User u2 = new User();
		u2.setUsername("Test1");
		u2.setAccessToken("anotherAccessToken");
		
		userService.createUser(u2);
	}	

	@Test
	public void incrementCountTests() throws ValidationFailureException {
		User u1 = new User();
		
		u1.setUsername("Test1");
		u1.setAccessToken("accessToken");
		
		userService.createUser(u1);
		
		userService.incrementMessageCount("Test1");
		
		u1 = userService.getUser("Test1");
		Assert.assertEquals(1, u1.getNumOfNotificationsPushed());
		
		userService.incrementMessageCount("Test1");
		
		u1 = userService.getUser("Test1");
		Assert.assertEquals(2, u1.getNumOfNotificationsPushed());

	}		

	@Test
	public void addTenUsersSucceed() throws ValidationFailureException {

		for( int i = 1; i <= 10; i++) {
			User u = new User();
			
			u.setUsername(String.format("Test%d", i));
			u.setAccessToken("accessToken");
			
			userService.createUser(u);
		}
		Assert.assertEquals(userService.Count(), 10);
		
	}		
	
	@Test
	public void clearSucceeds() throws ValidationFailureException {
		for( int i = 1; i <= 10; i++) {
			User u = new User();
			
			u.setUsername(String.format("Test%d", i));
			u.setAccessToken("accessToken");
			
			userService.createUser(u);
		}
		userService.Clear();
		Assert.assertEquals(userService.Count(), 0);
	}		
}
