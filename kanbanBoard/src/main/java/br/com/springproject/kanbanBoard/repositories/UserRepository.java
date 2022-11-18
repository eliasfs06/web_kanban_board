package br.com.springproject.kanbanBoard.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.springproject.kanbanBoard.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@Query("SELECT u FROM User u WHERE u.login = :login")
	public List<User> findByLogin(@Param("login") String login);
	
	@Query("SELECT u FROM User u WHERE u.person.email = :email")
	public List<User> findByEmail(@Param("email") String email);
	
}
