package fr.cda.eni.encherir.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * L'entity Adresse est un composant de l'entity Utilisateur
 * Elle est indepenant mais reutilisable donc elle porte l'annontation @Embeddable
 * 
 * @author Jean ANDRIANARISON
 * 
 */
@Entity
@Table(name = "adresse")
public class Adresse {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String rue;
	@Column(nullable=false)
	private int codePostal;
	@Column(nullable=false)
	private String ville;
	
	/**
	 * 
	 */
	public Adresse() {
	}


	/**
	 * @param rue
	 * @param codePostal
	 * @param ville
	 */
	public Adresse(String rue, int codePostal, String ville) {
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @return the rue
	 */
	public String getRue() {
		return rue;
	}


	/**
	 * @param rue the rue to set
	 */
	public void setRue(String rue) {
		this.rue = rue;
	}


	/**
	 * @return the codePostal
	 */
	public int getCodePostal() {
		return codePostal;
	}


	/**
	 * @param codePostal the codePostal to set
	 */
	public void setCodePostal(int codePostal) {
		this.codePostal = codePostal;
	}


	/**
	 * @return the ville
	 */
	public String getVille() {
		return ville;
	}


	/**
	 * @param ville the ville to set
	 */
	public void setVille(String ville) {
		this.ville = ville;
	}

	
	
}
