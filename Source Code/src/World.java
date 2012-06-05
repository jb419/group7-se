/**
 * A World is a singleton object that stores a set of cells and has methods
 * to manipulate those cells
 * 
 * @author Owen Cox, Brett Flitter
 * @version 05/06/2012 - 13
 */
public class World
{
	public static World theWorld;
	
	/**
	 * The getWorld method creates a new world and returns it.
	 *
	 * @param blackAntBrain the black ant's brain location.
	 * @param redAntBrain the red ant's brain location.
	 * @param worldLocation the location of the world
	 * @return the new world that was created
	 */
	public static World getNewWorld(String blackAntBrain, String redAntBrain, WorldToken[][] world)
	{
		theWorld = new World(blackAntBrain, redAntBrain, world);
		return getWorld();
	}
	
	/**
	 * The getWorld method gets the world if it exists and returns it
	 *
	 * @return the world
	 */
	public static World getWorld()
	{
		return theWorld;
	}
	
	
	private WorldToken[][] worldInitial; //A 2D array of world tokens representing the world
	private final static int WORLD_SIZE = 150; //The size of the world
	private Cell[][] cells; //The Array of cells
	private Ant[] ants; //The Array of ants
	private Cell[][] hills; //Stores the locations of all the ant hills. Added to make calculating the score more efficient. 
	//hills[0] contain all the red ant hill cells, hills[1] contains all the black ant hill cells.
	
	private AntBrainInterpreter redI;
	private AntBrainInterpreter blackI;
	
	private World(String brainLocRed, String brainLocBlack, WorldToken[][] world)
	{
		try
		{
			redI = new AntBrainInterpreter(brainLocRed);
			blackI = new AntBrainInterpreter(brainLocBlack);
			worldInitial = world;
			cells = new Cell[WORLD_SIZE][WORLD_SIZE];
			hills = new Cell[2][91];
		}
		catch(Exception e) //Should never be thrown, this was checked when the brain was entered.
		{
			System.err.println(e);
		}
		/*if(worldInitial == null)
		{
			WorldGenerator g = new WorldGenerator();
			worldInitial = g.getTokens();
		}*/
		ants = new Ant[182];
		
		for(int row = 0; row < WORLD_SIZE; row++)
		{
			for(int col = 0; col < WORLD_SIZE; col++)
			{
				Cell c = new Cell();
				cells[row][col] = c;
				switch(worldInitial[row][col].getType())
				{
					case Food://Add various stuff to cells
						for(int i = 0; i < 9; i++)
						{
							c.addFood();
						}
						break;
						
					case RedAntHill:
						addAntHill(AntColour.Red, c);
						break;
						
					case BlackAntHill:
						addAntHill(AntColour.Black, c);
						break;
						
					case Rock:
						c.addRock();
						break;
				}
			}
		}
		addAnts();
	}
	
	public void swapSides()
	{
		ants = new Ant[182];
		for(int row = 0; row < WORLD_SIZE; row++)
		{
			for(int col = 0; col < WORLD_SIZE; col++)
			{
				Cell c = new Cell();
				cells[row][col] = c;
				switch(worldInitial[row][col].getType())
				{
					case Food://Add various stuff to cells
						for(int i = 0; i < 9; i++)
						{
							c.addFood();
						}
						break;
						
					case RedAntHill:
						addAntHill(AntColour.Black, c);
						break;
						
					case BlackAntHill:
						addAntHill(AntColour.Red, c);
						break;
						
					case Rock:
						c.addRock();
						break;
				}
			}
		}
		addAnts();
	}
	
	/**
	 * The addAntHill method adds an ant hill of a specific colour
	 * to the world and stores the location of the hill in the hills array.
	 *
	 * @param c the colour of ant hill to add
	 */
	private void addAntHill(AntColour c, Cell cell)
	{
		int side = 0;
		if(c == AntColour.Red)
		{
			side = 1;
		}
		cell.addAntHill(c);
		hills[side][hills[side].length] = cell; //add the cell to the end of the array
	}
	
	/**
	 * The addFood method adds a food pellet to a given cell
	 *
	 * @param pos the cell to add the food pellet to
	 */
	public void addFood(int[] pos)
	{
		int row = pos[0];
		int col = pos[1];
		cells[row][col].addFood();
	}
	
