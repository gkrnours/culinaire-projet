package net.jellycopter.culinaire;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public abstract class Recettes {
	static Map<String, Recettes> hs = new HashMap<String, Recettes>();

	String nom ;
	int temps;
	Map<Ingredients , Integer> ingredients = new HashMap<Ingredients, Integer>();
	
	/**
	 * crée une représentation d'une recette sous forme d'un tableau de chaine 
	 * de caractère
	 * @return String[]
	 */
	public String[] emballer(){
		String[] e = new String[4];
		e[0] = nom;
		e[1] = temps+"";
		StringBuilder i = new StringBuilder();
		for(Map.Entry<Ingredients, Integer> elem: ingredients.entrySet() ){
			i
			.append(elem.getKey().getNom()).append(":")
			.append(elem.getKey().getClass().toString().split("[.]")[3])
			.append(":")
			.append(elem.getValue()).append(";");
		}
		e[2] = i.toString();
		e[3] = getClass().toString().split("[.]")[3];
		// la classe est le 4ème champ lors du split
		return e;
	}
	/**
	 * Crée une recette a partir d'un tableau de chaine de caractère formaté 
	 * comme le retour d'emballer. Le comportement de la fonction n'est pas 
	 * garantie si le tableau est mal formaté.
	 * @param e tableau de chaine de caractère
	 * @return Recettes
	 */
	public static Recettes deballer(String[] e){
		Map<Ingredients, Integer> ing = new HashMap<Ingredients, Integer>();
		for(String s: e[2].split(";")){
			String[] raw_ing = s.split(":");
			ing.put(Ingredients.deballer(raw_ing),
						Integer.parseInt(raw_ing[2]));
		}
		Recettes r;
		if(e[3].equals("plaqueChaufante")){
			r = new plaqueChaufante(e[0], Integer.parseInt(e[1]), ing);
		}else if(e[3].equals("MicroOndes")){
			r = new MicroOndes(e[0], Integer.parseInt(e[1]), ing);
		}else if(e[3].equals("Four")){
			r = new Four(e[0], Integer.parseInt(e[1]), ing);
		}else{
			r = new Froid(e[0], Integer.parseInt(e[1]), ing);
		}
		return r;
	}
	/**
	 * @return la collection de toutes recettes connues
	 */
	public static Collection<Recettes> getAll(){
		return hs.values();
	}
	
	public Recettes(String nom, int temps, Map<Ingredients, Integer> ingredients) {
		super();
		this.nom = nom;
		this.temps = temps;
		this.ingredients = ingredients;
		hs.put(nom, this);
	}


	public String getNom() {
		return nom;
	}

	public int getTemps() {
		return temps;
	}
//TODO 	memory: primary key
	public Map<Ingredients, Integer> getIngredients() {
		return ingredients;
	}

	public String toString(){
		String r = this.nom+"\n"
				+this.getClass().toString().split("[.]")[3]
				+": "+temps+" minutes\n"
				+"Ingredients :";
		for(Entry<Ingredients, Integer> e: ingredients.entrySet()){
			r += "\n"+e.getKey()+": "+e.getValue();
		}
		return r;
	}


	
}