package com.qa.user_app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.user_app.data.entity.User;
import com.qa.user_app.service.UserService;

//@SpringBootTest // this will start a full application context
// Start an application context with only beans required for the controller layer
// - the specified controller is loaded into the context
// - some other web layer components, such as filters, will be initialised too
@WebMvcTest(UserController.class)
public class UserControllerWebIntegrationTest {

	@Autowired
	private UserController controller;
	
	// we need a fake UserService
	// - we can use Mockito to create a mock object
	@MockBean // we are using our defined UserService, but the methods
			  // will be mocked (we have to specify what is returned from them)
	private UserService userService;
	
	// we need some data for our tests
	private List<User> users; // list of users with ids
	private User userToCreate; // without id
	private User validUser; // with id
	
	@BeforeEach // junit5 (jupiter) annotation to run this method before every test
	public void init() {
		users = new ArrayList<>();
		users.addAll(List.of(new User(1, "bob", "lee", 22), 
				  			 new User(2, "fred", "see", 25),
				  			 new User(3, "sarah", "fee", 28)));
		userToCreate = new User("bob", "lee", 22);
		validUser = new User(1, "bob", "lee", 22);
	}
	
	@Test // junit annotation
	public void getAllUsersTest() {
		ResponseEntity<List<User>> expected = new ResponseEntity<List<User>>(users, HttpStatus.OK);
		// given (some initial data/conditions)
		// this is being performed by init()
		
		// when (the action does occur)
		when(userService.getAll()).thenReturn(users);
		
		// then (assert this happened)
		ResponseEntity<List<User>> actual = controller.getUsers();
		assertThat(expected).isEqualTo(actual); // static assertThat method from assertJ
		
		// we also need to verify that the service was called by the controller
		verify(userService, times(1)).getAll(); // verify and times are from mockito
		// verify(userService).getAll(); // equivalent to the above line
	}
	
	@Test
	public void createUserTest() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/user/" + String.valueOf(validUser.getId()));
		ResponseEntity<User> expected = new ResponseEntity<User>(validUser, headers, HttpStatus.CREATED);
		
		// when
		when(userService.create(userToCreate)).thenReturn(validUser);
		
		// then
		ResponseEntity<User> actual = controller.createUser(userToCreate);
		assertEquals(expected, actual);
		
		verify(userService).create(userToCreate);
	}
	
	@Test
	public void getUserByIdTest() {
		// TODO: Implement me
		fail("Need implementing");
	}
	
	@Test
	public void updateUserTest() {
		// TODO: Implement me
		fail("Need implementing");
	}
	
	@Test
	public void deleteUserTest() {
		// TODO: Implement me
		fail("Need implementing");
	}
}
