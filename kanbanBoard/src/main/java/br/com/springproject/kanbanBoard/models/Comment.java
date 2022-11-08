package br.com.springproject.kanbanBoard.models;

import javax.persistence.Entity;

import org.springframework.lang.NonNull;

@Entity
public class Comment extends BoardObject {

	@NonNull
	private Long taskId;

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
	

}
