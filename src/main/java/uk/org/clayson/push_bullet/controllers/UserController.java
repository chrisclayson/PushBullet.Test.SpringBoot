package uk.org.clayson.push_bullet.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import uk.org.clayson.push_bullet.exception.ValidationFailureException;
import uk.org.clayson.push_bullet.model.dto.UserDTO;
import uk.org.clayson.push_bullet.model.entity.User;
import uk.org.clayson.push_bullet.services.UserService;

@RestController
@RequestMapping(value="/users")
public class UserController {

	@Autowired
	UserService userService;
	
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody UserDTO register(@RequestBody UserDTO user) throws ValidationFailureException {
		
		if ( user.getUsername() == null || user.getUsername().trim().isEmpty()) throw new ValidationFailureException("Username is required");
		if ( user.getAccessToken() == null || user.getAccessToken().trim().isEmpty()) throw new ValidationFailureException("Access Token is required");
		ModelMapper mapper = new ModelMapper();
		User userEntity = mapper.map(user, User.class);
		userEntity = userService.createUser(userEntity);
		
		return mapper.map(userEntity, UserDTO.class);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public Iterable<UserDTO> listUsers() {
		ModelMapper mapper = new ModelMapper();
		List<UserDTO> users = new ArrayList<UserDTO>();
		
		for( User u : this.userService.getAll() ) {			
			users.add(mapper.map(u, UserDTO.class));
		}
		return users;
	}
	
	
}
