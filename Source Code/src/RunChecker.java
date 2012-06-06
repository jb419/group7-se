
/**
 * A Runchecker is used to check if the Simulation is running, it is Thread safe.
 * 
 * @author Owen Cox
 * @version 06/06/2012 - 1
 */
public class RunChecker
{
	private enum State { Running, Stopped; }
	
	private static State state;
	
	public static synchronized void init()
	{
		state = State.Stopped;
	}
	
	/**
	 * The run method tells the RunChecker it is time to run
	 *
	 */
	public static synchronized void run()
	{
		state = State.Running;
	}
	
	/**
	 * The isRunning method checks if the Simulator should be running
	 *
	 * @return if the simulator should be running
	 */
	public static boolean isRunning()
	{
		return state == State.Running;
	}
}
