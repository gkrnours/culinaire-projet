package net.jellycopter.culinaire;

import java.util.*;


public abstract class Recettes {

	String nom ;
	int temps;
	Map<Ingredients , Integer> ingredients = new HashMap<Ingredients, Integer>();
	
	
	public Recettes(String nom, int temps, Map<Ingredients, Integer> ingredients) {
		super();
		this.nom = nom;
		this.temps = temps;
		this.ingredients = ingredients;
	}


	public String getNom() {
		return nom;
	}

	public int getTemps() {
		return temps;
	}

	public Map<Ingredients, Integer> getIngredients() {
		return ingredients;
	}




	
}