	/**
	 * The removeFood method removes a food pellet from a specified cell
	 *
	 * @param pos the position of the cell to remove the food pellet from
	 */
	public void removeFood(int[] pos)
	{
		int row = pos[0];
		int col = pos[1];
		cells[row][col].removeFood();
	}
	
	/**
	 * The addAnts method adds ants at the correct locations in the world.
	 *
	 */
	private void addAnts()
	{
		for(int i = 0; i < WORLD_SIZE; i++)
		{
			for(int j = 0; j < WORLD_SIZE; j++)
			{
				if(cells[i][j].checkCondition(Condition.BlackHill))
				{
					Ant a = new Ant(this, AntColour.Black); //Creates a new ant
					a.setAntBrain(new AntBrain(blackI.getStates(), a));
					cells[i][j].addAnt(a); //add the created ant to the cell
					int[] pos = {i, j};
					a.setPos(pos); //Set the ants position to be correct
					ants[ants.length] = a; //Sets the next free element of ants to be = to the created ant
				}
				else if(cells[i][j].checkCondition(Condition.RedHill))
				{
					Ant a = new Ant(this, AntColour.Red);
					a.setAntBrain(new AntBrain(redI.getStates(), a));
					cells[i][j].addAnt(a);
					ants[ants.length] = a;
				}
			}
		}
	}
	
	//Inspection Methods
	public int[] adjacentCell(int[] cell, Direction d)
	{
		int x = cell[0];
		int y = cell[1];
		
		switch(d)
		{
			case NW:
				if(y % 2 == 0) //% finds remainder, this is the same as saying if even
				{
					x--;
					y--;
				}
				else
				{
					//x unchanged
					y--;
				}
				break;
			case NE:
				if(y % 2 == 0) //% finds remainder, this is the same as saying if even
				{
					//x unchanged
					y--;
				}
				else
				{
					x++;
					y--;
				}
				break;
			case E:
				x++;
				break;
			case SE:
				if(y % 2 == 0) //% finds remainder, this is the same as saying if even
				{
					//x unchanged
					y++;
				}
				else
				{
					x++;
					y++;
				}
				break;
			case SW:
				if(y % 2 == 0) //% finds remainder, this is the same as saying if even
				{
					x--;
					y++;
				}
				else
				{
					//x unchanged
					y++;
				}
				break;
			case W:
				x = x - 1;
				break;
		}
		int[] adjCell = {x, y};
		return adjCell;
	}
	
	/**
	 * The antAt method checks if there is an ant at the given position
	 *
	 * @param pos the position to check if there is an ant at
	 */
	public boolean antAt(int[] pos)
	{
		int row = pos[0];
		int col = pos[1];
		Cell c = cells[row][col];
		boolean b = false;
		if(c.checkCondition(Condition.BlackAnt))
		{
			b = true;
		}
		else if(c.checkCondition(Condition.RedAnt))
		{
			b = true;
		}
		return b;
	}
	
	/**
	 * The setAntAt method adds a specific ant to a specific cell
	 *
	 * @param pos the position of the cell to add the ant to in the form {row, column}
	 * @param ant the ant to add to the cell
	 */
	public void setAntAt(int[] pos, Ant ant)
	{
		int row = pos[0];
		int col = pos[1];
		cells[row][col].addAnt(ant);
		ant.setPos(pos);
	}
	
	/**
	 * The clearAntAt method clears a specified ant 
	 *
	 * @param pos the position to clear the ant from
	 * @param ant the ant to clear from the position
	 */
	public void clearAntAt(int[] pos, Ant ant)
	{
		int row = pos[0];
		int col = pos[1];
		cells[row][col].removeAnt(ant);
	}
	
	/**
	 * The getAntAt method gets the ant at a specified location in the
	 * World
	 *
	 * @return the ant that is at the specified position, or null if none was found.
	 */
	public Ant getAntAt(int[] pos)
	{
		Ant a = null;
		if(antAt(pos))
		{
			int row = pos[0];
			int col = pos[1];
			a = cells[row][col].getAnt();
		}
		return a;
	}
	
	/**
	 * The findAnt method finds the location of a given ant specified by that ant's id number
	 *
	 * @param id the id number of the ant to look for
	 * @return the position of the specified ant, or -1, -1 if no such ant exists.
	 */
	public int[] findAnt(int id)
	{
		int[] pos = {-1, -1};
		if(id < 0 || id >= ants.length)
		{
			//do nothing
		}
		else
		{
			pos = ants[id].getPos();
		}
		return pos;
	}
	
