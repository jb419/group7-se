/**
 * A World is a singleton object that stores a set of cells and has methods
 * to manipulate those cells
 * 
 * @author Owen Cox
 * @version 27/05/2012 - 1
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
	public static World getNewWorld(String blackAntBrain, String redAntBrain, String worldLocation)
	{
		theWorld = new World(blackAntBrain, redAntBrain, worldLocation);
		getWorld();
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
	
	private World(String brainLocRed, String brainLocBlack, String worldLoc)
	{
		AntBrainInterpreter redInterp = new AntBrainInterpreter(brainLocRed);
		AntBrainInterpreter blackInterp = new AntBrainInterpreter(brainLocBlack);
		if(worldLoc == null)
		{
			WorldGenerator wg = new WorldGenerator();
			worldInitial = wg.getTokens();
		}
		else
		{
			WorldLoader wl = new WorldLoader(worldLoc);
			worldInitial = wl.getTokens();
		}
		ants = new Ant[182];
		
		for(int row = 0; row < WORLD_SIZE; row++)
		{
			for(int col = 0; col < WORLD_SIZE; col++)
			{
				Cell c = new Cell();
				cells[row][col] = c;
				switch(worldInitial[row][col].getType())
				{
					case WorldTokenType.Food://Add various stuff to cells
						for(int i = 0; i < 9; i++)
						{
							c.addFood();
						}
						break;
						
					case WorldTokenType.RedAntHill:
						addAntHill(AntColour.Red, c);
						break;
						
					case WorldTokenType.BlackAntHill:
						addAntHill(AntColour.Black, c);
						break;
						
					case WorldTokenType.Rock:
						c.addRock();
						break;
				}
			}
		}
		addAnts();
	}
	
	public void swapSides()
	{
		for(int row = 0; row < WORLD_SIZE; row++)
		{
			for(int col = 0; col < WORLD_SIZE; col++)
			{
				Cell c = new Cell();
				cells[row][col] = c;
				switch(worldInitial[row][col].getType())
				{
					case WorldTokenType.Food://Add various stuff to cells
						for(int i = 0; i < 9; i++)
						{
							c.addFood();
						}
						break;
						
					case WorldTokenType.RedAntHill:
						addAntHill(AntColour.Black, c);
						break;
						
					case WorldTokenType.BlackAntHill:
						addAntHill(AntColour.Red, c);
						break;
						
					case WorldTokenType.Rock:
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
					Ant a = new Ant(); //Creates a new ant
					cells[i][j].addAnt(a); //add the created ant to the cell
					//updates the ant's information about its position in the world
					ants[ants.length] = a; //Sets the next free element of ants to be = to the created ant
				}
				else if(cells[i][j].checkCondition(Condition.RedHill))
				{
					Ant a = new Ant();
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
				if(y % 2 == 0) //% finds remainer, this is the same as saying if even
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
				if(y % 2 == 0) //% finds remainer, this is the same as saying if even
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
				if(y % 2 == 0) //% finds remainer, this is the same as saying if even
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
				if(y % 2 == 0) //% finds remainer, this is the same as saying if even
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
		//TODO: update ant's internal position
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
		cells[r][c].removeAnt(ant);
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
				ant.update();
			}
		}
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
