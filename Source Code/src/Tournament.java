import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tournament class keeps track of the players and their statistics 
 * Keeps track of the current world be used
 * 
 * @author Brett Flitter, Owen Cox
 * @version 04/06/2012 - 4
 */
public class Tournament
{
	private ArrayList<WorldToken[][]> enteredWorlds;
	private ArrayList<Entry> enteredBrains;
	private Entry currentRed;
	private Entry currentBlack;
	private int redInt;
	private int blackInt;
	
	/**
	 * Constructor
	 */
	public Tournament()
	{	
		enteredBrains = new ArrayList<Entry>();
		enteredWorlds = new ArrayList<WorldToken[][]>();

	}
	
	/**
	 * The addPlayer() method creates an Entry object and adds the individual player's name and brain
	 * the Entry object is then added to the enteredBrains arrayList
	 * @param player the player to be added
	 * @param brain the brain to be added
	 */
	public void addPlayer(String player, String brain)
	{
		Entry e= new Entry();
		e.setPlayerName(player);
		e.setBrainLocation(brain);
		enteredBrains.add(e);
	}
	
	/**
	 * The addWorld() method adds an individual world to an array of Strings (world locations)
	 * @param worldLocation the world location as a String
	 */
	public void addWorld(WorldToken[][] world)
	{
		enteredWorlds.add(world);
	}
	
	/**
	 * The addPoints() method updates an individual player's points
	 * @param player takes the player to have its points updated
	 * @param points takes the new number of points to be stored
	 */
	public void addPoints(String player, int points)
	{
		if (player.equals("black"))
		{
			currentBlack.setPoints(points);
		}
		else
		{
			currentRed.setPoints(points);
		}
	}
	
	/**
	 * The getNextWorld method returns the next world to play on and also puts it at the back of the arraylist in case needed again
	 * @return nextWorld the next world to be played
	 */
	public WorldToken[][] getNextWorld()
	{
		// take first world (and return it) put it at the back in case world needs to be used again.
		WorldToken[][] nextWorld = enteredWorlds.get(0);
		enteredWorlds.remove(0);
		enteredWorlds.add(nextWorld);
		return nextWorld;
	}
	
	/**
	 * The hasWorld method checks if the tournament has had a world entered into it.
	 *
	 * @return a boolean representing if the tournament has any entered worlds.
	 */
	public boolean hasWorld()
	{
		return !(enteredWorlds.size() == 0);
	}
	
	
	/**
	 * The nextConstestants method generates the next contestants to play.
	 * If there is an overall draw it takes those players as the next contestants
	 * @return contestants an array of two contestants
	 */
	public String[] nextContestants()  
	{
		String[] contestants = new String[2];
		if (redInt < enteredBrains.size())
		{
			redInt++;
		}
		else
		{
			blackInt++;
			redInt = blackInt + 1;
		}
		if (blackInt != enteredBrains.size())
		{
			contestants[0] = enteredBrains.get(blackInt-1).getPlayerName();
			contestants[1] = enteredBrains.get(redInt-1).getPlayerName();
			return contestants;
		}
		else
		{
			//Check for overall draw
			if (hasMoreGames())
			{
				//it was an overall draw, getVictor replaces previous players in enteredBrains with the drawing players
				contestants[0] = enteredBrains.get(0).getPlayerName();
				contestants[1] = enteredBrains.get(1).getPlayerName();
				return contestants;
			}
			else
			{
				//There's a winner so return no one
				contestants[0] = "";
				contestants[1] = "";
				return contestants;
			}
		}
	}
	
	/**
	 * This method gets the brain location of a player
	 * 
	 * @param player the player to which the brain location will be returned for
	 * @return brain location
	 */
	public String getBrain(String player)
	{
		for(Entry e : enteredBrains)
		{
			if (e.getPlayerName().equals(player))
			{
				return e.getBrainLocation();
			}
			
		}
		return null;
	}
	
	/**
	 * the hasMoreGames() method is used to check whether there are any more games to be played
	 * @return boolean true if there is more games, false if there isn't.
	 */
	public boolean hasMoreGames()
	{
		String result = getVictor();
		if (blackInt == enteredBrains.size() && result.equals("Draw"))
		{
			return true;
		}
		else if (blackInt < enteredBrains.size())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * the getVictor() method works out and returns either the overall winner or if there are players who have drew.
	 * @return array of players
	 */
	public String getVictor()
	{
		ArrayList<Entry> sortedScores = new ArrayList<Entry>();
		sortedScores.add(enteredBrains.get(0)); // add first entry to test against
		
		for (int i = 1; i < enteredBrains.size(); i++)
		{
			if (enteredBrains.get(i).getPoints() > sortedScores.get(0).getPoints())	
			{
				sortedScores.clear(); // empty any previous entries, new highest score
				sortedScores.add(enteredBrains.get(i));
			}
			else if (enteredBrains.get(i).getPoints() == sortedScores.get(i).getPoints())
			{
				//score is equal to a score already found in sortedScores
				sortedScores.add(enteredBrains.get(i));
			}
		}
		if (sortedScores.size() > 1)
		{
			// must be a draw so wipe old players from enteredBrains and add the players that need a re-match
			enteredBrains.clear();
			for ( Entry e : sortedScores)
			{
				enteredBrains.add(e);
			}
			return "Draw";
		}
		else
		{	//return winner!
			return sortedScores.get(0).getPlayerName();
		}
		
	}
}
