package br.com.springproject.kanbanBoard.models;

import java.sql.Date;

import javax.persistence.ManyToOne;

import org.springframework.lang.NonNull;


public class BoardObject {
	
	@NonNull
	private String title;
	
	@NonNull
	private String descreption;
	
	@ManyToOne
	@NonNull
	private User owner;
	
	@NonNull
	private Date creationDate;
	
	public BoardObject() { }
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescreption() {
		return descreption;
	}

	public void setDescreption(String descreption) {
		this.descreption = descreption;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
