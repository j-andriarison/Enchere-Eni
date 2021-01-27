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
public class SecurityController {


	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	
	@GetMapping("/login") 
	public String login() {
		return "pages/login";
	}
	@GetMapping("/signin") 
	public String signin() {
		return "pages/signin";
	}

	@PostMapping("/signin") 
	public String signin(@ModelAttribute("utilisateur") Utilisateur utilisateur, 
			@RequestParam String rue,
			@RequestParam Integer codePostal,
			@RequestParam String ville) {
		System.out.println(utilisateur);
		
		

		Adresse adresse = new Adresse(rue, codePostal, ville);
		// on rattache l'adresse a 'lutilisateur
		utilisateur.setAdresse(adresse);
		
		// l'utilisateur inscrit ne sera pas enregiste en tant que administrateur
		utilisateur.setIsAdministrateur(false);
		
		// le nouvel utilisateur n'ayant pas de credit
		utilisateur.setCredit(0);
		
		// enregistrement dans la table en passant par le repository
		utilisateurRepository.save(utilisateur);
		
		
		return "pages/signin";
	}
}
