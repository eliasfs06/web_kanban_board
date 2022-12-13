package br.com.springproject.kanbanBoard.controllers;

import br.com.springproject.kanbanBoard.models.User;
import br.com.springproject.kanbanBoard.repositories.UserRepository;
import br.com.springproject.kanbanBoard.service.CookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

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
	public ModelAndView logar(Model model, String login, String password, HttpServletResponse response) {
		User log = this.userRepository.Login(login, password);
		if (log != null){
			CookieService.setCookie(response, "userID", String.valueOf(log.getId()) ,120);
			ModelAndView mv = new ModelAndView("home");
			return mv;
		}
		model.addAttribute("erro", "Invalid User or Password");
		return login();
	}

	@GetMapping("/logout")
	public ModelAndView logar(Model model, HttpServletResponse response){
		CookieService.setCookie(response, "userID","" ,0);
		model.addAttribute("logout", "You are now logged out!");
		return login();
	}
}