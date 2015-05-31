package modele;

import java.util.ArrayList;
import java.util.Random;

/**classe representant une fourmi evoluant a la recherche de nourriture a l'aide de pheromones*/
public class Fourmi {
	/**coordonnee x de la fourmi*/
	private int x;
	/**coordonnee y de la fourmi*/
	private int y;
	/**direction  de la fourmi*/
	private Direction direction;
	/**etat de la fourmi*/
	private EtatFourmi etat;	
	/**dose de pheromone que dépose la fourmi*/
	private double  dosePhero;

	/**dose maximal de pheromone que peut deposer une fourmi*/
	public static final double dosePheroMax = 30d;
	/**dose de nourriture que peut porter une fourmi*/
	public static final double doseNourriture = 10d;

	/**lien vers le terrain dans lequel se trouve la  fourmi*/
	private Terrain terrain;
	/**taille du terrain*/
	private int taille;

	/**objet necessaire pour le tirage aleatoire de la prochaine direction*/
	private Random hasard;


	public Fourmi(){}

	/**construit une fourmi
	 * @param _x coordonee x initiale de la fourmi
	 * @param _y coordonee y initiale de la fourmi
	 * @param _terrain terrain ou se trouve la fourmi
	 */
	public Fourmi(int _x, int _y, Terrain _terrain)
	{
		x=_x; 
		y=_y;
		terrain=_terrain;
		taille = terrain.getTaille();
		direction = Direction.randomDirection();
		etat = EtatFourmi.CHERCHER;
		hasard = new Random();
		dosePhero = Fourmi.dosePheroMax;
	}

	/**active les actions de la fourmi selon son etat*/
	public void evoluer()
	{
		Cellule[][] grille = terrain.getGrille();
		switch(etat)
		{
		case CHERCHER: //recherche de nourriture
			Cellule cell= getBestCell(); //s'orienter vers la case offrant le plus de nourriture, sinon le plus de phéromone, sinon au hasard devant
			bougerVersCellule(cell); // avancer si possible
			if(grille[x][y].getNourriture()>0) // si on trouve de la nourriture, on passe a l'etat suivant
				etat = EtatFourmi.PRENDRE;
			break;
		case PRENDRE:
			grille[x][y].oterNourriture(doseNourriture);// la foumi prend une dose de nourriture dans la cellule
			direction = Direction.getInverse(direction);// elle fait demi tour
			bougerVersDirection(); // avance
			etat = EtatFourmi.REVENIR; // et passe a l'etat suivant
			break;
		case REVENIR:
			if(dosePhero>0) grille[x][y].setPheromone(dosePhero--); // la foumi depose une goute de plus en plus petite de pheromone
			cell = getBestCelluleNid(); // elle s'ortiente vers la cellule ayant la plus forte odeur de nid
			bougerVersCellule(cell); // et avance
			if (grille[x][y].isNid())  // si elle est dans le nid, elle passe a l'état suivant
				etat = EtatFourmi.DEPOSER;
			break;
		case DEPOSER:
			dosePhero = Fourmi.dosePheroMax; // la fourmi regenere sa dose de pheromone
			direction = Direction.getInverse(direction); //la fourmi fait demi-tour
			cell= getBestCell(); //s'orienter vers la case offrant le plus de nourriture, sinon le plus de phéromone, sinon au hasard devant
			bougerVersCellule(cell); // avancer si possible
			etat = EtatFourmi.CHERCHER; // elle passe a l'état suivant
			break;
		}
	}


	/**recherche la direction menant vers la case devant possedant le plus de nourriture,
	 * sinon le plus de pheromone, sinon retourne une des trois directions devant la fourmi<br>
	 * pour un comportement "réaliste" la fourmi a un rayon de braquage de 45° <br>
	 * -> elle peut aller soit tout droit, à gauche devant ou à droite devant*/
	private Cellule getBestCell()
	{
		Cellule bestCell = null;
		double bestNourriture = 0d;
		Direction []dirAlentours = Direction.get3Dir(direction);
		ArrayList<Cellule> liste = getNextCellules(dirAlentours);
		if(!liste.isEmpty())
		{
			for(Cellule cell:liste) // recherche de trace de nourriture devant
			{
				double nourriture = cell.getNourriture();
				if(nourriture>bestNourriture) {bestNourriture=nourriture; bestCell=cell;}
			}
			if(bestNourriture==0)
			{
				double bestPhero = 0d;
				for(Cellule cell:liste)   // si pas trouve, recherche de trace de pheromone devant
				{
					double phero = cell.getPheromone();
					if(phero>bestPhero) {bestPhero=phero; bestCell=cell;}
				}
				if(bestPhero==0) // si pas trouve, prendre une direction au hasard devant non occupee
				{
					int i = hasard.nextInt(liste.size());
					bestCell = liste.get(i); 
				}			
			}
		}
		else // si pas possible, faire demi-tour
		{
			direction = Direction.getInverse(direction);
			bestCell = getNextCellule(direction);
		}
		return bestCell;
	}


	/**retourne une liste de directions possibles vers des cases videsdans les directions donnees
	 * @param directions tableaux des drections dans lesquelles il faut tester si les cellules sont vides de fourmis
	 * @return une liste de directions possibles vers des cases vides de fourmis*/
	private ArrayList<Direction> possibleNextDirections(Direction []directions)
	{
		ArrayList<Direction> liste = new ArrayList<Direction>();
		for(Direction dir:directions)
		{
			Cellule cell = getNextCellule(dir);
			if(cell != null && !cell.isFourmis())
				liste.add(dir);
		}
		return liste;		
	}


