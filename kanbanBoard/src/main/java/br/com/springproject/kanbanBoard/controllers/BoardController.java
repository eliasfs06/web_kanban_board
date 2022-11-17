package br.com.springproject.kanbanBoard.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.springproject.kanbanBoard.models.Board;
import br.com.springproject.kanbanBoard.models.User;
import br.com.springproject.kanbanBoard.repositories.BoardRepository;
import br.com.springproject.kanbanBoard.repositories.UserRepository;
import br.com.springproject.kanbanBoard.service.BoardService;
import br.com.springproject.kanbanBoard.utils.Messages;

@Controller
@RequestMapping("/boards")
public class BoardController {
	
	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BoardService boardValidator;
	
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
	
	@PostMapping
	public ModelAndView create(Board board, BindingResult br) {
		
		//Validate name
		try {
			boardValidator.isNameValid(board.getName());			
		} catch (Exception e) {
			br.rejectValue("name", "error.user", e.getMessage());	
		}
		
		//Validate owner
		try {
			boardValidator.isOwnerValid(board);	
		} catch (Exception e) {
			br.rejectValue("owner", "error.user", e.getMessage());
		}
		
		
		if(br.hasErrors()) {
			ModelAndView mv = new ModelAndView("boards/new");
			List<User> userList = userRepository.findAll();
			mv.addObject("userList", userList);
			
			return mv;
		}
		
		ModelAndView mv = new ModelAndView("redirect:/boards");	
		boardRepository.save(board);	
		
		return mv;
	}
	
	@GetMapping("/new")
	public ModelAndView newBoard(Board board) {	
		
		ModelAndView mv = new ModelAndView("boards/new");
		List<User> userList = userRepository.findAll();
		mv.addObject("userList", userList);
		
		return mv;
	}
	
	
	@GetMapping("/{id}/delete")
	public ModelAndView delete(@PathVariable Long id) {
		
		ModelAndView mv = new ModelAndView("redirect:/boards");
		
		try {
			boardRepository.deleteById(id);
			mv.addObject("message", Messages.DELETE_BOARD_SUCESS.getMessage());
			mv.addObject("error", Messages.DELETE_BOARD_SUCESS.getError());
			
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			mv.addObject("message", Messages.DELETE_BOARD_SUCESS.getMessage());
			mv.addObject("error", Messages.DELETE_BOARD_SUCESS.getError());
		}
		
		return mv;
	}
	
}
