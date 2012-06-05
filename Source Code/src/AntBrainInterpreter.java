import java.util.ArrayList;

/**
 * An AntBrainInterpreter takes the BrainTokens read in by the AntBrainLoader and translates them
 * into states for the AntBrain to be assigned.
 * 
 * @author Owen Cox
 * @version 05/06/1012 - 14
 */
public class AntBrainInterpreter
{
	class BrainException extends Exception
	{
		public BrainException(String expected, String found, int stateNumber)
		{
			super("Expected " + expected + ", found: " + found + " (state " + stateNumber + ")");
		}
	}

	private ArrayList<State> states;
	private AntBrainLoader loader;
	private String brainLocation;
	private BrainToken nextToken;

	/**
	 * Constructor for objects of type AntBrainInterpreter sets up the brain loader and the storage for
	 * the States, then creates them.
	 * 
	 * @param brainLocation
	 * @throws BrainException throw by creating the states, and then rethrown by this, used to check for syntax errors 
	 *                        in the brain by classes that use this.
	 */
	public AntBrainInterpreter(String brainLocation) throws BrainException
	{
		this.brainLocation = brainLocation;

		states = new ArrayList<State>();
		loader = new AntBrainLoader(brainLocation);

		nextToken = loader.getNextToken();

		createStates();
	}

	/**
	 * The getStates method gets the list of states the interpreter has found.
	 *
	 * @return the arraylist of states the interpreter has found
	 */
	public ArrayList<State> getStates()
	{
		return states;
	}

