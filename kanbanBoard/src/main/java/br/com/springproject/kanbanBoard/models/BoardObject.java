package br.com.springproject.kanbanBoard.models;

import java.sql.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.springframework.lang.NonNull;

@MappedSuperclass
public abstract class BoardObject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@NonNull
	private String title;
	
	@NonNull
	private String description;
	
	@ManyToOne
	@NonNull
	private User owner;
	
	@NonNull
	private Date creationDate;
	
	public BoardObject() { }
	
	
	public BoardObject(Long id, String title, String description, User owner, Date creationDate) {
		super();
		Id = id;
		this.title = title;
		this.description = description;
		this.owner = owner;
		this.creationDate = creationDate;
	}


	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String descreption) {
		this.description = descreption;
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
