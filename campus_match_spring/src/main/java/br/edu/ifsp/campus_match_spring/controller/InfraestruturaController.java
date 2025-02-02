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

import br.edu.ifsp.campus_match_spring.model.Infraestrutura;
import br.edu.ifsp.campus_match_spring.model.Instituicao;
import br.edu.ifsp.campus_match_spring.repository.InfraestruturaRepo;

@Controller
@RequestMapping("/infraestruturas")
public class InfraestruturaController {
	
	@Autowired
	private InfraestruturaRepo infraestruturaRepo; 
	
	
	@GetMapping("index")
	public String index(Model model) {
		
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Instituicao user = (Instituicao) authentication.getPrincipal();
		
        List<Infraestrutura> infraestruturas = infraestruturaRepo.findByInstituicao(user);

		model.addAttribute("infraestruturas", infraestruturas);
		
		if(model.getAttribute("infraestrutura") == null) {
			model.addAttribute("infraestrutura", new Infraestrutura());
		}
		
		return "/pages/infraestrutura/InfraestruturaIndex";
	}
	
	@GetMapping("index/{id}")
	public String index2(@PathVariable("id") Long id,Model model) {
		
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Instituicao user = (Instituicao) authentication.getPrincipal();
		
        List<Infraestrutura> infraestruturas = infraestruturaRepo.findByInstituicao(user);
        Infraestrutura i = infraestruturaRepo.findById(id).orElse(null);
		model.addAttribute("infraestruturas", infraestruturas);
		model.addAttribute("infraestrutura", i);

		if(model.getAttribute("infraestrutura") == null) {
			model.addAttribute("infraestrutura", new Infraestrutura());
		}
		
		return "/pages/infraestrutura/InfraestruturaIndex";
	}

	
	@GetMapping("new")
	public String newInfraestrutura(Model model) {
		
		model.addAttribute("infraestrutura", new Infraestrutura());
		
		return "/pages/infraestrutura/InfraestruturaNew";
	}
	
	@PostMapping("save")
	public String saveInfraestrutura(@ModelAttribute Infraestrutura infraestrutura) {
		
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    Instituicao user = (Instituicao) authentication.getPrincipal();
		
	    infraestrutura.setInstituicao(user);
	    
		infraestruturaRepo.save(infraestrutura);
		
		return "redirect:index";
	}
	
	@GetMapping("editInfraestrutura/{id}")
	public String editInfraestrutura(@PathVariable("id") Infraestrutura infraestrutura, Model model) {
		
		model.addAttribute(infraestrutura);
		
		return "/pages/infraestrutura/InfraestruturaNew";
	}
	

	@GetMapping("deleteInfraestrutura/{id}")
	public String deleteInfraestrutura(@PathVariable("id") Long id) {
		
		infraestruturaRepo.deleteById(id);
		
		return "redirect:/infraestruturas/index";
	}
}
