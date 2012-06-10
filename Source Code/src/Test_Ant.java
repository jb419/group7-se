import org.junit.* ;
import static org.junit.Assert.* ;

/**
 * 
 * 
 * @author Rajbir Bal
 * @version 06/06/2012 - 1
 */
public class Test_Ant
{
	//Tests that checks if an Ant has food.
	@Test
	public void test_AntHasFood(){
		Ant a = new Ant(null, null);
		assertTrue(a.hasFood() == true);
	}
	//Tests that checks if the Ant picks up food.
	@Test
	public void test_pickUpFood(){
		Ant a = new Ant(null, null);
		assertTrue(a.pickup() == true);
	}
	//Tests that checks if the Ant uses sense on the surrounding cells.
	@Test
	public void test_sense(){
		Ant a = new Ant(null, null);
		assertTrue(a.sense(null, null) == true);
	}
	//Tests that checks if a Ant leaves a sense marker.
	@Test
	public void test_senseMarker(){
		Ant a = new Ant(null, null);
		assertTrue(a.senseMarker(0, null) == true);
	}
	//Tests that checks if an Ant is surrounded.
	@Test
	public void test_isSurrounded(){
		Ant a = new Ant(null, null);
		assertTrue(a.isSurrounded() == true);
	}
}
