package net.jellycopter.culinaire;

import java.util.ArrayList;
import java.util.Map.Entry;

import net.jellycopter.lib.Outils;

public class Quest {
	private int gout;
	private int cuisson;
	private int temps;
	private ArrayList<Recettes> recettes;
	private String[] cuis = new String[]{
			"Froid","Four","plaqueChaufante","MicroOndes"};
	
	public Quest(){
		recettes = new ArrayList<Recettes>(Recettes.getAll());
		demandeGout();
		demandeCuisson();
		demandeTemps();
		filtre();
		// Afficher la liste des recettes
		// Afficher la recette
	}
	private void demandeGout(){
		gout = Outils.readOption(
				Menu.app_title, "Gout de votre repas ?", Menu.gout);
	}
	private void demandeCuisson(){
		cuisson = Outils.readOption(
				Menu.app_title, "Quel mode de cuisson ?", Menu.cuisson);
	}
	private void demandeTemps(){
		temps = Outils.readInt(Menu.app_title,"Combien de temps avais vous ?");
	}
	private void filtre(){
		for(int i = 0; i < recettes.size(); ++i){
			if(cuis[cuisson] != recettes.get(i).getClass()	.toString()
															.split("[.]")[3]){
				recettes.remove(i);
				continue;
			}
			if(gout == 0 && recettes.get(i).getGout() < 0){
				recettes.remove(i);
				continue;
			}
			if(gout == 1 && 0 < recettes.get(i).getGout()){
				recettes.remove(i);
				continue;
			}
			if(temps < recettes.get(i).getTemps()){
				recettes.remove(i);
				continue;
			}
		} 
		Frigo f = Frigo.get();
		goto_: 
		for(int i = 0; i < recettes.size(); ++i){
			for(Entry<Ingredients, Integer> e:
					recettes.get(i).getIngredients().entrySet())
				if(!f.estPresent(e.getKey(), e.getValue()*0.7)){
					recettes.remove(i);
					continue goto_;
				}
		}
		String str = "Recettes correspondant à votre demande";

		String[] choix = new String[recettes.size()+2];
		choix[0] = str;
		for(int i = 1; i < choix.length-1; ++i){
			choix[i] = i+" "+recettes.get(i-1).getNom();
		}
		choix[choix.length-1] = "Quel est votre choix ?";
		
		int affiche = Outils.readInt(Menu.app_title, choix);
		recettes.get(affiche-1).affiche();
	}
} 
