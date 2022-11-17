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
	DELETE_USER_OWNER_ERROR(true, "This user can not be deleted because it owns one or more boards.");
	
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