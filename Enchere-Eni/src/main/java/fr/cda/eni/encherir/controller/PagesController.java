package fr.cda.eni.encherir.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController 
{
	
	
	@GetMapping("/home") 
	public String home(HttpServletRequest request) {
		
		return "pages/index";
	}
	
	@GetMapping("/accueil") 
	public String accueil(HttpServletRequest request) {
		
		return "pages/index_connecte";
	}
	@GetMapping("/vente") 
	public String vente() {
		return "pages/vente";
	}
	@GetMapping("/nouvelle_vente") 
	public String nouvelleVente() {
		return "pages/nouvelle_vente";
	}
}
