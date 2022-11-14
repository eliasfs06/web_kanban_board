package br.com.springproject.kanbanBoard.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.springproject.kanbanBoard.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

	@Query("SELECT p FROM Person p WHERE p.email = :email")
	public List<Person> findAllByEmail(@Param("email") String email);
}
