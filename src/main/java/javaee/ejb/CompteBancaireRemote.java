package javaee.ejb;

import javax.ejb.Remote;

/**
 * 
 * @author Walid-YAICH
 * 
 * Cette classe contient les signatures des méthodes qui seront exposés au clients java
 * ==> Ces méthodes seront accessibles en dehors du serveur d'application
 */
@Remote
public interface CompteBancaireRemote {
		
	String versement(String nomPrenom, int montant);
	
	String retrait(String nomPrenom, int montant);
	
	String versementPermanent(String nomPrenom, int montant);

	String retraitPermanent(String nomPrenom, int montant);

}
