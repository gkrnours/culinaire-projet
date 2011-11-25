package net.jellycopter.culinaire;

import net.jellycopter.lib.Memory;
import net.jellycopter.lib.MemoryException;

class LoadFailedException extends Exception{
	private static final long serialVersionUID = 2198494962452362547L;}

public class Menu {
	public static void main(String[] args){
		new Menu();
	}
	
	public Menu(){
		try{
			Memory.write("ingredient", new String[] {"sel"});
			System.out.println("ok");
		}catch(MemoryException e){
			try{
				Memory.build("ingredient", new String[] {"name"});
				Memory.write("ingredient", new String[] {"sel"});
				System.out.println("fine");
			}catch(MemoryException f){
				System.err.println("Echec lors du chargement des données.");
				System.exit(-1);
			}
		}
	}
	
	private void frigo(){
		private Map<Ingredient, Integer> ingredients
					= new HashMap<Ingredient, Integer>();
	}
	
	
	private void load() throws LoadFailedException{
		try{
			Memory.read("Ingredient");
			System.out.print("ok");
		}catch(Exception e){ throw new LoadFailedException(); }
	}
}
