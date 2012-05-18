import java.io.FileReader;
import java.io.IOException;

/**
 * An AntBrainLoader can read a text file and convert it into brain tokens for the
 * AntBrainInterpreter. Currently it will check that all tokens are seperated by
 * whitespace but it will not check that each state takes place on a new line.
 * 
 * @author Owen Cox
 * @version 18/05/2012 - 2
 */
public class AntBrainLoader
{
	private FileReader fr;
	private char nextChar;
	private String brainLocation;
	
	public AntBrainLoader(String brainLocation)
	{
		this.brainLocation = brainLocation;
		try
		{
			fr = new FileReader(brainLocation);
			nextChar = (char)fr.read();
		}
		catch (IOException e)
		{
			System.err.println("IOException When Reading File: " + brainLocation);
			System.exit(1);
		}
	}
	
	/**
	 * The getNextToken method gets the next Token in the file. A token is a discrete block
	 * composed of either a keyword or numbers.
	 *
	 * @return the next token in the file
	 */
	public BrainToken getNextToken()
	{
		BrainToken b = new BrainToken(false, "");
		try
		{
			if(nextChar == -1)
			{
				b = new BrainToken(true, "%EOF%"); //Used %EOF% as it is unlikely to come up otherwise. Also making the isInteger true with %EOF% makes the EOF token unique.
			}
			else if(Character.isLetter(nextChar))
			{
				String s = "";
				while(Character.isLetter(nextChar))
				{
					s += nextChar;
					nextChar = (char)fr.read();
				}
				b = new BrainToken(false, s);
			}
			else if(Character.isDigit(nextChar))
			{
				String s = "";
				while(Character.isDigit(nextChar))
				{
					s += nextChar;
					nextChar = (char)fr.read();
				}
				b = new BrainToken(true, s);
			}
			//Once we've read a block we should reach either the end of the file or some whitespace to seperate one block from the next.
			if(Character.isWhitespace(nextChar))
			{
				while(Character.isWhitespace(nextChar))
				{
					nextChar = (char)fr.read();
				}
			}
			else if(nextChar == -1)
			{

			}
			else
			{
				System.err.println("Unrecognised Character in Brain: " + brainLocation);
				System.exit(1);
			}
		}
		catch(IOException e)
		{
			System.err.println("IOException when getting next token in brain: " + brainLocation);
		}

		return b; //brain token created earlier.
	}
}
