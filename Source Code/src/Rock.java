
/**
 * A Rock object represents a Rock stored in a cell
 * 
 * @author Owen Cox
 * @version 05/06/1012 - 14
 */
public class Rock extends Actor
{
	public Rock()
	{
		setStringVal("#");
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
