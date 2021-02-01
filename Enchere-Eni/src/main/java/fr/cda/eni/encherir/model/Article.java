package fr.cda.eni.encherir.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "article")
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Utilisateur utilisateur;

	@Column(nullable=false, columnDefinition = "varchar(255)")
	private String titre;

	@Column(nullable=false, columnDefinition = "varchar(255)")
	private String description;
	
	private LocalDateTime dateDebutEncheres;
	private LocalDateTime dateFinEncheres;
	private double prixInitial;
	private double prixVente;
	

	@ManyToOne(fetch = FetchType.LAZY)
	private Categorie categorie;
	
	
	
	
	
	/**
	 * Constructor sans parametre
	 */
	public Article() {
	}

	/**
	 * @param utilisateur
	 * @param titre
	 * @param description
	 * @param dateDebutEncheres
	 * @param dateFinEncheres
	 * @param prixInitial
	 * @param prixVente
	 * @param categorie
	 */
	public Article(Utilisateur utilisateur, String titre, String description, LocalDateTime dateDebutEncheres,
			LocalDateTime dateFinEncheres, double prixInitial, double prixVente, Categorie categorie) {
		this.utilisateur = utilisateur;
		this.titre = titre;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.categorie = categorie;
	}





	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @return the utilisateur
	 */
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	/**
	 * @param utilisateur the utilisateur to set
	 */
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	/**
	 * @return the titre
	 */
	public String getTitre() {
		return titre;
	}

	/**
	 * @param titre the titre to set
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the dateDebutEncheres
	 */
	public LocalDateTime getDateDebutEncheres() {
		return dateDebutEncheres;
	}

	/**
	 * @param dateDebutEncheres the dateDebutEncheres to set
	 */
	public void setDateDebutEncheres(LocalDateTime dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}

	/**
	 * @return the dateFinEncheres
	 */
	public LocalDateTime getDateFinEncheres() {
		return dateFinEncheres;
	}

	/**
	 * @param dateFinEncheres the dateFinEncheres to set
	 */
	public void setDateFinEncheres(LocalDateTime dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}

	/**
	 * @return the prixInitial
	 */
	public double getPrixInitial() {
		return prixInitial;
	}

	/**
	 * @param prixInitial the prixInitial to set
	 */
	public void setPrixInitial(double prixInitial) {
		this.prixInitial = prixInitial;
	}

	/**
	 * @return the prixVente
	 */
	public double getPrixVente() {
		return prixVente;
	}

	/**
	 * @param prixVente the prixVente to set
	 */
	public void setPrixVente(double prixVente) {
		this.prixVente = prixVente;
	}


	/**
	 * @return the categorie
	 */
	public Categorie getCategorie() {
		return categorie;
	}

	/**
	 * @param categorie the categorie to set
	 */
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article )) return false;
        return id != null && id.equals(((Article) o).getId());
    }

	@Override
	public String toString() {
		return "Article [id=" + id + ", utilisateur=" + utilisateur + ", titre=" + titre + ", description="
				+ description + ", dateDebutEncheres=" + dateDebutEncheres + ", dateFinEncheres=" + dateFinEncheres
				+ ", prixInitial=" + prixInitial + ", prixVente=" + prixVente + ", categorieArticle=" + categorie
				+ "]";
	}
	
	
}
