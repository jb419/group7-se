import static org.junit.Assert.*;

import org.junit.Test;


/**
 * 
 * 
 * @author Rajbir Bal
 * @version 11/06/2012 - 1
 */
public class Test_World
{
	//Test to see if the location of the ant is equal to the position that it is inserted into the world.
	@Test
	public void test_antAt()
	{
		WorldToken[][] world = new WorldToken[150][150];
		for (int i = 0; i < 150; i++){
		for(int c = 0; c < 150; c++){
		world[i][c] = new WorldToken(WorldTokenType.Empty);
		}
		}
		World w = World.getNewWorld("C:\\Users\\James Bedson\\Documents\\GitHub\\group7-se\\brain.ant", "C:\\Users\\James Bedson\\Documents\\GitHub\\group7-se\\brain.ant", world);
		int[] t = new int[2];
		t[0] = 50;
		t[1] = 50;
		Ant a = new Ant(w, AntColour.Black);
		w.setAntAt(t, a);
		a.getPos();
		a.setPos(t);
		assertTrue(w.antAt(t) == true);
	}
}
