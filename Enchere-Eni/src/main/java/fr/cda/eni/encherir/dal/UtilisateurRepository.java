package fr.cda.eni.encherir.dal;

import org.springframework.data.repository.CrudRepository;

import fr.cda.eni.encherir.model.Utilisateur;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, Long> {

}
