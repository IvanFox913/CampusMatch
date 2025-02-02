package br.edu.ifsp.campus_match_spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.campus_match_spring.model.Instituicao;
import br.edu.ifsp.campus_match_spring.model.Publicacao;

public interface PublicacaoRepo extends JpaRepository<Publicacao , Long>{

	List<Publicacao> findByInstituicao(Instituicao user);	
}
