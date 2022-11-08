package br.com.springproject.kanbanBoard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.springproject.kanbanBoard.models.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

}
