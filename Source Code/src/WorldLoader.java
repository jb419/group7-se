import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Loads a world from file and creates a 2D array of WorldTokens ready to be checked
 * 
 * @author Brett Flitter
 * @version 01/06/2012 - 3
 */
public class WorldLoader {

	//private String worldLocation;
	private WorldToken[][] currentWorld;
	private FileReader fileReader;
	private int character;
	private int worldLength;
	private WorldChecker worldChecker;
	private String isError;
	
	/**
	 * Constructor
	 */
	public WorldLoader() 
	{
		worldChecker = new WorldChecker();

	}
	
	private int nextChar()
	{
		try 
		{
			if ((character = fileReader.read()) != -1)
			{
			}
		} 
		catch (IOException e) 
		{
			isError = "Character not recognised!";
			
		}
		return -1;
		
	}
	
	private void readInDimensions()
	{
		int row = 0;
		int column = 0;
		String charactersRead = "";
		int num = 0;
		while (!isHash(character))
		{
			while (isWhiteSpace(character)) 
			{
				nextChar();
			}
	
			while (isNum(character))
			{
				charactersRead = charactersRead + Character.toString((char)character);
				nextChar();
				num = Integer.parseInt(charactersRead);
			}
			
			if (row == 0 && (num == 100 || num == 150 || num == 10)) // only allow theses numbers otherwise GUI fooks up
			{
				row = num;
				charactersRead = "";
			}
			else if (column == 0 && (num == 100 || num == 150 || num == 10)) // only allow theses numbers otherwise GUI fooks up
			{
				column = num;
				charactersRead = "";
			}
			else if (isHash(character) && row == column) // See a hash and row and column are equal = finished finding the dimensions of world
			{
				worldLength = row;
				break;
			}
			else 
			{
				isError = "World syntactically incorrect - Wrongly specified dimensions of world!";
			}
		}	
	}
	
	/**
	 * Creates a 2D array of WorldTokens
	 * 
	 * @param path the file path where the world is located
	 * @return a 2D array of WorldTokens
	 */
	public WorldToken[][] loadWorld(String path) 
	{
		isError = null;
		try 
		{
			this.fileReader = new FileReader(path);
		} 
		catch (FileNotFoundException e) 
		{
			isError = "No file found!";
		}
		
		readInDimensions();
		currentWorld = new WorldToken[worldLength][worldLength];
		
		for (int i = 0; i < worldLength; i++)
		{
			for (int j = 0; j < worldLength; j++)
			{
				while (isWhiteSpace(character)) 
				{
					nextChar();
				}
				if (isHash(character))
				{
					currentWorld[i][j] = new WorldToken(WorldTokenType.Rock);
					nextChar();
				}
				else if (isNum(character))
				{	
					currentWorld[i][j] = new WorldToken(WorldTokenType.Food);
					nextChar();
				}
				else if (isMinus(character))
				{
					currentWorld[i][j] = new WorldToken(WorldTokenType.RedAntHill);
					nextChar();
				}
				else if (isPlus(character))
				{
					currentWorld[i][j] = new WorldToken(WorldTokenType.BlackAntHill);
					nextChar();
				}	
				else if (isFullStop(character))
				{
					currentWorld[i][j] = new WorldToken(WorldTokenType.Empty);
					nextChar();
				}
				else
				{
					isError = "World syntactically incorrect - World contains unknown character!";
				}
			}
		}
		
		if (worldChecker.checkWorld(currentWorld))
		{
			return currentWorld;
		}
		else
		{
			isError = "World is not syntactically correct!";
			return null;
		}
	}
	
	private boolean isHash(int ch)
	{
		return ch == 35;
	}
	
	private boolean isNum(int ch)
	{
		String c = Integer.toString(ch);
		Pattern p = Pattern.compile("48|49|50|51|52|53|54|55|56|57");
		Matcher m = p.matcher(c);
		return m.matches();
	}
	
	private boolean isMinus(int ch)
	{
		return ch == 45;
	}
	
	private boolean isPlus(int ch)
	{
		return ch == 43;
	}
	
	private boolean isWhiteSpace(int ch)
	{
		return ch >= 0 && ch <= 32;
	}
	
	private boolean isFullStop(int ch)
	{
		return ch == 46;
	}
	
	/**
	 * Gets error message which is out putted by GUI to the user using a message dialog
	 * @return error message
	 */
	public String isError()
	{
		return isError;
	}
	
	/**
	 * Gets the length of the world
	 * @return worldLength
	 */
	public int getWorldLength()
	{
		return worldLength;
	}


}