	/**
	 * The createStates method creates the states and stores them in an
	 * ArrayList.
	 * @throws BrainException an exception that signals something is wrong with the brain, used to check syntax in when the brain is first added
	 *
	 */
	public void createStates() throws BrainException
	{
		while(!nextToken.getActualValue().equals("%EOF%"))
		{
			if(!nextToken.getIsInteger()) //first token of a new state should NOT be an integer
			{
				//deal with what the token's value actually is and check the next tokens contain the necessary values.
				String tokenVal = nextToken.getActualValue();
				nextToken = loader.getNextToken();

				//Dealing with turns, has 1 int and a LeftOrRight enum.
				if(tokenVal.equals("Turn"))
				{
					LeftOrRight lr = LeftOrRight.Left;
					if(nextToken.getActualValue().equals("Left"))
					{

					}
					else if(nextToken.getActualValue().equals("Right"))
					{
						lr = LeftOrRight.Right;
					}
					else
					{
						throw new BrainException("'Left' or 'Right'", nextToken.getActualValue(), states.size());
					}
					nextToken = loader.getNextToken();
					int next = -1;
					if(nextToken.getIsInteger())
					{
						next = Integer.parseInt(nextToken.getActualValue());
					}
					else
					{
						throw new BrainException("state number", nextToken.getActualValue(), states.size());
					}
					states.add(new TurnState(next, Instruction.Turn, lr));
				}

				//Moving, has 2 ints, 1 for success, the other for failure
				else if(tokenVal.equals("Move"))
				{
					int stateTrue = -1;
					int stateFalse = -1;
					if(nextToken.getIsInteger())
					{
						stateTrue = Integer.parseInt(nextToken.getActualValue());
						nextToken = loader.getNextToken();
					}
					else
					{
						throw new BrainException("state number", nextToken.getActualValue(), states.size());
					}

					if(nextToken.getIsInteger())
					{
						stateFalse = Integer.parseInt(nextToken.getActualValue());
					}
					else
					{
						throw new BrainException("state number", nextToken.getActualValue(), states.size());
					}
					states.add(new TwoIntState(stateTrue, Instruction.Move, stateFalse));
				}

				//Flipping, has 3 ints, 1 for the probability of the flip and 1 for each direction the flip is going in.
				else if(tokenVal.equals("Flip"))
				{
					int stateTrue = -1;
					int stateFalse = -1;
					int flipProbability = -1;

					if(nextToken.getIsInteger())
					{
						flipProbability = Integer.parseInt(nextToken.getActualValue());
						nextToken = loader.getNextToken();
					}
					else
					{
						throw new BrainException("flip probability", nextToken.getActualValue(), states.size());
					}

					if(nextToken.getIsInteger())
					{
						stateTrue = Integer.parseInt(nextToken.getActualValue());
						nextToken = loader.getNextToken();
					}
					else
					{
						throw new BrainException("state number", nextToken.getActualValue(), states.size());
					}

					if(nextToken.getIsInteger())
					{
						stateFalse = Integer.parseInt(nextToken.getActualValue());
					}
					else
					{
						throw new BrainException("state number", nextToken.getActualValue(), states.size());
					}
					states.add(new ThreeIntState(stateTrue, Instruction.Move, stateFalse, flipProbability));
				}

				//Sensing, takes 2 ints, a direction to sense in and a condition.
				else if(tokenVal.equals("Sense"))
				{
					SensingDirection senseDirection = SensingDirection.Here;
					int stateTrue = -1;
					int stateFalse = -1;
					BrainCondition cond = BrainCondition.Home;
					int markerConditionCheck = -1;
					//Sensing direction
					String senseLoc = nextToken.getActualValue();
					nextToken = loader.getNextToken();
					if(senseLoc.equals("Here"))
					{
						senseDirection = SensingDirection.Here; //Already has this value, but this is put in to be less confusing
					}
					else if(senseLoc.equals("Ahead"))
					{
						senseDirection = SensingDirection.Ahead;
					}
					else if(senseLoc.equals("RightAhead"))
					{
						senseDirection = SensingDirection.RightAhead;
					}
					else if(senseLoc.equals("LeftAhead"))
					{
						senseDirection = SensingDirection.LeftAhead;
					}
					else
					{
						throw new BrainException("Either Here, Ahead, RightAhead or LeftAhead", senseLoc, states.size());
					}
					//End of dealing with sensing direction. The next part is easier, just read in two ints

					if(nextToken.getIsInteger())
					{
						stateTrue = Integer.parseInt(nextToken.getActualValue());
						nextToken = loader.getNextToken();
					}
					else
					{
						throw new BrainException("state number", nextToken.getActualValue(), states.size());
					}

					if(nextToken.getIsInteger())
					{
						stateFalse = Integer.parseInt(nextToken.getActualValue());
						nextToken = loader.getNextToken();
					}
					else
					{
						throw new BrainException("state number", nextToken.getActualValue(), states.size());
					}
					//Now needs to read in the condition

					String condition = nextToken.getActualValue();
					if(condition.equals("Friend"))
					{
						cond = BrainCondition.Friend;
					}
					else if(condition.equals("Foe"))
					{
						cond = BrainCondition.Foe;
					}
					else if(condition.equals("FriendWithFood"))
					{
						cond = BrainCondition.FriendWithFood;
					}
					else if(condition.equals("FoeWithFood"))
					{
						cond = BrainCondition.FoeWithFood;
					}
					else if(condition.equals("Food"))
					{
						cond = BrainCondition.Food;
					}
					else if(condition.equals("Rock"))
					{
						cond = BrainCondition.Rock;
					}
					else if(condition.equals("Marker"))
					{
						cond = BrainCondition.Marker;
						nextToken = loader.getNextToken();
						if(nextToken.getIsInteger())
						{
							markerConditionCheck = Integer.parseInt(nextToken.getActualValue());
						}
						else
						{
							throw new BrainException("marker number", nextToken.getActualValue(), states.size());
						}
					}
					else if(condition.equals("FoeMarker"))
					{
						cond = BrainCondition.FoeMarker;
					}
					else if(condition.equals("Home"))
					{
						cond = BrainCondition.Home;
					}
					else if(condition.equals("FoeHome"))
					{
						cond = BrainCondition.FoeHome;
					}
					else
					{
						throw new BrainException("condition", condition, states.size());
					}

					if(markerConditionCheck != -1)
					{
						states.add(new SenseDirMarkerState(stateTrue, Instruction.Sense, stateFalse, cond, senseDirection, markerConditionCheck));
					}
					else
					{
						states.add(new SenseDirState(stateTrue, Instruction.Sense, stateFalse, cond, senseDirection));
					}
				}

				//Mark takes 2 ints, a state to move to and a number to mark
				else if(tokenVal.equals("Mark") || tokenVal.equals("Unmark"))
				{
					Instruction inst = Instruction.Mark;
					int markerNum = -1;
					int state = -1;
					if(tokenVal.equals("Mark"))
					{
						inst = Instruction.Mark;
					}
					else
					{
						inst = Instruction.Unmark;
					}

					if(nextToken.getIsInteger())
					{
						markerNum= Integer.parseInt(nextToken.getActualValue());
						nextToken = loader.getNextToken();
						if(markerNum > 6)
						{
							throw new BrainException("number 1-6 (marker number)", nextToken.getActualValue(), states.size());
						}
					}
					else
					{
						throw new BrainException("marker number", nextToken.getActualValue(), states.size());
					}

					if(nextToken.getIsInteger())
					{
						state = Integer.parseInt(nextToken.getActualValue());
					}
					else
					{
						throw new BrainException("state number", nextToken.getActualValue(), states.size());
					}

					states.add(new TwoIntState(state, inst, markerNum));
				}

				//Picking up food, similar to move, 2 ints, 1 for success, 1 for failure
				else if(tokenVal.equals("PickUp"))
				{
					int stateTrue = -1;
					int stateFalse = -1;
					if(nextToken.getIsInteger())
					{
						stateTrue = Integer.parseInt(nextToken.getActualValue());
						nextToken = loader.getNextToken();
					}
					else
					{
						throw new BrainException("state number", nextToken.getActualValue(), states.size());
					}

					if(nextToken.getIsInteger())
					{
						stateFalse = Integer.parseInt(nextToken.getActualValue());
					}
					else
					{
						throw new BrainException("state number", nextToken.getActualValue(), states.size());
					}
					states.add(new TwoIntState(stateTrue, Instruction.Pickup, stateFalse));
				}

				//Dropping food, only 1 int, will always go to that state upon completion
				else if(tokenVal.equals("Drop"))
				{
					int next = -1;
					if(nextToken.getIsInteger())
					{
						next = Integer.parseInt(nextToken.getActualValue());
					}
					states.add(new State(next, Instruction.Drop));
				}
				else
				{
					throw new BrainException("keyword", tokenVal, states.size());
				}
				//Once we've found an acceptable new state, go to the next token.
				nextToken = loader.getNextToken();
			}
			else
			{
				throw new BrainException("keyword at start of new state", nextToken.getActualValue(), states.size() + 1);
			}
		}
	}
	
	public static void main(String args[])
	{
		try
		{
			AntBrainInterpreter a = new AntBrainInterpreter("C:\\Users\\Owen\\ant.ant");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
