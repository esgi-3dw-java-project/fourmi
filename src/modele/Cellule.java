package modele;
/**
 * cette classe represente une cellule de la simulation de fourmis
 * @author  Emmanuel Adam
 */
public class Cellule  
{
	/**coordonnee de la cellule dans la grille*/ 
	private int x,y;
	/** degre d'evaporation de la pheromonoe (% de perte par tour)*/
	static double evaporation  = 0.1;
	/** degre de diffusion de la pheromone (% de distribution aux voisines par tour)*/
	static final double diffusion = 0.009;
	/** seuil a partir duquel la pheromone est consideree comme nulle*/
	static final double pheroNulle = 0.01;
	/**pheromones*/
	private double pheromone;
	/**nourriture*/
	private double nourriture;
	/**cellule d'un nid*/
	private boolean nid;
	/**presence d'au moins une fourmis*/
	private boolean fourmis;
	/**odeur du nid*/
	private double odeurNid;

	/** reference a la grille des cellule*/
	Cellule [][]grille;

	/**a change recemment*/
	boolean hasJustChanged;

	/** constructeur par defaut, inutilise*/
	public Cellule(){}

	/** constructeur initialisant la grille, les coordonnees et la nature de la cellule*/
	public Cellule(Cellule [][] grille, int x, int y)
	{
		this.grille = grille;
		this.x = x; this.y = y;
		hasJustChanged = true;
		nid = false;
	}

	/** determine le prochain etat de la cellule en fonction des cellules voisines*/
	void evoluer()
	{
		if(!nid)
		{
			diffuser();
			evaporer();
		}
	}

	/** determine le prochain etat de la cellule en fonction des cellules voisines*/
	void diffuser()
	{
		if(pheromone!=0)
		{
			int taille = grille.length;
			for(int i=-1; i<2; i++)
			{
				int xx = x+i;
				for(int j=-1; j<2; j++)
				{
					if (i==0 && j==0) continue;
					int yy = y+j;
					if(xx<0 || xx>taille-1) continue;
					if(yy<0 || yy>taille-1) continue;
					Cellule cell = grille[xx][yy];
					double phero = cell.getPheromone();
					cell.setPheromone(phero + pheromone * Cellule.diffusion);
				}
			}
		}
	}

	/** determine le prochain etat de la cellule en fonction des cellules voisines*/
	void evaporer()
	{
		if (pheromone!=0) 
		{
			pheromone = pheromone * (1d - Cellule.evaporation);
			if (pheromone<=Cellule.pheroNulle) {pheromone = 0; 	hasJustChanged = true;}
		}
	}

	/**diminuer la dose de nourriture dans la cellule
	 * @param dose de nourriture a oter de la cellule*/
	void oterNourriture(double doseNourriture)
	{
		nourriture -= doseNourriture;
		if(nourriture<0)nourriture=0;
	}


	/**
	 * @return the hasJustChanged
	 */
	public boolean isHasJustChanged() {
		return hasJustChanged;
	}

	/**
	 * @return the pheromone
	 */
	public double getPheromone() {
		return pheromone;
	}

	/**
	 * @param _pheromone the pheromone to set
	 */
	public void setPheromone(double _pheromone) {
		hasJustChanged = (pheromone==0d || _pheromone==0d);
		pheromone = _pheromone;
	}

	/**
	 * @return the nourriture
	 */
	public double getNourriture() {
		return nourriture;
	}

	/**
	 * @param nourriture the nourriture to set
	 */
	public void setNourriture(double nourriture) {
		this.nourriture = nourriture;
	}

	/**
	 * @return the nid
	 */
	public boolean isNid() {
		return nid;
	}

	/**
	 * @param nid the nid to set
	 */
	public void setNid(boolean nid) {
		this.nid = nid;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the fourmis
	 */
	public boolean isFourmis() {
		return fourmis;
	}

	/**
	 * @param fourmis the fourmis to set
	 */
	public void setFourmis(boolean fourmis) {
		this.fourmis = fourmis;
		this.hasJustChanged = true;
	}

	/**
	 * @return the odeurNid
	 */
	public double getOdeurNid() {
		return odeurNid;
	}

	/**
	 * @param odeurNid the odeurNid to set
	 */
	public void setOdeurNid(double odeurNid) {
		this.odeurNid = odeurNid;
	}

}