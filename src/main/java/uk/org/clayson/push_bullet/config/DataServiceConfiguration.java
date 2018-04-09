package uk.org.clayson.push_bullet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.map.repository.config.EnableMapRepositories;

import uk.org.clayson.push_bullet.services.UserService;

@Configuration
@EnableMapRepositories("uk.org.clayson.push_bullet.model.repository")
public class DataServiceConfiguration {

	@Bean
	public UserService userUserService() {
		return new UserService();
	}
	
	
}
