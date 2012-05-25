
/**
 * A World is a singleton object that stores a set of cells and has methods
 * to manipulate those cells
 * 
 * @author Owen Cox
 * @version 25/05/2012 - 2
 */
public class World
{
	public static World theWorld;
	
	/**
	 * The getWorld method gets the current instance of the world or creates one if one no longer exists
	 *
	 * @param blackAntBrain the black ant's brain location.
	 * @param redAntBrain the red ant's brain location.
	 * @param worldLocation the location of the world
	 * @return
	 */
	public static World getNewWorld(String blackAntBrain, String redAntBrain, String worldLocation)
	{
		if(theWorld != null)
		{
			theWorld = new World(blackAntBrain, redAntBrain, worldLocation);
		}
		return theWorld;
	}
	
	public static World getWorld()
	{
		return theWorld;
	}
	
	
	private WorldToken[][] worldInitial; //A 2D array of world tokens representing the world
	private final static int WORLD_SIZE = 150;
	private Cell[][] cells;
	
	private World(String brainLocRed, String brainLocBlack, String worldLoc)
	{
		AntBrainInterpreter redInterp = new AntBrainInterpreter(brainLocRed);
		AntBrainInterpreter blackInterp = new AntBrainInterpreter(brainLocBlack);
		WorldLoader wl = new WorldLoader(worldLoc);
		worldInitial = wl.getTokens();
		
		for(int row = 0; row < WORLD_SIZE; row++)
		{
			for(int col = 0; col < WORLD_SIZE; col++)
			{
				Cell c = new Cell()
				cells[row][col] = c;
				switch(wl[row][col].getType)
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
	}
}
