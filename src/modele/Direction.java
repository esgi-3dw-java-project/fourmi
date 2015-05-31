package modele;

/**type enumere evolue codant les directions des 4 points cardinaux et des 4 points sous forme de vecteur*/
public enum Direction {
	NORD(0, 0, -1), NORD_EST(1, 1, -1), EST(2,1,0), SUD_EST(3,1,1), SUD(4,0,1), SUD_OUEST(5,-1,1), OUEST(6,-1,0), NORD_OUEST(7,-1,-1);
	/**no d'indice de la direction*/
	int no;
	/**coordonnee x du vecteur de la direction*/
	int x;
	/**coordonnee y du vecteur de la direction*/
	int y;
	
	/**
	 * @param _no no d'indice de la direction
	 * @param _x coordonnee x du vecteur de la direction
	 * @param _y coordonnee y du vecteur de la direction
	 */
	Direction(int _no,int _x, int _y){no = _no; x = _x; y = _y;}
	
	/**@return coordonnee x du vecteur de la direction*/
	public int getX(){return x;}
	
	/**@return coordonnee y du vecteur de la direction*/
	public int getY(){return y;}
	
	/**
	 * @param dir direction dont on souhaite calculer l'inverse
	 * @return retourne la direction inverse a dir */
	static Direction getInverse(Direction dir)
	{
		Direction [] tab = Direction.values();
		int indice = (dir.no + 4) % 8;
		return tab[indice];
	}
	
	/**
	 * @param dir direction dont on souhaite calculer les directions voisines
	 * @return retourne les trois directions entourant la direction en parametre*/
	static Direction[] get3Dir(Direction dir)
	{
		Direction[] dir3 = new Direction[3];
		Direction [] tab = Direction.values();
		int j=0;
		for(int i=-1; i<=1; i++)
		{
			int indice = (dir.no + i + 8) % 8;
			dir3[j++] = tab[indice];
		}
		return dir3;
	}

	/**
	 * @param a coordonnee x du vecteur dont on souhaite la direction
	 * @param b coordonnee y du vecteur dont on souhaite la direction
	 * @return la direction correspondante au vecteur (a,b)*/
	static Direction getDirectionFromVector(int a, int b)
	{
		Direction dir = null;
		for(Direction d:Direction.values())
			if(d.x ==a && d.y==b){dir = d; break;}
		return dir;
	}
	
	/**retourne une direction tirée aléatoirement
	 * @return une direction tirée aléatoirement*/
	static Direction randomDirection()
	{
		Direction[]tab = Direction.values();
		int i = (int)(Math.random()*tab.length);
		return tab[i];
	}
	
}


