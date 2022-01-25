package com.qa.user_app.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qa.user_app.data.entity.User;
import com.qa.user_app.data.repository.UserRepository;

@RestController // this is a bean that should be stored in the app context
@RequestMapping(path = "/user") // access this controller at localhost:8080/user
public class UserController {
	
	// UserController has-a JpaRepository
	// - How do we get this repository?
	// - To get the repository, we use dependency injection
	private UserRepository repository;
	
	@Autowired // indicates that the repository must be injected via dependency injection
	public UserController(UserRepository repository) {
		this.repository = repository;
	}

	// READ ALL
	@GetMapping // localhost:8080/user
	public List<User> getUsers() {
		return repository.findAll();
	}

	// READ BY ID
	// {id} is a path variable
	// we send requests to: localhost:8080/user/{id}
	@RequestMapping(path = "/{id}", method = { RequestMethod.GET })
	// @GetMapping(path = "/{id}")
	public User getUserById(@PathVariable("id") long id) {
		if (repository.existsById(id)) {
			return repository.findById(id).get();
		}
		return null;
	}

	// CREATE
	// RequestMapping(method = { RequestMethod.POST })
	@PostMapping // accepts requests to: localhost:8080/user using POST
	public User createUser(@Valid @RequestBody User user) {
		User savedUser = repository.save(user);
		return savedUser;
	}

	// UPDATE
	// update everything, aside from the id
	@PutMapping("/{id}") // localhost:8080/user/1
	public User updateUser(@PathVariable("id") long id, @Valid @RequestBody User user) {
		// repository.save() will overwrite entities that already exist in the db
		// TODO: implement update user
		
		// 1. Check if the user exists
		if (repository.existsById(id)) {
			// 2. get user in db
			User userInDb = repository.getById(id);
			
			// 3. Update users fields
			userInDb.setAge(user.getAge());
			userInDb.setForename(user.getForename());
			userInDb.setSurname(user.getSurname());
			
			// 4. Save the updated user
			return repository.save(userInDb);
		} else {
		// else if the user doesn't exist, save them to the database
			return repository.save(user);
		}
	}

	// DELETE
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable("id") long id) {
		if (repository.existsById(id)) {
			repository.deleteById(id);
		}
	}

}
