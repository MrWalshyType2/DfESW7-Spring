package com.qa.user_app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.qa.user_app.service.UserService;

@RestController // this is a bean that should be stored in the app context
@RequestMapping(path = "/user") // access this controller at localhost:8080/user
public class UserController {
	
	// UserController has-a JpaRepository
	// - How do we get this repository?
	// - To get the repository, we use dependency injection
	private UserService userService;
	
	@Autowired // indicates that the repository must be injected via dependency injection
	public UserController(UserService userService) {
		this.userService = userService;
	}

	// READ ALL
	// GET requests do not have a body
	@GetMapping // localhost:8080/user
	public ResponseEntity<List<User>> getUsers() {
		ResponseEntity<List<User>> users = ResponseEntity.ok(userService.getAll());									 
		return users;
	}

	// READ BY ID
	// {id} is a path variable
	// we send requests to: localhost:8080/user/3
	@RequestMapping(path = "/{id}", method = { RequestMethod.GET })
	//@GetMapping(path = "/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
		User savedUser = userService.getById(id);
		
		ResponseEntity<User> response = ResponseEntity.status(HttpStatus.OK)
													  .body(savedUser);
		return response;
	}

	// CREATE
	// RequestMapping(method = { RequestMethod.POST })
	@PostMapping // accepts requests to: localhost:8080/user using POST
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		// the user object above is passed in the http requests body
		// - serialisation is converting an object into some file format, such as JSON or XML
		// - deserialisation is converting some file data, i.e, JSON or XML, into some object
		User savedUser = userService.create(user);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/user/" + String.valueOf(savedUser.getId()));
		
															  //(body, httpHeaders, responseStatusCode)
		ResponseEntity<User> response = new ResponseEntity<User>(savedUser, headers, HttpStatus.CREATED);
		return response;
	}

	// UPDATE
	// update everything, aside from the id
	@PutMapping("/{id}") // localhost:8080/user/1
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @Valid @RequestBody User user) {
		// repository.save() will overwrite entities that already exist in the db
		// TODO: implement update user
		return null;
	}

	// DELETE
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
		// TODO: implement delete user
		return null;
	}

}
