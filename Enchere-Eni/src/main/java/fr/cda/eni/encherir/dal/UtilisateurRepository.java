package fr.cda.eni.encherir.dal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.cda.eni.encherir.model.Utilisateur;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, Long> {
	

	@Query("select u from Utilisateur u where u.email = ?1")
	Utilisateur findByEmail(String email);

	@Query("select u from Utilisateur u where u.pseudo = ?1 or u.email = ?2")
	Utilisateur findByLoginOrEmail(String login, String email);
	
	@Query("select u from Utilisateur u where (u.pseudo = ?1 or u.email = ?1) and mot_de_passe=?2")
	Utilisateur seConnecter(String login, String motDePasse);


	@Query("select u from Utilisateur u where id=?1 and mot_de_passe=?2")
	Utilisateur findPassword(Long id, String motDePasse);
}
