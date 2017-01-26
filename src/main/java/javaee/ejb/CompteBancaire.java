package javaee.ejb;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.ejb.Stateless;

/**
 * 
 * @author Walid-YAICH
 * Cette classe représente un compte bancaire.
 * 
 * les méthodes versement et retrait utilisent l'attribut solde pour memoriser le solde d'un client.
 * 
 * les méthode versementPermanent et retraitPermanent utilisent 
 * la classe Comptes(@singleton) pour stocker le couple (client, solde), 
 * de cette façon les soldes des clients sont maintenu jusqu'a l'arret du serveur d'application.
 * 
 */
@Stateful
public class CompteBancaire implements CompteBancaireLocal, CompteBancaireRemote{
	
	@EJB //Injection de la classe Comptes dans la classe CompteBancaire
	private Comptes comptes;
	
	//Pour savoir le nombre d'instance que le conteneur EJB a crée
	private static int nb_instance = 0;
	
	private int solde = 0;
	
	//Le but est que chaque personne qui prend un bean du pool d'instance, marque son nom dessus.
	private String lastBeanUser;
	
//	@Resource
//	private SessionContext sessionContext; 
//il faut activer la sécurité pour pouvoir faire un sessionContext.getCallerPrincipal().getName()
	
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

	@Override
	public String versementPermanent(String nomPrenom, int montant) {
		int soldeClient = 0;
		if(comptes.getComptes().get(nomPrenom) != null){
			//Récupérer le solde depuis le singleton
			soldeClient = comptes.getComptes().get(nomPrenom);
		}
		System.out.println("Le solde de " + nomPrenom + " est : " + soldeClient +  " et il/elle veut retirer " + montant);
		soldeClient = soldeClient + montant;
		//Mettre a jour le compte du client dans le singleton
		comptes.getComptes().put(nomPrenom, soldeClient); 
		
		//Préparer la résponse
		String response = "Vous etes "+ nomPrenom +", Vous avez utilisé le bean de " + lastBeanUser + ", votre nouveau Solde est : " + soldeClient;
		lastBeanUser = nomPrenom;
		return response;
	}

	@Override
	public String retraitPermanent(String nomPrenom, int montant) {
		int soldeClient = 0;
		if(comptes.getComptes().get(nomPrenom) != null){
			//Récupérer le solde depuis le singleton
			soldeClient = comptes.getComptes().get(nomPrenom);
		}
		System.out.println("Le solde de " + nomPrenom + " est : " + soldeClient +  " et il/elle veut retirer " + montant);
		soldeClient = soldeClient - montant;
		//Mettre a jour le compte du client dans le singleton
		comptes.getComptes().put(nomPrenom, soldeClient); 
		
		//Préparer la réponse
		String response = "Vous etes "+ nomPrenom +", Vous avez utilisé le bean de " + lastBeanUser + ", votre nouveau Solde est : " + soldeClient;
		lastBeanUser = nomPrenom;
		return response;
	}

}