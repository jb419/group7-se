
/**
 * A TurnState is an extension of state that also stores a LeftOrRight
 * enum to signify if it is a left or a right turn.
 * 
 * @author Owen Cox
 * @version 09/06/2012 - 4
 */
public class TurnState extends State
{
	private LeftOrRight lr;
	
	/**
	 * Constructor for objects of type TurnState
	 * 
	 * @param nextState the number of the next state to go to
	 * @param instruction the type of the instruction (should always be Turn)
	 * @param lr whether the State is a left turn or a right turn
	 */
	public TurnState(int nextState, Instruction instruction, LeftOrRight lr)
	{
		super(nextState, instruction);
		this.lr = lr;
	}
	
	/**
	 * The getLeftOrRight method gets a LeftOrRight value to specify if this is a
	 * left turn state or a right turn state.
	 *
	 * @return either LeftOrRight.Left or LeftOrRight.Right
	 */
	public LeftOrRight getLeftOrRight()
	{
		return lr;
	}
	
	/**
	 * Overrides toString to output the state in the way it looks in the raw input
	 */
	public String toString()
	{
		return getInstruction() + " " + getLeftOrRight() + " " + getNextState();
	}
}
