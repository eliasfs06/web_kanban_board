package br.com.springproject.kanbanBoard.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Board extends BoardObject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	public Board() { }
	

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}
	
}
