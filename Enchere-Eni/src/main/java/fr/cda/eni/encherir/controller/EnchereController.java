package fr.cda.eni.encherir.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.cda.eni.encherir.dal.ArticleRepository;
import fr.cda.eni.encherir.dal.EnchereRepository;
import fr.cda.eni.encherir.dal.UtilisateurRepository;
import fr.cda.eni.encherir.model.Article;
import fr.cda.eni.encherir.model.Enchere;
import fr.cda.eni.encherir.model.Retrait;
import fr.cda.eni.encherir.model.Utilisateur;

@Controller
public class EnchereController {

	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	EnchereRepository enchereRepository;

	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	@PostMapping("/encherir")
	public String encherir(HttpServletRequest request, @ModelAttribute("enchere") Enchere enchere,
			RedirectAttributes redirectAttributes) {
		
		Map<String, String> sessionUtilisateurMap = (Map<String, String>) request.getSession()
				.getAttribute("sessionUtilisateurMap");
		
		
		Boolean controleValidation = true;

		if (sessionUtilisateurMap == null) {
			return "redirect:/connexion";
		}
		// Recuperation de la liste des articles par utilisateur donnee
		Long idArticle = Long.parseLong(request.getParameter("article"));
		
		// L'article a encherir
		Article article = articleRepository.findById(idArticle)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + idArticle));

		Long idUtilisateur = Long.parseLong(request.getParameter("utilisateur"));
		

		// L'utilisateur qui fait l'offre
		Utilisateur profileUtilisateur = utilisateurRepository.findById(idUtilisateur)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + idUtilisateur));

		// Validation de l'enchere
		// Le prix de l'article actuel
		double prixActuel = article.getPrixInitial();
		List<Enchere> listEnchere= article.getListEnchere();
		// S'il y un enchere concernant l'article, le dernier prix sera le prix de l'article
		
		if(!listEnchere.isEmpty()) {
			prixActuel = listEnchere.get(listEnchere.size()-1).getMontantEnchere();
		}
		
		// L'enchereur ne doit pas etre l'annonceur
		if(profileUtilisateur.getId() == article.getUtilisateur().getId()) {
			controleValidation =  false;
			redirectAttributes.addFlashAttribute("offreEchoueeUtilisateur", "Vous ne pouvez pas miser sur votre offre");
			
		}
		// l'enchere doit etre superieur au precedent
		if(profileUtilisateur.getCredit() < prixActuel ) {
			controleValidation =  false;
			redirectAttributes.addFlashAttribute("offreEchouee", "Votre solde est inferieur du prix mis");

		}

		if(controleValidation) {
			
			enchere.setArticle(article);
			enchere.setEncherit(profileUtilisateur);
			enchere.setDateEnchere(LocalDateTime.now());
			enchereRepository.save(enchere);
			
			redirectAttributes.addFlashAttribute("offreReuissie", "Bravo ! Votre offre est le max ");
		}
		
		return "redirect:/article/enchere/"+article.getTitre()+"_"+article.getId();
	}

}
