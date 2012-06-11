import java.security.acl.Permission;

import org.junit.* ;

import static org.junit.Assert.* ;

/**
 * 
 * 
 * @author Rajbir Bal
 * @version 06/06/2012 - 1
 */
public class Test_Ant{

	//Tests that checks if an Ant has food.
	@Test
	public void test_AntHasFood(){
		WorldToken[][] world = new WorldToken[150][150];
		for (int i = 0; i < 150; i++){
		for(int c = 0; c < 150; c++){
		world[i][c] = new WorldToken(WorldTokenType.Empty);
		}
		}
		World w = World.getNewWorld("C:\\Users\\Razor\\Documents\\GitHub\\group7-se\\brain.ant", "C:\\Users\\Razor\\Documents\\GitHub\\group7-se\\brain.ant", world);
		int[] t = new int[2];
		t[0] = 50;
		t[1] = 50;
		w.addFood(t);
		Ant a = new Ant(w, AntColour.Black);
		w.setAntAt(t, a);
		a.pickup();
		assertTrue(a.hasFood() == true);
	}
	//Tests that checks if the Ant picks up food.
	@Test
	public void test_pickUpFood(){
		WorldToken[][] world = new WorldToken[150][150];
		for (int i = 0; i < 150; i++){
		for(int c = 0; c < 150; c++){
		world[i][c] = new WorldToken(WorldTokenType.Empty);
		}
		}
		World w = World.getNewWorld("C:\\Users\\Razor\\Documents\\GitHub\\group7-se\\brain.ant", "C:\\Users\\Razor\\Documents\\GitHub\\group7-se\\brain.ant", world);
		int[] t = new int[2];
		t[0] = 50;
		t[1] = 50;
		w.addFood(t);
		Ant a = new Ant(w, AntColour.Black);
		w.setAntAt(t, a);
		assertTrue(a.pickup() == true);
	}
	//Tests that checks if the Ant uses sense on the surrounding cells.
	@Test
	public void test_sense(){
		WorldToken[][] world = new WorldToken[150][150];
		for (int i = 0; i < 150; i++){
		for(int c = 0; c < 150; c++){
		world[i][c] = new WorldToken(WorldTokenType.Empty);
		}
		}
		World w = World.getNewWorld("C:\\Users\\Razor\\Documents\\GitHub\\group7-se\\brain.ant", "C:\\Users\\Razor\\Documents\\GitHub\\group7-se\\brain.ant", world);
		int[] t = new int[2];
		t[0] = 50;
		t[1] = 50;
		Ant a = new Ant(w, AntColour.Black);
		w.setAntAt(t, a);
		int[] s = new int[3];
		s[0] = 50;
		s[1] = 51;
		w.mark(s, AntColour.Black, 0);
		int[] q = new int[4];
		q[0] = 50;
		q[1] = 49;
		w.mark(q, AntColour.Black, 0);
		int[] e = new int[4];
		e[0] = 51;
		e[1] = 50;
		w.mark(e, AntColour.Black, 0);
		int[] r = new int[5];
		r[0] = 51;
		r[1] = 51;
		w.mark(r, AntColour.Black, 0);
		int[] y = new int[6];
		y[0] = 51;
		y[1] = 49;
		w.mark(y, AntColour.Black, 0);
		int[] u = new int[7];
		u[0] = 49;
		u[1] = 50;
		w.mark(u, AntColour.Black, 0);
		int[] i = new int[8];
		i[0] = 49;
		i[1] = 51;
		w.mark(i, AntColour.Black, 0);
		int[] o = new int[9];
		o[0] = 50;
		o[1] = 51;
		w.mark(o, AntColour.Black, 0);
		a.sense(Condition.BlackMarker, SensingDirection.Ahead);
		assertTrue(a.sense(Condition.BlackMarker, SensingDirection.Ahead) == true);
	}
	//Tests that checks if a Ant leaves a sense marker.
	@Test
	public void test_senseMarker(){
		WorldToken[][] world = new WorldToken[150][150];
		for (int i = 0; i < 150; i++){
		for(int c = 0; c < 150; c++){
		world[i][c] = new WorldToken(WorldTokenType.Empty);
		}
		}
		World w = World.getNewWorld("C:\\Users\\Razor\\Documents\\GitHub\\group7-se\\brain.ant", "C:\\Users\\Razor\\Documents\\GitHub\\group7-se\\brain.ant", world);
		int[] t = new int[2];
		t[0] = 50;
		t[1] = 50;
		Ant a = new Ant(w, AntColour.Black);
		w.setAntAt(t, a);
		a.mark(0);
		assertTrue(a.senseMarker(0, SensingDirection.Here) == true);
	}
	//Tests that checks if an Ant is surrounded.
	@Test
	public void test_isSurrounded(){
		Ant a = new Ant(null, null);
		assertTrue(a.isSurrounded() == true);
	}
}
