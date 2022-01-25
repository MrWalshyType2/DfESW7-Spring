package com.qa.user_app.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.user_app.data.entity.User;

@Repository // this signifies its a bean
public interface UserRepository extends JpaRepository<User, Long> {

}
