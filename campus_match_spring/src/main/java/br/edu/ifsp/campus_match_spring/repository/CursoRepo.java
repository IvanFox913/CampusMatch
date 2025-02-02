package br.edu.ifsp.campus_match_spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.campus_match_spring.model.Curso;
import br.edu.ifsp.campus_match_spring.model.Instituicao;

public interface CursoRepo extends JpaRepository<Curso, Long>{

	List<Curso> findByInstituicao(Instituicao user);

}
