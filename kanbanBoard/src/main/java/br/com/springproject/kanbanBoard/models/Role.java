package br.com.springproject.kanbanBoard.models;

public enum Role {
	/*
	 * ADMIN : can delete user. Creates boards, tasks and comments.
	 * BOARD_USER : can create and delete boards, tasks and comments.
	 * TASK_USER : can create and delete tasks and comments. Can not create or delete boards.
	 * COMMENT_USER : just comments on tasks. Can not create or delete boards or tasks. 
	 */ 
	ADMIN, BOARD_USE, TASK_USER, COMMENT_USER;
	
	Role() { }

}
