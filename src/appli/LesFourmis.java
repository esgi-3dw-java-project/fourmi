package appli;
/**
 * LesFourmis.java
 * @author Emmanuel Adam 
 */

import modele.Terrain;
import gui.PanelEnvironment;


/**
 * classe principale, ne contient que le main
 * @author  Emmanuel Adam
 */
public class LesFourmis 
{
	/** cree un objet PanelEnvironment */
	public static void main(String args[])
	{
		int tailleTerrain = 70;
		int nbFourmis = 30;
		int tauxDeRaffraichissementEnMilliseconde = 30;
		Terrain terrain = new Terrain(tailleTerrain, nbFourmis);
		new PanelEnvironment(terrain, tauxDeRaffraichissementEnMilliseconde);

	}


}
