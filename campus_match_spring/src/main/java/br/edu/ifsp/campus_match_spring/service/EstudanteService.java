package br.edu.ifsp.campus_match_spring.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.campus_match_spring.model.Estudante;
import br.edu.ifsp.campus_match_spring.repository.EstudanteRepo;

@Service
public class EstudanteService {
	
	@Autowired
	private EstudanteRepo estudanteRepo;
			
	@Autowired
	private PasswordService passwordService;
			
	@Autowired
	private MailService mailService;

	public boolean register(Estudante estudante, String current_url) {
		estudante.setUuid(UUID.randomUUID().toString());
		estudante.setValidado(0);
		estudante.setSenha(passwordService.hashPassword(estudante.getSenha()));

		Estudante e = estudanteRepo.getByEmail(estudante.getEmail());
		
		if(e != null) {
			return false;
		}
		
		estudanteRepo.save(estudante);
		mailService.sendMail(
				estudante.getEmail()
			, 	"Valide sua conta (CampusMatch)"
			,	"Segue link de confirmação: " + current_url + "/" + estudante.getUuid() 
			);
		
		return true;
	}
	
	
	public boolean validate(String uuid) {
		Estudante e = estudanteRepo.getByUuid(uuid);
		
		if(e == null) {
			return false;
		}
		
		mailService.sendMail(
				e.getEmail()
			, 	"Confirmação de cadastro de aluno (CampusMatch)."
			,	"Seu cadastro de aluno no sistema CampusMatch foi confirmado!" 
			);
		return true;
	}


	public boolean edit(Estudante estudante, Estudante user) {
		if(estudante.getSenha() != "" && estudante.getSenha() != null) {
			estudante.setSenha(passwordService.hashPassword(estudante.getSenha()));
		}else {
			estudante.setSenha(user.getSenha());
		}
		
		if(estudante.getDataNascimento()  == null) {
			estudante.setDataNascimento(user.getDataNascimento());
		}
		
		estudante.setId(user.getId());
		estudante.setUuid(user.getUuid());

		
		estudanteRepo.save(estudante);
		
		return true;
	}
	
}
