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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.springproject.kanbanBoard.models.Comment;
import br.com.springproject.kanbanBoard.models.Task;
import br.com.springproject.kanbanBoard.models.User;
import br.com.springproject.kanbanBoard.repositories.CommentRepository;
import br.com.springproject.kanbanBoard.repositories.TaskRepository;
import br.com.springproject.kanbanBoard.repositories.UserRepository;
import br.com.springproject.kanbanBoard.validator.CommentValidator;

@Controller
@RequestMapping("/comments")
public class CommentController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	CommentValidator commentValidator;
	
	@Autowired
	TaskRepository taskRepository;
	
	@GetMapping("/new/{taskId}")
	public ModelAndView newTask(Comment comment, @PathVariable Long taskId) {
	
		ModelAndView mv = new ModelAndView("comments/new");
		List<User> userList = userRepository.findAll();
		mv.addObject("userList", userList);
		comment.setTaskId(taskId);
		
		return mv;
	}
	
	@PostMapping
	public ModelAndView create(Comment comment, BindingResult br, RedirectAttributes ra) {	
		
		try {
			commentValidator.isDescriptionValid(comment);
		} catch (Exception e) {
			br.rejectValue("description", "error.user", e.getMessage());	
		}
		
		try {
			commentValidator.isOwnerValid(comment);
		} catch (Exception e) {
			br.rejectValue("owner", "error.user", e.getMessage());	
		}
		
		
		if(br.hasErrors()) {
			ModelAndView mv = new ModelAndView("comments/new");
			List<User> userList = userRepository.findAll();
			mv.addObject("userList", userList);
			
			return mv;
		}
		
		try {
			commentRepository.save(comment);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Optional<Task> task = taskRepository.findById(comment.getTaskId());
		ra.addAttribute("task", task.get());
		ModelAndView mv = new ModelAndView("redirect:/tasks/"+comment.getTaskId());	
		
		return mv;
	}
	
}
