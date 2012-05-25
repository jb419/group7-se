/**
 * A World is a singleton object that stores a set of cells and has methods
 * to manipulate those cells
 * 
 * @author Owen Cox
 * @version 25/05/2012 - 4
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
		if(theWorld != null)
		{
			theWorld = new World(blackAntBrain, redAntBrain, worldLocation);
		}
		return theWorld;
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
	private final static int WORLD_SIZE = 150;
	private Cell[][] cells;
	private Ant[] ants;
	
	private World(String brainLocRed, String brainLocBlack, String worldLoc)
	{
		AntBrainInterpreter redInterp = new AntBrainInterpreter(brainLocRed);
		AntBrainInterpreter blackInterp = new AntBrainInterpreter(brainLocBlack);
		WorldLoader wl = new WorldLoader(worldLoc);
		worldInitial = wl.getTokens();
		ants = new Ant[182];
		
		for(int row = 0; row < WORLD_SIZE; row++)
		{
			for(int col = 0; col < WORLD_SIZE; col++)
			{
				Cell c = new Cell()
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
						c.addAntHill(AntColour.Red);
						break;
						
					case WorldTokenType.BlackAntHill:
						c.addAntHill(AntColour.Black);
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
				Cell c = new Cell()
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
						c.addAntHill(AntColour.Black);
						break;
						
					case WorldTokenType.BlackAntHill:
						c.addAntHill(AntColour.Red);
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
	
	
}
