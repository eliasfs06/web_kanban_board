package br.com.springproject.kanbanBoard.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.springproject.kanbanBoard.models.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

	@Query("SELECT b FROM Board b WHERE b.name = :name")
	public List<Board> findAllByName(@Param("name") String name); 
}