	/**
	 * The update method. Goes through all ants and then updates the score.
	 */
	public void update()
	{
		for(Ant ant : ants)
		{
			if(ant.isAlive())
			{
				ant.step();
			}
		}
	}
	
	/**
	 * The mark method marks a given cell with a marker of a given ant colour
	 *
	 * @param pos the location of the cell to mark
	 * @param colour the colour of the marking
	 * @param chemId the number of the marking
	 */
	public void mark(int[] pos, AntColour colour, int chemId)
	{
		int row = pos[0];
		int col = pos[0];
		cells[row][col].mark(chemId, colour);
	}
	
	/**
	 * The unmark method removes a given marking of given colour from a given cell
	 *
	 * @param pos the position of the cell to unmark
	 * @param colour the colour of marking to remove
	 * @param chemId the type of marker to remove
	 */
	public void unmark(int[] pos, AntColour colour, int chemId)
	{
		int row = pos[0];
		int col = pos[1];
		cells[row][col].unmark(chemId, colour);
	}
	
	/**
	 * The marked method checks if a cell is marked with a specific marker by
	 * a specific colour of ant
	 *
	 * @param pos the position of the cell to check
	 * @param markerNumber the number of marker to check for
	 * @param c the colour of ant to check for the marker of
	 * @return if the cell if marker with the specific coloured marker type
	 */
	public boolean marked(int[] pos, int markerNumber, AntColour c)
	{
		int row = pos[0];
		int col = pos[1];
		return cells[row][col].isMarked(c, markerNumber);
	}
	
	/**
	 * The moveableCell method checks if a given cell can be moved into
	 *
	 * @param pos the position of the cell to check
	 * @return a boolean representing if the cell can be moved into
	 */
	public boolean moveableCell(int[] pos)
	{
		boolean b = true;
		int row = pos[0];
		int col = pos[1];
		Cell c = cells[row][col];
		if(c.checkCondition(Condition.Rock))
		{
			b = false;
		}
		else if(c.checkCondition(Condition.BlackAnt))
		{
			b = false;
		}
		else if(c.checkCondition(Condition.RedAnt))
		{
			b = false;
		}
		//Cells are not moveable if they contain ants of either colour or rocks.
		return b;
	}
	
	/**
	 * The adjacentAnts method gets an array of ants adjacent to the given cell of a specific colour
	 *
	 * @param pos the cell to check for ants adjacent to 
	 * @return an array of adjacent ants
	 */
	public Ant[] adjacentAnts(int[] pos, AntColour colour)
	{
		Ant[] adjAnts = new Ant[6];
		for(Direction d: Direction.values())
		{
			int[] adjPos = adjacentCell(pos, d);
			if(antAt(adjPos))
			{
				Ant a = getAntAt(adjPos);
				if(a.getColour()== colour)
				{
					adjAnts[adjAnts.length] = a;
				}
			}
		}
		return adjAnts;
	}
	
	/**
	 * Returns the world as an array of Strings usable by the GUI
	 */
	public String[][] toStrings()
	{
		String[][] s = new String[WORLD_SIZE][WORLD_SIZE];
		for(int r = 0; r < WORLD_SIZE; r++)
		{
			for(int c = 0; c < WORLD_SIZE; c++)
			{
				s[r][c] = cells[r][c].toString();
			}
		}
		return s;
	}
	
	/**
	 * The cellMatches method checks if a given cell matches a given condition
	 *
	 * @param pos the position of the cell to check the condition of
	 * @param cond the condition to check
	 * @return a boolean representing the state of the cell, true if the condition is true, false otherwise.
	 */
	public boolean cellMatches(int[] pos, Condition cond)
	{
		int row = pos[0];
		int col = pos[1];
		return cells[row][col].checkCondition(cond);
	}
	
	/**
	 * The calculateScore method iterates over all the ant hill cells of
	 * a given colour and returns the total amount of food on that ant hill
	 *
	 * @param c the colour of ant hill to check
	 * @return the score of that ant colour
	 */
	public int calculateScore(AntColour c)
	{
		int side = 0;
		int score = 0;
		if(c == AntColour.Black)
		{
			side = 1;
		}
		for(Cell cell: cells[side])
		{
			score += cell.calculateFoodAmount();
		}
		return score;
	}
}
