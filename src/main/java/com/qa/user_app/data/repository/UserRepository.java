package com.qa.user_app.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.user_app.data.entity.User;

@Repository // this signifies its a bean, not necessary though as it is inherited
public interface UserRepository extends JpaRepository<User, Long> {
	// User is the type of entity being stored in the db
	// Long is the type of the User entities id field
	
	// our repositories must be defined as interfaces as
	// spring and hibernate will generate the implementations
}
