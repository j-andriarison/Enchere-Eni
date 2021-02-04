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
@Table(name = "fichier")
public class Fichier {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

	@Column(nullable=false, columnDefinition = "varchar(255)")
	private String 	nomFichier;

	private Long size;
	
	private LocalDateTime uploadedAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Article article;
	/**
	 * 
	 */
	public Fichier() {
	}

	/**
	 * @param id
	 * @param nomFichier
	 * @param size
	 * @param uploadedAt
	 */
	public Fichier(String nomFichier, Long size, LocalDateTime uploadedAt) {
		this.nomFichier = nomFichier;
		this.size = size;
		this.uploadedAt = uploadedAt;
	}

	/**
	 * @param id
	 * @param nomFichier
	 * @param size
	 * @param uploadedAt
	 * @param article
	 */
	public Fichier(String nomFichier, Long size, LocalDateTime uploadedAt, Article article) {
		
		this.nomFichier = nomFichier;
		this.size = size;
		this.uploadedAt = uploadedAt;
		this.article = article;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @return the nomFichier
	 */
	public String getNomFichier() {
		return nomFichier;
	}

	/**
	 * @param nomFichier the nomFichier to set
	 */
	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}

	/**
	 * @return the size
	 */
	public Long getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(Long size) {
		this.size = size;
	}

	/**
	 * @return the uploadedAt
	 */
	public LocalDateTime getUploadedAt() {
		return uploadedAt;
	}

	/**
	 * @param uploadedAt the uploadedAt to set
	 */
	public void setUploadedAt(LocalDateTime uploadedAt) {
		this.uploadedAt = uploadedAt;
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

	@Override
	public String toString() {
		return "Fichier [id=" + id + ", nomFichier=" + nomFichier + ", size=" + size + ", uploadedAt=" + uploadedAt
				+ ", article=" + article + "]";
	}
	
	
}
