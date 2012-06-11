import static org.junit.Assert.*;

import org.junit.Test;


/**
 * 
 * 
 * @author Rajbir Bal
 * @version 10/06/2012 - 1
 */
public class Test_AntHill
{
	//Test to see if AntHill is the correct colour.
	@Test
	public void test_IsAntHill()
	{
		AntHill h = new AntHill(AntColour.Black);
		assertTrue(h.isAntHill(AntColour.Black) == true);
	}

}
