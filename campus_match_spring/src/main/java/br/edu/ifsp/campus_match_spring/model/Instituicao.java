package br.edu.ifsp.campus_match_spring.model;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.edu.ifsp.campus_match_spring.util.Constants;

@Entity
@Table(name = "instituicao")
public class Instituicao implements UserDetails  {

    private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;
    
    @Column(name = "tipo")
    private String tipo;
    
    @Column(name = "senha")
    private String senha;
    
    @Column(name = "cep")
    private String cep;
    
    @Column(name = "nome")
    private String nome;
    
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "numero_mec")
    private int numeroMec;
    
    @Column(name = "campus")
    private String campus;
    
    @Column(name = "validado")
    private int validado;
    
    @Column(name = "publica")
    private boolean publica;
    
    @Column(name = "sigla")
    private String sigla;

    @OneToMany
    @JoinTable(
        name = "instituicao_curso",
        joinColumns = @JoinColumn(name = "id_instituicao"),
        inverseJoinColumns = @JoinColumn(name = "id_curso")
    )
    private List<Curso> cursos;

    @OneToMany
    @JoinTable(
        name = "instituicao_infraestrutura",
        joinColumns = @JoinColumn(name = "id_instituicao"),
        inverseJoinColumns = @JoinColumn(name = "id_infraestrutura")
    )
    private List<Infraestrutura> infraestruturas;

    @OneToMany
    @JoinTable(
        name = "instituicao_publicacao",
        joinColumns = @JoinColumn(name = "id_instituicao"),
        inverseJoinColumns = @JoinColumn(name = "id_publicacao")
    )
    private List<Publicacao> publicacoes;

    // Getters e setters para os campos e listas

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumeroMec() {
        return numeroMec;
    }

    public void setNumeroMec(int numeroMec) {
        this.numeroMec = numeroMec;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public boolean isPublica() {
        return publica;
    }

    public void setPublica(boolean publica) {
        this.publica = publica;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public List<Infraestrutura> getInfraestruturas() {
        return infraestruturas;
    }

    public void setInfraestruturas(List<Infraestrutura> infraestruturas) {
        this.infraestruturas = infraestruturas;
    }

    public List<Publicacao> getPublicacoes() {
        return publicacoes;
    }

    public void setPublicacoes(List<Publicacao> publicacoes) {
        this.publicacoes = publicacoes;
    }
    
    public int getValidado() {
    	return this.validado;
    }
    
    public String getUuid() {
    	return this.uuid;
    }
    
    public void setUuid(String uuid) {
    	this.uuid = uuid;
    }
    
    public void setValidado(int validado) {
    	this.validado = validado;
    }
    
    public void setSigla(String sigla) {
    	this.sigla = sigla;
    }
    
    public String getSigla() {
    	return this.sigla;
    }
    
    public String formatMail() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Nome: ").append(getNome()).append("\n");
        sb.append("Email: ").append(getEmail()).append("\n");
        sb.append("CEP: ").append(getCep()).append("\n");
        sb.append("MEC: ").append(getNumeroMec()).append("\n");
        sb.append("Campus: ").append(getCampus()).append("\n");
        sb.append("Tipo: ").append(getTipo()).append("\n");
        sb.append("Sigla: ").append(getSigla()).append("\n");
    	return sb.toString();
    }
    
    public String getTipo() {
    	return this.tipo;
    }
    
    public void setTipo(String tipo) {
    	this.tipo = tipo;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + Constants.USER_INSTITUICAO));	}

	@Override
	public String getPassword() {
		return getSenha();
	}

	@Override
	public String getUsername() {
		return getEmail();
	}


}
