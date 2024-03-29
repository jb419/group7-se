
/**
 * A State object is a state in the brain for the instructions. It hold the type
 * of instruction it is and the next state to jump to after executing it. It has
 * many subclasses, both to take advantage of polymorphism when storing it and
 * to allow different instructions to store more data.
 * 
 * @author Owen Cox
 * @version 09/06/2012 - 4
 */
public class State
{
	private int nextState;
	private Instruction instruction;
	
	/**
	 * Constructor for objects of type State
	 * 
	 * @param nextState the next state to jump to after executing this one
	 * @param instruction the instruction type
	 */
	public State(int nextState, Instruction instruction)
	{
		this.nextState = nextState;
		this.instruction = instruction;
	}
	
	/**
	 * Returns the int of the next state to go to after this one is carried out
	 *
	 * @return the number of the state for the brain to go to next
	 */
	public int getNextState()
	{
		return nextState;
	}
	
	/**
	 * Gets the instruction to the brain represented by this state.
	 *
	 * @return the type of instruction this state represents
	 */
	public Instruction getInstruction()
	{
		return instruction;
	}
	
	/**
	 * Overrides toString to output the state in the way it looks in the raw input
	 */
	public String toString()
	{
		return instruction + " " + nextState;
	}
}
