package br.com.springproject.kanbanBoard.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="users") 
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne (cascade = CascadeType.ALL , orphanRemoval = true)
	private Person person;
	
	private String login;
	
	private String password;
	
    @Enumerated(EnumType.STRING)
    private Role role;
	
	public User() { }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public void setEmail(String email) {
		this.person.setEmail(email);
	}
	
	public String getEmail(){
		return this.person.getEmail();
	}
	
	public void setName(String name) {
		this.person.setName(name);
	}
	
	public String getName(){
		return this.person.getName();
	}
	
}
