import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Simulation class is the first class to be created. It's responsible for keeping the GUI and tournament updated.

 * 
 * @author Brett Flitter, Owen Cox
 * @version 04/06/2012 - 3
 */
public class Simulation
{
	private Tournament tournament;
	private int numTick;
	private int foodRed;
	private int foodBlack;
	private GUI gui;
	private WorldLoader worldLoader;


	
	/**
	 * Constructor
	 */
	public Simulation()
	{

		gui = new GUI(this);
		tournament = new Tournament();
		foodRed = 0;
		foodBlack = 0;
		numTick = 0;
		worldLoader = new WorldLoader();
		
	}
	
	/**
	 * The run method runs through each possible matching of brains in the tournament and pits them
	 * against one another, playing 2 rounds for each.
	 *
	 */
	public void run()
	{
		while(tournament.hasMoreGames())
		{
			//Set up a new game
			//get the next 2 players
			String[] players = tournament.nextContestants();
			String redPlayer = players[0];
			String blackPlayer = players[1];
			gui.updateScore("red", redPlayer, "0"); 
			gui.updateScore("black", blackPlayer, "0");
			
			
			String blackBrain = tournament.getBrain(blackPlayer);
			String redBrain = tournament.getBrain(redPlayer);
			
			//get the next world
			WorldToken[][] nextWorld = null;
			if(tournament.hasWorld())
			{
				nextWorld = tournament.getNextWorld();
				//load world into cells
				gui.loadCells(nextWorld);
			}
			
			//create the new world for these players
			World.getNewWorld(blackBrain, redBrain, nextWorld);
			
			//run the new game
			int roundLength = 200000; //TODO: number of ticks in a game?
			
			//round 1
			for(numTick = 0; numTick < roundLength; numTick++)
			{
				World.getWorld().update();
				foodRed = World.getWorld().calculateScore(AntColour.Red);
				foodBlack = World.getWorld().calculateScore(AntColour.Black);
				
				//Update GUI
				String redScore = "" + foodRed;
				String blackScore = "" + foodBlack;
				
				gui.updateScore("red", redPlayer, redScore);
				gui.updateScore("black", blackPlayer, blackScore);
				
				String[][] grid = World.getWorld().toStrings();
				for(int r = 0; r < grid.length; r++)
				{
					for(int c = 0; c < grid.length; c++)
					{
						String tileValue = grid[r][c];
						gui.updateCell(r, c, tileValue);					
					}
				}
			}
			
			//Aftermath of round 1
			if(foodRed > foodBlack)
			{
				tournament.addPoints(AntColour.Red, 1);  // addpoints() is implemented in tournament according to the LLD. Should this be the players name?
			}
			else
			{
				tournament.addPoints(AntColour.Black, 1);
			}
			
			//swap sides
			World.getWorld().swapSides();
			
			//round 2
			for(numTick = 0; numTick < roundLength; numTick++) 
			{
				World.getWorld().update();
				foodRed = World.getWorld().calculateScore(AntColour.Red);
				foodBlack = World.getWorld().calculateScore(AntColour.Black);
				
				//Update GUI
				String redScore = "" + foodRed;
				String blackScore = "" + foodBlack;
				
				gui.updateScore("red", redPlayer, redScore);
				gui.updateScore("black", blackPlayer, blackScore);
				
				String[][] grid = World.getWorld().toStrings();
				for(int r = 0; r < grid.length; r++)
				{
					for(int c = 0; c < grid.length; c++)
					{
						String tileValue = grid[r][c];
						gui.updateCell(r, c, tileValue);						
					}
				}
			}
			
			//aftermath of round 2
			if(foodRed > foodBlack)
			{
				tournament.addPoints(AntColour.Red, 1);
			}
			else
			{
				tournament.addPoints(AntColour.Black, 1);
			}
		}
		
		String victor = tournament.getVictor(); 
		gui.showVictor(victor); 
	}
	
	
	/**
	 * The addPlayerAndBrain method is used to add the players along with their brain location.
	 * It takes a string and uses a regular expression to divide the player's name from its brain location.
	 * This method then calls the addPlayer method from tournament passing the name and brain location.
	 * 
	 * @param playerAndBrain takes as a String containing the players name and brain location or a string with many names and locations.
	 */
	public void addPlayerAndBrain(String playerAndBrain)
	{
		Pattern p = Pattern.compile("([a-zA-Z0-9]+);([a-zA-Z]:.+\\.txt)");

		Matcher m = p.matcher(playerAndBrain);
		if (m.find())
		{
			String brainLoc = m.group(0);
			boolean goodBrain = true;
			File f = new File(brainLoc);

			//Checking that the brain exists and can be read by this file
			if(!f.canRead())
			{
				goodBrain = false;
			}

			//Checking that the brain is syntactically correct
			try
			{
				@SuppressWarnings("unused")
				AntBrainInterpreter abi = new AntBrainInterpreter(brainLoc);
			}
			catch(Exception e)
			{
				goodBrain = false;
			}

			if (goodBrain)
			{
				tournament.addPlayer(m.group(1), m.group(2));
			}
		}
		else
		{
			gui.outPutError("brain");

		}
		
	}
	
	/**
	 * The addWorldLocaions method is used to add the word 
	 * locations.
	 * @param worldLocations
	 */
	public void addWorldLocation(String worldLocation)
	{
		Pattern p = Pattern.compile("([a-zA-Z]:.*\\.world)");
		
			Matcher m = p.matcher(worldLocation);
			if (m.find())
			{	
				WorldToken[][] world = worldLoader.loadWorld(m.group(1));  // LLD says World creates worldLoader.. what do you think, leave it like this?
				if (world != null)
				{
					tournament.addWorld(world);
				}
				else
				{
					gui.outPutError(worldLoader.isError());
				}
			}
			else 
			{
				gui.outPutError("world");
			}
		
	}
	
	
	public static void main(String args[])
	{
		new Simulation();
	}
	
}
