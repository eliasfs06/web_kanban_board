package br.com.springproject.kanbanBoard.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.springproject.kanbanBoard.models.Task;
import br.com.springproject.kanbanBoard.models.User;
import br.com.springproject.kanbanBoard.repositories.TaskRepository;
import br.com.springproject.kanbanBoard.repositories.UserRepository;

@Controller
@RequestMapping("/tasks")
public class TaskController {
	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/new")
	public ModelAndView newTask(Task task) {
		
		ModelAndView mv = new ModelAndView("tasks/new");
		List<User> userList = userRepository.findAll();
		mv.addObject("userList", userList);
		
		return mv;
	}
}
