package fr.cda.eni.encherir.dal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.cda.eni.encherir.model.Article;
import fr.cda.eni.encherir.model.Utilisateur;

public interface ArticleRepository extends CrudRepository<Article, Long> {

	@Query("select a from Article a where a.utilisateur = ?1")
	Iterable<Article> findAllByUtilisateur(Utilisateur idUtilisateur);
	
}
