package fr.cda.eni.encherir.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "echere")
public class Enchere {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Utilisateur encherit;
	
	private LocalDateTime dateEnchere;
	
	private double montantEnchere;
	

	@ManyToOne(fetch = FetchType.LAZY)
	private Article article;
	
	@OneToOne( optional = false, orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL )
	@JoinColumn(name = "retrait_id")
	private Retrait lieuRetrait;
	/**
	 * Constructor sans parametre
	 */
	public Enchere() {
	}

	/**
	 * @param encherit
	 * @param dateEnchere
	 * @param montantEnchere
	 * @param article
	 */
	public Enchere(Utilisateur encherit, LocalDateTime dateEnchere, double montantEnchere, Article article) {
		this.encherit = encherit;
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
		this.article = article;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the encherit
	 */
	public Utilisateur getEncherit() {
		return encherit;
	}

	/**
	 * @param encherit the encherit to set
	 */
	public void setEncherit(Utilisateur encherit) {
		this.encherit = encherit;
	}

	/**
	 * @return the dateEnchere
	 */
	public LocalDateTime getDateEnchere() {
		return dateEnchere;
	}

	/**
	 * @param dateEnchere the dateEnchere to set
	 */
	public void setDateEnchere(LocalDateTime dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	/**
	 * @return the montantEnchere
	 */
	public double getMontantEnchere() {
		return montantEnchere;
	}

	/**
	 * @param montantEnchere the montantEnchere to set
	 */
	public void setMontantEnchere(double montantEnchere) {
		this.montantEnchere = montantEnchere;
	}

	/**
	 * @return the article
	 */
	public Article getArticle() {
		return article;
	}

	/**
	 * @param article the article to set
	 */
	public void setArticle(Article article) {
		this.article = article;
	}

	
}
