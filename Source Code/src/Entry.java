
public class Entry
{
	private String brainLocation;
	private String playerName;
	private int points;
	
	public Entry()
	{
		points = 0;
	}

	public String getBrainLocation()
	{
		return brainLocation;
	}

	public void setBrainLocation(String brainLocation)
	{
		this.brainLocation = brainLocation;
	}

	public String getPlayerName()
	{
		return playerName;
	}

	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}

	public int getPoints()
	{
		return points;
	}

	public void setPoints(int points)
	{
		this.points = points;
	}
	
	
}
