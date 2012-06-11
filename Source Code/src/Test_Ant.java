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
		World w = World.getNewWorld("C:\\Users\\James Bedson\\Documents\\GitHub\\group7-se\\brain.ant", "C:\\Users\\James Bedson\\Documents\\GitHub\\group7-se\\brain.ant", world);
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
		World w = World.getNewWorld("C:\\Users\\James Bedson\\Documents\\GitHub\\group7-se\\brain.ant", "C:\\Users\\James Bedson\\Documents\\GitHub\\group7-se\\brain.ant", world);
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
		World w = World.getNewWorld("C:\\Users\\James Bedson\\Documents\\GitHub\\group7-se\\brain.ant", "C:\\Users\\James Bedson\\Documents\\GitHub\\group7-se\\brain.ant", world);
		int[] t = new int[2];
		t[0] = 50;
		t[1] = 50;
		Ant a = new Ant(w, AntColour.Black);
		w.setAntAt(t, a);
		int[] u = new int[7];
		u[0] = 49;
		u[1] = 50;
		w.mark(u, AntColour.Black, 0);
		a.sense(Condition.BlackMarker, SensingDirection.Ahead);
		a.turn(LeftOrRight.Left);
		assertTrue(a.sense(Condition.BlackMarker, SensingDirection.LeftAhead) == true);
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
		World w = World.getNewWorld("C:\\Users\\James Bedson\\Documents\\GitHub\\group7-se\\brain.ant", "C:\\Users\\James Bedson\\Documents\\GitHub\\group7-se\\brain.ant", world);
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
		int[] q = new int[2];
		q[0] = 50;
		q[1] = 51;
		Ant b = new Ant(w, AntColour.Red);
		w.setAntAt(q, b);
		int[] e = new int[2];
		e[0] = 50;
		e[1] = 49;
		Ant c = new Ant(w, AntColour.Red);
		w.setAntAt(e, c);
		int[] x = new int[2];
		x[0] = 51;
		x[1] = 50;
		Ant g = new Ant(w, AntColour.Red);
		w.setAntAt(x, g);
		int[] r = new int[2];
		r[0] = 51;
		r[1] = 50;
		Ant d = new Ant(w, AntColour.Red);
		w.setAntAt(r, d);
		int[] y = new int[2];
		y[0] = 51;
		y[1] = 49;
		Ant f = new Ant(w, AntColour.Red);
		w.setAntAt(y, f);
		int[] z = new int[2];
		z[0] = 49;
		z[1] = 50;
		Ant h = new Ant(w, AntColour.Red);
		w.setAntAt(z, h);
		int[] k = new int[2];
		k[0] = 49;
		k[1] = 50;
		Ant i = new Ant(w, AntColour.Red);
		w.setAntAt(k, i);
		int[] l = new int[2];
		l[0] = 49;
		l[1] = 49;
		Ant j = new Ant(w, AntColour.Red);
		w.setAntAt(l, j);
		int[] v = new int[2];
		v[0] = 49;
		v[1] = 49;
		Ant o = new Ant(w, AntColour.Red);
		w.setAntAt(v, o);
		assertTrue(a.isSurrounded() == true);
	}
}
