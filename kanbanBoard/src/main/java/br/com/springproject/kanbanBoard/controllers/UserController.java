package br.com.springproject.kanbanBoard.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.springproject.kanbanBoard.models.Role;
import br.com.springproject.kanbanBoard.models.User;
import br.com.springproject.kanbanBoard.repositories.BoardRepository;
import br.com.springproject.kanbanBoard.repositories.UserRepository;
import br.com.springproject.kanbanBoard.service.UserService;
import br.com.springproject.kanbanBoard.utils.Messages;
import br.com.springproject.kanbanBoard.validator.UserValidator;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	UserValidator userValidator;
	
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
			userValidator.isNameValid(user);			
		} catch(Exception e) {
			br.rejectValue("person.name", "error.user", e.getMessage());	
		}
		
		try {
			userValidator.isEmailValid(user);			
		} catch(Exception e) {
			br.rejectValue("person.email", "error.user", e.getMessage());	
		}
		
		try {
			userValidator.isLoginValid(user);			
		} catch(Exception e) {
			br.rejectValue("login", "error.user", e.getMessage());	
		}
		
		try {
			userValidator.isPasswordValid(user.getPassword());			
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
		try {
			user.getName().toUpperCase();
			userRepository.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mv;
	}
	
	@GetMapping("/new")
	public ModelAndView newUser(User user) {	
		
		ModelAndView mv = new ModelAndView("users/new");
		List<Role> roles = Role.getAllRoles();
		mv.addObject("roles", roles);
		
		return mv;
	}
	
	@GetMapping("/{id}/delete")
	public ModelAndView delete(@PathVariable Long id) {
				
		ModelAndView mv = new ModelAndView("redirect:/users");
		
		try {
			
			userService.deleteUser(id);
			mv.addObject("message", Messages.DELETE_USER_SUCESS.getMessage());
			mv.addObject("error", Messages.DELETE_USER_SUCESS.getError());
			
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			mv.addObject("message", Messages.DELETE_USER_ERROR.getMessage());
			mv.addObject("error", Messages.DELETE_USER_SUCESS.getError());
			
		} catch (Exception e) {
			mv.addObject("message", Messages.DELETE_USER_OWNER_ERROR.getMessage());
			mv.addObject("error", Messages.DELETE_USER_OWNER_ERROR.getError());
		}
		
		return mv;
	}
	
	@GetMapping("/{id}/edit")
	public ModelAndView edit(@PathVariable Long id) {
		
		ModelAndView mv = new ModelAndView("users/edit");
		
		try {
			Optional<User> userOpt = userRepository.findById(id);
			List<Role> roles = Role.getAllRoles();
			
			if(userOpt.isPresent()) {
				mv.addObject("user", userOpt.get());
				mv.addObject("roles", roles);
				return mv;
			}
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		mv.setViewName("redirect:/users");
		
		return mv;
	}
	
	@PostMapping("/{id}")
	public ModelAndView update(@PathVariable Long id, User formUser, BindingResult br) {
		
		ModelAndView mv = new ModelAndView("redirect:/users");
		Optional<User> userOpt = userRepository.findById(id);
		List<Role> roles = Role.getAllRoles();
		
		if(userOpt.isPresent()) {
			
			User user = userOpt.get();
			
			//validation
			try {
				userValidator.isNameValid(formUser);			
			} catch(Exception e) {
				br.rejectValue("person.name", "error.user", e.getMessage());	
			}
			
			try {
				userValidator.isEmailValid(formUser);			
			} catch(Exception e) {
				br.rejectValue("person.email", "error.user", e.getMessage());	
			}
			
			try {
				userValidator.isLoginValid(formUser);			
			} catch(Exception e) {
				br.rejectValue("login", "error.user", e.getMessage());	
			}
			
			if(formUser.getRole() == null) {
				br.rejectValue("role", "error.user", "User role can not be empty!");
			}
			
			if(br.hasErrors()) {
				mv.setViewName("users/edit");
				mv.addObject("roles", roles);
				return mv;
			}
			
			user.setLogin(formUser.getLogin());
			user.getPerson().setName(formUser.getPerson().getName());
			user.setEmail(formUser.getEmail());
			user.setRole(formUser.getRole());
			
			try {
				userRepository.save(user);
				mv.addObject("message", Messages.EDIT_USER_SUCESS.getMessage());
				mv.addObject("error", Messages.EDIT_USER_SUCESS.getError());
				
			} catch(Exception e){
				e.printStackTrace();
				mv.addObject("message", Messages.EDIT_USER_ERROR.getMessage());
				mv.addObject("error", Messages.EDIT_USER_ERROR.getError());
			}
		}	
			
		return mv;
		
	}
}
