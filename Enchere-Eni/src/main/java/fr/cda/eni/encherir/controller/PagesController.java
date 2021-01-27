package fr.cda.eni.encherir.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.cda.eni.encherir.dal.UtilisateurRepository;
import fr.cda.eni.encherir.model.Adresse;
import fr.cda.eni.encherir.model.Utilisateur;

@Controller
public class PagesController 
{
	
	
	@GetMapping("/home") 
	public String home() {
		return "pages/index";
	}
	
	@GetMapping("/accueil") 
	public String accueil() {
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
