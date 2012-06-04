
/**
 * 
 * 
 * @author Owen Cox
 * @version 04/06/2012 - 4
 */
public class Ant extends Actor
{
	private int[] position;
	private boolean alive;
	private AntBrain brain;
	private AntColour colour;
	private int resting;
	private boolean hasFood;
	private Direction direction;
	private World world;
	
	/**
	 * Constructor for objects of type Ant
	 * 
	 * @param world the world the ant belongs to
	 * @param antBrain the brain the ant has
	 * @param colour the colour of the ant
	 */
	public Ant(World world, AntColour colour)
	{
		this.world = world;
		this.colour = colour;
		
		resting = 0;
		direction = Direction.E;
		hasFood = false;
		alive = true;
		position = new int[2];
		position[0] = -1;
		position[1] = -1;
	}
	
	public void setAntBrain(AntBrain antBrain)
	{
		this.brain = antBrain;
	}
	
	/**
	 * The hasFood method checks if the ant is holding a food pellet
	 *
	 * @return true if the ant has food, false otherwise
	 */
	public boolean hasFood()
	{
		return hasFood;
	}
	
	/**
	 * The setPos method sets the ant's position
	 *
	 * @param pos the position to set the ant to have
	 */
	public void setPos(int[] pos)
	{
		this.position = pos;
	}
	
	/**
	 * The getPos method gets the ant's position
	 *
	 * @return the ant's position
	 */
	public int[] getPos()
	{
		return position;
	}
	
	/**
	 * The isAlive method gets if the ant is alive
	 *
	 * @return if the ant is alive
	 */
	public boolean isAlive()
	{
		return alive;
	}
	
	/**
	 * The step method tells the brain to get the next instruction, the brain
	 * then tells the ant what to do.
	 *
	 */
	public void step()
	{
		if(resting == 0)
		{
			brain.getNextInstruction();
		}
		else
		{
			resting --;
		}
	}
	
	/**
	 * The move method moves the ant foward 1
	 *
	 * @return if the ant could move successfully
	 */
	public boolean move()
	{
		boolean b;
		int[] desiredPos = world.adjacentCell(position, direction);
		if(world.moveableCell(desiredPos))
		{
			b = true;
			world.clearAntAt(position, this);
			world.setAntAt(desiredPos, this); //This method updates the internal position var
			combatCheck();
			resting = 14;
		}
		else
		{
			b = false;
		}
		return b;
	}
	
	/**
	 * The mark method marks the cell the ant is in with a specified marker
	 * number
	 *
	 * @param markId the number of marker to set at the ant's position
	 */
	public void mark(int markId)
	{
		world.mark(position, colour, markId);
	}
	
	/**
	 * The unmark method removes a mark from the cell the ant is in
	 *
	 * @param markId the number of marker to remove from the ant's position
	 */
	public void unmark(int markId)
	{
		world.unmark(position, colour, markId);
	}
	
	
	/**
	 * The turn method turns the ant left or right
	 * 
	 * @param lr whether to turn the ant left or right
	 */
	public void turn(LeftOrRight lr)
	{
		Direction[] directions = Direction.values();
		int i = direction.ordinal();
		switch(lr)
		{
			case Left:
				i--;
				if(i < 0);
				{
					i = directions.length - 1; //5, but this allows for later extension
				}
				
				break;
			case Right:
				i = (i + 1) % directions.length; //uses % to loop from 5(5 + 1 = 6) to 0(6 % 6 = 0);
				break;
		}
		direction = directions[i];
	}
	
	/**
	 * The pickup method tries to pick up a food pellet from the ant's current
	 * cell
	 *
	 * @return if the ant succeeded at picking up a food pellet
	 */
	public boolean pickup()
	{
		boolean b;
		if(world.cellMatches(position, Condition.Food))
		{
			b = true;
			world.removeFood(position);
			this.hasFood = true;
		}
		else
		{
			b = false;
		}
		return b;
	}
	
