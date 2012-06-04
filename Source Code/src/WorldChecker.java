import java.util.LinkedList;
import java.util.Queue;

/**
 * This class takes a world all ready made into Tokens and checks to see if its syntactically correct
 * 
 * @author Brett Flitter
 * @version 04/06/2012 - 2
 *
 */
public class WorldChecker {
	
	/**
	 * This method checks that the world is syntactically correct
	 * 
	 * @param currentWorld the world to be checked
	 * @return boolean true if world is correct, false otherwise
	 */
	public boolean checkWorld(WorldToken[][] currentWorld)
	{
		if (!checkOuterWalls(currentWorld))
		{
			//System.out.println("walls Not correct!");
			return false;
		}
		else if(!checkForRocks(currentWorld))
		{
			//System.out.println("rocks Not correct!");
			return false;
		}
		else if (!checkForFood(currentWorld))
		{
			//System.out.println("Food Not correct!");
			return false;
		}
		else if (!checkForHills(currentWorld))
		{
			//System.out.println("Hill not correct!");
			return false;
		}
		else if(!checkForEmptyCellBetweenElements(currentWorld))
		{
			//System.out.println("cell between not correct!");
			return false;
		}
		else
		{
			return true;
		}
		
	}
	
	private boolean checkForEmptyCellBetweenElements(WorldToken[][] currentWorld)
	{
		boolean correct = true;
		WorldTokenType[] surrounding = new WorldTokenType[4];
		
		for (int i = 2; i < currentWorld.length-2; i++)
		{
			for (int j = 2; j < currentWorld.length-2; j++)
			{
				if(currentWorld[i][j].equals(WorldTokenType.Rock) || currentWorld[i][j].getType() == WorldTokenType.Food || currentWorld[i][j].getType() == WorldTokenType.BlackAntHill || currentWorld[i][j].getType() == WorldTokenType.RedAntHill)
				{
					WorldTokenType type = currentWorld[i][j].getType();
					surrounding[0] = currentWorld[i+1][j].getType();
					surrounding[1] = currentWorld[i-1][j].getType();
					surrounding[2] = currentWorld[i][j+1].getType();
					surrounding[3] = currentWorld[i][j-1].getType();
					
					for (WorldTokenType s: surrounding)
					{
						if (s == type)
						{
							
						}
						else if (s == WorldTokenType.Empty)
						{
							
						}
						else
						{
							correct = false;
						}
					}
				}
			}
			
		}
		return correct;
	}
	
	private boolean checkOuterWalls(WorldToken[][] currentWorld)
	{
		//CHECK FOR THESE'S '##########'
		
		int i = 0;
		boolean correct = true;
		
		// Check top horizontal
		while (i < currentWorld.length && correct)
		{
			if (i != 0 && i != currentWorld.length-1 && currentWorld[1][i].getType() != WorldTokenType.Empty)
			{
				//Check that nothing is next to wall
				correct = false;
			}
			if (currentWorld[0][i].getType() != WorldTokenType.Rock)
			{
				correct = false;
			}
			else
			{
				i++;
			}
			
		}
		i = 0;
		
		// Check bottom horizontal
		while (i < currentWorld.length && correct)
		{
			if (i != 0 && i != currentWorld.length-1 && currentWorld[currentWorld.length-2][i].getType() != WorldTokenType.Empty)
			{
				//Check that nothing is next to wall
				correct = false;
			}
			if (currentWorld[currentWorld.length-1][i].getType() !=WorldTokenType.Rock)
			{
				correct = false;
			}
			else
			{
				i++;
			}
		}
		i = 0;
		
		// Check left vertical
		while (i < currentWorld.length && correct)
		{	
			if (i != 0 && i != currentWorld.length-1 && currentWorld[i][1].getType() != WorldTokenType.Empty)
			{
				//Check that nothing is next to wall
				correct = false;
			}
			if (currentWorld[i][0].getType() != WorldTokenType.Rock)
			{
				correct = false;
			}
			else
			{
				i++;
			}
		}
		i = 0;
		
		// Check right vertical
		while (i < currentWorld.length && correct)
		{
			if (i != 0 && i != currentWorld.length-1 && currentWorld[i][currentWorld.length-2].getType() != WorldTokenType.Empty)
			{
				//Check that nothing is next to wall
				correct = false;
			}
			if (currentWorld[i][currentWorld.length-1].getType() != WorldTokenType.Rock)
			{
				correct = false;
			}
			else
			{
				i++;
			}
		}
		
		return correct;
	}
	
