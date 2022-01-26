package com.qa.user_app.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.qa.user_app.data.entity.User;
import com.qa.user_app.data.repository.UserRepository;

@Profile("dev")
@Configuration
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {

	private UserRepository userRepository;
	
	@Autowired // dependency injection
	public ApplicationStartupListener(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	// The application has booted and its components are ready to server
	// content when this method fires
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		userRepository.saveAll(List.of(
				new User("Fred", "Daly", 32),
				new User("Sarah", "Daly", 36),
				new User("Bob", "Sir", 27)
		));
	}

}
