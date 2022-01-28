package com.qa.user_app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.qa.user_app.data.entity.User;
import com.qa.user_app.data.repository.UserRepository;
import com.qa.user_app.exceptions.UserNotFoundException;

// No need to use the spring boot context, just create stubs using pure Mockito rather than Springs variant of Mockito
@ExtendWith(MockitoExtension.class) // JUnit test runner
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
		users.addAll(List.of(new User(1, "bob", "lee", 22), new User(2, "fred", "see", 25),
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
		long id = expectedUserWithId.getId();
		when(userRepository.findById(id)).thenReturn(Optional.of(expectedUserWithId));
		assertThat(userService.getById(id)).isEqualTo(expectedUserWithId);
		verify(userRepository).findById(id);
	}
	
	@Test
	public void getUserByInvalidIdTest() {
		// Arrange-Act-Assert testing structure
		// - simplifies testing
		
		// Arrange (the data and components under test)
		long id = 34;
		when(userRepository.findById(id)).thenReturn(Optional.empty());
		
		// Act (perform the action under test)
		// assert that the code in the lambda (second param) throws the exception specified in
		// the first param
		UserNotFoundException e = Assertions.assertThrows(UserNotFoundException.class, () -> {
			userService.getById(id);
		});
		
		// Assert (the action was successful)
		String expected = "User with id " + id + " does not exist";
		assertThat(e.getMessage()).isEqualTo(expected);
		verify(userRepository).findById(id);
	}

	@Test
	public void updateUserTest() {
		// id and userWithUpdatesToMake are passed to service.update()
		long id = expectedUserWithId.getId();
		User userWithUpdatesToMake = new User(expectedUserWithId.getId(),
											  expectedUserWithId.getForename(), 
											  expectedUserWithId.getSurname(), 
											  expectedUserWithId.getAge() + 1);
		
		// expectedUserWithId is the one in the database that we are pretending is their
		when(userRepository.existsById(id)).thenReturn(true);
		when(userRepository.getById(id)).thenReturn(expectedUserWithId);
		when(userRepository.save(expectedUserWithId)).thenReturn(userWithUpdatesToMake);
		
		assertThat(userService.update(id, userWithUpdatesToMake)).isEqualTo(userWithUpdatesToMake);
		verify(userRepository).existsById(id);
		verify(userRepository).getById(id);
		verify(userRepository).save(expectedUserWithId);
	}

	@Test
	public void deleteUserTest() {
		long id = expectedUserWithId.getId();
		when(userRepository.existsById(id)).thenReturn(true);
		userService.delete(id);
		verify(userRepository).existsById(id);
		verify(userRepository).deleteById(id);
	}
}
