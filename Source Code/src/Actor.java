
/**
 * An Actor can be stored in a cell in the World. It is the superclass for all
 * objects in the game world.
 * 
 * @author Owen Cox
 * @version 23/05/2012 - 1
 */
public abstract class Actor
{
	private String stringVal;
	
	//All methods will be overriden in the appropriate subclass. Designed to make checking type
	//simpler than just comparing classes.
	
	/**
	 * Checks if the Actor is a rock, overridden in the Rock subclass
	 *
	 * @return false
	 */
	public boolean isRocky()
	{
		return false;
	}
	
	/**
	 * Checks if the Actor is an Ant of a specified colour, overridden in the Ant subclass
	 *
	 * @return false
	 */
	public boolean isAnt(AntColour c)
	{
		return false;
	}
	
	/**
	 * Checks if the Actor is an ant hill of the specified colour, overridden in the AntHill subclass
	 *
	 * @return false
	 */
	public boolean isAntHill(AntColour c)
	{
		return false;
	}
	
	/**
	 * Checks if the Actor is food, overridden in the FoodPellet subclass
	 *
	 * @return false
	 */
	public boolean isFood()
	{
		return false;
	}
	
	
	/**
	 * Returns a String representation of the Actor, the return value is changed in
	 * all subclasses within their constructor.
	 */
	public String toString()
	{
		return stringVal;
	}

	/**
	 * Sets the stringVal to the value provided
	 * 
	 * @param stringVal the stringVal to set
	 */
	public void setStringVal(String stringVal)
	{
		this.stringVal = stringVal;
	}
}
