package br.com.springproject.kanbanBoard.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.springproject.kanbanBoard.models.Role;
import br.com.springproject.kanbanBoard.models.Task;
import br.com.springproject.kanbanBoard.models.User;
import br.com.springproject.kanbanBoard.repositories.TaskRepository;


@Service
public class TaskValidator {
	
	@Autowired
	TaskRepository taskRepository;

	public void isNameValid(Task task) throws Exception {	
		
		if(task.getName().isEmpty() || task.getName().trim() == "") {
			throw new Exception("The task name can not be empty!");		
		} else if(task.getName().length() > 30) {
			throw new Exception("The task name must be 30 characters or less!");
		}
	}
	
	
	public void isAssigneToValid(Task task) throws Exception {	
		
		if (task.getAssignedTo() != null && !validateTaskAssignedTo(task.getAssignedTo())) {
			throw new Exception("This user can not own a Task!");
		}
		
	}
	
	/*
	 * An user must have ADMIN, BOARD_USER or TASK_USER  roles to own a Task
	 */
	public boolean validateTaskAssignedTo(User assignedTo) {
		
		if(assignedTo.getRole() == Role.COMMENT_USER) return false;

		return true;
	}
		
}

