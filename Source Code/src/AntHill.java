
/**
 * 
 * 
 * @author Owen Cox, Brett Flitter 
 * @version 09/06/2012 - 2
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
			setStringVal("x"); // changed from '-' to 'x' because it was easier to see in the world when running
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
