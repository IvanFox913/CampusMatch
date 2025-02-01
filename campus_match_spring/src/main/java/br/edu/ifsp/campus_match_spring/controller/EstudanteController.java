package br.edu.ifsp.campus_match_spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ifsp.campus_match_spring.model.Estudante;
import br.edu.ifsp.campus_match_spring.repository.EstudanteRepo;
import br.edu.ifsp.campus_match_spring.service.EstudanteService;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/estudantes")
public class EstudanteController {

	@Autowired
	private EstudanteRepo estudanteRepo;
	
	@Autowired
	private EstudanteService estudanteService;
	
	@GetMapping("home")
	public String home() {
		
		return "/pages/estudante/EstudanteHome";
	}
	
	@GetMapping("index")
	public String index(Model model) {
		
		List<Estudante> estudantes = estudanteRepo.findAll();
		
		model.addAttribute("estudantes", estudantes);
		
		return "/pages/estudante/EstudanteIndex";
	}
	
	@GetMapping("get_all")
	public ResponseEntity<List<Estudante>> getAllStudents(){
		
		List<Estudante> estudantes = estudanteRepo.findAll();
		
		return new ResponseEntity<>(estudantes, HttpStatus.OK);
		
	}
	
	@GetMapping("new")
	public String newEstudante(Model model) {
		
		model.addAttribute("estudante", new Estudante());
		
		return "/pages/estudante/EstudanteNew";
	}
	
	@PostMapping("save")
	public String saveEstudante(@ModelAttribute Estudante estudante, Model model, RedirectAttributes redirectAttrs) {
		
		model.addAttribute("estudante",estudante);
		redirectAttrs.addFlashAttribute("estudante",estudante);
		String current_url = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();

		if(estudanteService.register(estudante,current_url)) {
			return "/pages/auth/estudanteconfirmacao";
		} else {
			return "redirect:/auth/estudante";
		}

	}
	
	@GetMapping("save/{uuid}")
	public String validateEstudante(@PathVariable("uuid") String uuid, Model model) {
		
		if(estudanteService.validate(uuid)) {
			return "redirect:/auth/login";
		}else {
			return "redirect:/auth/login";
		}
	}
	
	@GetMapping("editEstudante/{id}")
	public String editEstudante(@PathVariable("id") Estudante estudante, Model model) {
		
		model.addAttribute(estudante);
		
		return "/pages/web/EstudanteNew";
	}
	
	@GetMapping("deleteEstudante/{id}")
	public String deleteEstudante(@PathVariable("id") Long id) {
		
		estudanteRepo.deleteById(id);
		
		return "redirect:/estudantes/index";
	}
}
