package fr.cda.eni.encherir.dal;

import org.springframework.data.repository.CrudRepository;

import fr.cda.eni.encherir.model.Fichier;

public interface FichierRepository extends CrudRepository<Fichier, Long> {

}
