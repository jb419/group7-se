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
	//Test to see if AntHill is a AntHill.
	@Test
	public void test_AntHill()
	{
		AntHill h = new AntHill(null);
		assertTrue(h.isAntHill(null) == true);
	}

}
