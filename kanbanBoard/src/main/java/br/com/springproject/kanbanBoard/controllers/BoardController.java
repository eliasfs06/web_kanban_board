package br.com.springproject.kanbanBoard.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.springproject.kanbanBoard.models.Board;
import br.com.springproject.kanbanBoard.repositories.BoardRepository;

@Controller
@RequestMapping("/boards")
public class BoardController {
	
	@Autowired
	BoardRepository boardRepository;
	
	@GetMapping("")
	public ModelAndView index() {
		
		ModelAndView mv = new ModelAndView("boards/index");
		
		try {
			List<Board> boards = boardRepository.findAll();
			mv.addObject("boards",boards);
		} catch(RuntimeException e) {
			e.printStackTrace();
		}
		
		return mv;
	}
	
}
