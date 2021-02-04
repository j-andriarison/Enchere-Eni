package fr.cda.eni.encherir.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="retrait")
public class Retrait extends Adresse{


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 
	 */
	public Retrait() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param rue
	 * @param codePostal
	 * @param ville
	 */
	public Retrait(String rue, int codePostal, String ville) {
		super(rue, codePostal, ville);
		// TODO Auto-generated constructor stub
	}
}
