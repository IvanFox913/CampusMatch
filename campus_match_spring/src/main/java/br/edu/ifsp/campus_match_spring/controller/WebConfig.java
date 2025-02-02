package br.edu.ifsp.campus_match_spring.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.edu.ifsp.campus_match_spring.util.Constants;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;


@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Value("${spring.mail.username}")
	private String mailUsername;
	
	@Value("${spring.mail.port}")
	private int mailPort;
	
	@Value("${spring.mail.host}")
	private String mailHost;
	
	@Value("${spring.mail.properties.mail.smtp.auth}")
	private String mailAuth;
	
	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private String mailTlsEnable;
	
	@Value("${spring.mail.password}")
	private String mailPassword;
		
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("/pages/web/lading_page2");
	}

	@Bean
	JavaMailSender javaMailSender() {
	    System.out.println("Creating JavaMailSender bean");
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost(this.mailHost);
	    mailSender.setPort(this.mailPort);
	    mailSender.setUsername(this.mailUsername);
	    mailSender.setPassword(this.mailPassword);

	    Properties properties = mailSender.getJavaMailProperties();
	    properties.put("mail.transport.protocol", "smtp");
	    properties.put("mail.smtp.auth", this.mailAuth);
	    properties.put("mail.smtp.starttls.enable", this.mailTlsEnable);
	    properties.put("mail.debug", "true");

	    return mailSender;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf->csrf.disable())
			.authorizeHttpRequests(
					
					(requests) -> requests
					.requestMatchers(
							"/"
							,"/css/**"
							,"/auth/**"
							,"/estudantes/save/**"
							,"/instituicoes/save/**"
							,"/favicon.ico"
							).permitAll()
					
					.requestMatchers(
							"/instituicoes/**"
							).hasRole(Constants.USER_INSTITUICAO)
					
					.requestMatchers(
							"/estudantes/**"
							).hasRole(Constants.USER_ESTUDANTE)
					
					.anyRequest().authenticated()
					
					
			).formLogin(
					login -> 
						login.loginPage("/auth/login")
						.successHandler(new CustomAuthenticationSuccessHandler())
					)
			.httpBasic(Customizer.withDefaults())
			
			;

		return http.build();
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
