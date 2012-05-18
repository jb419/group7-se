
/**
 * A SenseDirState extends TwoIntState to include a condition
 * and a direction to sense in. It will be used solely by the
 * SenseDir instruction.
 * 
 * @author Owen Cox
 * @version 18/05/2012 - 1
 */
public class SenseDirState extends TwoIntState
{
	private BrainCondition condition;
	private SensingDirection senseDir;
	
	/**
	 * Constructor for objects of type SenseDirState
	 * 
	 * @param nextState the nextState to go to if the condition is true
	 * @param instruction the instruction type. Should always be Sense.
	 * @param otherInt the state to go to if the condition is false
	 * @param condition the condition to check on the given cell
	 * @param senseDir which cell to check
	 */
	public SenseDirState(int nextState, Instruction instruction, int otherInt, BrainCondition condition, SensingDirection senseDir)
	{
		super(nextState, instruction, otherInt);
		this.condition = condition;
		this.senseDir = senseDir;
	}
	
	/**
	 * Gets the condition to check the cell for as a BrainCondition
	 * this will later need to be interpreted by the brain to take it
	 * from a relative Friend or Foe to an absolute Black or Red.
	 *
	 * @return the condition to check
	 */
	public BrainCondition getCondition()
	{
		return condition;
	}
	
	/**
	 * Gets the direction to sense in
	 *
	 * @return the direction to sense in as a SensingDirection enum
	 */
	public SensingDirection getSenseDir()
	{
		return senseDir;
	}
}
