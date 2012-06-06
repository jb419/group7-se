import java.util.Random;

/**
 * This Class generates a random world
 * 
 * @author Brett Flitter
 * @version 06/06/2012 - 2
 * 
 */

public class WorldGenerator {

	private WorldToken[][] world;
	private Random random;
	private boolean correct;
	boolean[][] seen;
	int numOfFood;
	int numOfHills;
	int numOfRocks;
	private WorldChecker worldChecker;

	public WorldGenerator()
	{
		//generate();
	}
	public WorldToken[][] generate()
	{
		world = new WorldToken[150][150];
		correct = true;
		seen = new boolean[world.length][world.length];  
		random = new Random();
		numOfFood = 0;
		numOfHills = 0;
		numOfRocks = 0;
		worldChecker = new WorldChecker();

		
		addWalls();
		addFood();
		addHills(WorldTokenType.BlackAntHill);
		addHills(WorldTokenType.RedAntHill);
		addRocks();
		addEmpty();
		
		/*
		 * LEFT FOR TESTING PURPOSES - WILL PRINT THE GENERATED WORLD IN TERMINAL
		 * 
		for (int i = 0; i < world.length; i++)
		{
			for (int j = 0; j < world.length; j++)
			{
				String element ="";
				if (world[i][j].getType() == WorldTokenType.BlackAntHill)
				{
					element = "+";
				}
				else if (world[i][j].getType() == WorldTokenType.RedAntHill)
				{
					element = "-";
				}
				else if (world[i][j].getType() == WorldTokenType.Food)
				{
					element = "5";
				}
				else if (world[i][j].getType() == WorldTokenType.Empty)
				{
					element = ".";
				}
				else
				{
					element = "#";
				}
				if (j == 149)
				{
					System.out.println(element);
				}
				else
				{
					System.out.print(element);
				}
			}
		}
		*/
		
		if(!worldChecker.checkWorld(world)) {
			generate();
		}
		else
		{
			return world;
		}
		
		return null;

	}
	
