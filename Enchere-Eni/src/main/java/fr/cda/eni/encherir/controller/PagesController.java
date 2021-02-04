package fr.cda.eni.encherir.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.cda.eni.encherir.dal.ArticleRepository;
import fr.cda.eni.encherir.dal.CategorieRepository;
import fr.cda.eni.encherir.dal.FichierRepository;
import fr.cda.eni.encherir.dal.UtilisateurRepository;
import fr.cda.eni.encherir.model.Article;
import fr.cda.eni.encherir.model.Utilisateur;

@Controller
public class PagesController 
{
	

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Autowired
	private FichierRepository fichierRepository;

	@Autowired
	private CategorieRepository categorieRepository;
	
	
	public static String uploadRootPath = System.getProperty("user.dir") + "/uploads/images";

	
	@RequestMapping(value = { "/" })
	public String listeArticle(HttpServletRequest request,
			@PathVariable(value = "userId", required = false) Long idUtilisateurAVisualiser) throws Exception {
		
		
		// Recuperation de la liste des articles par utilisateur donnee
		
		Iterable<Article> listeArticle = null;
		try {
			listeArticle = articleRepository.findAll();
			
		} catch (Exception e) {

		      return "/pages/index";
		}
		
		request.setAttribute("listeArticle", listeArticle);
		
		//System.out.println(listeArticle);
		
		request.setAttribute("uploadRootPath", uploadRootPath);
		
		return "/pages/articles/index";
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
