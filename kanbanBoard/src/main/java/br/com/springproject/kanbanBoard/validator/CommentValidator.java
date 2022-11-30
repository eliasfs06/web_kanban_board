package br.com.springproject.kanbanBoard.validator;

import org.springframework.stereotype.Service;

import br.com.springproject.kanbanBoard.models.Comment;


@Service
public class CommentValidator {
	
	public void isDescriptionValid(Comment comment) throws Exception {	
		
		if(comment.getDescription().isEmpty() || comment.getDescription().trim() == "") 
			throw new Exception("The comment description can not be empty!");	
		
	}
	
	public void isOwnerValid(Comment comment) throws Exception {	
		
		if(comment.getOwner() == null) 
			throw new Exception("The comment owner can not be empty!");	
		
	}
}
