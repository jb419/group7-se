
/**
 * SenseDirMarkerState is a SenseDirState with an additional variable to
 * store what number of marker it should be looking for.
 * 
 * @author Owen Cox
 * @version 09/06/2012 - 4
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
	
	/**
	 * The getMarkerNumber method gets the marker number to check when sensing.
	 *
	 * @return the marker that should be checked
	 */
	public int getMarkerNumber()
	{
		return markerNumber;
	}
	
	/**
	 * Overrides toString to output the state in the way it looks in the raw input
	 */
	public String toString()
	{
		return super.toString() + " " + getMarkerNumber();
	}
}
