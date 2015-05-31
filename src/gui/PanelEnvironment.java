package gui;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import modele.Fourmi;
import modele.Cellule;
import modele.Terrain;

/**
 * represente la fenetre affichant la grille des cellules
 * @author  Emmanuel Adam
 */
@SuppressWarnings("serial")
public class PanelEnvironment extends JFrame implements ActionListener
{

	/**timer qui donne le tempo de la vie....*/
	private Timer timer;
	/**terrain liee a cet objet graphique*/
	private Terrain terrain;
	/**taille de la terrain*/
	private int taille;
	/**taille d'une cellule en pixel*/
	private int espace = 10;
	/** zone de dessin*/
	private JPanel jpanel;
	/**couleur du terrain*/
	private final Color colTerrain;

	/** constructeur par defaut, définit la couleur du terrain et lance l'initialisation*/
	public PanelEnvironment()
	{
		colTerrain = new Color(0, 80, 0); // vert foncé
		init();
	}
	
	/** constructeur par defaut, définit la couleur du terrain, relie ce panneau graphique au modèle, lance l'initialisation, ainsi que le TImer*/
	public PanelEnvironment(Terrain _matrice, int tempo)
	{
		terrain = _matrice;
		taille = terrain.getTaille();
		colTerrain = new Color(0, 80, 0); // vert foncé
		init();
		timer = new Timer(tempo, this);
		timer.start();
	}

	/** 
	 *initialise la fenetre : taille, zone de dessin, gestionnaire de fenetre...
	 */
	void init()
	{
		setBounds(10,10,3*espace + taille*espace, 5*espace + taille*espace);
		jpanel = new JPanel();
		jpanel.setBackground(Color.gray);
		setBackground(Color.black);
		getContentPane().add(jpanel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}



	/** 
	 *dessin de la grille et des cellules
	 */
	void dessinMatrice(Graphics2D g2d)
	{
		Cellule[][] grille = terrain.getGrille();
		for(int i=0; i<taille; i++)
			for(int j=0; j<taille; j++)
			{
				Cellule cell = grille[i][j]; 
				if (cell.getNourriture()>0) // affichage des zones de nourritures 
				{
					double ratio = cell.getNourriture() / 50d; // pourcentage de pheromone par rapport au max possible (50)
					int b = (int)(ratio * 255); 
					g2d.setColor(new Color(0,0,b)); // la case est +- bleue en fonction de la densité de nourriture
					g2d.fillRect((i+1)*espace, (j+1)*espace, espace, espace);
				}
				else
					if (cell.isNid()) // affichage de la zone du nid
					{
						g2d.setColor(Color.yellow);
						g2d.fillOval((i+1)*espace, (j+1)*espace, espace, espace);
					}
				else 
				{
					g2d.setColor(colTerrain);
					g2d.fillRect((i+1)*espace, (j+1)*espace, espace, espace);
				}

				if (cell.getPheromone()>0) // s'il y a une trace de phéromone
				{
					double ratio = (cell.getPheromone() / Fourmi.dosePheroMax ); // pourcentage de pheromone par rapport au max
					int g = (int)(ratio* 255*10) + 70; // au plus bas, la zone est grise (color(70,70,70)
					if(g>255) g=255; // ne pas depasser 255
					Color c = new Color(g,g,g);
					float transparence = (float)ratio + 0.2f; if (transparence>1f) transparence = 1f; // calcul du degré de transparence
					AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)ratio); 
					g2d.setComposite(alpha);//demande de transparence
					g2d.setColor(c);
					g2d.fillRect((i+1)*espace, (j+1)*espace, espace, espace);
					alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f); //retablissement à un affichage opaque
					g2d.setComposite(alpha);
				}

				if (cell.isFourmis()) // s'il y a une foumi dans la cellule
				{
					g2d.setColor(Color.red);
					g2d.fillOval((i+1)*espace+1, (j+1)*espace+1, espace-2, espace-2);
				}
			}
	}



	/** 
	 * appel du dessin de dessinGrille, 
	 */
	public void paint(Graphics g)
	{
		Graphics gg = jpanel.getGraphics();
		Graphics2D g2 = (Graphics2D)gg;
		// pour un 'rendu' de qualite
		RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHints(qualityHints);
		// fin pour un 'rendu' de qualite

		dessinMatrice(g2);
	}

	/** 
	 * a chaque top du timer, appel de animGrille, et demande de reaffichage
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer) {
			terrain.animGrille();
			repaint();
		}
	}
	
	public Timer getTimer(){
		return timer;
	}
}
