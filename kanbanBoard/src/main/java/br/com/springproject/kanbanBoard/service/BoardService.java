package br.com.springproject.kanbanBoard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.springproject.kanbanBoard.models.Board;
import br.com.springproject.kanbanBoard.models.Role;
import br.com.springproject.kanbanBoard.models.User;
import br.com.springproject.kanbanBoard.repositories.BoardRepository;

@Service
public class BoardService {
	
	@Autowired
	BoardRepository boardRepository;

	public void isNameValid(String boardName) throws Exception {	
		
		if(boardName.isEmpty()) {
			throw new Exception("The board name can not be empty!");
			
		} else if (!validateBoardName(boardName)) {
			throw new Exception("This board name is not available!");
		}
		
	}
	
	/*
	 * Can not have two board with the same name. 
	 */
	public boolean validateBoardName(String boardName) {
		
		try {	
			List<Board> allBoards = boardRepository.findAllByName(boardName);
			if(!allBoards.isEmpty()) return false;			
			
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

