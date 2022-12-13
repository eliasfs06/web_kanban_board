package br.com.springproject.kanbanBoard.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public interface AbstractController {
	
	public ModelAndView index();
	public ModelAndView create();
	public ModelAndView delete();
	public ModelAndView update();

}
