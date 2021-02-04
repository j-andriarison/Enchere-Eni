package fr.cda.eni.encherir.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.cda.eni.encherir.dal.ArticleRepository;
import fr.cda.eni.encherir.dal.CategorieRepository;
import fr.cda.eni.encherir.dal.FichierRepository;
import fr.cda.eni.encherir.dal.UtilisateurRepository;
import fr.cda.eni.encherir.model.Article;
import fr.cda.eni.encherir.model.Categorie;
import fr.cda.eni.encherir.model.Fichier;
import fr.cda.eni.encherir.model.Retrait;
import fr.cda.eni.encherir.model.Utilisateur;

@Controller
public class ArticleController {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Autowired
	private FichierRepository fichierRepository;

	@Autowired
	private CategorieRepository categorieRepository;
	
	
	public static String uploadRootPath = System.getProperty("user.dir") + "/uploads/images";

	@GetMapping("article/creer")
	public String creerArticle(HttpServletRequest request) {
		Iterable<Categorie> listCategorie = categorieRepository.findAll();
		
		request.setAttribute("listCategorie", listCategorie);
		
		Map<String, String> sessionUtilisateurMap = (Map<String, String>) request.getSession()
				.getAttribute("sessionUtilisateurMap");

		Long utilisateurAChercher;
		
		if (sessionUtilisateurMap == null) {
			return "redirect:/connexion";
		} else {
			 utilisateurAChercher =  Long.parseLong(sessionUtilisateurMap.get("id"));
		}


		Utilisateur profileUtilisateur = utilisateurRepository.findById(utilisateurAChercher)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + utilisateurAChercher));

		request.setAttribute("profileUtilisateur", profileUtilisateur);
		
		
		return "/pages/articles/article-creer";
	}

	@PostMapping("/article/creer")
	public String creerArticle(
			HttpServletRequest request,
			@ModelAttribute("article") Article article, 
			@ModelAttribute("retrait") Retrait retrait, 
			@RequestParam("images") MultipartFile[] images,
			@RequestParam("dateDebutEnchere") String dateDebutEncheres,
			@RequestParam("dateFinEnchere") String dateFinEncheres, 
			RedirectAttributes redirectAttributes) {

		
		File uploadRootDir = new File(uploadRootPath);
		// Create directory if it not exists.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		// MultipartFile[] fileDatas = images.getOriginalFilename();
		//
		List<File> uploadedFiles = new ArrayList<File>();
		List<String> failedFiles = new ArrayList<String>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		article.setDateDebutEncheres(LocalDateTime.parse(dateDebutEncheres, formatter));
		article.setDateFinEncheres(LocalDateTime.parse(dateFinEncheres, formatter));
		
		Map<String, String> sessionUtilisateurMap = (Map<String, String>) request.getSession()
				.getAttribute("sessionUtilisateurMap");

		Long utilisateurAChercher;
		
		if (sessionUtilisateurMap == null) {
			return "redirect:/connexion";
		} else {
			 utilisateurAChercher =  Long.parseLong(sessionUtilisateurMap.get("id"));
		}


		Utilisateur profileUtilisateur = utilisateurRepository.findById(utilisateurAChercher)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + utilisateurAChercher));

		
		article.setUtilisateur(profileUtilisateur);
		
		// Adresse de retrait
		article.setLieuRetrait(retrait);
		
		
		List<Fichier> listImage = new ArrayList<>();
		
		// Enregistrement de l'article dans la base
		articleRepository.save(article);
		
		for (MultipartFile fileData : images) {

			// Client File Name
			String nomFichierImage = fileData.getOriginalFilename();
			System.out.println("Client File Name = " + nomFichierImage);

			
			 if (nomFichierImage != null && nomFichierImage.length() > 0) {
			 
				try {
					// Create the file at server
					File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + nomFichierImage);

					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(fileData.getBytes());
					stream.close();

					uploadedFiles.add(serverFile);

					// On attache l'image avec l'article
					Fichier fichierImage = new Fichier(nomFichierImage, fileData.getSize(), LocalDateTime.now(), article);
					
					listImage.add(fichierImage);
					
					// Enregistrement des fichiers images
					fichierRepository.save(fichierImage);
					
					// on rattache les images a l'article
					System.out.println("Write file: " + serverFile);
				} catch (Exception e) {
					System.out.println("Error Write file: " + nomFichierImage);
					failedFiles.add(nomFichierImage);
				}
			}
			
		}
		article.setFichierImage(listImage);
		
		redirectAttributes.addFlashAttribute("message", "You successfully uploaded all files!");

		return "redirect:/article/enchere/"+article.getTitre()+"_"+article.getId();
	}

	@RequestMapping(value = { "/article/mes-ventes", "/article/{userPseudo}-ventes/{userId}" })
	public String listeArticle(HttpServletRequest request,
			@PathVariable(value = "userPseudo", required = false) String pseudoVendeur,
			@PathVariable(value = "userId", required = false) Long idUtilisateurAVisualiser) {
		
		Map<String, String> sessionUtilisateurMap = (Map<String, String>) request.getSession()
				.getAttribute("sessionUtilisateurMap");
		Long utilisateurAChercher;

		if (sessionUtilisateurMap == null && idUtilisateurAVisualiser == null) {
			return "redirect:/connexion";
		} else {
			utilisateurAChercher = (idUtilisateurAVisualiser == null) ? Long.parseLong(sessionUtilisateurMap.get("id"))
					: idUtilisateurAVisualiser;
		}

		Utilisateur profileUtilisateur = utilisateurRepository.findById(utilisateurAChercher)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + utilisateurAChercher));

			
		// Recuperation de la liste des articles par utilisateur donnee
		
		Iterable<Article> listeArticle = articleRepository.findAllByUtilisateur(profileUtilisateur);
		request.setAttribute("listeArticle", listeArticle);
		
		//System.out.println(listeArticle);
		
		request.setAttribute("profileUtilisateur", profileUtilisateur);
		request.setAttribute("uploadRootPath", uploadRootPath);
		
		return "/pages/articles/index";
	}

	@RequestMapping(value = { "/article/enchere/{titreArticle}_{idArticle}" })
	public String detailArticle(HttpServletRequest request,
			@PathVariable(value = "idArticle", required = false) Long idArticle) {
		
		Map<String, String> sessionUtilisateurMap = (Map<String, String>) request.getSession()
				.getAttribute("sessionUtilisateurMap");
		Long utilisateurAChercher;

		if (sessionUtilisateurMap == null) {
			return "redirect:/connexion";
		}
			
		// Recuperation de la liste des articles par utilisateur donnee
		
		Article detailArticle = articleRepository.findById(idArticle)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + idArticle));
		
		
		request.setAttribute("detailArticle", detailArticle);
		
		
		return "/pages/articles/detail";
	}
}