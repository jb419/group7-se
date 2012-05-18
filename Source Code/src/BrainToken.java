
/**
 * A BrainToken is a token that can be passed from the AntBrainLoader to the
 * AntBrainInterpreter.
 * 
 * @author Owen Cox
 * @version 18/02/2012 - 2
 */
public class BrainToken
{
	private boolean isInteger;
	private String actualValue;
	
	/**
	 * Constructor for objects of type BrainToken, sets up all variables
	 * 
	 * @param isInteger if the token represents an integer or letters
	 * @param ActualValue the actual value of the token as a String
	 */
	public BrainToken(boolean isInteger, String actualValue)
	{
		this.isInteger = isInteger;
		this.actualValue = actualValue;
	}
	
	/**
	 * Accessor method for the isInteger variable
	 *
	 * @return if the token represents an integer or a keyword
	 */
	public boolean getIsInteger()
	{
		return isInteger;
	}
	
	/**
	 * Accessor method for the actual value of the token
	 *
	 * @return the String that was read from the brain file
	 */
	public String getActualValue()
	{
		return actualValue;
	}
}
