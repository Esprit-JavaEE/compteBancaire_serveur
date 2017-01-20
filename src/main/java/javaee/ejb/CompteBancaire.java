package javaee.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Stateless;

@Singleton
public class CompteBancaire implements CompteBancaireLocal, CompteBancaireRemote{
	
	private int solde = 0;
	
	private String lastBeanUser;
	
	@PostConstruct
	void postconstruct(){
		lastBeanUser = "nobody";		
	}
	
	@Override
	public String versement(String nomPrenom, int montant) {
		solde = solde + montant;
		System.out.println("Le solde de " +nomPrenom + " est : " + solde +  " et il/elle veut verser " + montant);
		String response = "Vous etes "+ nomPrenom +", Vous avez utilisé le bean de " + lastBeanUser + ", votre nouveau Solde est : " + solde;
		lastBeanUser = nomPrenom;
		return response;
	}

	@Override
	public String retrait(String nomPrenom, int montant) {
		lastBeanUser = nomPrenom;
		System.out.println("Le solde de " +nomPrenom + " est : " + solde +  " et il/elle veut retirer " + montant);
		solde = solde - montant;
		return "Vous etes "+ nomPrenom + ", Vous avez utilisé le bean de " + lastBeanUser + ", votre nouveau Solde est : " + solde;
	}

}