	private boolean checkForRocks(WorldToken[][] currentWorld)
	{
		int limit = 0;
		if ( currentWorld.length == 150 || currentWorld.length == 100)
		{
			limit = 14; // World should have 14 rocks
		}

		
		// Using a flood fill based algorithm
		boolean correct = true;
		boolean[][] seen = new boolean[currentWorld.length][currentWorld.length];  //Keep record of what cells we've seen
		Queue<Node> queue = new LinkedList<Node>();
		int amountOfRocks = 0;
		for (int i = 2; i < currentWorld.length-2; i++)
		{
			for (int j = 2; j < currentWorld.length-2; j++)
			{
				if (currentWorld[i][j].getType() == WorldTokenType.Rock && !seen[i][j])
				{
					queue.add(new Node(i, j));
					amountOfRocks++;
				}
				while(!queue.isEmpty())
				{	Node node = queue.remove();
					if((node.getRow() >= 2) && (node.getRow() < currentWorld.length - 2) && (node.getCol() >= 2) && (node.getCol() < currentWorld.length - 2))
					{
						if (!seen[node.getRow()][node.getCol()] && currentWorld[node.getRow()][node.getCol()].getType() == WorldTokenType.Rock)
						{
							seen[node.getRow()][node.getCol()] = true;
							queue.add(new Node(node.getRow() + 1, node.getCol()));
							queue.add(new Node(node.getRow() - 1, node.getCol()));
							queue.add(new Node(node.getRow(), node.getCol() + 1));
							queue.add(new Node(node.getRow(), node.getCol() - 1));
						}
					}
				}
			}
		}
		if (amountOfRocks != limit)
		{
			correct = false;
		}
		return correct;
	}
	
