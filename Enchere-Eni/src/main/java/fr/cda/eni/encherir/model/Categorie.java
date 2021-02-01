package fr.cda.eni.encherir.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "categorie")

public class Categorie {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long 	id;


	@Column(nullable=false, columnDefinition = "varchar(255)")
	private String 	titre;
	

	@OneToMany(
			mappedBy = "categorie",
			cascade = CascadeType.ALL, 
			orphanRemoval = true)
	private List<Article> articles = new ArrayList<>();


	/**
	 * Constructeur sans parametre
	 */
	public Categorie() {
	}

	/**
	 * @param titre
	 */
	public Categorie(String titre) {
		this.titre = titre;
	}
	

	/**
	 * @param titre
	 * @param articles
	 */
	public Categorie(String titre, List<Article> articles) {
		this.titre = titre;
		this.articles = articles;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
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


	
}
