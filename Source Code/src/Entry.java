/**
 * The Entry class stores all relevant information about a players entry 
 * in a tournament
 * 
 * @author Brett Flitter
 * @version 23/05/2012 - 1
 */

public class Entry
{
	private String brainLocation;
	private String playerName;
	private int points;
	
	/**
	 * Constructor
	 */
	public Entry()
	{
		points = 0;
	}

	/**
	 * Get brain file location
	 * @return brainLocation as a string
	 */
	public String getBrainLocation()
	{
		return brainLocation;
	}

	/**
	 * Set brain file location
	 * @param brainLocation takes file location
	 */
	public void setBrainLocation(String brainLocation)
	{
		this.brainLocation = brainLocation;
	}

	/**
	 * Get Player's name
	 * @return playerName the name of the player
	 */
	public String getPlayerName()
	{
		return playerName;
	}

	/**
	 * Set player's name
	 * @param playerName takes a name
	 */
	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}

	/**
	 * Get player's points
	 * @return points the amount of points a player has scored
	 */
	public int getPoints()
	{
		return points;
	}

	/**
	 * Set the amount of points
	 * @param points takes an int as points
	 */
	public void setPoints(int points)
	{
		this.points = points;
	}

}
