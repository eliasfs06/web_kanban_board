package br.com.springproject.kanbanBoard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.springproject.kanbanBoard.models.Person;
import br.com.springproject.kanbanBoard.models.User;
import br.com.springproject.kanbanBoard.repositories.PersonRepository;
import br.com.springproject.kanbanBoard.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	UserRepository userRepository;
	
	String[] invalidChars = {"@","#","$","%",")","(","?","=",".","*","[","}","{",",","?","~","=","+","/", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
	
	public void isNameValid(String userName) throws Exception {	
		
		if(userName.isEmpty()) {
			throw new Exception("The user name can not be empty!");
			
		} else if (!validateUserName(userName)) {
			throw new Exception("The user name can only contain letters!");
		
		}
		
	}
	
	/*
	 * User names can only contain letters. 
	 */
	public boolean validateUserName(String userName) {
				
		for(String c : invalidChars) {
			if(userName.contains(c)) return false;
		}
		return true;
		
	}
	
	public void isEmailValid(String userEmail) throws Exception {	
		
		if(userEmail.isEmpty()) {
			throw new Exception("The user email can not be empty!");
			
		} else if (!validateUserEmail(userEmail)) {
			throw new Exception("This e-mail name is not available!");
		
		}
		
	}
	/*
	 * Can not have two users with the same email. 
	 */
	public boolean validateUserEmail(String userEmail) {
		
		List<Person> persons = personRepository.findAllByEmail(userEmail);
		if(persons.isEmpty()) return true;
		
		return false;
		
	}
	
	public void isLoginValid(String userLogin) throws Exception {	
		
		if(userLogin.isEmpty()) {
			throw new Exception("The user login can not be empty!");
			
		} else if (!validateUserLogin(userLogin)) {
			throw new Exception("This login name is not available!");
		
		}
		
	}
	/*
	 * Can not have two users with the same login. 
	 */
	public boolean validateUserLogin(String userLogin) {
		
		List<User> users = userRepository.findByLogin(userLogin);
		if(users.isEmpty()) return true;
		
		return false;
		
	}
	
	public void isPasswordValid(String userPassword) throws Exception {	
		
		if(userPassword.isEmpty() || userPassword.length() < 6 || userPassword.length() > 12) {
			throw new Exception("The user password must be between 6 to 12 characters!");
			
		} else if (!validateUserPassword(userPassword)) {
			throw new Exception("The user password must have unless one number or special character!");
		}
		
	}
	/*
	 * Can not have two users with the same login. 
	 */
	public boolean validateUserPassword(String userPassword) {
			
		for(String c : invalidChars) {
			if(userPassword.contains(c)) return true;
		}
		return false;
		
	}
	

}
