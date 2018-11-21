package cs2113.zombies;

import cs2113.util.Helper;

import java.awt.Color;
import java.util.ArrayList;


public class City {

	/** walls is a 2D array with an entry for each space in the city.
	 *  If walls[x][y] is true, that means there is a wall in the space.
	 *  else the space is free. Humans should never go into spaces that
	 */
	public boolean walls[][];
	public boolean survivors[][];
	public boolean zombies[][];
	public boolean healers[][];
	public int width, height;
	public ArrayList<Survivor> SurvivorsList = new ArrayList<>();			// arrayList for survivors
	public ArrayList<Zombie> ZombiesList = new ArrayList<>();				// arrayList for zombies
	public ArrayList<Healer> HealerList = new ArrayList<>();				// arrayList for healers
	/**
	 * Create a new City and fill it with buildings and people.
	 * @param w width of city
	 * @param h height of city
	 * @param numB number of buildings
	 * @param numP number of people
	 */
	public City(int w, int h, int numB, int numP) {
		width = w;
		height = h;
		walls = new boolean[w][h];
		survivors = new boolean[w][h];										// mark survivors' locations
		zombies = new boolean[w][h];										// mark zombies' locations
		healers = new boolean[w][h];										// mark healers' locations

		randomBuildings(numB);
		populate(numP);
	}

	/**
	 * Generates numPeople random people distributed throughout the city.
	 * People must not be placed inside walls!
	 *
	 * @param numPeople the number of people to generate
	 */
	private void populate(int numPeople)
	{
		int c;
		int r;
		for (int i =0; i<numPeople; i++) {
			c = Helper.nextInt(200);
			r = Helper.nextInt(200);
			Survivor s = new Survivor (c, r);								// create a new human in random location
			while (walls[s.getX()][s.getY()]) {								// check if it is inside the walls
				s.switchPos();												// if so, switch its position to another random location
				//System.out.println ("Print");
			}
			SurvivorsList.add(s);										    // add to 'humans' arraylist
			survivors[s.getX()][s.getY()] = true;							// mark survivor's location
		}

		c = Helper.nextInt(200);
		r = Helper.nextInt(200);
		Zombie z = new Zombie (c, r);										// create a new zombie in random location
		while (walls[z.getX()][z.getY()]) {									// check if it is inside the walls
			z.switchPos();													// if so, switch its position to another random location
		}
		ZombiesList.add(z);												    // add to 'zombies' arraylist
		zombies[z.getX()][z.getY()] = true;									// mark zombie's location

//		c = Helper.nextInt(200);
//		r = Helper.nextInt(200);
//		Healer h = new Healer (c, r);;										// create a new healer in random location
//		while (walls[h.getX()][h.getY()]) {									// check if it is inside the walls
//			h.switchPos();													// if so, switch its position to another random location
//		}
//		HealerList.add(h);												    // add to 'healers' arraylist
//		zombies[h.getX()][h.getY()] = true;									// mark healer's location
	}

	/**
	 * Generates a random set of numB buildings.
	 *
	 * @param numB the number of buildings to generate
	 */
	private void randomBuildings(int numB) {
		/* Create buildings of a reasonable size for this map */
		int bldgMaxSize = width/6;
		int bldgMinSize = width/50;

		/* Produce a bunch of random rectangles and fill in the walls array */
		for(int i=0; i < numB; i++) {
			int tx, ty, tw, th;
			tx = Helper.nextInt(width);
			ty = Helper.nextInt(height);
			tw = Helper.nextInt(bldgMaxSize) + bldgMinSize;
			th = Helper.nextInt(bldgMaxSize) + bldgMinSize;

			for(int r = ty; r < ty + th; r++) {
				if(r >= height)
					continue;
				for(int c = tx; c < tx + tw; c++) {
					if(c >= width)
						break;
					walls[c][r] = true;
				}
			}
		}
	}

	/**
	 * Updates the state of the city for a time step.
	 */
	public void update() {
		// Move humans, zombies, etc

		if (SurvivorsList != null) {										// until all the humans turn into zombies
			for (Survivor s: SurvivorsList) {
				s.move(walls, width, height, zombies);						// update each survivor's movement
			}

			for (Zombie z: ZombiesList) {
				z.move(walls, width, height, survivors);					// update each zombie's movement
			}

			for (Healer h: HealerList) {
				h.move (walls, width, height, zombies);
			}
		}

	}

	/**
	 * Draw the buildings and all humans.
	 */
	public void draw(){
		/* Clear the screen */
		ZombieSim.dp.clear(Color.black);

		drawWalls();
		drawHumans();											// draw humans
		drawZombies();											// draw zombies
		drawHealers();											// draw healers
	}

