package br.edu.ifsp.campus_match_spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.campus_match_spring.model.Infraestrutura;
import br.edu.ifsp.campus_match_spring.model.Instituicao;

public interface InfraestruturaRepo extends JpaRepository<Infraestrutura, Long>{

	public List<Infraestrutura> findByInstituicao(Instituicao user);

}
