package net.jellycopter.culinaire;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public abstract class Recettes {
	static Map<String, Recettes> hs = new HashMap<String, Recettes>();

	String nom ;
	int temps;
	Map<Ingredients , Integer> ingredients = new HashMap<Ingredients, Integer>();
	
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
	public static Recettes[] getAll(){
		return hs.values().toArray(new Recettes[hs.size()]);
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