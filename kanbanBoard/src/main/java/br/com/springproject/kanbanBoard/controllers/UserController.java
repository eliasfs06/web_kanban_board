package br.com.springproject.kanbanBoard.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.springproject.kanbanBoard.models.Role;
import br.com.springproject.kanbanBoard.models.User;
import br.com.springproject.kanbanBoard.repositories.UserRepository;
import br.com.springproject.kanbanBoard.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;

	@GetMapping("")
	public ModelAndView index() {
		
		ModelAndView mv = new ModelAndView("users/index");
		
		try {
			List<User> users = userRepository.findAll();
			mv.addObject("users", users);
			
		} catch(RuntimeException e) {
			e.printStackTrace();
		}
		
		return mv;
	}
	
	@PostMapping
	public ModelAndView create(User user, BindingResult br) {
		
		try {
			userService.isNameValid(user.getPerson().getName());			
		} catch(Exception e) {
			br.rejectValue("person.name", "error.user", e.getMessage());	
		}
		
		try {
			userService.isEmailValid(user.getPerson().getEmail());			
		} catch(Exception e) {
			br.rejectValue("person.email", "error.user", e.getMessage());	
		}
		
		try {
			userService.isLoginValid(user.getLogin());			
		} catch(Exception e) {
			br.rejectValue("login", "error.user", e.getMessage());	
		}
		
		try {
			userService.isPasswordValid(user.getPassword());			
		} catch(Exception e) {
			br.rejectValue("password", "error.user", e.getMessage());	
		}
		if(user.getRole() == null) {
			br.rejectValue("role", "error.user", "User role can not be empty!");
		}
		
		if(br.hasErrors()) {		
			ModelAndView mv = new ModelAndView("users/new");
			List<Role> roles = Role.getAllRoles();
			mv.addObject("roles", roles);
			
			return mv;
		}
		
		ModelAndView mv = new ModelAndView("redirect:/users");
		userRepository.save(user);
		
		return mv;
	}
	
	@GetMapping("/new")
	public ModelAndView newUser(User user) {	
		
		ModelAndView mv = new ModelAndView("users/new");
		List<Role> roles = Role.getAllRoles();
		mv.addObject("roles", roles);
		
		return mv;
	}
}
