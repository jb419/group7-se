import java.math.BigInteger;
import java.util.ArrayList;


/**
 * An AntBrain stores the possible states the brain can be in and is in charge of telling the
 * and what to do.
 * 
 * @author Owen Cox
 * @version 06/06/1012 - 3
 */
//Will not work until ant is completed.
public class AntBrain
{
	private State[] states;
	private int currentState;
	private Ant ant;
	BigInteger seed;
	
	public AntBrain(ArrayList<State> states, Ant a)
	{
		seed = BigInteger.valueOf(12345);//random initial seed
		for(int i = 0; i < 4; i++) //Go to s(4)
		{
			iterateSeed();
		}
		currentState = 0;
		Object[] o = states.toArray();
		this.ant = a;
		this.states = new State[o.length];
		for(int i = 0; i < o.length; i++)
		{
			this.states[i] = (State)o[i];
		}
	}
	
	/**
	 * The iterateSeed method gets s(i+1) from s(i)
	 *
	 */
	private void iterateSeed()
	{
		seed = seed.multiply(BigInteger.valueOf(22695477));
		seed = seed.add(BigInteger.valueOf(1));
	}
	
	/**
	 * The getNextInstruction method is the most commonly called method of the brain
	 * it gets the next instruction, tells the ant what to do and updates the currentState
	 * variable.
	 *
	 */
	public void getNextInstruction()
	{
		if(!(currentState < states.length))
		{
			currentState = states.length - 1;
		}
		else if(currentState < 0)
		{
			currentState = 0;
		}
		State examinedState = states[currentState];
		switch(states[currentState].getInstruction())
		{
			case Move:
				TwoIntState moveState = (TwoIntState)examinedState;
				if(ant.move())
				{
					currentState = moveState.getNextState();
				}
				else
				{
					currentState = moveState.getOtherInt();
				}
				break;
				
			case Turn:
				TurnState turnState = (TurnState)examinedState;
				ant.turn(turnState.getLeftOrRight());
				currentState = turnState.getNextState();
				break;
				
			case Pickup:
				TwoIntState pickupState = (TwoIntState)examinedState;
				if(ant.pickup())
				{
					currentState = pickupState.getNextState();
				}
				else
				{
					currentState = pickupState.getOtherInt();
				}
				break;
				
			case Drop:
				ant.drop();
				currentState = examinedState.getNextState();
				break;
				
			case Mark:
				TwoIntState markState = (TwoIntState)examinedState;
				ant.mark(markState.getOtherInt());
				currentState = markState.getNextState();
				break;
				
			case Unmark:
				TwoIntState unmarkState = (TwoIntState)examinedState;
				ant.unmark(unmarkState.getOtherInt());
				currentState = unmarkState.getNextState();
				break;
				
			case Flip:
				ThreeIntState flipState = (ThreeIntState)examinedState;
				if(flip(flipState.getThirdInt()))
				{
					currentState = flipState.getNextState();
				}
				else
				{
					currentState = flipState.getOtherInt();
				}
				break;
				
			case Sense:
				boolean b = false;
				SenseDirState sensingState = (SenseDirState)examinedState;
				if(sensingState.getCondition() == BrainCondition.Marker)
				{
					SenseDirMarkerState markerSensingState = (SenseDirMarkerState)sensingState;
					b = ant.senseMarker(markerSensingState.getMarkerNumber(), markerSensingState.getSenseDir());
				}
				else
				{
					Condition cond = translateCondition(sensingState.getCondition());
					b = ant.sense(cond, sensingState.getSenseDir());
				}
				
				if(b)
				{
					currentState = sensingState.getNextState();
				}
				else
				{
					currentState = sensingState.getOtherInt();
				}
		}
	}
	
	/**
	 * The flip method
	 *
	 * @param probability the number to flip against
	 * @return if flip has returned true or false
	 */
	public boolean flip(int probability)
	{
		//Using the customer's random int generator formula
		BigInteger x = seed.divide(BigInteger.valueOf(65536));
		x = x.mod(BigInteger.valueOf(16384));
		BigInteger result = x.mod(BigInteger.valueOf(probability));
		iterateSeed();
		boolean b;
		if(result.compareTo(BigInteger.valueOf(0)) == 0)
		{
			b = true;
		}
		else
		{
			b = false;
		}
		return b;
	}
	
	/**
	 * The translateCondition method translates from a relative brain condition
	 * eg. Friend, Foe to an absolute condition used in checking, eg RedAnt, BlackAnt
	 *
	 * @param brainCond the BrainCondition to take as input
	 * @return the absolute Condition based on the colour of the ant this brain
	 *         is assigned to.
	 */
	private Condition translateCondition(BrainCondition brainCond)
	{
		Condition c = null;
		switch(brainCond)
		{
			case Friend:
				if(ant.getColour() == AntColour.Red)
				{
					c = Condition.RedAnt;
				}
				else
				{
					c = Condition.BlackAnt;
				}
				break;
				
			case Foe:
				if(ant.getColour() == AntColour.Red)
				{
					c = Condition.BlackAnt;
				}
				else
				{
					c = Condition.RedAnt;
				}
				break;
				
			case FriendWithFood:
				if(ant.getColour() == AntColour.Red)
				{
					c = Condition.RedAntWithFood;
				}
				else
				{
					c = Condition.BlackAntWithFood;
				}
				break;
				
			case FoeWithFood:
				if(ant.getColour() == AntColour.Red)
				{
					c = Condition.BlackAntWithFood;
				}
				else
				{
					c = Condition.RedAntWithFood;
				}
				break;
				
			case Home:
				if(ant.getColour() == AntColour.Red)
				{
					c = Condition.RedHill;
				}
				else
				{
					c = Condition.BlackHill;
				}
				break;
				
			case FoeHome:
				if(ant.getColour() == AntColour.Red)
				{
					c = Condition.BlackHill;
				}
				else
				{
					c = Condition.RedHill;
				}
				break;
			
			//Marker will not need to be translated	
				
			case FoeMarker:
				if(ant.getColour() == AntColour.Red)
				{
					c = Condition.BlackMarker;
				}
				else
				{
					c = Condition.RedMarker;
				}
				break;
				
			case Food:
				c = Condition.Food;
				break;
				
			case Rock:
				c = Condition.Rock;
				break;
		}
		return c;
	}
	
	public static void main(String args[])
	{
		ArrayList<State> al = new ArrayList<State>();
		AntBrain a = new AntBrain(al, null);
		for(int i = 0; i < 100; i++)
		{
			System.out.println(a.flip(2));
		}
	}
}
