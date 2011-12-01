package net.jellycopter.culinaire;

import java.util.*;

public abstract class Ingredients {

String nom;

HashSet hs = new HashSet();

public Ingredients(String nom) {

super();

this.nom = nom;

}

public String getNom() {

return nom;

}

abstract void gout();

}