	/**
	 * The drop method makes the ant drop a food pellet at its current location
	 * if it has a food pellet
	 *
	 */
	public void drop()
	{
		if(hasFood)
		{
			hasFood = false;
			world.addFood(position);
		}
	}
	
	/**
	 * The getColour method returns the colour of this ant
	 *
	 * @return the colour of this ant
	 */
	public AntColour getColour()
	{
		return colour;
	}
	
	/**
	 * The otherColour method returns the opposite ant colour from the one this
	 * ant is
	 *
	 * @return Black if this ant is Red, Red if this ant is Black
	 */
	public AntColour otherColour()
	{
		AntColour c;
		if(colour == AntColour.Red)
		{
			c = AntColour.Black;
		}
		else
		{
			c = AntColour.Red;
		}
		return c;
	}
	
	/**
	 * The isAnt method checks if the ant is an ant of a given colour, overrides
	 * the method in Actor that is used for checking.
	 * 
	 * @return if the ant is an ant of the specified colour
	 */
	public boolean isAnt(AntColour c)
	{
		return colour == c;
	}
	
	/**
	 * The sense method makes the ant check a nearby cell for a specified condition
	 *
	 * @param cond the condition to check the cell for
	 * @param dir the direction to sense in
	 * @return if the condition holds up for the cell
	 */
	public boolean sense(Condition cond, SensingDirection dir)
	{
		int[] senseCell = {0, 0};
		switch(dir)
		{
			case Here:
				senseCell = position;
				break;
			case Ahead:
				senseCell = world.adjacentCell(position, direction);
				break;
			case LeftAhead:
				turn(LeftOrRight.Left);
				senseCell = world.adjacentCell(position, direction);
				turn(LeftOrRight.Right);
				break;
			case RightAhead:
				turn(LeftOrRight.Right);
				senseCell = world.adjacentCell(position, direction);
				turn(LeftOrRight.Left);
				break;
		}
		boolean b = world.cellMatches(senseCell, cond);
		return b;
	}
	
	/**
	 * The senseMarker method is a specific sense method for markers
	 *
	 * @param markerNumber the number of the marker to look for
	 * @param dir the direction to look for the marker in
	 * @return if the marker is present at the cell
	 */
	public boolean senseMarker(int markerNumber, SensingDirection dir)
	{
		int[] senseCell = {0, 0};
		switch(dir)
		{
			case Here:
				senseCell = position;
				break;
			case Ahead:
				senseCell = world.adjacentCell(position, direction);
				break;
			case LeftAhead:
				turn(LeftOrRight.Left);
				senseCell = world.adjacentCell(position, direction);
				turn(LeftOrRight.Right);
				break;
			case RightAhead:
				turn(LeftOrRight.Right);
				senseCell = world.adjacentCell(position, direction);
				turn(LeftOrRight.Left);
				break;
		}
		return world.marked(senseCell, markerNumber, colour);
	}
	
	/**
	 * The combatCheck method is called after the ant moves, it checks
	 * if the ant is surrounded and then if any ants around this ant are
	 * surrounded and kills them if they are
	 *
	 */
	public void combatCheck()
	{
		Ant[] adjacentAnts = world.adjacentAnts(position);
		if(isSurrounded())
		{
			die();
		}
		for(Ant a: adjacentAnts)
		{
			if(a.isSurrounded())
			{
				a.die();
			}
		}
	}
	
	/**
	 * The isSurrounded method checks if the ant is surrounded, it is used
	 * in combatCheck
	 *
	 * @return if the ant is surrounded
	 */
	public boolean isSurrounded()
	{
		Ant[] adjacentAnts = world.adjacentAnts(position);
		return adjacentAnts.length >= 5;
	}
	
	/**
	 * The die method kills the ant, removes it from the world and spawns the
	 * required number of food pellets at its location
	 *
	 */
	public void die()
	{
		alive = false;
		drop();
		for(int i = 0; i < 3; i++)
		{
			world.addFood(position);
		}
		world.clearAntAt(position, this);
	}
}
