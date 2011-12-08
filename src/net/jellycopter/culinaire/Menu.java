package net.jellycopter.culinaire;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.jellycopter.lib.Memory;
import net.jellycopter.lib.MemoryException;
import net.jellycopter.lib.Outils;

class LoadFailedException extends Exception{
	private static final long serialVersionUID = 2198494962452362547L;}

public class Menu {
	private String app_title = new String("Culinaire");
	private Set<Recettes> recettes = new HashSet<Recettes>();
	
	public static void main(String[] args){
		new Menu();
	}
	
	public Menu(){
		load();
		Outils.affiche(app_title, "Recettes");
		for(Recettes r: Recettes.getAll()){
			Outils.affiche(app_title, new String[]{
					r.getNom(), r.getTemps()+""	});
		}
		livreRecette();
		Outils.affiche(app_title, "Recettes");
		for(Recettes r: Recettes.getAll()){
			Outils.affiche(app_title, new String[]{
					r.getNom(), r.getTemps()+""	});
		}
		save();
	}
	
	private void frigo(){
		
	}
	private void livreRecette(){
		String t = " - Ajout d'une recette";
		do{
			String nom = Outils.readString(app_title+t, "Nom de la recette ?");
			int temps = Outils.readInt(app_title+t, "Temps de préparation ?");
			Map<Ingredients, Integer> ingredient = 
				new HashMap<Ingredients, Integer>();
			do{ // ajout des ingrédients à la recette
				String n = Outils.readString(app_title+t, 
						"Nom d'un ingredient ?");
				Ingredients i = Ingredients.get(n);
				if(i == null){ // si l'ingrédient est inconnu, création
					int g =	Outils.readOption(app_title+t,
							"Gout de l'ingredient",
							new String[]{"Salé","Sucré","Basique"});
					switch(g){
					case 0:	i = new Sale(n);	break;
					case 1:	i = new Sucre(n);	break;
					case 2:	i = new Basique(n);	break; }
				}
				int q =	Outils.readInt(app_title+t, "Quantité ?");
				ingredient.put(i, q);
			}while(Outils.readBoolean(app_title+t, "Plus d'ingredient ?"));
			int c = Outils.readOption(app_title+t, "Mode de cuisson ?",
									new String[]{"Froid", "Four", 
									"Plaque chauffante", "Micro-ondes"});
			Recettes r = null;
			switch(c){
			case 0:	r = new Froid(nom, temps, ingredient);		break;
			case 1:	r = new Four(nom, temps, ingredient);		break;
			case 2:	r = new plaqueChaufante(nom, temps, ingredient);	break;
			case 3:	r = new MicroOndes(nom, temps, ingredient);	break;
			}
			recettes.add(r);
		}while(Outils.readBoolean(app_title, "Ajouter une recette ?"));
	}
	
	private void load(){
		String[][] ingredients = null;
		String[][] recettes = null;
		Memory.mute();
		try{
			ingredients = Memory.read("ingredient");
			recettes = Memory.read("recette");
		}catch(Exception e){
			try{
				build();
				ingredients = Memory.read("ingredient");
				recettes = Memory.read("recette");
			}catch(MemoryException f){
				System.err.println("Echec lors du chargement des données.");
				System.exit(-1);
			}
		}
		for(String[] packed: ingredients){
			Ingredients.deballer(packed);
		}
		for(String[] packed: recettes){
			Recettes.deballer(packed);
		}
	}
	private void save(){
		for(Recettes r: Recettes.getAll()){
			try{ Memory.write("recette", r.emballer()); }
			catch(Exception e){
				System.err.println("Erreur lors de l'écriture de "+r);
			}
		}
		for(Ingredients i: Ingredients.getAll()){
			try{ Memory.write("ingredient", Ingredients.emballer(i)); }
			catch(Exception e){
				System.err.println("Erreur lors de l'écriture de "+i);
			}
		}
	}
	private void build() throws MemoryException{
		Memory.build("ingredient", new String[] {"nom", "gout"});
		Memory.build("recette", new String[] 
					{"nom", "temps", "ingredient", "cuisson"});
	}
}
