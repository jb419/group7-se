
/**
 * A TwoIntState is an extension of State that holds more than one int
 * it is used in any case where more than 1 int is needed. For example
 * move needs a state to jump to if it can move as well as one if it cannot.
 * 
 * @author Owen
 * @version 18 May 2012
 */
public class TwoIntState extends State
{
	private int otherInt;
	
	/**
	 * Constructor for objects of type TwoIntState
	 * 
	 * @param nextState the next state to jump to in most cases
	 * @param instruction the instruction this represents
	 * @param otherInt the other int needed by the state, the marker
	 * 				   number, other jump int etc.
	 */
	public TwoIntState(int nextState, Instruction instruction, int otherInt)
	{
		super(nextState, instruction);
		this.otherInt = otherInt;
	}
	
	/**
	 * Gets the other int stored by the state
	 *
	 * @return the other int stored by the state
	 */
	public int getOtherInt()
	{
		return otherInt;
	}
}
