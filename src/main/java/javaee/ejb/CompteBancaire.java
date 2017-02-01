package javaee.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * 
 * @author Walid-YAICH
 * 
 * Cette classe représente un compte bancaire.
 * 
 * les méthodes versement et retrait utilisent l'attribut solde pour memoriser le solde d'un client.
 * les méthodes versement et retrait utilisent l'attribut nom_prenom pour memoriser le (nom, prenom) du client.
 * L'état d'une instance de cette classe est défini par la valeur du champs solde et la valeur du champs nom_prenom
 * 
 * En Stateless, l'état d'une instance n'est pas mémorisé (pas de relation entre client et instance)
 * En Stateful, l'état d'une instance est mémorisé (chaque client a sa propre instance)
 * En Singleton, une seule instance existe dans le pool d'instance, tout les clients utilisent la meme instance.
 * 
 * 
 * les méthode versementPermanent et retraitPermanent utilisent 
 * la classe Comptes(@singleton) pour stocker le couple (client, solde), 
 * de cette façon les soldes des clients sont maintenu jusqu'a l'arret du serveur d'application.
 * 
 */

//Changer vers Stateless, Stateful puis Singleton
//Dans les 3 cas, lancer un seul client et analyser le resultat
//Dans les 3 cas, Lancer n clients en parallele et analyser le resultat
@Stateless 
public class CompteBancaire implements CompteBancaireRemote{
	
	@EJB //Injection de la classe Comptes dans la classe CompteBancaire
	private Comptes comptes;
	
	//Pour savoir le nombre d'instance que le conteneur EJB a crée
	private static int nb_instance = 0;
	
	//Si chaque client aura sa propose instance de cette classe, il ne perdera pas son solde
	private int solde = 0;
	
	//Le but est que chaque personne qui prend un bean du pool d'instance, marque son nom dessus.
	private String nom_prenom;

	
	@PostConstruct
	void postconstruct(){
		nom_prenom = "instance" + ++nb_instance;
	}
	
	@Override
	public String versement(String nomPrenom, int montant) {
		System.out.println("Le solde de " +nomPrenom + " est : " + solde +  " et il/elle veut verser " + montant);
		solde = solde + montant;
		String response = "Vous etes "+ nomPrenom +", Vous avez utilisé le bean de " + nom_prenom + ", votre nouveau Solde est : " + solde;
		nom_prenom = nomPrenom;
		return response;
	}

	@Override
	public String retrait(String nomPrenom, int montant) {
		System.out.println("Le solde de " +nomPrenom + " est : " + solde +  " et il/elle veut retirer " + montant);
		solde = solde - montant;
		String response = "Vous etes "+ nomPrenom +", Vous avez utilisé le bean de " + nom_prenom + ", votre nouveau Solde est : " + solde;
		nom_prenom = nomPrenom;
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
		String response = "Vous etes "+ nomPrenom +", Vous avez utilisé le bean de " + nom_prenom + ", votre nouveau Solde est : " + soldeClient;
		nom_prenom = nomPrenom;
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
		String response = "Vous etes "+ nomPrenom +", Vous avez utilisé le bean de " + nom_prenom + ", votre nouveau Solde est : " + soldeClient;
		nom_prenom = nomPrenom;
		return response;
	}

}