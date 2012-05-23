
/**
 * 
 * 
 * @author Owen Cox
 * @version 23/05/2012 - 1
 */
public class Rock extends Actor
{
	public Rock()
	{
		setStringVal("O");
	}
	
	/**
	 * Checks if the object is a rock
	 * 
	 * @return true
	 * @override Actor.isRocky()
	 */
	public boolean isRocky()
	{
		return true;
	}
}
