package javaee.ejb;

import javax.ejb.Remote;

@Remote
public interface CompteBancaireRemote {
		
	String versement(String nomPrenom, int montant);
	
	String retrait(String nomPrenom, int montant);

}
