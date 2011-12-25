package net.jellycopter.culinaire;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.jellycopter.lib.Outils;

public class Frigo {
	private static Frigo instance = null;
	private String app_title = Menu.app_title;
	private Map<Ingredients, Integer> contenu = new HashMap<Ingredients, Integer>();
	
	public static Frigo get(){
		if(instance == null){
			instance = new Frigo();
		}
		return instance;
	}
	
	public void remplir(){
		String t = app_title+" - Remplissage du frigo";
		do{
			String nom = Outils.readString(t, "Ingrédient à ajouter ?");
			Ingredients i = Ingredients.get(nom);
			int qt;
			if(i == null){
				System.err.println("ingredient inconnu");
				Ingredients.creer(nom);
				qt = Outils.readInt(t, new String[]{
						"En quel quantité l'ajouter au frigo ?"} );
			} else {
				int prev = contenu.get(i);
				qt = Outils.readInt(t, new String[]{
						"Il y a actuellement "+prev+" "+nom+"au frigo.",
						"Quel est la nouvelle valeur ?"} );
			}
			if(qt < 0){
				contenu.remove(nom);
				Outils.affiche(t, "La quantité étant négative, "+nom
						+" a été retiré sur frigo");
			}else{
				contenu.put(i, qt);
				Outils.affiche(t, "Le frigo contient maintenant "+qt+" "+nom);
			}
		}while(Outils.readBoolean(app_title,"Continuer à remplir le frigo ?"));
	}
	
	public boolean estPresent(Ingredients i, int qt){
		int present = contenu.get(i);
		return qt <= present;
	}
	public boolean estPresent(Map<Ingredients, Integer> is){
		for(Entry<Ingredients, Integer> i: is.entrySet()){
			int present = contenu.get(i.getKey());
			if(i.getValue() < present){ return false; }
		}
		return true;
	}
}
