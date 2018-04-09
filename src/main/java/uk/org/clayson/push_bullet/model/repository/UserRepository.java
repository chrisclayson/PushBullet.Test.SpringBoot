package uk.org.clayson.push_bullet.model.repository;

import org.springframework.data.repository.CrudRepository;

import uk.org.clayson.push_bullet.model.entity.User;

public interface UserRepository extends CrudRepository<User, String>{

}
