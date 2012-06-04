/**
 * Creates World Tokens 
 * 
 * @author Brett Flitter
 * @version 18/06/2012 - 1
 */
public class WorldToken {

	private WorldTokenType type;
	
	/**
	 * Constructor - on creation sets it self to a WorldTokenType
	 * @param type the type of WorldTokenType
	 */
	public WorldToken(WorldTokenType type)
	{
		this.type = type;
	}
	
	/**
	 * Gets the type
	 * @return type
	 */
	public WorldTokenType getType()
	{
		return type;
	}
	
}
