package edu.upb.lp.progra.adapterFiles;

import edu.upb.lp.progra.Ajedrez.AjedrezUI;


/**
 * This class allows to select what UI will be used by the Android library.
 * 
 * @author Alexis Marechal
 * @author Alfredo Villalba
 */
public class GameAdapter {
	public static UI selectGame(AndroidGameGUI gui) {
		return new AjedrezUI(gui);
	}	
}
