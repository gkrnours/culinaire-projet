package net.jellycopter.lib;


import java.awt.Panel;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;



/**
 * <h1>Outils permettant de demander facilement des informations à l'utilisateur.</h1>
 * <br>cette classe propose des méthodes statiques permettant de demander des informations sans avoir accès à un objet plateau.</br>
 * @author ludovic
 *
 */
public class Outils{
	
	/**
	 * Essai de récupérer une image à partir de l'entrée
	 */
	public static ImageIcon getIcon(String image){
		ImageIcon icon;
		try{ 
			icon = new ImageIcon(ImageManager.getImage(image));
			return icon;
		} catch(FileNotFoundException e) {
			// En ne provoquant pas d'exeption, il est possible d'utiliser la fonction pour tester la présence d'une image.
			return null;
		} catch(NullPointerException e) {
			return null;
		}
	}
	
	/**
	 * Construit un Panel java, représentant un morceau d'interface graphique
	 * mélant textes et images sur une même ligne.
	 * @param part Un tableau de chaine de caractère. Toutes chaine qui peut se
	 * résoudre en une image sera remplacer.
	 * @return un objet Panel
	 */
	public static Panel texteMix(String[] part){
		Panel r = new Panel();
		ImageIcon img;
		JLabel tmpLabel;

		for(String s: part){
			img = Outils.getIcon(s);
			tmpLabel = (img == null)? new JLabel(s)
									: new JLabel(img);
			r.add(tmpLabel);
		}
		r.validate();
		return r;
	}

