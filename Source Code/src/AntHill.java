
/**
 * 
 * 
 * @author Owen Cox
 * @version 23/05/2012 - 1
 */
public class AntHill extends Actor
{
	private AntColour colour;
	
	/**
	 * Constructor for objects of type AntHill, sets up the colour of
	 * the hill
	 * 
	 * @param colour the colour of the ant hill that this actor is part of
	 */
	public AntHill(AntColour colour)
	{
		this.colour = colour;
		if(this.colour == AntColour.Red)
		{
			setStringVal("+");
		}
		else
		{
			setStringVal("-");
		}
	}
	
	/**
	 * Checks if the Actor is an ant hill of a specific colour
	 * 
	 * @param the colour to check if the ant hill is
	 * @return true if the AntHill is the specified colour, false otherwise
	 */
	public boolean isAntHill(AntColour colour)
	{
		return this.colour == colour;
	}
	
	/**
	 * Returns the colour of the AntHill
	 * 
	 * @return the colour of the ant hill.
	 */
	public AntColour getColour()
	{
		return colour;
	}
}
