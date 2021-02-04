package fr.cda.eni.encherir.model;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "utilisateur")

public class Utilisateur {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, columnDefinition = "varchar(255)")
	private String nom;
	private String prenom;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String pseudo;
	private String motDePasse;

	@OneToOne( optional = false, orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL )
	@JoinColumn(name = "adresse_id")
	private Adresse adresse;
	
	private String telephone;
	private Integer credit;

	@Column(nullable = false)
	private Boolean isAdministrateur;

	@OneToMany(
			mappedBy = "utilisateur",
			cascade = CascadeType.ALL, 
			orphanRemoval = true)
	private List<Article> articles = new ArrayList<>();

	/**
	 * 
	 */
	public Utilisateur() {
	}

	/**
	 * @param nom
	 * @param prenom
	 * @param email
	 * @param pseudo
	 * @param motDePasse
	 * @param adresse
	 * @param telephone
	 * @param credit
	 * @param isAdministrateur
	 */
	public Utilisateur(String nom, String prenom, String email, String pseudo, String motDePasse, Adresse adresse,
			String telephone, Integer credit, Boolean isAdministrateur) {
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.pseudo = pseudo;
		this.motDePasse = motDePasse;
		this.adresse = adresse;
		this.telephone = telephone;
		this.credit = credit;
		this.isAdministrateur = isAdministrateur;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the pseudo
	 */
	public String getPseudo() {
		return pseudo;
	}

	/**
	 * @param pseudo the pseudo to set
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	/**
	 * @return the motDePasse
	 */
	public String getMotDePasse() {
		return motDePasse;
	}

	/**
	 * @param motDePasse the motDePasse to set
	 */
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	/**
	 * @return the adresse
	 */
	public Adresse getAdresse() {
		return adresse;
	}

	/**
	 * @param adresse the adresse to set
	 */
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the credit
	 */
	public Integer getCredit() {
		return credit;
	}

	/**
	 * @param credit the credit to set
	 */
	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	/**
	 * @return the isAdministrateur
	 */
	public Boolean getIsAdministrateur() {
		return isAdministrateur;
	}

	/**
	 * @param isAdministrateur the isAdministrateur to set
	 */
	public void setIsAdministrateur(Boolean isAdministrateur) {
		this.isAdministrateur = isAdministrateur;
	}

	/**
	 * @return the articles
	 */
	public List<Article> getArticles() {
		return articles;
	}

	/**
	 * @param articles the articles to set
	 */
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	@Override
	public String toString() {
		return "Utilisateur [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", pseudo="
				+ pseudo + ", motDePasse=" + motDePasse + ", adresse=" + adresse + ", telephone=" + telephone
				+ ", credit=" + credit + ", isAdministrateur=" + isAdministrateur + "]";
	}

}
