package br.com.springproject.kanbanBoard.models;

import javax.persistence.Entity;

import org.springframework.lang.NonNull;

@Entity
public class Comment extends AbstractBoardObject {

	@NonNull
	private Long taskId;
	
	private String imageName;

	public Comment() {
		super();
	}

	public Comment(Long taskId) {
		super();
		this.taskId = taskId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	

}
