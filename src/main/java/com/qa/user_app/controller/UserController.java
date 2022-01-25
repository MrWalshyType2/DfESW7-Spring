package com.qa.user_app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

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

@RestController // this is a bean that should be stored in the app context
@RequestMapping(path = "/user") // access this controller at localhost:8080/user
public class UserController {
	
	private static long counter = 0;
	
	private List<User> users = new ArrayList<>(List.of(new User(counter++, "Fred", "Daly", 33), new User(counter++, "Sarah", "Daly", 33)));

	@GetMapping // localhost:8080/user
	public List<User> getUsers() {
		return users;
	}
	
	// {id} is a path variable
	// we send requests to: localhost:8080/user/{id}
	@RequestMapping(path = "/{id}", method = { RequestMethod.GET })
	//@GetMapping(path = "/{id}")
	public User getUserById(@PathVariable("id") int id) {
		for (User user : users) {
			if (user.getId() == id) {
				return user;
			}
		}
		throw new EntityNotFoundException("Entity with id " + id + " was not found.");
	}
	
	// RequestMapping(method = { RequestMethod.POST })
	@PostMapping // accepts requests to: localhost:8080/user using POST
	public User createUser(@RequestBody User user) {
		user.setId(counter++);
		users.add(user);
		return user;
	}
	
	@PutMapping("/{id}")
	public User updateUser(@PathVariable("id") long id, User user) {
		// TODO: Update user in list if they exist
		return null;
	}
	
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable("id") long id) {
		// TODO: Delete user in list if they exist
	}
}