	/**
	 * Informe l'utilisateur
	 * @param title Titre de la fenêtre.
	 * @param message Message pour l'utilisateur.
	 * @see JOptionPane#message
	 */
	public static void affiche(String title, Object message){
		affiche(title, message, "");
	}
	/**
	 * Informe l'utilisateur
	 * @param title Titre de la fenêtre.
	 * @param message Message pour l'utilisateur.
	 * @param image chemin de l'image à afficher à coté du message
	 * @see JOptionPane#message
	 */
	public static void affiche(String title , Object message, String image) {
		ImageIcon icon = getIcon(image);
		JOptionPane.showConfirmDialog(null, message, title,
								JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
	}

	/**
	 * Pose une question avec oui ou non comme réponse à l'utilisateur.
	 * @param title Titre de la fenêtre posant la question
	 * @param question Question à poser à l'utilisateur. Peut être un String, ou une objet plus complexe. 
	 * @see JOptionPane#message
	 */
	public static boolean readBoolean(String title, Object question){
		return readBoolean(title, question, "");
	}
	/**
	 * Pose une question avec oui ou non comme réponse à l'utilisateur.
	 * @param title Titre de la fenêtre posant la question
	 * @param question Question à poser à l'utilisateur. Peut être un String, ou une objet plus complexe. 
	 * @param image chemin de l'image à afficher à coté de la question
	 * @see JOptionPane#message
	 */
	public static boolean readBoolean(String title , Object question, String image) {
		ImageIcon icon = getIcon(image);
		int reponse = JOptionPane.showConfirmDialog(null, question, title,
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
		return reponse == 0;
	}

	/**
	 * Demande d'un nombre entier à l'utilisateur.
	 * @param title Titre de la fenêtre posant la question
	 * @param question Question à poser à l'utilisateur. Peut être un String, ou une objet plus complexe. 
	 * @return Retourne un entier valide, pouvant être nul ou négatif
	 * @see JOptionPane#message
	 */
	public static int readInt(String title, Object question){
		return readInt(title, question, "");
	}
	/**
	 * Demande d'un nombre entier à l'utilisateur, avec une icône non-standard.
	 * @param title Titre de la fenêtre posant la question
	 * @param question Question à poser à l'utilisateur.
	 * @param image chemin de l'image à afficher à coté de la question
	 * @return Retourne un entier, pouvant être nul ou négatif, ou null
	 * @see JOptionPane#message
	 */
	public static Integer readInt(String title , Object question, String image) {
		int result ;
		Object request;
		request = JOptionPane.showInputDialog(null, question, title,
					JOptionPane.QUESTION_MESSAGE, getIcon(image), null, null);
		try{
			result = Integer.parseInt(request.toString());
		}catch (java.lang.NumberFormatException e){
			return readInt(title, question+"\n entrez un entier", image);
		}catch (java.lang.NullPointerException e){
			return null;
		}
		return result;
	}
	
	/**
	 * Demande un nombre réel à l'utilisateur
	 * @param title Titre de la fenêtre posant la question
	 * @param question Question à poser à l'utilisateur.
	 * @return Retourne un double valide, potentiellement nul ou négatif
	 * @see JOptionPane#message
	 */
	public static Double readDouble(String title, Object question){
		return readDouble(title, question, "");
	}
	/**
	 * Demande un nombre réel à l'utilisateur
	 * @param title Titre de la fenêtre posant la question
	 * @param question Question à poser à l'utilisateur.
	 * @param img Chemin vers une image. Sera affiché à coté de question
	 * @return Retourne un double valide, potentiellement nul ou négatif
	 * @see JOptionPane#message
	 */
	public static Double readDouble(String title, Object question, String img){
		double result;
		Object request;
		request = JOptionPane.showInputDialog(null,  question, title,
					JOptionPane.QUESTION_MESSAGE, getIcon(img), null, null);
		try{
			result = Double.parseDouble(request.toString());
		}catch(java.lang.NumberFormatException e){
			return readDouble(title, question+"\n entrez un entier", img);
		}
		return result;
	}

	/**
	 * Demande d'une chaine de caractère à l'utilisateur.
	 * @param title Titre de la fenêtre posant la question
	 * @param question Question à poser à l'utilisateur. Peut être un String, ou une objet plus complexe. 
	 * @return Renvoie une chaine de caractère, potentiellement vide
	 * @see JOptionPane#message
	 */
	public static String readString(String title, Object question){
		return readString(title, question, "");
	}
	/**
	 * Demande d'une chaine de caractère à l'utilisateur, avec une icône non-standard.
	 * @param title Titre de la fenêtre posant la question
	 * @param question Question à poser à l'utilisateur. Peut être un String, ou une objet plus complexe. 
	 * @param image chemin de l'image à afficher à coté de la question
	 * @return Renvoie une chaine de caractère, potentiellement vide
	 * @see JOptionPane#message
	 */
	public static String readString(String title , Object question, String image) {
		ImageIcon icon = getIcon(image);
		Object request = JOptionPane.showInputDialog(null, question, title,
								JOptionPane.QUESTION_MESSAGE, icon, null, null);
		return (request != null)? request.toString(): null;
	}

	/**
	 * Propose au joueur un choix entre plusieurs options
	 * @param title Titre de la fenêtre posant la question
	 * @param question Question à poser à l'utilisateur. Peut être un String, ou une objet plus complexe. 
	 * @param choix Liste des choix possible. Chaque choix pouvant être résolu comme une image afficheras une icone à la place d'une chaine de caractère.
	 * @return Renvoie un entier, correspondant à l'index de la réponse dans la liste des choix.
	 * @see JOptionPane#message
	 */
	public static int readOption(String title, Object question, String[] choix){
		return readOption(title, question, choix, null);
	}
	/**
	 * Propose au joueur un choix entre plusieurs options
	 * @param title Titre de la fenêtre posant la question
	 * @param question Question à poser à l'utilisateur. Peut être un String, ou une objet plus complexe. 
	 * @param choix Liste des choix possible. Chaque choix pouvant être résolu comme une image afficheras une icone à la place d'une chaine de caractère.
	 * @param image chemin de l'image à afficher à coté de la question
	 * @return Renvoie un entier, correspondant à l'index de la réponse dans la liste des choix.
	 * @see JOptionPane#message
	 */
	public static int readOption(String title, Object question, String[] choix, String image){
		ImageIcon icon = getIcon(image);
		ImageIcon tmpIcon;
		int nbeOption = choix.length;
		Object[] options = new Object[nbeOption];
		for(int i = 0; i < nbeOption; ++i){
			// si choix[i] n'est pas le chemin d'une image, geticon renvoie null, on utilise donc le String tel quel.
			tmpIcon = getIcon(choix[i]);
			options[i] = (tmpIcon == null) ? choix[i] : tmpIcon;
		}
		int reponse = JOptionPane.showOptionDialog(null, question, title,
												JOptionPane.DEFAULT_OPTION,
												JOptionPane.QUESTION_MESSAGE,
												icon, options, null);
		return reponse;

	}
	

}




