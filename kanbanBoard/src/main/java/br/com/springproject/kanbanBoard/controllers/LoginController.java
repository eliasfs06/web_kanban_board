package br.com.springproject.kanbanBoard.controllers;

import br.com.springproject.kanbanBoard.models.Role;
import br.com.springproject.kanbanBoard.models.User;
import br.com.springproject.kanbanBoard.repositories.BoardRepository;
import br.com.springproject.kanbanBoard.repositories.UserRepository;
import br.com.springproject.kanbanBoard.service.UserService;
import br.com.springproject.kanbanBoard.utils.Messages;
import br.com.springproject.kanbanBoard.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller

public class LoginController {

	@Autowired
	UserRepository userRepository;

	@RequestMapping("/login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}

	@PostMapping("/logar")
	public ModelAndView logar(Model model, String login, String password) {
		User log = this.userRepository.Login(login, password);
		if (log != null) {
			ModelAndView mv = new ModelAndView("home");
			return mv;
		}
		model.addAttribute("erro", "Inválid User or Password");
		return login();
	}
}