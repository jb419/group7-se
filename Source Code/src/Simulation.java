import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Simulation class is the first class to be created. It's responsible for keeping the GUI and tournament updated.

 * 
 * @author Brett Flitter
 * @version 20/05/2012 - 1
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
				// if (findBrain())
				//{
					//CALL TO ANTBRAIN INTERPRETER TO CHECK THAT BRAIN LOCATION IS OK & BRAIN IS WELL FORMED
					tournament.addPlayer(m.group(1), m.group(2));
				//}
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
				// if (findWorld())
				//{
					//CALL TO WORLD TO CHECK THE WORLD LOCATION IS CORRECT & WORLD IS WELL FORMED
					tournament.addWorld(m.group(1));
				//}
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
