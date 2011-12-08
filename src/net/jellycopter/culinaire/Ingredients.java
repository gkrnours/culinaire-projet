package net.jellycopter.culinaire;

import java.util.*;

public abstract class Ingredients {

String nom;

static Map<String, Ingredients> hs = new HashMap<String, Ingredients>();
static public void ajouter(Ingredients i){
	hs.put(i.getNom(), i);
}
static public Ingredients get(String nom){
	return hs.get(nom);
}
static public Ingredients[] getAll(){
	return hs.values().toArray(new Ingredients[hs.size()]);
}

public static String[] emballer(Ingredients i){
	return new String[]{ i.getNom(), i.getClass().toString() };
}
public static Ingredients deballer(String[] s){
	Ingredients i;
	if(s[1].equals("Sucre")){
		i = new Sucre(s[0]);
	}else if(s[1].equals("Sale")){
		i = new Sale(s[0]);
	}else{
		i = new Basique(s[0]);
	}
	return i;
}

public Ingredients(String nom) {

super();

this.nom = nom;

hs.put(nom, this);
}

public String getNom() {

return nom;

}

abstract void gout();

}