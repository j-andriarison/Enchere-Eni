package fr.cda.eni.encherir.controller;

import java.util.HashMap;
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

import fr.cda.eni.encherir.dal.UtilisateurRepository;
import fr.cda.eni.encherir.model.Adresse;
import fr.cda.eni.encherir.model.Utilisateur;

@Controller
public class UtilisateurController {

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	// -----------------------------------------------------------------------------------------------------
	@GetMapping("/connexion")
	public String login() {
		return "/pages/utilisateur/connexion";
	}
	@PostMapping("/connexion")
	public String login(HttpServletRequest request) {
		String loginOrEmail = request.getParameter("pseudoOrEmail");
		String motDePasse = request.getParameter("motDePasse");

		System.out.println(loginOrEmail + motDePasse);
		Utilisateur sessionIdUtilisateur = utilisateurRepository.seConnecter(loginOrEmail, motDePasse);
		if (sessionIdUtilisateur == null) {
			request.setAttribute("messageErreurConnexion", "Identifiant ou mot de passe incorrect");
			return "/pages/utilisateur/connexion";
		}
		Map<String, String> sessionUtilisateurMap = new HashMap<String, String>();
		sessionUtilisateurMap.put("id", String.valueOf(sessionIdUtilisateur.getId()));
		sessionUtilisateurMap.put("isAdministrateur", String.valueOf(sessionIdUtilisateur.getIsAdministrateur()));

		request.getSession().setAttribute("sessionUtilisateurMap", sessionUtilisateurMap);
		return "/pages/index";
	}

	// -----------------------------------------------------------------------------------------------------
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
			request.getSession().setAttribute("sessionUtilisateur", userCreated.getId());

			Map<String, String> sessionUtilisateurMap = new HashMap<String, String>();
			sessionUtilisateurMap.put("id", String.valueOf(userCreated.getId()));
			sessionUtilisateurMap.put("isAdministrateur", String.valueOf(userCreated.getIsAdministrateur()));

			request.getSession().setAttribute("sessionUtilisateurMap", sessionUtilisateurMap);

		} else {

			request.setAttribute("messageErreurInscription",
					"L'adresse email:" + utilisateur.getEmail() + "est deja enregistre dans la base");
			request.setAttribute("messageInvitationConnexion", "Veuillez vous connecter");
			return "pages/utilisateur/connexion";
		}

		return "redirect:/home";

	}

	// -----------------------------------------------------------------------------------------------------
	@RequestMapping(value = { "/utilisateur/profile", "/utilisateur/profile/{userId}" })
	public String profile(HttpServletRequest request,
			@PathVariable(value = "userId", required = false) Long idUtilisateurAVisualiser) {

		// Recuperation de la session Utilisateur
		@SuppressWarnings("unchecked")
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

		request.setAttribute("profileUtilisateur", profileUtilisateur);
		
		return "pages/utilisateur/profile";
	}

	@RequestMapping(value = { "/utilisateur/profile/edit", "/utilisateur/profile/edit/{userId}" })
	public String profileEdit(HttpServletRequest request,
			@PathVariable(value = "userId", required = false) Long idUtilisateurAVisualiser) {

		// Recuperation de la session Utilisateur
				@SuppressWarnings("unchecked")
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

		// Verification de mot de passe
		Utilisateur utilisateurPassword = utilisateurRepository.findPassword(Long.parseLong(request.getParameter("id")),
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

	// -----------------------------------------------------------------------------------------------------
	@GetMapping("/utilisateur/profile/motdepasse-change")
	public String profileModifMotDePasse(HttpServletRequest request) {

		// Recuperation de la session Utilisateur
		@SuppressWarnings("unchecked")
		Map<String, String> sessionUtilisateurMap = (Map<String, String>) request.getSession()
				.getAttribute("sessionUtilisateurMap");
		
		if (sessionUtilisateurMap == null) {
			return "redirect:/connexion";
		}

		Utilisateur profileUtilisateur = utilisateurRepository.findById(Long.parseLong(sessionUtilisateurMap.get("id")))
				.orElseThrow(() -> new IllegalArgumentException(
						"Invalid user Id:" + Long.parseLong(sessionUtilisateurMap.get("id"))));

		request.setAttribute("profileUtilisateur", profileUtilisateur);
		return "pages/utilisateur/motdepasse-change";
	}

	// -----------------------------------------------------------------------------------------------------
	
	@PostMapping("/utilisateur/profile/motdepasse-change")
	public String profileModifMotDePasse(@ModelAttribute("utilisateur") Utilisateur utilisateur,
			HttpServletRequest request) {

		System.out.println(Long.parseLong(request.getParameter("id")) + 
				utilisateur.getMotDePasse());
		// Verification utilisateur a modifier

		// Verification de mot de passe
		Utilisateur utilisateurPassword = utilisateurRepository.findPassword(Long.parseLong(request.getParameter("id")),
				request.getParameter("motDePasseActuel"));

		String page = null;

		if (utilisateurPassword == null) {
			request.setAttribute("messageErreurPassword", "Erreur de mot de passe");
			request.setAttribute("messageInvitationConnexion", "Veuillez vous reconnecter");
			page = "/pages/utilisateur/motdepasse-change";
		} else {

			
			utilisateurPassword.setMotDePasse(utilisateur.getMotDePasse());

			// appliquer la mise a jour de la table
			utilisateurRepository.save(utilisateurPassword);
			
			request.setAttribute("messageSucces", "Votre mot de passe a ete modifie");

			page = "redirect:/utilisateur/profile";
		}
		return page;

	}

	// -----------------------------------------------------------------------------------------------------
	@GetMapping("/utilisateur/profile/supprimer")
	public String profileSupprimer(HttpServletRequest request) {

		Long sessionUtilisateur = (Long) request.getSession().getAttribute("sessionUtilisateur");
		// Verification d'une enchere en cours

		// Suppression

		// TODO Faut cmpleter la page supprimer utitilisateur

		if (sessionUtilisateur == null) {
			return "redirect:/connexion";
		}
		request.setAttribute("messageCompteSupprimer", "Votre compte a ete supprime.");

		request.getSession().invalidate();
		return "pages/utilisateur/messager";
	}

	// -----------------------------------------------------------------------------------------------------
	@GetMapping("/logout")
	public String destroySession(HttpServletRequest request) {
		request.getSession().invalidate();
		return "pages/index";
	}
}
