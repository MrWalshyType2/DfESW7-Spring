package com.qa.user_app.controller.request_object;

import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class UserRequest {

	@NotNull
	@Length(min = 1, message = "Names cannot be empty")
	private String forename;
	
	@NotNull
	@Length(min = 1)
	private String surname;
	
	@Max(130)
	@Min(18)
	private Integer age;

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, forename, surname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRequest other = (UserRequest) obj;
		return Objects.equals(age, other.age) && Objects.equals(forename, other.forename)
				&& Objects.equals(surname, other.surname);
	}
	
}
