package javaee.ejb;

import javax.ejb.Stateful;

@Stateful
public class CompteBancaire implements CompteBancaireLocal, CompteBancaireRemote{

	private int solde = 0;

	@Override
	public String versement(String nomPrenom, int montant) {
		System.out.println("Le solde de " +nomPrenom + " est : " + solde +  " et il/elle veut verser " + montant);
		solde = solde + montant;
		return "Vous etes" + nomPrenom + ", votre nouveau Solde est : " + solde;
	}

	@Override
	public String retrait(String nomPrenom, int montant) {
		System.out.println("Le solde de " +nomPrenom + " est : " + solde +  " et il/elle veut retirer " + montant);
		solde = solde - montant;
		return "Vous etes" + nomPrenom + ", votre nouveau Solde est : " + solde;
	}
	

}