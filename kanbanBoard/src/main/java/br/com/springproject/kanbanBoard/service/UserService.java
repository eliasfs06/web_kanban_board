package br.com.springproject.kanbanBoard.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.springproject.kanbanBoard.repositories.TaskRepository;
import br.com.springproject.kanbanBoard.repositories.UserRepository;
import br.com.springproject.kanbanBoard.validator.UserValidator;
import br.com.springproject.kanbanBoard.models.Task;
import br.com.springproject.kanbanBoard.models.User;


@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserValidator userValidator;
	
	@Autowired
	TaskRepository taskRepository;
	
	public void deleteUser(Long id) throws EmptyResultDataAccessException, Exception{
		
		userValidator.userOwnsBoards(id);
		
		Optional<User> userOpt = userRepository.findById(id);
		if(userOpt.isPresent()) { 
			User user = userOpt.get();
			List<Task> tasks = taskRepository.findAllByUser(user);
			for(Task t : tasks) {
				t.setAssignedTo(null);
			}
		}
		
		userRepository.deleteById(id);
	}
}
