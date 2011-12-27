package net.jellycopter.culinaire;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.jellycopter.lib.Memory;
import net.jellycopter.lib.MemoryException;
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
				int prev = 0;
				if(contenu.get(i) != null){
					prev = contenu.get(i);
				}
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
	
	public void affiche(){
		if(contenu.isEmpty()){
			Outils.affiche(Menu.app_title, "Le frigo est vide !");
			return;
		}
		String[] aff = new String[contenu.size()+1]; 
		int i = 0;
		aff[0] = "Le frigo contient :";
		for(Entry<Ingredients, Integer> ing: contenu.entrySet()){
			aff[++i] = ing.getValue()+" "+ing.getKey().toString();
		}
		Outils.affiche(Menu.app_title, aff);
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
	
	public void load(){
		String[][] ingredients = null;
		try{	ingredients = Memory.read("frigo"); }
		catch(Exception e){
			try{
				build();
				ingredients = Memory.read("frigo");
			}catch(Exception f){
				System.err.println("Erreur lors du chargement du frigo");
				System.exit(-1);
			}
		}
		for(String[] packed: ingredients){
			contenu.put(Ingredients.deballer(packed),
							Integer.parseInt(packed[packed.length-1]));
		}
	}
	public void save(){
		for(Entry<Ingredients, Integer> ing: contenu.entrySet()){
			try{ 
				String[] toSave = new String[ing.getKey().emballer().length+1];
				System.arraycopy(ing.getKey().emballer(), 0,
						toSave, 0, ing.getKey().emballer().length);
				toSave[toSave.length-1] = ing.getValue()+"";
				Memory.write("frigo", toSave);
				Outils.affiche(Menu.app_title, "frigo");
				Outils.affiche(Menu.app_title, toSave);
			}
			catch(Exception e){
				System.err.println("Erreur lors de l'écriture: "+e);
			}
		}
	}
	public void build() throws MemoryException{
		Memory.build("frigo", new String[] {"nom", "gout", "quantite"});
	}
}
