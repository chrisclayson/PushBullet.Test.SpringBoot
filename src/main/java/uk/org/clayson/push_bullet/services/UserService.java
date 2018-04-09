package uk.org.clayson.push_bullet.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.org.clayson.push_bullet.model.entity.User;
import uk.org.clayson.push_bullet.model.repository.UserRepository;

@Service
public class UserService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository repository;
	
	public User getUser(String username) {
		Optional<User> user = repository.findById(username);
		if (!user.isPresent()) return null;
		return user.get(); 
	}
	
	public Iterable<User> getAll() {
		return repository.findAll();
	}
	
	public synchronized User createUser(User user) {
		
		user.setCreateTime(LocalDateTime.now());
		user.setNumOfNotificationsPushed(0);
		return repository.save(user);
	}
	
	public Boolean userExists(String username) {
		return repository.existsById(username);
	}
	
	public synchronized User incrementMessageCount(String username) {
		
		Optional<User> user = repository.findById(username);
		if (user.isPresent()) {
			User u = user.get();
			long count = u.getNumOfNotificationsPushed() + 1;
			log.info(String.format("User %s count incremented to %d", username, count));
			u.setNumOfNotificationsPushed(count);
			
			return repository.save(u);
		}
		return null;
	}
	
}
