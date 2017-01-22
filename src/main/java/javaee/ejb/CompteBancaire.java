package javaee.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.ejb.Stateless;

@Stateless
public class CompteBancaire implements CompteBancaireLocal, CompteBancaireRemote{
	
	private static int nb_instance = 0;
	
	private int solde = 0;
	
	private String lastBeanUser;
	
	@PostConstruct
	void postconstruct(){
		lastBeanUser = "instance" + ++nb_instance;
	}
	
	@Override
	public String versement(String nomPrenom, int montant) {
		System.out.println("Le solde de " +nomPrenom + " est : " + solde +  " et il/elle veut verser " + montant);
		solde = solde + montant;
		String response = "Vous etes "+ nomPrenom +", Vous avez utilisé le bean de " + lastBeanUser + ", votre nouveau Solde est : " + solde;
		lastBeanUser = nomPrenom;
		return response;
	}

	@Override
	public String retrait(String nomPrenom, int montant) {
		System.out.println("Le solde de " +nomPrenom + " est : " + solde +  " et il/elle veut retirer " + montant);
		solde = solde - montant;
		String response = "Vous etes "+ nomPrenom +", Vous avez utilisé le bean de " + lastBeanUser + ", votre nouveau Solde est : " + solde;
		lastBeanUser = nomPrenom;
		return response;
	}

}