package br.edu.ifsp.campus_match_spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import br.edu.ifsp.campus_match_spring.model.Curso;
import br.edu.ifsp.campus_match_spring.model.Estudante;
import br.edu.ifsp.campus_match_spring.model.Infraestrutura;
import br.edu.ifsp.campus_match_spring.model.Instituicao;
import br.edu.ifsp.campus_match_spring.model.Publicacao;
import br.edu.ifsp.campus_match_spring.repository.CursoRepo;
import br.edu.ifsp.campus_match_spring.repository.EstudanteRepo;
import br.edu.ifsp.campus_match_spring.repository.InfraestruturaRepo;
import br.edu.ifsp.campus_match_spring.repository.InstituicaoRepo;
import br.edu.ifsp.campus_match_spring.repository.PublicacaoRepo;
import br.edu.ifsp.campus_match_spring.service.EstudanteService;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/estudantes")
public class EstudanteController {

	@Autowired
	private EstudanteRepo estudanteRepo;
	
	@Autowired
	private EstudanteService estudanteService;
	
	@Autowired
	private InstituicaoRepo instituicaoRepo;
	@Autowired
	private CursoRepo cursoRepo;
	@Autowired
	private InfraestruturaRepo infraestruturaRepo;
	@Autowired
	private PublicacaoRepo publicacaoRepo;
	
	
	@GetMapping("home")
	public String home() {
		
		return "/pages/estudante/EstudanteHome";
	}
	
	@GetMapping("profile")
	public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Estudante user = (Estudante) authentication.getPrincipal();
        Estudante estudante = estudanteRepo.getById(user.getId());
        model.addAttribute("estudante",estudante);
		
		return "/pages/estudante/EstudanteProfile";
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
	
	@PostMapping("edit")
	public String editInstituicao(@ModelAttribute Estudante estudante, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Estudante user = (Estudante) authentication.getPrincipal();
       		
        if(estudanteService.edit(estudante, user)) {
    		return "redirect:/estudantes/profile";
        }else {
    		return "redirect:/estudantes/profile";
        }
	}
	
	@GetMapping("instituicoes")
	public String instituicoes(@ModelAttribute Estudante estudante, Model model) {
        
        List<Instituicao> instituicoes = instituicaoRepo.findAll();
        
        model.addAttribute("instituicoes",instituicoes);
       		
		return "/pages/instituicao/InstituicaoIndex";
	}
	
	@GetMapping("cursos/{id}")
	public String cursos(@PathVariable("id") Long id,Model model) {
        Instituicao instituicao = instituicaoRepo.findById(id).orElse(null);

        List<Curso> cursos = cursoRepo.findByInstituicao(instituicao);
        
        model.addAttribute("cursos",cursos);
               
		return "/pages/curso/CursoNew";
	}
	
	@GetMapping("infraestruturas/{id}")
	public String instituicoes(@PathVariable("id") Long id,Model model) {
        Instituicao instituicao = instituicaoRepo.findById(id).orElse(null);

        List<Infraestrutura> infraestruturas = infraestruturaRepo.findByInstituicao(instituicao);
        
        model.addAttribute("infraestruturas",infraestruturas);
               
		return "/pages/infraestrutura/InfraestruturaIndexNew";
	}
	
	@GetMapping("publicacoes/{id}")
	public String publicacoes(@PathVariable("id") Long id,Model model) {
        Instituicao instituicao = instituicaoRepo.findById(id).orElse(null);

        List<Publicacao> publicacoes = publicacaoRepo.findByInstituicao(instituicao);
        
        model.addAttribute("publicacoes",publicacoes);
               
		return "/pages/publicacao/PublicacaoIndexNew";
	}
	
}
