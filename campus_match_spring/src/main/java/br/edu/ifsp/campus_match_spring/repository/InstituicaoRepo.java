package br.edu.ifsp.campus_match_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.edu.ifsp.campus_match_spring.model.Instituicao;

@Repository
public interface InstituicaoRepo extends JpaRepository<Instituicao, Long>{

	Instituicao getByEmail(String email);

	Instituicao getByUuid(String uuid);
	
	UserDetails findByEmail(String email);

}