	private void addRocks()
	{
		while (numOfRocks< 14)
		{
			correct = true;
			int i = random.nextInt(138) + 2;
			int j = random.nextInt(138) + 2;
			int shape = random.nextInt(5);
			
			if (shape == 0)
			{
				int length = random.nextInt(30) + 5;
				// ########
				// ########
				for (int m = i; m < i + 2; m ++) 
				{
					for (int n = j; n < j + length; n++ ) // first row
					{
						try
						{
							if (seen[m][n] || seen[m+1][n] || seen[m-1][n] || seen[m][n+1] || seen[m][n-1])
							{
								correct = false;
							}
						}
						catch (Exception e)
						{
							correct = false;
						}
					}
				}
				
				if(correct)
				{

					for (int m = i; m < i + 2; m ++) 
					{
						for (int n = j; n < j + length; n++ ) // first row
						{
							seen[m][n] = true;
							world[m][n] = new WorldToken(WorldTokenType.Rock);
						}
					}
					numOfRocks++;
					
				}
				else
				{
					addRocks();
				}
			}
			else if (shape == 1)
			{
				// ##
				// ##
				// ##
				// ##
				// and so on..
				int length = random.nextInt(30) + 5;
				for (int m = i; m < i + length; m ++) 
				{
					for (int n = j; n < j + 2; n++ ) // first row
					{
						try
						{
							if (seen[m][n] || seen[m+1][n] || seen[m-1][n] || seen[m][n+1] || seen[m][n-1])
							{
								correct = false;
							}
						}
						catch (Exception e)
						{
							correct = false;
						}
					}
				}
				
				if(correct)
				{

					for (int m = i; m < i + length; m ++) 
					{
						for (int n = j; n < j + 2; n++ ) // first row
						{
							seen[m][n] = true;
							world[m][n] = new WorldToken(WorldTokenType.Rock);
						}
					}
					numOfRocks++;
					
				}
				else
				{
					addRocks();
				}
				
			}
			else if (shape == 2)
			{
				// ####
				// ####
				// ####
				// ####
				for (int m = i; m < i + 4; m ++) 
				{
					for (int n = j; n < j + 4; n++ ) // first row
					{
						try
						{
							if (seen[m][n] || seen[m+1][n] || seen[m-1][n] || seen[m][n+1] || seen[m][n-1])
							{
								correct = false;
							}
						}
						catch (Exception e)
						{
							correct = false;
						}
					}
				}
				
				if(correct)
				{

					for (int m = i; m < i + 4; m ++) 
					{
						for (int n = j; n < j + 4; n++ ) // first row
						{
							seen[m][n] = true;
							world[m][n] = new WorldToken(WorldTokenType.Rock);
						}
					}
					numOfRocks++;
					
				}
				else
				{
					addRocks();
				}
				
			}
			else if (shape == 3)
			{
				int counter = 0;
				
				int p = j;
				for (int m = i; m < i + 10; m ++) 
				{
					for (int n = p; n < p + 2; n++ ) // first row
					{
						try
						{
							if (seen[m][n] || seen[m+1][n] || seen[m-1][n] || seen[m][n+1] || seen[m][n-1])
							{
								correct = false;
							}
						}
						catch (Exception e)
						{
							correct = false;
						}
						counter++;
					}
					if (counter == 4)
					{
						p++;
						counter = 0;
					}
				}
				
				counter = 0;
				if(correct)
				{
					p = j;
					for (int m = i; m < i + 10; m ++) 
					{
						for (int n = p; n < p + 2; n++ ) // first row
						{
							seen[m][n] = true;
							world[m][n] = new WorldToken(WorldTokenType.Rock);
							counter++;
						}
						if (counter == 4)
						{
							p++;
							counter = 0;
						}
					}
					numOfRocks++;
				}
				else
				{
					addRocks();
				}
			}
			else if (shape == 4)
			{
				int counter = 0;
				int p = j;
				for (int m = i; m < i + 10; m ++) 
				{
					for (int n = p; n > p - 2; n-- ) // first row
					{
						try
						{
							if (seen[m][n] || seen[m+1][n] || seen[m-1][n] || seen[m][n+1] || seen[m][n-1])
							{
								correct = false;
							}
						}
						catch (Exception e)
						{
							correct = false;
						}
						counter++;
					}
					if (counter == 4)
					{
						p--;
						counter = 0;
					}
				}
				
				counter = 0;
				if(correct)
				{
					p = j;
					for (int m = i; m < i + 10; m ++) 
					{
						for (int n = p; n > p - 2; n-- ) // first row
						{
							seen[m][n] = true;
							world[m][n] = new WorldToken(WorldTokenType.Rock);
							counter++;
						}
						if (counter == 4)
						{
							p--;
							counter = 0;
						}
					}
					numOfRocks++;
				}
				else
				{
					addRocks();
				}
			}
		}
	}
	
	private void addWalls()
	{	// Place right vertical
		int i = 0;
		while (i < world.length)
		{
			world[i][world.length-1] = new WorldToken(WorldTokenType.Rock);
			seen[i][world.length-1] = true;
			i++;
		}
		
		// Place left vertical
		i = 0;
		while (i < world.length)
		{
			world[i][0] = new WorldToken(WorldTokenType.Rock);
			seen[i][0] = true;
			i++;
		}
		
		// Place bottom horizontal
		i = 0;
		while (i < world.length)
		{
			world[world.length-1][i] = new WorldToken(WorldTokenType.Rock);
			seen[world.length-1][i] = true;
			i++;
		}
		
		//Place top horizontal
		i = 0;
		while (i < world.length)
		{
			world[0][i] = new WorldToken(WorldTokenType.Rock);
			seen[0][i] = true;
			i++;
		}
	}
	
	private void addEmpty()
	{
		for (int i = 0; i < world.length; i++)
		{
			for (int j = 0; j < world.length; j++)
			{
				if(!seen[i][j])
				{
					world[i][j] = new WorldToken(WorldTokenType.Empty);
				}
			}
		}
	}
	
