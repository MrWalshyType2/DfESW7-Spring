package com.qa.user_app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.qa.user_app.data.entity.User;
import com.qa.user_app.data.repository.UserRepository;

// No need to use the spring boot context, just create stubs using pure Mockito rather than Springs variant of Mockito
@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

	@Mock // equivalent to MockBean
	private UserRepository userRepository;
	
	@InjectMocks // equivalent to @Autowired
	private UserService userService;
	
	private List<User> users;
	private User expectedUserWithId;
	private User expectedUserWithoutId;
	
	@BeforeEach // junit5 (jupiter) annotation to run this method before every test
	public void init() {
		users = new ArrayList<>();
		users.addAll(List.of(new User(1, "bob", "lee", 22), 
				  			 new User(2, "fred", "see", 25),
				  			 new User(3, "sarah", "fee", 28)));
		expectedUserWithoutId = new User("bob", "lee", 22);
		expectedUserWithId = new User(1, "bob", "lee", 22);
	}
	
	@Test
	public void getAllUsersTest() {
		when(userRepository.findAll()).thenReturn(users);
		assertThat(userService.getAll()).isEqualTo(users);
		verify(userRepository).findAll();
	}
	
	@Test
	public void createUserTest() {
		when(userRepository.save(expectedUserWithoutId)).thenReturn(expectedUserWithId);
		assertThat(userService.create(expectedUserWithoutId)).isEqualTo(expectedUserWithId);
		verify(userRepository).save(expectedUserWithoutId);
	}
	
	@Test
	public void getUserByIdTest() {
		// TODO: Implement me
		fail("Implement me");
	}

	@Test
	public void updateUserTest() {
		// TODO: Implement me
		fail("Implement me");
	}

	@Test
	public void deleteUserTest() {
		// TODO: Implement me
		fail("Implement me");
	}
}
