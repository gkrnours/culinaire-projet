package net.jellycopter.culinaire;

import java.util.*;

import net.jellycopter.lib.Outils;

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

public String[] emballer(){
	return new String[]{ getNom(), getClass().toString().split("[.]")[3]};
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

public static Ingredients creer(){
	String nom = Outils.readString(Menu.app_title, "Nom de l'ingrédient");
	int g = Outils.readOption(Menu.app_title, 
			"Gout de l'ingrédient", Menu.gout);
	Ingredients i = null;
	
	switch(g){
	case 0: i = new Sale(nom);	break;
	case 1: i = new Sucre(nom);	break;
	default: i = new Basique(nom);
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

public String toString(){ 
	return this.getClass().toString().split("[.]")[3]+": "+nom; 
}
		
}