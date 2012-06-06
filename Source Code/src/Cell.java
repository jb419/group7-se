import java.util.ArrayList;


/**
 * A Cell knows what actors are in it and can manipulate those actors
 * 
 * @author Owen Cox
 * @version 05/06/1012 - 14
 */
public class Cell
{
	private ArrayList<Actor> contains; //The actors currently in this cell
	private boolean[] redMarkers; //The markers for the red ants.
	private boolean[] blackMarkers; //The markers for the black ants.
	
	/**
	 * Constructor for objects of type Cell, sets up the various lists and
	 * sets all markers to false.
	 *
	 */
	public Cell()
	{
		contains = new ArrayList<Actor>();
		blackMarkers = new boolean[6];
		redMarkers = new boolean[6];
		
		//Initialised cells should not be marked initially.
		for(int i = 0; i < blackMarkers.length; i++)
		{
			blackMarkers[i] = false;
			redMarkers[i] = false;
		}
	}
	
	//////////////////////////////////////
	//Methods to add Actors to the cell //
	//////////////////////////////////////
	/**
	 * Adds a given ant to the cell
	 *
	 * @param a the Ant to add to the cell
	 */
	public void addAnt(Ant a)
	{
		contains.add(a);
	}
	
	/**
	 * Adds a FoodPellet to the cell
	 *
	 */
	public void addFood()
	{
		contains.add(new FoodPellet());
	}
	
	/**
	 * Adds an anthill of the specified colour to the cell
	 * 
	 * @param c the colour of anthill to add to the cell
	 */
	public void addAntHill(AntColour c)
	{
		contains.add(new AntHill(c));
	}
	
	/**
	 * Adds a rock to the cell
	 * 
	 */
	public void addRock()
	{
		contains.add(new Rock());
	}

	//////////////////////////////////////////
	//Methods to remove actors from the cell//
	//////////////////////////////////////////
	/**
	 * Removes a given ant from the cell
	 *
	 * @param a the ant to remove from the cell
	 */
	public void removeAnt(Ant a)
	{
		contains.remove(a); //This uses == equality which is ideal for this purpose
	}

	/**
	 * Removes a food pellet from the cell
	 *
	 */
	public void removeFood()
	{
		int i = 0;
		while(i < contains.size() && !contains.get(i).isFood())
		{
			i++;
		}
		//Loop will exit when either i reaches the end of the arrayList or food is found

		if(i < contains.size()) //Then food has been found in the list
		{
			contains.remove(i);
		}
	}

	//There is no need for removeRock and removeAntHill methods here so they were removed. Once a rock or anthill has been placed it should never be removed

	//Methods dealing with checking the contents or properties of the cell

	/**
	 * The checkCondition method checks to see if the cell fulfills a given condition
	 *
	 * @param c the condition to check the cell for
	 * @return if the condition is true or not
	 */
	public boolean checkCondition(Condition c)
	{
		boolean b = false;
		int i = 0;

		if(contains.size() > 0)
		{
			Actor a = contains.get(i);

			/////////////////////////////Checking for conditions related to ants/////////////////////////////
			if(c == Condition.BlackAnt || c == Condition.RedAnt|| c == Condition.BlackAntWithFood || c == Condition.RedAntWithFood)
			{
				while((!a.isAnt(AntColour.Red) && !a.isAnt(AntColour.Black)) && i < contains.size())
				{
					i ++;
				}
				if(i < contains.size())
				{
					a = contains.get(i);
					switch(c)
					{
						case BlackAnt:
							b = a.isAnt(AntColour.Black); //Check if it an ant of the correct colour
							break;
						case RedAnt:
							b = a.isAnt(AntColour.Red);
							break;
						case BlackAntWithFood:
							if(a.isAnt(AntColour.Black)) //Need to check if the ant is the correct colour before checking if it has food
							{
								Ant ant = (Ant)a;
								b = ant.hasFood();
							}
							else
							{
								b = false;
							}
							break;
						case RedAntWithFood:
							if(a.isAnt(AntColour.Red))
							{
								Ant ant = (Ant)a;
								b = ant.hasFood();
							}
							else
							{
								b = false;
							}
							break;
					}
				}
				else
				{
					b = false;
				}
			}
			/////////////////////////////Dealing with conditions related to the ant hills./////////////////////////////
			else if(c == Condition.RedHill || c == Condition.BlackHill)
			{
				while((!a.isAntHill(AntColour.Red) && !a.isAntHill(AntColour.Black)) && i < contains.size())
				{
					i ++;
				}
				if(i < contains.size())
				{
					a = contains.get(i);
					switch(c)
					{
						case RedHill:
							b = a.isAntHill(AntColour.Red);
							break;
						case BlackHill:
							b = a.isAntHill(AntColour.Black);
							break;
					}
				}
				else
				{
					b = false;
				}
			}
			/////////////////////////////Dealing with other conditions/////////////////////////////
			else if(c == Condition.Rock)
			{
				while(!a.isRocky() && i < contains.size())
				{
					i++;
				}
				b = i < contains.size(); //True is a rock has been found, false if i has reached the end of the contains list, these are the only reasons the loop will terminate
			}
			else if(c == Condition.Food)
			{
				while(!a.isFood() && i < contains.size())
				{
					i++;
				}
				b = i < contains.size(); //True if food has been found, false if i has reached the end of the contains list
			}
			//Dealing with checking for the existence of any marker, these are used for the foe markers which only check if a marker exists, not a specific number
			else if(c == Condition.RedMarker)
			{
				for(int j = 0; j < redMarkers.length; j++)
				{
					if(redMarkers[j])
					{
						b = true;
					}
				}
			}
			else if(c == Condition.BlackMarker)
			{
				for(int j = 0; j < blackMarkers.length; j++)
				{
					if(blackMarkers[j])
					{
						b = true;
					}
				}
			}
		}
		else
		{
			b = false;
		}
		return b;
	}
	
