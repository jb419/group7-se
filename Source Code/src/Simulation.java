import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Simulation class is the first class to be created. It's responsible for keeping the GUI and tournament updated.

 * 
 * @author Brett Flitter, Owen Cox
 * @version 04/06/2012 - 1
 */
public class Simulation
{
	private Tournament tournament;
	//private World world;
	private Boolean sidesSwapped;
	private int numTick;
	private int foodRed;
	private int foodBlack;
	private GUI gui;
	private Boolean isError;

	/**
	 * Constructor
	 */
	public Simulation()
	{

		gui = new GUI(this);
		tournament = new Tournament();
		sidesSwapped = false;
		int foodRed = 0;
		int foodBlack = 0;
		int numTick = 0;
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
			
			//TODO: Get red brain and black brain
			String blackBrain = /*code here*/"";
			String redBrain = /*code here*/"";
			
			//get the next world
			String nextWorld = null;
			if(tournament.hasWorld())
			{
				nextWorld = tournament.getNextWorld();
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
				
				gui.updateScore("red", redScore);
				gui.updateScore("black", blackScore);
				
				String[][] grid = World.getWorld().toStrings();
				for(int r = 0; r < grid.length; r++)
				{
					for(int c = 0; c < grid.length; c++)
					{
						String tileValue = grid[r][c];
						gui.updateLabel(r, c, tileValue);						
					}
				}
			}
			
			//Aftermath of round 1
			if(foodRed > foodBlack)
			{
				tournament.addPoints(AntColour.Red, 1);
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
				
				gui.updateScore("red", redScore);
				gui.updateScore("black", blackScore);
				
				String[][] grid = World.getWorld().toStrings();
				for(int r = 0; r < grid.length; r++)
				{
					for(int c = 0; c < grid.length; c++)
					{
						String tileValue = grid[r][c];
						gui.updateLabel(r, c, tileValue);						
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
		//TODO: add code to display victor in GUI.
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
			gui.incorrectNameOrBrain();

		}

	}

	/**
	 * The addWorldLocaions method is used to add the word 
	 * locations.
	 * @param worldLocations
	 */
	public void addWorldLocation(String worldLocation)
	{
		Pattern p = Pattern.compile("([a-zA-Z]:.*\\.txt)");

		Matcher m = p.matcher(worldLocation);
		if (m.find())
		{	
			boolean goodWorld = true;
			String worldLoc = m.group(1);
			File f = new File(worldLoc);
			//Checking that the file exists and is reachable
			if(!f.canRead())
			{
				goodWorld = false;
			}
			//CALL TO WORLDLOADER TO CHECK THE WORLD LOCATION IS CORRECT & WORLD IS WELL FORMED
			tournament.addWorld(worldLoc);
		}
		else 
		{
			gui.incorrectWorld();
		}

	}


	//private boolean findBrain()
	//{
	//	if brain is ok then return true
	//}

	//priave boolean findWorld()
	//{ 
	// if world is ok then return true
	//}

	public static void main(String args[])
	{
		new Simulation();
	}

}
