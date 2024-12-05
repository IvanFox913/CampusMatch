package br.edu.ifsp.campus_match_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.campus_match_spring.model.Estudante;

public interface EstudanteRepo extends JpaRepository<Estudante, Long> {

}