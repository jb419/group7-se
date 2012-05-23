
/**
 * 
 * 
 * @author Owen Cox
 * @version 23/05/2012 - 1
 */
public class FoodPellet extends Actor
{
	public FoodPellet()
	{
		setStringVal("1"); //This representation will not be used for this, cell will handle food pellets differently.
	}
	
	/**
	 * Checks if the object is food
	 * 
	 * @return true
	 * @override Actor.isFood()
	 */
	public boolean isFood()
	{
		return true;
	}
}
