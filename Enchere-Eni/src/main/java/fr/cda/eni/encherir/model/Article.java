package fr.cda.eni.encherir.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "article")
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Utilisateur utilisateur;

	@Column(nullable=false)
	private String titre;

	@Lob
	@Column(nullable=false)
	private String description;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime dateDebutEncheres;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime dateFinEncheres;
	
	private double prixInitial;
	private double prixVente;
	
	@OneToMany(
			mappedBy = "article",
			cascade = CascadeType.ALL, 
			orphanRemoval = true)
	private List<Fichier> fichierImage = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	private Categorie categorie;
	
	@OneToMany(
			mappedBy = "article",
			cascade = CascadeType.ALL, 
			orphanRemoval = true)
	private List<Enchere> listEnchere = new ArrayList<>();
	

	@OneToOne( optional = false, orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL )
	
	private Retrait lieuRetrait;
	
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
	 * @param utilisateur
	 * @param titre
	 * @param description
	 * @param dateDebutEncheres
	 * @param dateFinEncheres
	 * @param prixInitial
	 * @param prixVente
	 * @param fichierImage
	 * @param categorie
	 * @param listEnchere
	 * @param lieuRetrait
	 */
	public Article(Utilisateur utilisateur, String titre, String description, LocalDateTime dateDebutEncheres,
			LocalDateTime dateFinEncheres, double prixInitial, double prixVente, List<Fichier> fichierImage,
			Categorie categorie, List<Enchere> listEnchere, Retrait lieuRetrait) {
		this.utilisateur = utilisateur;
		this.titre = titre;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.fichierImage = fichierImage;
		this.categorie = categorie;
		this.listEnchere = listEnchere;
		this.lieuRetrait = lieuRetrait;
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
	 * @param dateFinEncheres the dateFinEncheres to set
	 */
	public void setDateFinEncheres(String dateFinEncheres) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		
		this.dateFinEncheres = LocalDateTime.parse(dateFinEncheres, formatter);
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

	
	/**
	 * @return the fichierImage
	 */
	public List<Fichier> getFichierImage() {
		return fichierImage;
	}

	/**
	 * @param fichierImage the fichierImage to set
	 */
	public void setFichierImage(List<Fichier> fichierImage) {
		this.fichierImage = fichierImage;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article )) return false;
        return id != null && id.equals(((Article) o).getId());
    }

	/**
	 * @return the listEnchere
	 */
	public List<Enchere> getListEnchere() {
		return listEnchere;
	}

	/**
	 * @param listEnchere the listEnchere to set
	 */
	public void setListEnchere(List<Enchere> listEnchere) {
		this.listEnchere = listEnchere;
	}

	/**
	 * @return the lieuRetrait
	 */
	public Retrait getLieuRetrait() {
		return lieuRetrait;
	}

	/**
	 * @param lieuRetrait the lieuRetrait to set
	 */
	public void setLieuRetrait(Retrait lieuRetrait) {
		this.lieuRetrait = lieuRetrait;
	}


}
