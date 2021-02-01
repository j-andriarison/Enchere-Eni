package fr.cda.eni.encherir.controller;

import javax.servlet.http.HttpServletRequest;

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
public class UtilisateurController {

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@GetMapping("/connexion")
	public String login() {
		return "/pages/utilisateur/connexion";
	}

	@PostMapping("/connexion")
	public String login(HttpServletRequest request) {
		String loginOrEmail = request.getParameter("pseudoOrEmail");
		String motDePasse = request.getParameter("motDePasse");

		System.out.println(loginOrEmail + motDePasse);
		Utilisateur utilisateurConnecte = utilisateurRepository.seConnecter(loginOrEmail, motDePasse);
		if (utilisateurConnecte == null) {
			request.setAttribute("messageErreurConnexion", "Identifiant ou mot de passe incorrect");
			return "/pages/utilisateur/connexion";
		}

		request.getSession().setAttribute("utilisateurConnecte", utilisateurConnecte.getId());
		return "/pages/index";
	}

	@GetMapping("/inscription")
	public String inscription() {
		return "pages/utilisateur/inscription";
	}

	@PostMapping("/inscription")
	public String inscription(@ModelAttribute("utilisateur") Utilisateur utilisateur, @RequestParam String rue,
			@RequestParam Integer codePostal, @RequestParam String ville, HttpServletRequest request) {

		Adresse adresse = new Adresse(rue, codePostal, ville);
		// on rattache l'adresse a 'lutilisateur
		utilisateur.setAdresse(adresse);

		// l'utilisateur inscrit ne sera pas enregiste en tant que administrateur
		utilisateur.setIsAdministrateur(false);

		// le nouvel utilisateur n'ayant pas de credit
		utilisateur.setCredit(0);

		// Verification si tel adresse est enregistre deja dans la table

		if (utilisateurRepository.findByEmail(utilisateur.getEmail()) == null) {

			// enregistrement dans la table en passant par le repository
			Utilisateur userCreated = utilisateurRepository.save(utilisateur);
			request.getSession().setAttribute("utilisateurConnecte", userCreated.getId());
		} else {

			request.setAttribute("messageErreurInscription",
					"L'adresse email:" + utilisateur.getEmail() + "est deja enregistre dans la base");
			request.setAttribute("messageInvitationConnexion", "Veuillez vous connecter");
			return "pages/utilisateur/connexion";
		}

		return "pages/index";

	}

	@GetMapping("/utilisateur/profile")
	public String profile(HttpServletRequest request) {

		Long sessionIdUtilisateur = (Long) request.getSession().getAttribute("utilisateurConnecte");

		if (sessionIdUtilisateur == null) {
			return "redirect:/connexion";
		}
		Utilisateur profileUtilisateur = utilisateurRepository.findById(sessionIdUtilisateur)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + sessionIdUtilisateur));
		request.setAttribute("profileUtilisateur", profileUtilisateur);
		return "pages/utilisateur/profile";
	}

	@GetMapping("/utilisateur/profile/edit")
	public String profileEdit(HttpServletRequest request) {

		Long sessionIdUtilisateur = (Long) request.getSession().getAttribute("utilisateurConnecte");

		if (sessionIdUtilisateur == null) {
			return "redirect:/connexion";
		}
		Utilisateur profileUtilisateur = utilisateurRepository.findById(sessionIdUtilisateur)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + sessionIdUtilisateur));
		request.setAttribute("profileUtilisateur", profileUtilisateur);
		return "pages/utilisateur/edit";
	}

	@PostMapping("/utilisateur/profile/edit")
	public String profileEdit(@ModelAttribute("utilisateur") Utilisateur utilisateur, @RequestParam String rue,
			@RequestParam Integer codePostal, @RequestParam String ville, HttpServletRequest request) {

		Adresse adresse = new Adresse(rue, codePostal, ville);
		// on rattache l'adresse a 'lutilisateur
		utilisateur.setAdresse(adresse);

		// l'utilisateur inscrit ne sera pas enregiste en tant que administrateur
		utilisateur.setIsAdministrateur(false);

		// le nouvel utilisateur n'ayant pas de credit
		utilisateur.setCredit(0);

		// Verification utilisateur a modifier

		Long sessionIdUtilisateur = (Long) request.getSession().getAttribute("utilisateurConnecte");

		// Verification de mot de passe
		Utilisateur utilisateurPassword = utilisateurRepository.findPassword(sessionIdUtilisateur,
				utilisateur.getMotDePasse());

		String page = null;

		if (utilisateurPassword == null) {
			request.setAttribute("messageErreurPassword", "Erreur de mot de passe");
			request.setAttribute("messageInvitationConnexion", "Veuillez vous reconnecter");
			request.setAttribute("profileUtilisateur", utilisateur);
			page = "/pages/utilisateur/edit";
		} else {

			utilisateurPassword.setNom(utilisateur.getNom());
			utilisateurPassword.setPrenom(utilisateur.getPrenom());
			utilisateurPassword.setEmail(utilisateur.getEmail());
			utilisateurPassword.setPseudo(utilisateur.getPseudo());
			utilisateurPassword.setTelephone(utilisateur.getTelephone());
			utilisateurPassword.setAdresse(adresse);

			// appliquer la mise a jour de la table
			utilisateurRepository.save(utilisateurPassword);

			page = "redirect:/utilisateur/profile";
		}
		return page;

	}


	@GetMapping("/utilisateur/profile/supprimer")
	public String profileSupprimer(HttpServletRequest request) {

		Long sessionIdUtilisateur = (Long) request.getSession().getAttribute("utilisateurConnecte");
		// Verification d'une enchere en cours
		
		// Suppression
		
		//TODO 
		
		
		
		if (sessionIdUtilisateur == null) {
			return "redirect:/connexion";
		}
		request.setAttribute("messageCompteSupprimer", "Votre compte a ete supprime.");
		
		request.getSession().invalidate();
		return "pages/utilisateur/messager";
	}
	@GetMapping("/logout")
	public String destroySession(HttpServletRequest request) {
		request.getSession().invalidate();
		return "pages/index";
	}
}
