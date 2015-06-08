package appli;
/**
 * LesFourmis.java
 * @author Emmanuel Adam 
 */

import modele.Terrain;
import gui.PanelEnvironment;
import gui.Gui;

/**
 * classe principale, ne contient que le main
 */
public class LesFourmis 
{
	/** cree un objet PanelEnvironment */
	public static void main(String args[])
	{
		Gui g = new Gui();
		g.launch();
		
		//int tailleTerrain = 70;
		//int nbFourmis = 30;
		//int tauxDeRaffraichissementEnMilliseconde = 16;
		//Terrain terrain = new Terrain(tailleTerrain, nbFourmis);
		//new PanelEnvironment(terrain, tauxDeRaffraichissementEnMilliseconde);

	}


}
