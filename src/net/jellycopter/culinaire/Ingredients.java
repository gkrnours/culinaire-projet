package net.jellycopter.culinaire;

import java.util.*;

import net.jellycopter.lib.Outils;

/**
 * @author gkr
 *
 */
/**
 * @author gkr
 *
 */
public abstract class Ingredients {

String nom;

static Map<String, Ingredients> hs = new HashMap<String, Ingredients>();
/**
 * ajoute un ingrédient à la liste de tout les ingrédients.
 * @param i l'ingrédients à ajouter.
 */
static public void ajouter(Ingredients i){
	hs.put(i.getNom(), i);
}
/**
 * cherche si un ingrédients est connu
 * @param nom nom de l'ingrédient a chercher
 * @return Ingredient ou null
 */
static public Ingredients get(String nom){
	return hs.get(nom);
}
/**
 * @return la liste de tout les ingrédients connu
 */
static public Collection<Ingredients> getAll(){
	return hs.values();
}

/**
 * Crée une représentation d'un ingrédient sous la forme d'un tableau de 
 * chaine de caractère
 * @return String[]
 */
public String[] emballer(){
	return new String[]{ getNom(), getClass().toString().split("[.]")[3]};
}

/**
 * transforme un tableau de chaine de caractère correspondant au retour de 
 * emballer en un ingrédient. Le comportement de la fonction n'est pas garantie
 * si le tableau est mal formaté
 * @param s un tableau de chaine de caractère
 * @return Ingredients
 */
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

/**
 * crée un ingrédients a partir de son nom, en demandant à l'utilisateur le gout
 * @param nom nom de l'ingrédient
 * @return Ingredients
 */
public static Ingredients creer(String nom){
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
/**
 * demande à l'utilisateur de créer un ingrédients
 * @return Ingredients
 */
public static Ingredients creer(){
	String nom = Outils.readString(Menu.app_title, "Nom de l'ingrédient");
	return creer(nom);
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