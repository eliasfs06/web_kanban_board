package br.com.springproject.kanbanBoard.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.springproject.kanbanBoard.models.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

	@Query("SELECT c FROM Comment c WHERE c.taskId = :taskId")
	public List<Comment> findAllByTaskId(@Param("taskId") Long taskId);
}
