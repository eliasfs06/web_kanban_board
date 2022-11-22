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

import br.com.springproject.kanbanBoard.models.Board;
import br.com.springproject.kanbanBoard.models.User;
import br.com.springproject.kanbanBoard.repositories.BoardRepository;
import br.com.springproject.kanbanBoard.repositories.UserRepository;
import br.com.springproject.kanbanBoard.utils.Messages;
import br.com.springproject.kanbanBoard.validator.BoardValidator;

@Controller
@RequestMapping("/boards")
public class BoardController {
	
	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BoardValidator boardValidator;
	
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
			boardValidator.isNameValid(board);			
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
	
	@GetMapping("/{id}/edit")
	public ModelAndView edit(@PathVariable Long id) {
		
		ModelAndView mv = new ModelAndView("boards/edit");
		
		try {
			Optional<Board> boardOpt = boardRepository.findById(id);
			List<User> userList = userRepository.findAll();
			
			if(boardOpt.isPresent()) {
				mv.addObject("board", boardOpt.get());
				mv.addObject("userList", userList);
				return mv;
			}
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		mv.setViewName("redirect:/boards");
		
		return mv;
	}
	
	@PostMapping("/{id}")
	public ModelAndView update(@PathVariable Long id, Board formBoard, BindingResult br) {
		
		ModelAndView mv = new ModelAndView("redirect:/boards");
		Optional<Board> boardOpt = boardRepository.findById(id);
		List<User> userList = userRepository.findAll();
		
		if(boardOpt.isPresent()) {
			
			Board board = boardOpt.get();
			//Validate name
			try {
				boardValidator.isNameValid(formBoard);			
			} catch (Exception e) {
				br.rejectValue("name", "error.user", e.getMessage());	
			}
			
			//Validate owner
			try {
				boardValidator.isOwnerValid(formBoard);	
			} catch (Exception e) {
				br.rejectValue("owner", "error.user", e.getMessage());
			}
			
			if(br.hasErrors()) {
				mv.setViewName("boards/edit");
				mv.addObject("userList", userList);
				return mv;
			}
			
			board.setName(formBoard.getName());
			board.setDescription(formBoard.getDescription());
			board.setOwner(formBoard.getOwner());
			
			try {
				boardRepository.save(board);
				mv.addObject("message", Messages.EDIT_BOARD_SUCESS.getMessage());
				mv.addObject("error", Messages.EDIT_BOARD_SUCESS.getError());
				
			} catch(Exception e){
				e.printStackTrace();
				mv.addObject("message", Messages.EDIT_BOARD_ERROR.getMessage());
				mv.addObject("error", Messages.EDIT_BOARD_ERROR.getError());
			}
		}	
			
		return mv;
		
	}
	
	@GetMapping("/{id}")
	public ModelAndView show(@PathVariable Long id) {
		
		ModelAndView mv  = new ModelAndView("boards/show");
		
		return mv;
	}
}

