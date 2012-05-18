
/**
 * SenseDirMarkerState is a SenseDirState with an additional variable to
 * store what number of marker it should be looking for.
 * 
 * @author Owen Cox
 * @version 18/05/2012 - 2
 */
public class SenseDirMarkerState extends SenseDirState
{
	private int markerNumber;
	
	/**
	 * 
	 * Constructor for objects of type SenseDirMarkerState sets up the various variables
	 * 
	 * @param nextState the state to go to if the cell checked fills the condition checked for
	 * @param instruction the instruction type, this will always be Sense
	 * @param otherInt the state to go to if the cell checked does not fill the condition checked for
	 * @param condition the condition to check the cell for
	 * @param senseDir the direction to sense in
	 * @param markerNumber the number of the marker to check for
	 */
	public SenseDirMarkerState(int nextState, Instruction instruction, int otherInt, BrainCondition condition, SensingDirection senseDir, int markerNumber)
	{
		super(nextState, instruction, otherInt, condition, senseDir);
		this.markerNumber = markerNumber;
	}
}
