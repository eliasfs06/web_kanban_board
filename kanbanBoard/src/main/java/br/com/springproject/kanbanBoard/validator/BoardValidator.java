package br.com.springproject.kanbanBoard.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.springproject.kanbanBoard.models.Board;
import br.com.springproject.kanbanBoard.models.Role;
import br.com.springproject.kanbanBoard.models.User;
import br.com.springproject.kanbanBoard.repositories.BoardRepository;

@Service
public class BoardValidator {
	
	@Autowired
	BoardRepository boardRepository;

	public void isNameValid(Board board) throws Exception {	
		
		if(board.getName().isEmpty() || board.getName().trim() == "") {
			throw new Exception("The board name can not be empty!");
			
		} else if (!validateBoardName(board)) {
			throw new Exception("This board name is not available!");
		}
		
	}
	
	/*
	 * Can not have two board with the same name. 
	 */
	public boolean validateBoardName(Board board) {
		
		try {	
			List<Board> allBoards = boardRepository.findAllByName(board.getName());
			if(!allBoards.isEmpty()) {
				for(Board b : allBoards) {
					if(b.getId() != board.getId()) return false;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	
	public void isOwnerValid(Board board) throws Exception {	
		
		if(board.getOwner() == null) {
			throw new Exception("The board must have an owner!");
			
		} else if (!validateBoardOwner(board.getOwner())) {
			throw new Exception("This user can not own a board!");
		}
		
	}
	
	/*
	 * An user must have ADMIN or BOARD_USE roles to own a board
	 */
	public boolean validateBoardOwner(User boardOwner) {
		
		if(boardOwner.getRole() == Role.ADMIN || boardOwner.getRole() == Role.BOARD_USE) return true;

		return false;
	}
		
}

