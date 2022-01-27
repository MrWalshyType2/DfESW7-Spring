package com.qa.user_app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.user_app.data.entity.User;
import com.qa.user_app.data.repository.UserRepository;

@SpringBootTest
@Transactional
public class UserServiceIntegrationTest {
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	private List<User> usersInDatabase;
	private long nextNewElementsId;
	
	// initialise the database for each test as it is rolled back afterwards
	@BeforeEach
	public void init() {
		List<User> users = List.of(new User(1, "bob", "lee", 22), new User(2, "fred", "see", 25),
				new User(3, "sarah", "fee", 28));
		usersInDatabase = new ArrayList<>();
		usersInDatabase.addAll(userRepository.saveAll(users));
		int size = usersInDatabase.size();
		nextNewElementsId = usersInDatabase.get(size - 1).getId() + 1;
	}
	
	@Test
	public void getAllUsersTest() {
		assertThat(usersInDatabase).isEqualTo(userService.getAll());
	}
	
	@Test
	public void createUserTest() {
		User userToSave = new User("Janet", "Carlisle", 32);
		User expectedUser = new User(nextNewElementsId, userToSave.getForename(), userToSave.getSurname(), userToSave.getAge());
		
		assertThat(expectedUser).isEqualTo(userService.create(userToSave));
	}
	
	@Test
	public void getUserByIdTest() {
		User userInDb = usersInDatabase.get(0);
		assertThat(userService.getById(userInDb.getId())).isEqualTo(userInDb);
	}

	@Test
	public void updateUserTest() {
		User userInDb = usersInDatabase.get(0);
		long id = userInDb.getId();
		User userWithUpdatesToMake = new User(userInDb.getId(), 
											  userInDb.getForename(), 
											  userInDb.getSurname(), 
											  userInDb.getAge() + 1);
		
		User actual = userService.update(id, userWithUpdatesToMake);
		assertThat(actual).isEqualTo(userWithUpdatesToMake);
	}

	@Test
	public void deleteUserTest() {
		User userInDb = usersInDatabase.get(0);
		long id = userInDb.getId();
		
		userService.delete(id);
		
		assertThat(userRepository.findById(id)).isEqualTo(Optional.empty());
	}
}
