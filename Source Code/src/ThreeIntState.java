
/**
 * ThreeIntState is an extension of TwoIntState that takes an
 * additional int, it is used for the flip instruction.
 * 
 * @author Owen Cox
 * @version 09/06/2012 - 4
 */
public class ThreeIntState extends TwoIntState
{
	private int thirdInt;
	
	/**
	 * Constructor for objects of type ThreeIntState, sets up the int and
	 * passes everything necessary to the previous states.
	 * 
	 * @param thirdInt the third int stored by this state
	 */
	public ThreeIntState(int nextState, Instruction instruction, int otherInt, int thirdInt)
	{
		super(nextState, instruction, otherInt);
		this.thirdInt = thirdInt;
	}
	
	/**
	 * Accessor method for the third int stored by this state
	 *
	 * @return the third int
	 */
	public int getThirdInt()
	{
		return thirdInt;
	}
	
	/**
	 * Overrides toString to output the state in the way it looks in the raw input
	 * 
	 * @return the State 
	 */
	public String toString()
	{
		return getInstruction() + " " + getThirdInt() + " " + getNextState() + " " + getOtherInt();
	}
}
