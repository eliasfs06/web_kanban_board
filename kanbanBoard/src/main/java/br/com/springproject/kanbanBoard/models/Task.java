package br.com.springproject.kanbanBoard.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.lang.NonNull;

@Entity
public class Task extends BoardObject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@NonNull
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@NonNull
	private Long boardId;
	
    @ElementCollection(targetClass = Role.class)
    @CollectionTable(name = "task_assignees")
	private List<User> assignees;
	
	private Date endDate;
	
	private Task(){ }

	public Status getStatus() {
		return status;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
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

	public List<User> getAssignees() {
		return assignees;
	}

	public void setAssignees(List<User> assignees) {
		this.assignees = assignees;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
