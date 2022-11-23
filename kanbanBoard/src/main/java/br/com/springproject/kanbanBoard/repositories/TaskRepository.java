package br.com.springproject.kanbanBoard.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.springproject.kanbanBoard.models.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	
	@Query("SELECT t FROM Task t WHERE t.boardId = :boardId")
	public List<Task> findAllByBoardId(@Param("boardId") Long boardId);
}
