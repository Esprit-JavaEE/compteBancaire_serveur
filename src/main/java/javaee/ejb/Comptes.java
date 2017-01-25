package javaee.ejb;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

@Singleton
public class Comptes {
	
	private Map<String, Integer> comptes = new HashMap<String, Integer>();
	
	
	@PostConstruct
	void postconstruct(){
		System.out.println("L'instance Singleton Comptes est crée !");
	}

	public Map<String, Integer> getComptes() {
		return comptes;
	}

	public void setComptes(Map<String, Integer> comptes) {
		this.comptes = comptes;
	}

}
