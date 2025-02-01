package br.edu.ifsp.campus_match_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.edu.ifsp.campus_match_spring.model.Estudante;

@Repository
public interface EstudanteRepo extends JpaRepository<Estudante, Long> {

	Estudante getByEmail(String email);

	Estudante getByUuid(String uuid);
	
	UserDetails findByEmail(String email);

}