	/**
	 * The isMarked method checks if the cell is marked with a specific marker of a specific ant colour
	 *
	 * @param c the colour of marker to check
	 * @param markerType the number of the marker to check
	 * @return
	 */
	public boolean isMarked(AntColour c, int markerType)
	{
		boolean b = false;
		switch(c)
		{
			case Black:
				b = blackMarkers[markerType]; //- 1 since input will be 1 - 6 and array looks at 0 to 5
				break;
			case Red:
				b = redMarkers[markerType];
				break;
		}
		return b;
	}
	
	/**
	 * The mark method marks the cell with a specific marker type of a specific
	 * ant colour
	 *
	 * @param markerType the type of marker to mark the cell with
	 * @param c the colour of the marking
	 */
	public void mark(int markerType, AntColour c)
	{
		switch(c)
		{
			case Black:
				blackMarkers[markerType] = true; //Looks at markerType - 1 so it is looking at 0 - 5 not 1 - 6
				break;
			case Red:
				redMarkers[markerType] = true;
				break;
		}
	}
	
	/**
	 * The unmark method is the opposite of the mark method
	 *
	 * @param markerType the type of marker to unmark the cell with
	 * @param c the colour of the marking
	 * @see mark(int markerType, AntColour c)
	 */
	public void unmark(int markerType, AntColour c)
	{
		switch(c)
		{
			case Black:
				blackMarkers[markerType] = false;
				break;
			case Red:
				redMarkers[markerType] = false;
				break;
		}
	}
	
	/**
	 * Returns a String representation of the cell that is used by the GUI when
	 * updating.
	 */
	public String toString()
	{
		String emptyCellString = ".";//This should be the String to return if the cell is empty, this is used for checking later
		String returnString = emptyCellString;
		int numFoodPellets = 0;
		for(int i = 0; i < contains.size(); i++)
		{
			Actor a = contains.get(i);
			//Dealing with the contents of the cell. Most of these cannot overlap so it will just
			//base the string to return on the first one found in the contains ArrayList.
			//Ants will always be given priority with drawing.
			if(a.isRocky() && returnString.equals(emptyCellString))
			{
				returnString = a.toString();
			}
			else if(a.isFood())
			{
				numFoodPellets++;
			}
			else if(a.isAntHill(AntColour.Red) && returnString.equals(emptyCellString))
			{
				returnString = a.toString();
			}
			else if(a.isAntHill(AntColour.Black) && returnString.equals(emptyCellString))
			{
				returnString = a.toString();
			}
			if(a.isAnt(AntColour.Red))
			{
				returnString = a.toString();
			}
			else if(a.isAnt(AntColour.Black))
			{
				returnString = a.toString();
			}
		}
		//Need to now check if any food is in the cell.
		if(numFoodPellets > 0 && returnString.equals(emptyCellString))
		{
			returnString = "" + numFoodPellets;
		}
		return returnString;
	}
	
	/**
	 * Finds the amount of food in this cell.
	 */
	public int calculateFoodAmount()
	{
		int score = 0;
		for(int i = 0; i < contains.size(); i++)
		{
			if(contains.get(i).isFood())
			{
				score++;
			}
		}
		return score;
	}
	
	/**
	 * The getAnt method returns the ant in the cell
	 *
	 * @return the ant in the cell
	 */
	public Ant getAnt()
	{
		int i = 0;
		Actor a = contains.get(i);
		while(!a.isAnt(AntColour.Red) && !a.isAnt(AntColour.Black) && i < contains.size())
		{
			i++;
			a = contains.get(i);
		}
		return (Ant)a;
	}
}
