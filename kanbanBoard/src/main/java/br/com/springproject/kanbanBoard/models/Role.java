package br.com.springproject.kanbanBoard.models;

import java.util.ArrayList;
import java.util.List;

public enum Role {
	/*
	 * ADMIN : can delete user. Creates boards, tasks and comments.
	 * BOARD_USER : can create and delete boards, tasks and comments.
	 * TASK_USER : can create and delete tasks and comments. Can not create or delete boards.
	 * COMMENT_USER : just comments on tasks. Can not create or delete boards or tasks. 
	 */ 
	ADMIN, BOARD_USE, TASK_USER, COMMENT_USER;
	
	Role() { }
	
	public static List<Role> getAllRoles() {
		
		List<Role> roles = new ArrayList<>();
		roles.add(ADMIN);
		roles.add(BOARD_USE);
		roles.add(TASK_USER);
		roles.add(COMMENT_USER);
		
		return roles;
	}

}