	private void addHills(WorldTokenType hill)
	{

			correct = true;
			int i = random.nextInt(146) + 2;
			int j = random.nextInt(146) + 2;
			if(i % 2 == 0)
			{ // if even
				// place this shape
				//       +++++++
				//      ++++++++
				//      +++++++++
				//     ++++++++++
				//     +++++++++++
				//    ++++++++++++
				//    +++++++++++++
				//    ++++++++++++
				//     +++++++++++
				//     ++++++++++ 
				//      +++++++++
				//      ++++++++
				//       +++++++
				//   
			
				for (int n = j; n < j + 7; n++ ) // first row
				{
					try
					{
						if (seen[i][n] || seen[i+1][n] || seen[i-1][n] || seen[i][n+1] || seen[i][n-1])
						{
						correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				for (int n = j-1; n < (j-1) + 8; n++ ) // second row
				{
					try
					{
						if (seen[i+1][n] || seen[i+2][n] || seen[i-2][n] || seen[i][n+2] || seen[i][n-2])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				for (int n = j-1; n < (j-1) + 9; n++ ) // third row
				{
					try
					{
						
						if (seen[i+2][n] || seen[i+3][n] || seen[i-3][n] || seen[i][n+3] || seen[i][n-3])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				for (int n = j-2; n < (j-2) + 10; n++ ) // forth row
				{
					try
					{
						if (seen[i+3][n] || seen[i+4][n] || seen[i-4][n] || seen[i][n+4] || seen[i][n-4])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				for (int n = j-2; n < (j-2) + 11; n++ ) // fifth row
				{
					try
					{
						if (seen[i+4][n] || seen[i+5][n] || seen[i-5][n] || seen[i][n+5] || seen[i][n-5])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				for (int n = j-3; n < (j-3) + 12; n++ ) // sixth row
				{
					try
					{
						if (seen[i+5][n] || seen[i+6][n] || seen[i-6][n] || seen[i][n+6] || seen[i][n-6])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				for (int n = j-3; n < (j-3) + 13; n++ ) // seventh row
				{
					try
					{
						if (seen[i+6][n] || seen[i+7][n] || seen[i-7][n] || seen[i][n+7] || seen[i][n-7])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				for (int n = j-3; n < (j-3) + 12; n++ ) // eighth row
				{
					try
					{
						if (seen[i+7][n] || seen[i+8][n] || seen[i-8][n] || seen[i][n+8] || seen[i][n-8])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				for (int n = j-2; n < (j-2) + 11; n++ ) // ninth row
				{
					try
					{
						if (seen[i+8][n] || seen[i+8][n] || seen[i-9][n] || seen[i][n+9] || seen[i][n-9])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				for (int n = j-2; n < (j-2) + 10; n++ ) // tenth row
				{
					try
					{
						if (seen[i+9][n] || seen[i+10][n] || seen[i-10][n] || seen[i][n+10] || seen[i][n-10])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				for (int n = j-1; n < (j-1) + 9; n++ ) // eleventh row
				{
					try
					{
						if (seen[i+10][n] || seen[i+11][n] || seen[i-11][n] || seen[i][n+11] || seen[i][n-11])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				for (int n = j-1; n < (j-1) + 8; n++ ) // Twelfth row
				{
					try
					{
						if (seen[i+11][n] || seen[i+12][n] || seen[i-12][n] || seen[i][n+12] || seen[i][n-12])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				for (int n = j; n < j + 7; n++ ) // Thirteenth row
				{
					try
					{
						if (seen[i+12][n] || seen[i+13][n] || seen[i-13][n] || seen[i][n+13] || seen[i][n-13])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				if (correct)
				{
					for (int n = j; n < j + 7; n++ ) // first row
					{
						seen[i][n] = true;
						world[i][n] = new WorldToken(hill);
					}
					for (int n = j-1; n < (j-1) + 8; n++ ) // second row
					{
						seen[i+1][n] = true;
						world[i+1][n] = new WorldToken(hill);
					}
					for (int n = j-1; n < (j-1) + 9; n++ ) // third row
					{
						seen[i+2][n] = true;
						world[i+2][n] = new WorldToken(hill);
					}
					for (int n = j-2; n < (j-2) + 10; n++ ) // forth row
					{
						seen[i+3][n] = true;
						world[i+3][n] = new WorldToken(hill);
					}
					for (int n = j-2; n < (j-2) + 11; n++ ) // fifth row
					{
						seen[i+4][n] = true;
						world[i+4][n] = new WorldToken(hill);
					}
					for (int n = j-3; n < (j-3) + 12; n++ ) // sixth row
					{
						seen[i+5][n] = true;
						world[i+5][n] = new WorldToken(hill);
					}
					for (int n = j-3; n < (j-3) + 13; n++ ) // seventh row
					{
						seen[i+6][n] = true;
						world[i+6][n] = new WorldToken(hill);
					}
					for (int n = j-3; n < (j-3) + 12; n++ ) // eighth row
					{
						seen[i+7][n] = true;
						world[i+7][n] = new WorldToken(hill);
					}
					for (int n = j-2; n < (j-2) + 11; n++ ) // ninth row
					{
						seen[i+8][n] = true;
						world[i+8][n] = new WorldToken(hill);
					}
					for (int n = j-2; n < (j-2) + 10; n++ ) // tenth row
					{
						seen[i+9][n] = true;
						world[i+9][n] = new WorldToken(hill);
					}
					for (int n = j-1; n < (j-1) + 9; n++ ) // eleventh row
					{
						seen[i+10][n] = true;
						world[i+10][n] = new WorldToken(hill);
					}
					for (int n = j-1; n < (j-1) + 8; n++ ) // Twelfth row
					{
						seen[i+11][n] = true;
						world[i+11][n] = new WorldToken(hill);
					}
					for (int n = j; n < j + 7; n++ ) // Thirteenth row
					{
						seen[i+12][n] = true;
						world[i+12][n] = new WorldToken(hill);
					}
				}
				else
				{
					addHills(hill);
				}
			}
			else
			{	// if odd
				// place this shape - notice the slight difference in shape from the other
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
			
				for (int n = j; n < j + 7; n++ ) // first row
				{
					try
					{
						if (seen[i][n] || seen[i+1][n] || seen[i-1][n] || seen[i][n+1] || seen[i][n-1])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				for (int n = j; n < j + 8; n++ ) // second row
				{
					try
					{
						if (seen[i+1][n] || seen[i+2][n] || seen[i-2][n] || seen[i][n+2] || seen[i][n-2])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}

				}
				for (int n = j-1; n < (j-1) + 9; n++ ) // third row
				{
					try
					{
						if (seen[i+2][n] || seen[i+3][n] || seen[i-3][n] || seen[i][n+3] || seen[i][n-3])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				for (int n = j-1; n < (j-1) + 10; n++ ) // forth row
				{
					try
					{
						if (seen[i+3][n] || seen[i+4][n] || seen[i-4][n] || seen[i][n+4] || seen[i][n-4])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				for (int n = j-2; n < (j-2) + 11; n++ ) // fifth row
				{
					try
					{
						if (seen[i+4][n] || seen[i+5][n] || seen[i-5][n] || seen[i][n+5] || seen[i][n-5])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				for (int n = j-2; n < (j-2) + 12; n++ ) // sixth row
				{
					try
					{
						if (seen[i+5][n] || seen[i+6][n] || seen[i-6][n] || seen[i][n+6] || seen[i][n-6])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				for (int n = j-3; n < (j-3) + 13; n++ ) // seventh row
				{
					try
					{
						if (seen[i+6][n] || seen[i+7][n] || seen[i-7][n] || seen[i][n+7] || seen[i][n-7])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				for (int n = j-2; n < (j-2) + 12; n++ ) // eighth row
				{
					try
					{
						if (seen[i+7][n] || seen[i+8][n] || seen[i-8][n] || seen[i][n+8] || seen[i][n-8])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				for (int n = j-2; n < (j-2) + 11; n++ ) // ninth row
				{
					try
					{
						if (seen[i+8][n] || seen[i+9][n] || seen[i-9][n] || seen[i][n+9] || seen[i][n-9])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				for (int n = j-1; n < (j-1) + 10; n++ ) // tenth row
				{
					try
					{
						if (seen[i+9][n] || seen[i+10][n] || seen[i-10][n] || seen[i][n+10] || seen[i][n-10])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				for (int n = j-1; n < (j-1) + 9; n++ ) // eleventh row
				{
					try
					{
						if (seen[i+10][n] || seen[i+11][n] || seen[i-11][n] || seen[i][n+11] || seen[i][n-11])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				for (int n = j; n < j + 8; n++ ) // Twelfth row
				{
					try
					{
						if (seen[i+11][n] || seen[i+12][n] || seen[i-12][n] || seen[i][n+12] || seen[i][n-12])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				for (int n = j; n < j + 7; n++ ) // Thirteenth row
				{
					try
					{
						if (seen[i+12][n] || seen[i+13][n] || seen[i-13][n] || seen[i][n+13] || seen[i][n-13])
						{
							correct = false;
						}
					}
					catch (Exception e)
					{
						correct = false;
					}
				}
				if(correct)
				{
					for (int n = j; n < j + 7; n++ ) // first row
					{
						seen[i][n] = true;
						world[i][n] = new WorldToken(hill);
					}
					for (int n = j; n < j + 8; n++ ) // second row
					{
						seen[i+1][n] = true;
						world[i+1][n] = new WorldToken(hill);
					}
					for (int n = j-1; n < (j-1) + 9; n++ ) // third row
					{
						seen[i+2][n] = true;
						world[i+2][n] = new WorldToken(hill);
					}
					for (int n = j-1; n < (j-1) + 10; n++ ) // forth row
					{
						seen[i+3][n] = true;
						world[i+3][n] = new WorldToken(hill);
					}
					for (int n = j-2; n < (j-2) + 11; n++ ) // fifth row
					{
						seen[i+4][n] = true;
						world[i+4][n] = new WorldToken(hill);
					}
					for (int n = j-2; n < (j-2) + 12; n++ ) // sixth row
					{
						seen[i+5][n] = true;
						world[i+5][n] = new WorldToken(hill);
					}
					for (int n = j-3; n < (j-3) + 13; n++ ) // seventh row
					{
						seen[i+6][n] = true;
						world[i+6][n] = new WorldToken(hill);
					}
					for (int n = j-2; n < (j-2) + 12; n++ ) // eighth row
					{
						seen[i+7][n] = true;
						world[i+7][n] = new WorldToken(hill);
					}
					for (int n = j-2; n < (j-2) + 11; n++ ) // ninth row
					{
						seen[i+8][n] = true;
						world[i+8][n] = new WorldToken(hill);
					}
					for (int n = j-1; n < (j-1) + 10; n++ ) // tenth row
					{
						seen[i+9][n] = true;
						world[i+9][n] = new WorldToken(hill);
					}
					for (int n = j-1; n < (j-1) + 9; n++ ) // eleventh row
					{
						seen[i+10][n] = true;
						world[i+10][n] = new WorldToken(hill);
					}
					for (int n = j; n < j + 8; n++ ) // Twelfth row
					{
						seen[i+11][n] = true;
						world[i+11][n] = new WorldToken(hill);
					}
					for (int n = j; n < j + 7; n++ ) // Thirteenth row
					{
						seen[i+12][n] = true;
						world[i+12][n] = new WorldToken(hill);
					}
				}
				else
				{
					addHills(hill);
				}
			
			}
	}
	
	
	private void addFood()
	{	
		while (numOfFood < 11)
		{
			correct = true;
			int i = random.nextInt(138) + 2;
			int j = random.nextInt(138) + 2;
			if(i % 2 == 0)
			{
				int shape = random.nextInt(3);
				if(shape == 0)
				{
					// place this shape
					//        5 5 5 5 5
					//      5 5 5 5 5
					//      5 5 5 5 5
					//    5 5 5 5 5
					//    5 5 5 5 5
				
					for (int n = j; n < j + 5; n++ ) // first row
					{
						if (seen[i][n])
						{
							correct = false;
						}
					}
					for (int n = j - 1; n < (j -1) + 5; n++) // second row
					{
						if (seen[i+1][n])
						{
							correct = false;
						}
					}
					for (int n = j -1; n < (j -1) + 5; n++) // third row
					{
						if (seen[i+2][n])
						{
							correct = false;
						}
					}
					for (int n = j -2; n < (j -2) + 5; n++) // forth row
					{
						if (seen[i+3][n])
						{
							correct = false;
						}
					}
					for (int n = j -2; n < (j -2) + 5; n++) // fifth row
					{
						if (seen[i+4][n])
						{
							correct = false;
						}
					}
					if(correct)
					{
						for (int n = j; n < j + 5; n++ ) // first row
						{
							seen[i][n] = true;
							world[i][n] = new WorldToken(WorldTokenType.Food);
						}
						for (int n = j - 1; n < (j -1) + 5; n++) // second row
						{
							seen[i+1][n] = true;
							world[i+1][n] = new WorldToken(WorldTokenType.Food);
						}
						for (int n = j -1; n < (j -1) + 5; n++) // third row
						{
							seen[i+2][n] = true;
							world[i+2][n] = new WorldToken(WorldTokenType.Food);
						}
						for (int n = j -2; n < (j -2) + 5; n++) // forth row
						{
							seen[i+3][n] = true;
							world[i+3][n] = new WorldToken(WorldTokenType.Food);
						}
						for (int n = j -2; n < (j -2) + 5; n++) // fifth row
						{
							seen[i+4][n] = true;
							world[i+4][n] = new WorldToken(WorldTokenType.Food);
						}
						numOfFood++;
					}
					else
					{
						addFood();
					}
				}
				else if (shape ==1)
				{
					// place this shape
					//        5 5 5 5 5
					//        5 5 5 5 5
					//          5 5 5 5 5
					//          5 5 5 5 5
					//            5 5 5 5 5
					
					for (int n = j; n < j + 5; n++ ) // first row
					{
						if (seen[i][n])
						{
							correct = false;
						}
					}
					for (int n = j; n < j + 5; n++) // second row
					{
						if (seen[i+1][n])
						{
							correct = false;
						}
					}
					for (int n = j +1; n < (j +1) + 5; n++) // third row
					{
						if (seen[i+2][n])
						{
							correct = false;
						}
					}
					for (int n = j +1; n < (j +1) + 5; n++) // forth row
					{
						if (seen[i+3][n])
						{
							correct = false;
						}
					}
					for (int n = j +2; n < (j +2) + 5; n++) // fifth row
					{
						if (seen[i+4][n])
						{
							correct = false;
						}
					}
					if(correct)
					{
						for (int n = j; n < j + 5; n++ ) // first row
						{
							seen[i][n] = true;
							world[i][n] = new WorldToken(WorldTokenType.Food);
							
						}
						for (int n = j; n < j + 5; n++) // second row
						{
							seen[i+1][n] = true;
							world[i+1][n] = new WorldToken(WorldTokenType.Food);
						}
						for (int n = j +1; n < (j +1) + 5; n++) // third row
						{
							seen[i+2][n] = true;
							world[i+2][n] = new WorldToken(WorldTokenType.Food);
						}
						for (int n = j +1; n < (j +1) + 5; n++) // forth row
						{
							seen[i+3][n] = true;
							world[i+3][n] = new WorldToken(WorldTokenType.Food);
						}
						for (int n = j +2; n < (j +2) + 5; n++) // fifth row
						{
							seen[i+4][n] = true;
							world[i+4][n] = new WorldToken(WorldTokenType.Food);
						}
						numOfFood++;
					}	
					else
					{
						addFood();
					}
				}
				else
				{
					// find this shape
					//        5 
					//      5 5 
					//      5 5 5
					//    5 5 5 5 
					//    5 5 5 5 5
					//    5 5 5 5
					//      5 5 5
					//      5 5
					// 		  5
					
					if (seen[i+1][j-1] || seen[i][j] || seen[i+1][j]) // First and Second row
					{
						correct = false;
					}
					
					for (int n = j -1; n < (j -1) + 3; n++ ) // third row
					{
						if (seen[i+2][n])
						{
							correct = false;
						}
					}
					for (int n = j -2; n < (j -2) + 4; n++) // forth row
					{
						if (seen[i+3][n])
						{
							correct = false;
						}
					}
					for (int n = j -2; n < (j -2) + 5; n++) // fifth row
					{
						if (seen[i+4][n])
						{
							correct = false;
						}
					}
					for (int n = j -2; n < (j -2) + 4; n++) // sixth row
					{
						if (seen[i+5][n])
						{
							correct = false;
						}
					}
					for (int n = j -1; n < (j -1) + 3; n++) // seventh row
					{
						if (seen[i+6][n])
						{
							correct = false;
						}
					}
					if (seen[i+7][j-1] || seen[i+7][j] || seen[i+8][j])
					{
						correct = false;
					}
					if(correct)
					{
						seen[i+1][j-1] = true;
						world[i+1][j-1] = new WorldToken(WorldTokenType.Food);
						seen[i][j] = true;
						world[i][j] = new WorldToken(WorldTokenType.Food);
						seen[i+1][j] = true;
						world[i+1][j] = new WorldToken(WorldTokenType.Food);
						
						for (int n = j -1; n < (j -1) + 3; n++ ) // third row
						{
							seen[i+2][n] = true;
							world[i+2][n] = new WorldToken(WorldTokenType.Food);
						}
						for (int n = j -2; n < (j -2) + 4; n++) // forth row
						{
							seen[i+3][n] = true;
							world[i+3][n] = new WorldToken(WorldTokenType.Food);
						}
						for (int n = j -2; n < (j -2) + 5; n++) // fifth row
						{
							seen[i+4][n] = true;
							world[i+4][n] = new WorldToken(WorldTokenType.Food);

						}
						for (int n = j -2; n < (j -2) + 4; n++) // sixth row
						{
							seen[i+5][n] = true;
							world[i+5][n] = new WorldToken(WorldTokenType.Food);

						}
						for (int n = j -1; n < (j -1) + 3; n++) // seventh row
						{
							seen[i+6][n] = true;
							world[i+6][n] = new WorldToken(WorldTokenType.Food);
						}
						seen[i+7][j-1] = true;
						world[i+7][j-1] = new WorldToken(WorldTokenType.Food);
						seen[i+7][j] = true;
						world[i+7][j] = new WorldToken(WorldTokenType.Food);
						seen[i+8][j] = true;
						world[i+8][j] = new WorldToken(WorldTokenType.Food);

						numOfFood++;
					}
					else
					{
						addFood();
					}
				}
			}
			else
			{ //choose one of the following shapes
				int shape = random.nextInt(3);
				if(shape == 0)
				{
					// place this shape
					//        5 5 5 5 5
					//        5 5 5 5 5
					//      5 5 5 5 5
					//      5 5 5 5 5
					//    5 5 5 5 5
					
					for (int n = j; n < j + 5; n++ ) // first row
					{
						if (seen[i][n])
						{
							correct = false;
						}
					}
					for (int n = j; n < j + 5; n++) // second row
					{
						if (seen[i+1][n])
						{
							correct = false;
						}
				
					}
					for (int n = j -1; n < (j -1) + 5; n++) // third row
					{
						if (seen[i+2][n])
						{
							correct = false;
						}
					}
					for (int n = j -1; n < (j -1) + 5; n++) // forth row
					{
						if (seen[i+3][n])
						{
							correct = false;
						}
					}
					for (int n = j - 2; n < (j -2) + 5; n++) // fifth row
					{
						if (seen[i+4][n])
						{
							correct = false;
						}
					}
					if (correct)
					{
						for (int n = j; n < j + 5; n++ ) // first row
						{
							seen[i][n] = true;
							world[i][n] = new WorldToken(WorldTokenType.Food);
						}
						for (int n = j; n < j + 5; n++) // second row
						{
							seen[i+1][n] = true;
							world[i+1][n] = new WorldToken(WorldTokenType.Food);
						
						}
						for (int n = j -1; n < (j -1) + 5; n++) // third row
						{
							seen[i+2][n] = true;
							world[i+2][n] = new WorldToken(WorldTokenType.Food);
						}
						for (int n = j -1; n < (j -1) + 5; n++) // forth row
						{
							seen[i+3][n] = true;
							world[i+3][n] = new WorldToken(WorldTokenType.Food);
						}
						for (int n = j - 2; n < (j -2) + 5; n++) // fifth row
						{
							seen[i+4][n] = true;
							world[i+4][n] = new WorldToken(WorldTokenType.Food);
						}
						numOfFood++;
					}
					else 
					{
						addFood();
					}
					
				}
				else if (shape == 2)
				{
					// find this shape
					//        5 5 5 5 5
					//          5 5 5 5 5
					//          5 5 5 5 5
					//            5 5 5 5 5
					//            5 5 5 5 5
				
					for (int n = j; n < j + 5; n++ ) // first row
					{
						if (seen[i][n])
						{
							correct = false;
						}
					}
					for (int n = j + 1; n < (j +1) + 5; n++) // second row
					{
						if (seen[i+1][n])
						{
							correct = false;
						}
					}
					for (int n = j +1; n < (j +1) + 5; n++) // third row
					{
						if (seen[i+2][n])
						{
							correct = false;
						}
					}
					for (int n = j +2; n < (j +2) + 5; n++) // forth row
					{
						if (seen[i+3][n])
						{
							correct = false;;
						}
					}
					for (int n = j +2; n < (j +2) + 5; n++) // fifth row
					{
						if (seen[i+4][n])
						{
							correct = false;
						}
					}
					if(correct)
					{
						for (int n = j; n < j + 5; n++ ) // first row
						{
							seen[i][n] = true;
							world[i][n] = new WorldToken(WorldTokenType.Food);
						}
						for (int n = j + 1; n < (j +1) + 5; n++) // second row
						{
							seen[i+1][n] = true;
							world[i+1][n] = new WorldToken(WorldTokenType.Food);
						}
						for (int n = j +1; n < (j +1) + 5; n++) // third row
						{
							seen[i+2][n] = true;
							world[i+2][n] = new WorldToken(WorldTokenType.Food);
						}
						for (int n = j +2; n < (j +2) + 5; n++) // forth row
						{
							seen[i+3][n] = true;
							world[i+3][n] = new WorldToken(WorldTokenType.Food);
						}
						for (int n = j +2; n < (j +2) + 5; n++) // fifth row
						{
							seen[i+4][n] = true;
							world[i+4][n] = new WorldToken(WorldTokenType.Food);
						}
						numOfFood++;
					}
					else
					{
						addFood();
					}
				}
				else
				{
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
				
					if (seen[i+1][j+1] || seen[i][j] || seen[i+1][j]) // first & Second row
					{
						correct = false;
					}

					for (int n = j -1; n < (j -1) + 3; n++ ) // third row
					{
						if (seen[i+2][n])
						{
							correct = false;
						}
					}
					for (int n = j -1; n < (j -1) + 4; n++) // forth row
					{
						if (seen[i+3][n])
						{
							correct = false;
						}
					}
					for (int n = j -2; n < (j -2) + 5; n++) // fifth row
					{
						if (seen[i+4][n])
						{
							correct = false;
						}
					}
					for (int n = j -1; n < (j -1) + 4; n++) // sixth row
					{
						if (seen[i+5][n])
						{
							correct = false;
						}
					}
					for (int n = j -1; n < (j -1) + 3; n++) // seventh row
					{
						if (seen[i+6][n])
						{
							correct = false;
						}
					}
					if (seen[i+7][j] || seen[i+7][j+1] || seen[i+8][j])
					{
						correct = false;
					}
					if (correct)
					{
						seen[i+1][j+1] = true;
						world[i+1][j+1] = new WorldToken(WorldTokenType.Food);
						seen[i][j] = true;
						world[i][j] = new WorldToken(WorldTokenType.Food);
						seen[i+1][j] = true;
						world[i+1][j] = new WorldToken(WorldTokenType.Food);

						for (int n = j -1; n < (j -1) + 3; n++ ) // third row
						{
							seen[i+2][n] = true;
							world[i+2][n] = new WorldToken(WorldTokenType.Food);
						}
						for (int n = j -1; n < (j -1) + 4; n++) // forth row
						{
							seen[i+3][n] = true;
							world[i+3][n] = new WorldToken(WorldTokenType.Food);
						}
						for (int n = j -2; n < (j -2) + 5; n++) // fifth row
						{
							seen[i+4][n] = true;
							world[i+4][n] = new WorldToken(WorldTokenType.Food);
						}
						for (int n = j -1; n < (j -1) + 4; n++) // sixth row
						{
							seen[i+5][n] = true;
							world[i+5][n] = new WorldToken(WorldTokenType.Food);
						}
						for (int n = j -1; n < (j -1) + 3; n++) // seventh row
						{
							seen[i+6][n] = true;
							world[i+6][n] = new WorldToken(WorldTokenType.Food);
						}
						seen[i+7][j] = true;
						world[i+7][j] = new WorldToken(WorldTokenType.Food);
						seen[i+7][j+1] = true;
						world[i+7][j+1] = new WorldToken(WorldTokenType.Food);
						seen[i+8][j] = true;
						world[i+8][j] = new WorldToken(WorldTokenType.Food);
						
						numOfFood++;
					}
					else
					{
						addFood();
					}
				}
			}
		}
		
	}
	
	//public static void main(String[] args)
	//{
	//	new WorldGenerator();
	//}
}

