package javaee.ejb;

import javax.ejb.Local;

@Local
public interface CompteBancaireLocal {
	
	String versement(String nomPrenom, int montant);
	
	String retrait(String nomPrenom, int montant);

}