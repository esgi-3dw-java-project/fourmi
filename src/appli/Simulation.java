package appli;

import gui.Gui;
import modele.Terrain;
import gui.PanelEnvironment;
import javax.swing.Timer;

public class Simulation {
	
	private PanelEnvironment panelEnv;
	
	public Simulation(){
		int tailleTerrain = 70;
		int nbFourmis = 30;
		int tauxDeRaffraichissementEnMilliseconde = 16;
		Terrain terrain = new Terrain(tailleTerrain, nbFourmis);
		panelEnv = new PanelEnvironment(terrain, tauxDeRaffraichissementEnMilliseconde);
	}
	
	public Timer getTimer(){
		return panelEnv.getTimer();
	}
}
