package appli;

import gui.Gui;
import modele.Terrain;
import gui.PanelEnvironment;

public class Simulation {
	
	public Simulation(){
		int tailleTerrain = 70;
		int nbFourmis = 30;
		int tauxDeRaffraichissementEnMilliseconde = 16;
		Terrain terrain = new Terrain(tailleTerrain, nbFourmis);
		new PanelEnvironment(terrain, tauxDeRaffraichissementEnMilliseconde);
	}
}