	/**recherche la direction menant vers la case ayant l'odeur de nid la plus forte, 
	 * sinon retourne une des trois directions devant la fourmi
	 * @return la direction de la case ayant la valeur la plus elevee de odeurNid*/
	private Cellule getBestCelluleNid()
	{
		Cellule bestCell = null;
		double bestNid = 0d;
		Direction []dirAlentours = Direction.get3Dir(direction);
		ArrayList<Cellule> liste = getNextCellules(dirAlentours);
		if(!liste.isEmpty())
		{
			for(Cellule cell:liste)  // recherche de trace d'odeur de nid devant
			{
				double odeurNid = cell.getOdeurNid();
				if(odeurNid>bestNid) {bestNid=odeurNid; bestCell=cell;}
			}
			if(bestNid==0) // si pas trouve, prendre une direction au hasard devant non occupee
			{
				int i = hasard.nextInt(liste.size());
				bestCell = liste.get(i); 
			}
		}
		else // si pas possible, faire demi-tour
		{
			direction = Direction.getInverse(direction);
			bestCell = getNextCellule(direction);
		}
		return bestCell;
	}


	/**fait avancer la fourmi dans sa direction si la case devant existe et est non occupee*/
	private void bougerVersCellule(Cellule cell)
	{
		if(cell!=null && !cell.isFourmis()) 
		{
			Cellule[][]grille = terrain.getGrille();
			grille[x][y].setFourmis(false);
			x = cell.getX();
			y = cell.getY();
			cell.setFourmis(true);
		}
	}

	/**fait avancer la fourmi dans sa direction si la case devant existe et est non occupee*/
	private void bougerVersDirection()
	{
		Cellule cell = getNextCellule(direction);
		if(cell!=null && !cell.isFourmis()) 
		{
			Cellule[][] grille = terrain.getGrille();
			grille[x][y].setFourmis(false);
			x = cell.getX();
			y = cell.getY();
			cell.setFourmis(true);
		}
	}


	/**retourne le degre de pheromone dans la case voisine situe dans la direction dir
	 * @param dir direction vers laquelle il faut effectuer le test
	 * @return le degre de pheromone dans la case voisine situee dans la direcion dir*/
	private double getPheroProchaineCase(Direction dir)
	{
		double phero = -1;
		Cellule cell = getNextCellule(dir);
		if(cell!=null) 
			if(!cell.isFourmis())phero = cell.getPheromone();
		return phero;		
	}

	/**retourne le degre de nourriture dans la case voisine situe dans la direction dir
	 * @param dir direction vers laquelle il faut effectuer le test
	 * @return le degre de nourriture dans la case voisine situee dans la direcion dir*/
	private double getNourritureProchaineCase(Direction dir)
	{
		double nourriture = -1;
		Cellule cell = getNextCellule(dir);
		if(cell!=null) 
			if(!cell.isFourmis())nourriture = cell.getNourriture();
		return nourriture;		
	}

	/**retourne le degre de nourriture dans la case voisine situe dans la direction dir
	 * 	@param dir direction vers laquelle il faut effectuer le test
	 * @return le degre d'odeur de nid dans la case voisine situee dans la direcion dir*/
	private double getOdeurNidProchaineCase(Direction dir)
	{
		double odeurNid = -1;
		Cellule cell = getNextCellule(dir);
		if(cell!=null) 
			if(!cell.isFourmis())odeurNid = cell.getOdeurNid();
		return odeurNid;		
	}


	/*orienter la fourmi vers le nid, <br>
	 * calcule le vecteur normal de la fourmi vers le nid<br>
	 * puis demande le calcul de la direction correspondant a ce vecteur.
	 * non utilisee dans cette version du code
	 * 
	private void 	orienterVersNid()
	{
		int xNid = terrain.getxNid();
		int yNid = terrain.getyNid();
		int distNormaleX = (x==xNid?0:(xNid - x)/Math.abs((xNid - x)));
		int distNormaleY = (y==yNid?0:(yNid - y)/Math.abs((yNid - y)));
		direction = Direction.getDirectionFromVector(distNormaleX, distNormaleY);
	}
	 */

	/**donne la prochaine case dans la direction donnée
	 * @param dir la direction
	 * @return la cellule voisine dans la direction donnée, null si aucune cellule*/
	private Cellule getNextCellule(Direction dir)
	{
		Cellule cell = null;
		int xx = x + dir.getX();
		int yy = y + dir.getY();
		if ((xx>=0 && xx < taille) && (yy>=0 && yy<taille))
		{
			Cellule[][] grille = terrain.getGrille();
			cell = grille[xx][yy];
		}
		return cell;
	}

	/**donne les prochaines cases dans les direction données
	 * @param dir les directions
	 * @return les cellules voisines dans les direction données*/
	private ArrayList<Cellule> getNextCellules(Direction[] directions)
	{
		ArrayList<Cellule> liste= new  ArrayList<Cellule>();
		for(Direction dir:directions)
		{
			Cellule cell = getNextCellule(dir);
			if(cell!=null && !cell.isFourmis()) 
				liste.add(cell); 
		}
		return liste;
	}



}
