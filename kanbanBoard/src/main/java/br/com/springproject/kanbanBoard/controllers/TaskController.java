package br.com.springproject.kanbanBoard.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.springproject.kanbanBoard.models.Status;
import br.com.springproject.kanbanBoard.models.Task;
import br.com.springproject.kanbanBoard.models.User;
import br.com.springproject.kanbanBoard.repositories.TaskRepository;
import br.com.springproject.kanbanBoard.repositories.UserRepository;
import br.com.springproject.kanbanBoard.validator.TaskValidator;

@Controller
@RequestMapping("/tasks")
public class TaskController {
	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	TaskValidator taskValidator;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/{id}")
	public ModelAndView show(@PathVariable Long id) {
		
		ModelAndView mv = new ModelAndView("tasks/show");
		
		try {
			Optional<Task> task = taskRepository.findById(id);
			if(task.isPresent()) {
				mv.addObject("task", task.get());
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return mv;
	}
	
	@GetMapping("/new/{boardId}")
	public ModelAndView newTask(Task task, @PathVariable Long boardId) {
	
		ModelAndView mv = new ModelAndView("tasks/new");
		List<User> userList = userRepository.findAll();
		task.setBoardId(boardId);
		mv.addObject("userList", userList);
		
		return mv;
	}
	
	@PostMapping
	public ModelAndView create(Task task, BindingResult br) {	
		
		//Validate name
		try {
			taskValidator.isNameValid(task);			
		} catch (Exception e) {
			br.rejectValue("name", "error.user", e.getMessage());	
		}
		
		try {
			taskValidator.isAssigneToValid(task);
		} catch (Exception e) {
			br.rejectValue("assignedTo", "error.user", e.getMessage());
		}
		
		
		if(br.hasErrors()) {
			ModelAndView mv = new ModelAndView("tasks/new");
			List<User> userList = userRepository.findAll();
			mv.addObject("userList", userList);
			
			return mv;
		}
		
		ModelAndView mv = new ModelAndView("redirect:/boards");	
		try {
			task.setStatus(Status.TO_DO);
			taskRepository.save(task);	
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return mv;
	}
}
