package br.com.springproject.kanbanBoard.utils;

public enum Messages {
	
	/*
	  0 = sucess
	  1 = error
	  */
	
	DELETE_BOARD_SUCESS(false, "Board deleted successfully."),
	DELETE_BOARD_ERROR(true, "An error occurred when deleting the board. Please try again."),
	DELETE_USER_SUCESS(false, "User deleted successfully."),
	DELETE_USER_ERROR(true, "An error occurred when deleting the user. Please try again."),
	DELETE_USER_OWNER_ERROR(true, "This user can not be deleted because it owns one or more boards."),
	EDIT_BOARD_SUCESS(false, "Board updated successfully."),
	EDIT_BOARD_ERROR(true, "An error occurred when updating the board. Please try again."),
	EDIT_USER_SUCESS(false, "User updated successfully."),
	EDIT_USER_ERROR(true, "An error occurred when updating the user. Please try again."),
	DELETE_TASK_SUCESS(false, "Task deleted successfully."),
	DELETE_TASK_ERROR(true, "An error occurred when deleting the task. Please try again."),
	DELETE_COMMENT_SUCESS(false, "Comment deleted successfully."),
	DELETE_COMMENT_ERROR(true, "An error occurred when deleting the Comment. Please try again.");
	
	private final boolean error;
	private final String message;
	
	Messages(boolean error, String message){
		this.error = error;
		this.message = message;	
	}

	public boolean getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}
	
	

}