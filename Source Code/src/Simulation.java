import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingWorker;

/**
 * The Simulation class is the first class to be created. It's responsible for keeping the GUI and tournament updated.

 * 
 * @author Brett Flitter, Owen Cox
 * @version 09/06/2012 - 3
 */
public class Simulation extends SwingWorker<Void, Void>
{
	private Tournament tournament;
	private int foodRed;
	private int foodBlack;
	private GUI gui;
	private WorldLoader worldLoader;
	private int numOfPlayers;
	private int numOfWorlds;

	private WorldToken[][] nextWorld;
	private String redPlayer;
	private String blackPlayer;
	private int numTicks;
	private boolean sidesSwapped;
	private boolean isFinished;
	private final int roundLength = 300000;
	private int redScore;
	private int blackScore;
	
	/**
	 * Constructor
	 */
	public Simulation()
	{

		gui = new GUI(this);
		tournament = new Tournament();
		foodRed = 0;
		foodBlack = 0;
		redPlayer = "None";
		blackPlayer = "None";
		redScore = 0;
		blackScore = 0;
		numTicks = 0;
		sidesSwapped = false;
		worldLoader = new WorldLoader();
		isFinished = false;
		
		// A bit hacked together but hopefully it'll work.
		WorldToken[][] wt = new WorldToken[150][150];
		for(int i = 0; i < 150; i++)
		{
			for(int j = 0; j < 150; j++)
			{
				wt[i][j] = new WorldToken(WorldTokenType.Empty);
			}
		}
		gui.loadCells(wt);
		
	}
	
	protected Void doInBackground() throws Exception
	{
	
		while(RunChecker.isRunning())
		{
			step();
			updateGUI();
			Thread.sleep(5L);
			
		}
		
		return null;
	}
	
	
	/**
	 * The updateGUI method updates the GUI, using the toStrings method of the World
	 *
	 */
	private void updateGUI()
	{
		gui.updateScore("red", redPlayer, "" + foodRed); 
		gui.updateScore("black", blackPlayer, "" + foodBlack);
		gui.updateRound("" + numTicks);
		String[][] grid = World.getWorld().toStrings();
		for(int r = 0; r < grid.length; r++)
		{
			for(int c = 0; c < grid.length; c++)
			{
				String tileValue = grid[c][r];
				gui.updateCell(r, c, tileValue);					
			}
		}
		if(isFinished)
		{
			gui.showVictor(tournament.getVictor());
		}
	}
	
	/**
	 * The updatePoints method updates the points of the players based on the food totals from the game
	 *
	 */
	private void updatePoints()
	{
		if(redScore > blackScore)
		{
			tournament.addPoints("red", 2);
		}
		else if(blackScore > redScore)
		{
			tournament.addPoints("black", 2);
		}
		else
		{
			tournament.addPoints("black", 1);  
			tournament.addPoints("red", 1);
		}
	}
	
	/**
	 * The step method plays a single step, that is, it updates the World and all the ants once
	 *
	 */
	public void step()
	{
		if(World.getWorld() == null)
		{
			String[] players = tournament.nextContestants();
			blackPlayer = players[0];
			redPlayer = players[1];
			foodRed = 0;
			foodBlack = 0;
			String redBrainLoc = tournament.getBrain(redPlayer);
			String blackBrainLoc = tournament.getBrain(blackPlayer);
			if(tournament.hasWorld())
			{
				nextWorld = tournament.getNextWorld();
				World.getNewWorld(blackBrainLoc, redBrainLoc, nextWorld);
			}
			else
			{
				World.getNewWorld(blackBrainLoc, redBrainLoc, null);
				nextWorld = World.getWorld().getTokens();
			}
		}
		else
		{
			if(numTicks <= roundLength)
			{
				foodRed = World.getWorld().calculateScore(AntColour.Red);
				foodBlack = World.getWorld().calculateScore(AntColour.Black);
				if(numTicks == roundLength)
				{
					//first update points as the end of a round has been reached
					redScore += foodRed;
					blackScore += foodBlack;
					
					//next see what type of round end it is
					if(!sidesSwapped)
					{
						World.getWorld().swapSides(); //swap sides if necessary
						sidesSwapped = true;
					}
					
					else if(tournament.hasMoreGames()) //otherwise get new players and world
					{
						updatePoints();
						sidesSwapped = false;
						String[] players = tournament.nextContestants();
						if(players == null)
						{
							isFinished = true; //if there aren't any more games left we're done here
							RunChecker.stop();
							updateGUI();
						}
						blackPlayer = players[0];
						redPlayer = players[1];
						redScore = 0;
						blackScore = 0;
						//get new brains
						String redBrainLoc = tournament.getBrain(redPlayer);
						String blackBrainLoc = tournament.getBrain(blackPlayer);
						
						if(tournament.hasWorld())
						{
							nextWorld = tournament.getNextWorld();
							World.getNewWorld(blackBrainLoc, redBrainLoc, nextWorld);
						}
						else
						{
							World.getNewWorld(blackBrainLoc, redBrainLoc, null);
						}
					}
					else
					{
						updatePoints();
						isFinished = true; //if there aren't any more games left we're done here
						RunChecker.stop();
					}
					numTicks = 0;
				}
				else
				{
					World.getWorld().update(); //if not at the end of the round update and tick
					numTicks ++;
				}
			}
		}
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
		Pattern p = Pattern.compile("([a-zA-Z0-9]+);([a-zA-Z]:.*\\.ant)");

		Matcher m = p.matcher(playerAndBrain);
		if (m.find())
		{
			String brainLoc = m.group(2);
			boolean goodBrain = true;
			File f = new File(brainLoc);

			//Checking that the brain exists and can be read by this file
			if(!f.canRead())
			{
				goodBrain = false;
			}
			
			String message = "";
			//Checking that the brain is syntactically correct
			try
			{
				@SuppressWarnings("unused")
				AntBrainInterpreter abi = new AntBrainInterpreter(brainLoc);
			}
			catch(Exception e)
			{
				goodBrain = false;
				message = e.getMessage();
			}

			if (goodBrain)
			{
				tournament.addPlayer(m.group(1), m.group(2));
				numOfPlayers++;
			}
			else
			{
				gui.outPutError("Brain not syntactically correct! " + message);
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
				WorldToken[][] world = worldLoader.loadWorld(m.group(1));  
				if (world != null)
				{
					tournament.addWorld(world);
					numOfWorlds++;
				}
				else
				{
					gui.outPutError(worldLoader.isError());
				}
			}
			else if (worldLocation.equals("random"))
			{
				WorldGenerator worldGenerator = new WorldGenerator();
				WorldToken[][] world = worldGenerator.generate();  
				if (world != null)
				{
					tournament.addWorld(world);
					numOfWorlds++;
				}
				else
				{
					gui.outPutError("Couldn't generate a world, please try again!");
				}
			}
			else 
			{
				gui.outPutError("world");
			}
		
	}
	
	/**
	 * Get the number of players
	 * @return number of players
	 */
	public int getNumOfPlayers()
	{
		return numOfPlayers;
	}
	
	/**
	 * Get the number of world
	 * @return number of worlds
	 */
	public int getNumOfWorlds()
	{
		return numOfWorlds;
	}
	
	public static void main(String args[])
	{
		new Simulation();
	}
	
}
