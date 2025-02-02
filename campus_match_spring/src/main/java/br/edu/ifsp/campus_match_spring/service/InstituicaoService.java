package br.edu.ifsp.campus_match_spring.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.edu.ifsp.campus_match_spring.model.Instituicao;
import br.edu.ifsp.campus_match_spring.repository.InstituicaoRepo;

@Service
public class InstituicaoService {

	@Autowired
	private InstituicaoRepo instituicaoRepo;
			
	@Autowired
	private PasswordService passwordService;
			
	@Autowired
	private MailService mailService;
	
	@Value("${spring.mail.username}")
	private String instituicaoValidateMail;

	public boolean register(Instituicao instituicao, String current_url) {
		
		instituicao.setValidado(0);
		instituicao.setUuid(UUID.randomUUID().toString());
		instituicao.setSenha(passwordService.hashPassword(instituicao.getSenha()));
		
		Instituicao i = instituicaoRepo.getByEmail(instituicao.getEmail());

		if(i == null) {
			instituicaoRepo.save(instituicao);
			
			mailService.sendMail(
					instituicaoValidateMail
				, 	"Nova requisição para cadastro de instituição (CampusMatch)"
				,	instituicao.formatMail() + "Caso o cadastro deseje aprovar o cadastro, segue o link:" + current_url + "/" + instituicao.getUuid() 
				);
			
			return true;
		}

		return false;
		
	}
	
	public boolean validate (String uuid) {
		Instituicao i = instituicaoRepo.getByUuid(uuid);
		
		if(i != null) {
			i.setValidado(1);
			instituicaoRepo.save(i);
			mailService.sendMail(
					i.getEmail()
				, 	"Confirmação de cadastro de instituição (CampusMatch)."
				,	"Seu cadastro de instituição no sistema CampusMatch foi confirmado!" 
				);
			return true;
		}
		return false;

	}
	
	public boolean edit(Instituicao instituicao, Instituicao user) {
		
        if(instituicao.getSenha() != null & !instituicao.getSenha().equals("")){
        	instituicao.setSenha(passwordService.hashPassword(instituicao.getSenha()));
        } else {
        	instituicao.setSenha(user.getSenha());
        }
        
        instituicao.setId(user.getId());
        instituicao.setUuid(user.getUuid());
        instituicao.setValidado(user.getValidado());

		instituicaoRepo.save(instituicao);
		
		return true;
	}
	
}
