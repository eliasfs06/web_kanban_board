package br.com.springproject.kanbanBoard.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.springproject.kanbanBoard.models.Comment;
import br.com.springproject.kanbanBoard.models.Task;
import br.com.springproject.kanbanBoard.models.User;
import br.com.springproject.kanbanBoard.repositories.CommentRepository;
import br.com.springproject.kanbanBoard.repositories.TaskRepository;
import br.com.springproject.kanbanBoard.repositories.UserRepository;
import br.com.springproject.kanbanBoard.utils.Messages;
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
	
	@Value("${file.path}")
	private String filePath;
	
	@GetMapping("/new/{taskId}")
	public ModelAndView newTask(Comment comment, @PathVariable Long taskId) {
	
		ModelAndView mv = new ModelAndView("comments/new");
		List<User> userList = userRepository.findAll();
		mv.addObject("userList", userList);
		comment.setTaskId(taskId);
		
		return mv;
	}
	
	@PostMapping
	public ModelAndView create(Comment comment, BindingResult br, RedirectAttributes ra, @RequestParam("file") MultipartFile file) {	
		
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
		
		try {
			if(!file.isEmpty()) {
				byte[] bytes = file.getBytes();
				Path path = Paths.get(filePath + file.getOriginalFilename());
				Files.write(path, bytes);
				
				comment.setImageName(filePath + file.getOriginalFilename());
			}
		} catch (IOException e) {
			e.printStackTrace();
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
	
	@GetMapping("/{id}/delete")
	public ModelAndView delete(@PathVariable Long id, RedirectAttributes ra) {
		
		Optional<Comment> comment = commentRepository.findById(id);
		Optional<Task> task = taskRepository.findById(comment.get().getTaskId());
		ra.addAttribute("task", task.get());
		ModelAndView mv = new ModelAndView("redirect:/tasks/"+comment.get().getTaskId());	
		
		try {
			commentRepository.deleteById(id);
			mv.addObject("message", Messages.DELETE_COMMENT_SUCESS.getMessage());
			mv.addObject("error", Messages.DELETE_COMMENT_SUCESS.getError());
			
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			mv.addObject("message", Messages.DELETE_COMMENT_ERROR.getMessage());
			mv.addObject("error", Messages.DELETE_COMMENT_ERROR.getError());
		}
		
		return mv;
	}
	
	@GetMapping("/findImage/{id}")
	@ResponseBody
	public byte[] findImage(@PathVariable Long id) throws IOException {
		
		Optional<Comment> comment = commentRepository.findById(id);
		File fileImage = new File(comment.get().getImageName());
		if(comment.get().getImageName() != null) {
			return Files.readAllBytes(fileImage.toPath());
		}
		return null;
	}
	
}
