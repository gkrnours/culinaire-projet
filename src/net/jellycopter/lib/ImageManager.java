package net.jellycopter.lib;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manage imaging serving and caching
 * @author ludovic
 *
 */
public class ImageManager {
	protected static Map<String, Image> map= new HashMap<String, Image>(10);
	
	/**
	 * Check a given filename resolve to a cached image. If not, fetch the it or
	 * rise an exception if there is nothing at the given path.
	 * @param filename path to an image, relative to the working directory
	 * @return an java.awt.Image, from map if cached, or a newly created.
	 * @throws FileNotFoundException
	 */
	public static Image getImage(String filename) throws FileNotFoundException{
		if(map.containsKey(filename)){ return map.get(filename); }
		Image img;
		
		File f = new File (filename);
		if(!f.exists()){
				throw new FileNotFoundException(
						"le fichier "+filename+" n'existe pas dans le répertoire "
								+System.getProperty("user.dir"));
		}
		
		img=java.awt.Toolkit.getDefaultToolkit().createImage(filename);
		map.put(filename, img);
		return img;
	}

}
