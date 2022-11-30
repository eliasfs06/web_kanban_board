package br.com.springproject.kanbanBoard.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.springproject.kanbanBoard.models.Board;
import br.com.springproject.kanbanBoard.models.User;
import br.com.springproject.kanbanBoard.repositories.BoardRepository;
import br.com.springproject.kanbanBoard.repositories.PersonRepository;
import br.com.springproject.kanbanBoard.repositories.UserRepository;

@Service
public class UserValidator {

	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BoardRepository boardRepository;
	
	String[] invalidChars = {"@","#","$","%",")","(","?","=",".","*","[","}","{",",","?","~","=","+","/", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
	
	public void isNameValid(User user) throws Exception {	
		
		if(user.getName().isEmpty()) {
			throw new Exception("The user name can not be empty!");
			
		} else if (!validateUserName(user.getName())) {
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
	
	public void isEmailValid(User user) throws Exception {	
		
		if(user.getEmail().isEmpty()) {
			throw new Exception("The user email can not be empty!");
			
		} else if (!validateUserEmail(user)) {
			throw new Exception("This e-mail name is not available!");
		
		}
		
	}
	/*
	 * Can not have two users with the same email. 
	 */
	public boolean validateUserEmail(User user) {
		
		try {
			List<User> users = userRepository.findByEmail(user.getEmail());
			if(!users.isEmpty()) {
				for(User u : users) {
					if(u.getId() != user.getId()) return false;
				}
			}		
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return true;	
	}
	
	public void isLoginValid(User user) throws Exception {	
		
		if(user.getLogin().isEmpty()) {
			throw new Exception("The user login can not be empty!");
			
		} else if (!validateUserLogin(user)) {
			throw new Exception("This login name is not available!");
		
		}
		
	}
	/*
	 * Can not have two users with the same login. 
	 */
	public boolean validateUserLogin(User user) {
		
		try {
			List<User> users = userRepository.findByLogin(user.getLogin());
			if(!users.isEmpty()) {
				for(User u : users) {
					if(u.getId() != user.getId()) return false;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return true;
		
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
	
	public void userOwnsBoards(Long userId) {
		
		try {
			List<Board> userBoards = boardRepository.findAllByOwner(userId);
			if(userBoards.size() > 0)
				throw new Exception("This user can not be deleted because it owns " + userBoards.size() + " boards.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	

}
