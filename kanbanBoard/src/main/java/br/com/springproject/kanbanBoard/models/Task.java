package br.com.springproject.kanbanBoard.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.springframework.lang.NonNull;

@Entity
public class Task extends AbstractBoardObject {

	@NonNull
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@NonNull
	private Long boardId;
	
	@ManyToOne
	private User assignedTo;
	
	private Date endDate;

	public Task() {
		super();
	}

	public Task(Status status, Long boardId, User assignedTo, Date endDate) {
		super();
		this.status = status;
		this.boardId = boardId;
		this.assignedTo = assignedTo;
		this.endDate = endDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getBoardId() {
		return boardId;
	}

	public void setBoardId(Long boardId) {
		this.boardId = boardId;
	}

	public User getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(User assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