	private boolean checkForHills(WorldToken[][] currentWorld)
	{
		
		// Using a flood fill based algorithm
		
		int plus = 0;
		int minus = 0;
		boolean correct = true;
		boolean[][] seen = new boolean[currentWorld.length][currentWorld.length];  //Keep record of what cells we've seen
		
		for (int i = 2; i < currentWorld.length-2; i++)
		{
			for (int j = 2; j < currentWorld.length-2; j++)
			{
				if (currentWorld[i][j].getType() == WorldTokenType.BlackAntHill && !seen[i][j] || currentWorld[i][j].getType() == WorldTokenType.RedAntHill  && !seen[i][j])
				{
					WorldTokenType hill = null;
					if (currentWorld[i][j].getType() == WorldTokenType.BlackAntHill )
					{
						hill = WorldTokenType.BlackAntHill ;
					}
					else
					{
						hill = WorldTokenType.RedAntHill ;
					}
					
					
					if (currentWorld[i][j+1].getType() == (hill) && currentWorld[i+1][j-1].getType() == hill)
						// find this shape
						//        + + + + + + +
						//      + + + + + + + +
						//      + + + + + + + + +
						//    + + + + + + + + + +
						//    + + + + + + + + + + +
						//  + + + + + + + + + + + +
						//  + + + + + + + + + + + + +
						//  + + + + + + + + + + + +
						//    + + + + + + + + + + +
						//    + + + + + + + + + + 
						//      + + + + + + + + +
						//      + + + + + + + +
						//        + + + + + + +
						//   
					{
						for (int n = j; n < j + 7; n++ ) // first row
						{
							if (currentWorld[i][n].getType() == hill && !seen[i][n])
							{
								seen[i][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j-1; n < (j-1) + 8; n++ ) // second row
						{
							if (currentWorld[i+1][n].getType() == hill && !seen[i+1][n])
							{
								seen[i+1][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j-1; n < (j-1) + 9; n++ ) // third row
						{
							if (currentWorld[i+2][n].getType() == hill && !seen[i+2][n])
							{
								seen[i+2][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j-2; n < (j-2) + 10; n++ ) // forth row
						{
							if (currentWorld[i+3][n].getType() == hill && !seen[i+3][n])
							{
								seen[i+3][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j-2; n < (j-2) + 11; n++ ) // fifth row
						{
							if (currentWorld[i+4][n].getType() == hill && !seen[i+4][n])
							{
								seen[i+4][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j-3; n < (j-3) + 12; n++ ) // sixth row
						{
							if (currentWorld[i+5][n].getType() == hill && !seen[i+5][n])
							{
								seen[i+5][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j-3; n < (j-3) + 13; n++ ) // seventh row
						{
							if (currentWorld[i+6][n].getType() == hill && !seen[i+6][n])
							{
								seen[i+6][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j-3; n < (j-3) + 12; n++ ) // eighth row
						{
							if (currentWorld[i+7][n].getType() == hill && !seen[i+7][n])
							{
								seen[i+7][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j-2; n < (j-2) + 11; n++ ) // ninth row
						{
							if (currentWorld[i+8][n].getType() == hill && !seen[i+8][n])
							{
								seen[i+8][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j-2; n < (j-2) + 10; n++ ) // tenth row
						{
							if (currentWorld[i+9][n].getType() == hill && !seen[i+9][n])
							{
								seen[i+9][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j-1; n < (j-1) + 9; n++ ) // eleventh row
						{
							if (currentWorld[i+10][n].getType() == hill && !seen[i+10][n])
							{
								seen[i+10][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j-1; n < (j-1) + 8; n++ ) // Twelfth row
						{
							if (currentWorld[i+11][n].getType() == hill && !seen[i+11][n])
							{
								seen[i+11][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j; n < j + 7; n++ ) // Thirteenth row
						{
							if (currentWorld[i+12][n].getType() == hill && !seen[i+12][n])
							{
								seen[i+12][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						if(correct)
						{
							if (hill == WorldTokenType.BlackAntHill)
							{
								plus++;
							}
							else
							{
								minus++;
							}
						}
						
					}
					else if (currentWorld[i][j+1].getType() == hill && currentWorld[i+1][j-1].getType() == hill)
						// find this shape - notice the slight difference in shape from the other
						//        + + + + + + +
						//        + + + + + + + +
						//      + + + + + + + + +
						//      + + + + + + + + + +
						//    + + + + + + + + + + +
						//    + + + + + + + + + + + +
						//  + + + + + + + + + + + + +
						//    + + + + + + + + + + + +
						//    + + + + + + + + + + +
						//      + + + + + + + + + + 
						//      + + + + + + + + +
						//        + + + + + + + +
						//        + + + + + + +
						//   
					{
						for (int n = j; n < j + 7; n++ ) // first row
						{
							if (currentWorld[i][n].getType() == hill && !seen[i][n])
							{
								seen[i][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j; n < j + 8; n++ ) // second row
						{
							if (currentWorld[i+1][n].getType() == hill && !seen[i+1][n])
							{
								seen[i+1][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j-1; n < (j-1) + 9; n++ ) // third row
						{
							if (currentWorld[i+2][n].getType() == hill && !seen[i+2][n])
							{
								seen[i+2][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j-1; n < (j-1) + 10; n++ ) // forth row
						{
							if (currentWorld[i+3][n].getType() == hill && !seen[i+3][n])
							{
								seen[i+3][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j-2; n < (j-2) + 11; n++ ) // fifth row
						{
							if (currentWorld[i+4][n].getType() == hill && !seen[i+4][n])
							{
								seen[i+4][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j-2; n < (j-2) + 12; n++ ) // sixth row
						{
							if (currentWorld[i+5][n].getType() == hill && !seen[i+5][n])
							{
								seen[i+5][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j-3; n < (j-3) + 13; n++ ) // seventh row
						{
							if (currentWorld[i+6][n].getType() == hill && !seen[i+6][n])
							{
								seen[i+6][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j-2; n < (j-2) + 12; n++ ) // eighth row
						{
							if (currentWorld[i+7][n].getType() == hill && !seen[i+7][n])
							{
								seen[i+7][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j-2; n < (j-2) + 11; n++ ) // ninth row
						{
							if (currentWorld[i+8][n].getType() == hill && !seen[i+8][n])
							{
								seen[i+8][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j-1; n < (j-1) + 10; n++ ) // tenth row
						{
							if (currentWorld[i+9][n].getType() == hill && !seen[i+9][n])
							{
								seen[i+9][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j-1; n < (j-1) + 9; n++ ) // eleventh row
						{
							if (currentWorld[i+10][n].getType() == hill && !seen[i+10][n])
							{
								seen[i+10][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j; n < j + 8; n++ ) // Twelfth row
						{
							if (currentWorld[i+11][n].getType() == hill && !seen[i+11][n])
							{
								seen[i+11][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j; n < j + 7; n++ ) // Thirteenth row
						{
							if (currentWorld[i+12][n].getType() == hill && !seen[i+12][n])
							{
								seen[i+12][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						if(correct)
						{
							if (hill == WorldTokenType.BlackAntHill)
							{
								plus++;
							}
							else
							{
								minus++;
							}
						}
						
					}
					else
					{
						correct = false;
					}
				}
				
			}
		}
		if (plus != 1 && minus != 1)
		{
			correct = false;
		}
		return correct;
	}
	
	private boolean checkForFood(WorldToken[][] currentWorld)
	{
		int limit = 0;
		if ( currentWorld.length == 150)
		{
			limit = 11; // World should have 11 blobs of food
		}
		else if ( currentWorld.length == 100)
		{
			limit = 8; // customer worlds have 8 blobs of food
		}
		
		// Using a flood fill based algorithm
		boolean correct = true;
		boolean[][] seen = new boolean[currentWorld.length][currentWorld.length];  //Keep record of what cells we've seen
		int amountOfFoodBlobs = 0; // food blobs found so far counter
		for (int i = 2; i < currentWorld.length-2; i++)
		{
			for (int j = 2; j < currentWorld.length-2; j++)
			{
				if (currentWorld[i][j].getType() == WorldTokenType.Food && !seen[i][j])
				{
					if (currentWorld[i][j+1].getType() == WorldTokenType.Food  && currentWorld[i+1][j-1].getType() == WorldTokenType.Empty  && currentWorld[i+2][j-1].getType() == WorldTokenType.Food )
						// find this shape
						//        5 5 5 5 5
						//        5 5 5 5 5
						//      5 5 5 5 5
						//      5 5 5 5 5
						//    5 5 5 5 5
					{
						for (int n = j; n < j + 5; n++ ) // first row
						{
							if (currentWorld[i][n].getType() == WorldTokenType.Food  && !seen[i][n])
							{
								seen[i][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j; n < j + 5; n++) // second row
						{
							if (currentWorld[i+1][n].getType() == WorldTokenType.Food && !seen[i+1][n])
							{
								seen[i+1][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j -1; n < (j -1) + 5; n++) // third row
						{
							if (currentWorld[i+2][n].getType() == WorldTokenType.Food && !seen[i+2][n])
							{
								seen[i+2][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j -1; n < (j -1) + 5; n++) // forth row
						{
							if (currentWorld[i+3][n].getType() == WorldTokenType.Food && !seen[i+3][n])
							{
								seen[i+3][n] = true;
							}
							else
							{
							correct = false;
							}
						}
						for (int n = j - 2; n < (j -2) + 5; n++) // fifth row
						{
							if (currentWorld[i+4][n].getType() == WorldTokenType.Food && !seen[i+4][n])
							{
							seen[i+4][n] = true;
							}
							else
							{
							correct = false;
							}
						}
						if(correct)
						{
							amountOfFoodBlobs++;
						}
					
					}
					else if (currentWorld[i][j+1].getType() == WorldTokenType.Food && currentWorld[i+1][j-1].getType() == WorldTokenType.Food && currentWorld[i+2][j-1].getType() == WorldTokenType.Food)
						// find this shape
						//        5 5 5 5 5
						//      5 5 5 5 5
						//      5 5 5 5 5
						//    5 5 5 5 5
						//    5 5 5 5 5
					{
						for (int n = j; n < j + 5; n++ ) // first row
						{
							if (currentWorld[i][n].getType() == WorldTokenType.Food && !seen[i][n])
							{
								seen[i][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j - 1; n < (j -1) + 5; n++) // second row
						{
							if (currentWorld[i+1][n].getType() == WorldTokenType.Food&& !seen[i+1][n])
							{
								seen[i+1][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j -1; n < (j -1) + 5; n++) // third row
						{
							if (currentWorld[i+2][n].getType() == WorldTokenType.Food && !seen[i+2][n])
							{
								seen[i+2][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j -2; n < (j -2) + 5; n++) // forth row
						{
							if (currentWorld[i+3][n].getType() == WorldTokenType.Food&& !seen[i+3][n])
							{
								seen[i+3][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j -2; n < (j -2) + 5; n++) // fifth row
						{
							if (currentWorld[i+4][n].getType() == WorldTokenType.Food && !seen[i+4][n])
							{
								seen[i+4][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						if(correct)
						{
							amountOfFoodBlobs++;
						}
					
					}
					else if (currentWorld[i][j+1].getType() == WorldTokenType.Food && currentWorld[i+1][j].getType() == WorldTokenType.Empty && currentWorld[i+2][j].getType() == WorldTokenType.Empty)
						// find this shape
						//        5 5 5 5 5
						//          5 5 5 5 5
						//          5 5 5 5 5
						//            5 5 5 5 5
						//            5 5 5 5 5
					{
						for (int n = j; n < j + 5; n++ ) // first row
						{
							if (currentWorld[i][n].getType() == WorldTokenType.Food && !seen[i][n])
							{
								seen[i][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j + 1; n < (j +1) + 5; n++) // second row
						{
							if (currentWorld[i+1][n].getType() == WorldTokenType.Food && !seen[i+1][n])
							{
								seen[i+1][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j +1; n < (j +1) + 5; n++) // third row
						{
							if (currentWorld[i+2][n].getType() == WorldTokenType.Food && !seen[i+2][n])
							{
								seen[i+2][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j +2; n < (j +2) + 5; n++) // forth row
						{
							if (currentWorld[i+3][n].getType() == WorldTokenType.Food && !seen[i+3][n])
							{
								seen[i+3][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j +2; n < (j +2) + 5; n++) // fifth row
						{
							if (currentWorld[i+4][n].getType() == WorldTokenType.Food && !seen[i+4][n])
							{
								seen[i+4][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						if(correct)
						{
							amountOfFoodBlobs++;
						}
					}
					else if (currentWorld[i][j+1].getType() == WorldTokenType.Food && currentWorld[i+1][j].getType() == WorldTokenType.Food && currentWorld[i+2][j].getType() == WorldTokenType.Empty)
						// find this shape
						//        5 5 5 5 5
						//        5 5 5 5 5
						//          5 5 5 5 5
						//          5 5 5 5 5
						//            5 5 5 5 5
					{
						for (int n = j; n < j + 5; n++ ) // first row
						{
							if (currentWorld[i][n].getType() == WorldTokenType.Food && !seen[i][n])
							{
								seen[i][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j; n < j + 5; n++) // second row
						{
							if (currentWorld[i+1][n].getType() == WorldTokenType.Food && !seen[i+1][n])
							{
								seen[i+1][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j +1; n < (j +1) + 5; n++) // third row
						{
							if (currentWorld[i+2][n].getType() == WorldTokenType.Food && !seen[i+2][n])
							{
								seen[i+2][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j +1; n < (j +1) + 5; n++) // forth row
						{
							if (currentWorld[i+3][n].getType() == WorldTokenType.Food && !seen[i+3][n])
							{
								seen[i+3][n] = true;
							}
							else
							{
							correct = false;
							}
						}
						for (int n = j +2; n < (j +2) + 5; n++) // fifth row
						{
							if (currentWorld[i+4][n].getType() == WorldTokenType.Food && !seen[i+4][n])
							{
							seen[i+4][n] = true;
							}
							else
							{
							correct = false;
							}
						}
						if(correct)
						{
							amountOfFoodBlobs++;
						}
					}
						
					else if (currentWorld[i][j+1].getType() == WorldTokenType.Empty && currentWorld[i+1][j-1].getType() == WorldTokenType.Empty && currentWorld[i+1][j].getType() == WorldTokenType.Food && !seen[i+1][j])
						// find this shape
						//        5 
						//        5 5 
						//      5 5 5
						//      5 5 5 5 
						//    5 5 5 5 5
						//      5 5 5 5
						//      5 5 5
						//        5 5
						//        5
					{
						seen[i][j] = true; // First row
						seen[i+1][j] = true; // start of second
						if (currentWorld[i+1][j+1].getType() == WorldTokenType.Food && !seen[i+1][j+1]) // end of second
						{
							seen[i+1][j+1] = true;
						}
						else
						{
							correct = false;
						}
						for (int n = j -1; n < (j -1) + 3; n++ ) // third row
						{
							if (currentWorld[i+2][n].getType() == WorldTokenType.Food && !seen[i+2][n])
							{
								seen[i+2][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j -1; n < (j -1) + 4; n++) // forth row
						{
							if (currentWorld[i+3][n].getType() == WorldTokenType.Food && !seen[i+3][n])
							{
								seen[i+3][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j -2; n < (j -2) + 5; n++) // fifth row
						{
							if (currentWorld[i+4][n].getType() == WorldTokenType.Food && !seen[i+4][n])
							{
								seen[i+4][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j -1; n < (j -1) + 4; n++) // sixth row
						{
							if (currentWorld[i+5][n].getType() == WorldTokenType.Food && !seen[i+5][n])
							{
								seen[i+5][n] = true;
							}
							else
							{
							correct = false;
							}
						}
						for (int n = j -1; n < (j -1) + 3; n++) // seventh row
						{
							if (currentWorld[i+6][n].getType() == WorldTokenType.Food && !seen[i+6][n])
							{
							seen[i+6][n] = true;
							}
							else
							{
							correct = false;
							}
						}
						if (currentWorld[i+7][j].getType() == WorldTokenType.Food && !seen[i+7][j] && currentWorld[i+7][j+1].getType() == WorldTokenType.Food && !seen[i+7][j+1])
						{
							seen[i+7][j] = true;
							seen[i+7][j+1] = true;
						}
						else
						{
							correct = false;
						}
						
						if (currentWorld[i+8][j].getType() == WorldTokenType.Food && !seen[i+8][j])
						{
							seen[i+8][j] = true;
						}
						else
						{
							correct = false;
						}
						if(correct)
						{
							amountOfFoodBlobs++;
						}
					}
					
					else if (currentWorld[i][j+1].getType() == WorldTokenType.Empty && currentWorld[i+1][j-1].getType() == WorldTokenType.Food&& !seen[i+1][j-1] && currentWorld[i+1][j].getType() == WorldTokenType.Food && !seen[i+1][j])
						// find this shape
						//        5 
						//      5 5 
						//      5 5 5
						//    5 5 5 5 
						//    5 5 5 5 5
						//    5 5 5 5
						//      5 5 5
						//      5 5
						//        5
					{
						seen[i][j] = true; // First row
						seen[i+1][j] = true; // start of second
						if (currentWorld[i+1][j-1].getType() == WorldTokenType.Food && !seen[i+1][j-1]) // end of second
						{
							seen[i+1][j-1] = true;
						}
						else
						{
							correct = false;
						}
						for (int n = j -1; n < (j -1) + 3; n++ ) // third row
						{
							if (currentWorld[i+2][n].getType() == WorldTokenType.Food && !seen[i+2][n])
							{
								seen[i+2][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j -2; n < (j -2) + 4; n++) // forth row
						{
							if (currentWorld[i+3][n].getType() == WorldTokenType.Food && !seen[i+3][n])
							{
								seen[i+3][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j -2; n < (j -2) + 5; n++) // fifth row
						{
							if (currentWorld[i+4][n].getType() == WorldTokenType.Food && !seen[i+4][n])
							{
								seen[i+4][n] = true;
							}
							else
							{
								correct = false;
							}
						}
						for (int n = j -2; n < (j -2) + 4; n++) // sixth row
						{
							if (currentWorld[i+5][n].getType() == WorldTokenType.Food && !seen[i+5][n])
							{
								seen[i+5][n] = true;
							}
							else
							{
							correct = false;
							}
						}
						for (int n = j -1; n < (j -1) + 3; n++) // seventh row
						{
							if (currentWorld[i+6][n].getType() == WorldTokenType.Food && !seen[i+6][n])
							{
							seen[i+6][n] = true;
							}
							else
							{
							correct = false;
							}
						}
						if (currentWorld[i+7][j-1].getType() == WorldTokenType.Food && !seen[i+7][j-1] && currentWorld[i+7][j].getType() == WorldTokenType.Food && !seen[i+7][j])
						{
							seen[i+7][j-1] = true;
							seen[i+7][j] = true;
						}
						else
						{
							correct = false;
						}
						
						if (currentWorld[i+8][j].getType() == WorldTokenType.Food && !seen[i+8][j])
						{
							seen[i+8][j] = true;
						}
						else
						{
							correct = false;
						}
						if(correct)
						{
							amountOfFoodBlobs++;
						}
					}
					else
					{
						correct = false;
					}

				}
			}
		}
		if (amountOfFoodBlobs != limit)
		{
			correct = false;
		}
		return correct;
	}
	
	class Node
	{
		private int row;
		private int col;
		
		public Node(int row, int col)
		{
			this.row = row;
			this.col = col;
		}
		
		public int getRow() {
			return row;
		}

		public int getCol() {
			return col;
		}
	}
}