	/**
	 * Draw the buildings.
	 * First set the color for drawing, then draw a dot at each space
	 * where there is a wall.
	 */
	private void drawWalls() {
		ZombieSim.dp.setPenColor(Color.DARK_GRAY);
		for(int r = 0; r < height; r++)
		{
			for(int c = 0; c < width; c++)
			{
				if(walls[c][r])
				{
					ZombieSim.dp.drawDot(c, r);
				}
			}
		}
	}

	private void drawHumans() {									// Draw humans

		ArrayList<Human> infectedHumans = new ArrayList<Human>(0);

		for (int i=0; i<survivors.length; i++) {
			for (int j=0; j<survivors[i].length; j++) {
				survivors[i][j] = false;						// make all the previous spots to be false
			}
		}

		for (Survivor s: SurvivorsList) {
			int x = s.getX();
			int y = s.getY();

			if (infected(s)) {
				infectedHumans = infection(s, zombies, survivors, infectedHumans, ZombiesList);
			}

			else
				survivors[x][y] = true;
				s.draw();
		}

		if (infectedHumans != null) {									// if any human is infected
			SurvivorsList.removeAll(infectedHumans);					// remove from 'humans' arrayList
		}
	}

	private void drawZombies() {								// Draw zombies

		ArrayList<Zombie> sanitizedZombies = new ArrayList<Zombie>(0);

		for (int i=0; i<zombies.length; i++) {
			for (int j=0; j<zombies[i].length; j++) {
				zombies[i][j] = false;							// make all the previous spots to be false
			}
		}

		for (Zombie z: ZombiesList) {
			int x = z.getX();
			int y = z.getY();

			if (sanitized(z)) {
				sanitizedZombies = sanitation(z, zombies, survivors, sanitizedZombies, HealerList);
			}

			else
				zombies[x][y] = true;							// draw zombies in the ZombiesList
				z.draw();
		}

		if (sanitizedZombies != null) {									// if any human is infected
			ZombiesList.removeAll(sanitizedZombies);					// remove from 'humans' arrayList
		}
	}

	private void drawHealers() {

		for (int i=0; i<healers.length; i++) {
			for (int j=0; j<healers[i].length; j++) {
				healers[i][j] = false;							// make all the previous spots to be false
			}
		}

		for (Healer h: HealerList) {
			h.draw();											// draw healers in the HealersList
		}
	}

	private boolean infected (Survivor s) {
		int x = s.getX();
		int y = s.getY();

		if (s.inBoundsNotWalls(walls, width, height, x + 1, y)) {
			if (zombies[x + 1][y]) {
				return true;
			}
		}
		else if (s.inBoundsNotWalls(walls, width, height, x - 1, y)) {
			if (zombies[x - 1][y]) {
				return true;
			}
		}
		else if (s.inBoundsNotWalls(walls, width, height, x, y+1)) {
			if (zombies[x][y + 1]) {
				return true;
			}
		}
		else if (s.inBoundsNotWalls(walls, width, height, x, y+1)) {
			if (zombies[x][y - 1]) {
				return true;
			}
		}
		return false;
	}


	public ArrayList<Human> infection(Survivor s, boolean[][] zombies, boolean[][] survivors, ArrayList<Human> infectedHumans, ArrayList<Zombie> ZombiesList) {

		int x = s.getX();
		int y = s.getY();

		Zombie z = new Zombie (x, y);
		z.draw();

		infectedHumans.add(s);
		zombies[x][y] = true;
		survivors[x][y] = false;
		ZombiesList.add(z);

		return infectedHumans;
	}

	private boolean sanitized (Zombie z) {
		int x = z.getX();
		int y = z.getY();

		if (z.inBoundsNotWalls(walls, width, height, x + 1, y)) {
			if (healers[x + 1][y]) {
				return true;
			}
		}
		else if (z.inBoundsNotWalls(walls, width, height, x - 1, y)) {
			if (healers[x - 1][y]) {
				return true;
			}
		}
		else if (z.inBoundsNotWalls(walls, width, height, x, y+1)) {
			if (healers[x][y + 1]) {
				return true;
			}
		}
		else if (z.inBoundsNotWalls(walls, width, height, x, y+1)) {
			if (healers[x][y - 1]) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Zombie> sanitation(Zombie z, boolean[][] zombies, boolean[][] healers, ArrayList<Zombie> sanitizedZombies, ArrayList<Healer> HealerList) {

		int x = z.getX();
		int y = z.getY();

		Healer h = new Healer (x, y);
		h.draw();

		sanitizedZombies.add(z);
		healers[x][y] = true;
		zombies[x][y] = false;
		HealerList.add(h);

		return sanitizedZombies;
	}

}
