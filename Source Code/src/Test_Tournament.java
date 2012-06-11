import static org.junit.Assert.*;

import org.junit.Test;


/**
 * 
 * 
 * @author Rajbir Bal
 * @version 11/06/2012 - 1
 */
public class Test_Tournament
{

	//Test to see if the tournament has a world.
	@Test
	public void test_hasWorld()
	{	
		WorldToken[][] world = new WorldToken[150][150];
		for (int i = 0; i < 150; i++){
		for(int c = 0; c < 150; c++){
		world[i][c] = new WorldToken(WorldTokenType.Empty);
	}
	}
	World w = World.getNewWorld("C:\\Users\\James Bedson\\Documents\\GitHub\\group7-se\\brain.ant", "C:\\Users\\James Bedson\\Documents\\GitHub\\group7-se\\brain.ant", world);
	//assert(w.hashCode() != 0);
	Tournament t = new Tournament();
	t.addWorld(world);
	assertTrue(t.hasWorld() == true);
	}
}
