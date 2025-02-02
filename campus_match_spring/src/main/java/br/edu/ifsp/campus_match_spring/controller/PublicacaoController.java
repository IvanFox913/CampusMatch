package br.edu.ifsp.campus_match_spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifsp.campus_match_spring.model.Instituicao;
import br.edu.ifsp.campus_match_spring.model.Publicacao;
import br.edu.ifsp.campus_match_spring.repository.PublicacaoRepo;

@Controller
@RequestMapping("/publicacoes")
public class PublicacaoController {
	
	@Autowired
	private PublicacaoRepo publicacaoRepo; 
	
	@GetMapping("index")
	public String index(Model model) {
		
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Instituicao user = (Instituicao) authentication.getPrincipal();
		List<Publicacao> publicacoes = publicacaoRepo.findByInstituicao(user);

		model.addAttribute("publicacoes", publicacoes);
		
		if(model.getAttribute("publicacao") == null) {
			model.addAttribute("publicacao", new Publicacao());
		}
		
		return "/pages/publicacao/PublicacaoIndex";
	}
	
	@GetMapping("index/{id}")
	public String index(Model model,@PathVariable("id") Long id) {
		
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Instituicao user = (Instituicao) authentication.getPrincipal();
		List<Publicacao> publicacoes = publicacaoRepo.findByInstituicao(user);

		model.addAttribute("publicacoes", publicacoes);		

		model.addAttribute("publicacao", publicacaoRepo.getById(id));
		
		if(model.getAttribute("publicacao") == null) {
			model.addAttribute("publicacao", new Publicacao());
		}
		
		return "/pages/publicacao/PublicacaoIndex";
	}
	

	
	@GetMapping("new")
	public String newPublicacao(Model model) {
		
		model.addAttribute("publicacao", new Publicacao());
		
		return "/pages/publicacao/PublicacaoNew";
	}
	
	@PostMapping("save")
	public String savePublicacao(@ModelAttribute Publicacao publicacao) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Instituicao user = (Instituicao) authentication.getPrincipal();
        
        publicacao.setInstituicao(user);
        
		publicacaoRepo.save(publicacao);
		
		return "redirect:index";
	}
	
	@GetMapping("editPublicacao/{id}")
	public String editPublicacao(@PathVariable("id") Publicacao publicacao, Model model) {
		
		model.addAttribute(publicacao);
		
		return "/pages/publicacao/PublicacaoNew";
	}
	

	@GetMapping("deletePublicacao/{id}")
	public String deletePublicacao(@PathVariable("id") Long id) {
		
		publicacaoRepo.deleteById(id);
					
		return "redirect:/publicacoes/index";
	}
}
