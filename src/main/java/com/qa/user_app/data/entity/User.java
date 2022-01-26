package com.qa.user_app.data.entity;

import java.util.Objects;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
//hibernate requires we also annotate domain entities
// with the @Entity annotation
@Entity 
@Table(name = "user") // optional, allows us to specify the tables name
public class User {

	@Id // any class marked with @Entity must have @Id to signify
		// the primary key field
	// this tells hibernate to auto-increment IDs
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Length(min = 1, message = "Names cannot be empty")
	private String forename;
	
	@NotNull
	@Length(min = 1)
	private String surname;
	
	@Max(130)
	@Min(18)
	private Integer age;

	// for JPA to work, we need an empty
	// default constructor for Hibernate to use
	public User() {
		super();
	}

	public User(String forename, String surname, Integer age) {
		super();
		this.forename = forename;
		this.surname = surname;
		this.age = age;
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", forename=" + forename + ", surname=" + surname + ", age=" + age + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, forename, id, surname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(age, other.age) && Objects.equals(forename, other.forename)
				&& Objects.equals(id, other.id) && Objects.equals(surname, other.surname);
	}

	
}
