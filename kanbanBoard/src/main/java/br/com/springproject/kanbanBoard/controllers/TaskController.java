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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.springproject.kanbanBoard.models.Board;
import br.com.springproject.kanbanBoard.models.Comment;
import br.com.springproject.kanbanBoard.models.Status;
import br.com.springproject.kanbanBoard.models.Task;
import br.com.springproject.kanbanBoard.models.User;
import br.com.springproject.kanbanBoard.repositories.BoardRepository;
import br.com.springproject.kanbanBoard.repositories.CommentRepository;
import br.com.springproject.kanbanBoard.repositories.TaskRepository;
import br.com.springproject.kanbanBoard.repositories.UserRepository;
import br.com.springproject.kanbanBoard.utils.Messages;
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
	
	@Autowired
	CommentRepository commentRespository;
	
	@Autowired
	BoardRepository boardRepository;
	
	@GetMapping("/{id}")
	public ModelAndView show(@PathVariable Long id) {
		
		ModelAndView mv = new ModelAndView("tasks/show");
		
		try {
			Optional<Task> task = taskRepository.findById(id);
			if(task.isPresent()) {
				mv.addObject("task", task.get());
			}
			
			List<Comment> comments = commentRespository.findAllByTaskId(id);
			mv.addObject("comments", comments);
			
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
	public ModelAndView create(Task task, BindingResult br, RedirectAttributes ra) {	
		
		ModelAndView mv = new ModelAndView("redirect:/boards/"+task.getBoardId());	
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
			mv = new ModelAndView("tasks/new");
			List<User> userList = userRepository.findAll();
			mv.addObject("userList", userList);
			
			return mv;
		}
		
		try {
			task.setStatus(Status.TO_DO);
			taskRepository.save(task);	
		} catch (Exception e) {
			e.printStackTrace();
		}	
				
		Optional<Board> board = boardRepository.findById(task.getBoardId());
		ra.addFlashAttribute("board", board.get());
		return mv;
	}
	
	@GetMapping("/{id}/delete")
	public ModelAndView delete(@PathVariable Long id, RedirectAttributes ra) {
		
		ModelAndView mv = new ModelAndView("redirect:/boards");
		Optional<Task> task = taskRepository.findById(id);

		try {
			taskRepository.deleteById(id);
			mv.addObject("message", Messages.DELETE_TASK_SUCESS.getMessage());
			mv.addObject("error", Messages.DELETE_TASK_SUCESS.getError());
			
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			mv.addObject("message", Messages.DELETE_TASK_ERROR.getMessage());
			mv.addObject("error", Messages.DELETE_TASK_ERROR.getError());
		}
		
		Optional<Board> board = boardRepository.findById(task.get().getBoardId());
		ra.addFlashAttribute("board", board.get());
		mv.setViewName("redirect:/boards/"+task.get().getBoardId());
		return mv;
	}
	
	@GetMapping("/move/{movement}/{taskId}")
	public ModelAndView move(@PathVariable String movement, @PathVariable Long taskId, RedirectAttributes ra){
		
		Optional<Task> task = taskRepository.findById(taskId);
		
		if(movement.equals("toDo")) {
			task.get().setStatus(Status.TO_DO);
		} else if(movement.equals("doing")) {
			task.get().setStatus(Status.DOING);
		} else if(movement.equals("done")) {
			task.get().setStatus(Status.DONE);
		}
		
		try {
			taskRepository.save(task.get());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ModelAndView mv = new ModelAndView("redirect:boards/"+task.get().getBoardId());
		Optional<Board> board = boardRepository.findById(task.get().getBoardId());
		ra.addFlashAttribute("board", board.get());
		mv.setViewName("redirect:/boards/"+task.get().getBoardId());
		return mv;
	}
	
}
