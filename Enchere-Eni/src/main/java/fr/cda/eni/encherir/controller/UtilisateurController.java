package fr.cda.eni.encherir.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.cda.eni.encherir.dal.UtilisateurRepository;
import fr.cda.eni.encherir.model.Adresse;
import fr.cda.eni.encherir.model.Utilisateur;

@Controller
@RequestMapping("/utilisateur")
public class UtilisateurController {

	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	@PostMapping("/ajout")
	public @ResponseBody String ajoutNouvelUtilisateur(
			@RequestParam String nom, 
			@RequestParam String prenom, 
			@RequestParam String email, 
			@RequestParam String pseudo, 
			@RequestParam String motDePasse, 
			@RequestParam String rue,
			@RequestParam Integer codePostal,
			@RequestParam String ville,
			@RequestParam String telephone, 
			@RequestParam Integer credit, @RequestParam Boolean isAdministrateur) {
		
		Adresse adresse = new Adresse(rue, codePostal, ville);
		Utilisateur utilisateur = new Utilisateur(nom, prenom, email, pseudo, motDePasse, adresse, telephone, credit, isAdministrateur);
		
		utilisateurRepository.save(utilisateur);
		
		return "Saved";
	}
}
