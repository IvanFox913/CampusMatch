package br.edu.ifsp.campus_match_spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifsp.campus_match_spring.model.Instituicao;
import br.edu.ifsp.campus_match_spring.repository.InstituicaoRepo;

@Controller
@RequestMapping("/instituicoes")
public class InstituicaoController {
	
	@Autowired
	private InstituicaoRepo instituicaoRepo;
	
	@RequestMapping("index")
	public String index(Model model) {
		
		List<Instituicao> instituicoes = instituicaoRepo.findAll();
		
		model.addAttribute("instituicoes", instituicoes);
		
		return "/pages/instituicao/InstituicaoIndex";
	}
	
	@RequestMapping("new")
	public String newInstituicao(Model model) {
		
		model.addAttribute("instituicao", new Instituicao());
		
		return "/pages/instituicao/InstituicaoNew";
	}
	
	@PostMapping("save")
	public String saveInstituicao(@ModelAttribute Instituicao instituicao) {
		
		instituicaoRepo.save(instituicao);
		
		return "redirect:index";
	}
	
	@RequestMapping("editInstituicao/{id}")
	public String editInstituicao(@PathVariable("id") Long id, Model model) {
	    Instituicao instituicao = instituicaoRepo.findById(id).orElse(null);
	    model.addAttribute("instituicao", instituicao);
	    return "/pages/instituicao/InstituicaoNew";
	}

	
	@RequestMapping("deleteInstituicao/{id}")
	public String deleteInstituicao(@PathVariable("id") Long id) {
		
		instituicaoRepo.deleteById(id);
		
		return "redirect:/instituicoes/index";
	}
	
}
