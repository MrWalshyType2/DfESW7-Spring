package com.qa.user_app.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.user_app.data.entity.User;
import com.qa.user_app.data.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
	@Autowired // dependency injection
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}
	
	public User getById(Long id) {
		if (userRepository.existsById(id)) {
			return userRepository.findById(id).get();
		}
		throw new EntityNotFoundException("User with id " + id + " does not exist");
	}
	
	public User create(User user) {
		User savedUser = userRepository.save(user);
		return savedUser;
	}
	
	public User update(long id, User user) {
		// 1. Check if the user exists
		if (userRepository.existsById(id)) {
			// 2. get user in db
			User userInDb = userRepository.getById(id);
			
			// 3. Update users fields
			userInDb.setAge(user.getAge());
			userInDb.setForename(user.getForename());
			userInDb.setSurname(user.getSurname());
			
			// 4. Save the updated user
			return userRepository.save(userInDb);
		} else {
			throw new EntityNotFoundException("User with id " + id + " does not exist");
		}
	}
	
	public void delete(Long id) {
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
		} else {
			throw new EntityNotFoundException("User with id " + id + " does not exist");
		}
	}
}
