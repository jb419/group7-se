import org.junit.* ;
import static org.junit.Assert.* ;

/**
 * 
 * 
 * @author James Bedson
 * @version 06/06/2012 - 1
 */
public class test_Cell
{
	//Tests that calculate food returns 0 when there is no food in the cell.
	@Test
	public void test_calculateFoodAmount(){
		Cell c = new Cell();
		assertTrue(c.calculateFoodAmount() == 0);
	}
	//Tests that calculate food returns 1 when there is 1 blob of food in the cell.
	@Test
	public void test_calculateFoodAmount1(){
		Cell c = new Cell();
		c.addFood();
		assertTrue(c.calculateFoodAmount() == 1);
	}
	//Tests that calculate food returns 2 when there is 2 blobs of food in the cell.
	@Test
	public void test_calculateFoodAmount2(){
		Cell c = new Cell();
		c.addFood();
		c.addFood();
		assertTrue(c.calculateFoodAmount() == 2);
	}
	//Tests that calculate food returns 100 when there is 100 blobs of food in the cell.
	@Test
	public void test_calculateFoodAmount3(){
		Cell c = new Cell();
		for(int i = 0; i < 100; i++){
			c.addFood();
		}
		assertTrue(c.calculateFoodAmount() == 100);
	}
	//************************
	//*checkCondition() Tests*
	//************************
	//Certain variables that are common to quite a few tests.
	private World w;
	private Cell c;
	//This method is run before each test, it sets up some common elements involved in ant/cell related tests. 
	@Before
	public void setup_checkCondition(){
		WorldToken[][] world = new WorldToken[150][150];//Sets up a new WorldToken array.
		for(int i = 0; i < 150; i++){//Iterates through every element in the array and creates a new WorldToken of type Empty to avoid null pointer exceptions.
			for (int c = 0; c < 150; c++){
				world[i][c] = new WorldToken(WorldTokenType.Empty);
			}
			
		}
		//Creates a new World object, passing the location of an ant brain and the previously created world array.
		//Note. This test will fail if tried on another machine without changing the file path.
		w = World.getNewWorld("C:\\Users\\James Bedson\\Documents\\GitHub\\group7-se\\brain.ant", "C:\\Users\\James Bedson\\Documents\\GitHub\\group7-se\\brain.ant", world);
		c = new Cell();
	}
	
	@Test
	public void test_checkConditionBlackAnt(){
		//Creates a new Ant with colour Black.
		Ant a = new Ant(w, AntColour.Black);
		c.addAnt(a);
		assertTrue(c.checkCondition(Condition.BlackAnt) == true);
	}
	@Test
	public void test_checkConditionBlackAnt2(){
		//Creates a new Ant with colour Red.
		Ant a = new Ant(w, AntColour.Red);
		c.addAnt(a);
		assertTrue(c.checkCondition(Condition.BlackAnt) == false);
	}
	@Test
	public void test_checkConditionRedAnt(){
		Ant a = new Ant(w, AntColour.Red);
		c.addAnt(a);
		assertTrue(c.checkCondition(Condition.RedAnt) == true);
	}
	@Test
	public void test_checkConditionRedAnt2(){
		Ant a = new Ant(w, AntColour.Black);
		c.addAnt(a);
		assertTrue(c.checkCondition(Condition.RedAnt) == false);
	}
	@Test
	public void test_checkConditionBlackHill(){
		c.addAntHill(AntColour.Black);
		assertTrue(c.checkCondition(Condition.BlackHill) == true);
	}
	@Test
	public void test_checkConditionRedHill(){
		c.addAntHill(AntColour.Red);
		assertTrue(c.checkCondition(Condition.RedHill) == true);
	}
	@Test
	public void test_checkConditionBlackMarker(){
		c.mark(2, AntColour.Black);
		assertTrue(c.checkCondition(Condition.BlackMarker) == true);
	}
	@Test
	public void test_checkConditionRedMarker(){
		c.mark(4, AntColour.Red);
		assertTrue(c.checkCondition(Condition.RedMarker) == true);
	}
	@Test
	public void test_checkConditionFood(){
		c.addFood();
		assertTrue(c.checkCondition(Condition.Food) == true);
	}
	@Test
	public void test_checkConditionRock(){
		c.addRock();
		assertTrue(c.checkCondition(Condition.Rock) == true);
	}
	
	//************************
	//*isMarked() Tests*
	//************************
	private Cell v;
	@Before
	public void setup_isMarked(){
		v = new Cell();
	}
	//Start of black marker tests
	@Test
	public void test_isMarkedBlack1(){
		v.mark(1, AntColour.Black);
		assertTrue(v.isMarked(AntColour.Black, 1));
	}
	@Test
	public void test_isMarkedBlack2(){
		v.mark(2, AntColour.Black);
		assertTrue(v.isMarked(AntColour.Black, 2));
	}
	@Test
	public void test_isMarkedBlack3(){
		v.mark(3, AntColour.Black);
		assertTrue(v.isMarked(AntColour.Black, 3));
	}
	@Test
	public void test_isMarkedBlack4(){
		v.mark(4, AntColour.Black);
		assertTrue(v.isMarked(AntColour.Black, 4));
	}
	@Test
	public void test_isMarkedBlack5(){
		v.mark(5, AntColour.Black);
		assertTrue(v.isMarked(AntColour.Black, 5));
	}
	@Test
	public void test_isMarkedBlack6(){
		v.mark(6, AntColour.Black);
		assertTrue(v.isMarked(AntColour.Black, 6));
	}
	//Start of red marker tests
	@Test
	public void test_isMarkedRed1(){
		v.mark(1, AntColour.Red);
		assertTrue(v.isMarked(AntColour.Red, 1));
	}
	@Test
	public void test_isMarkedRed2(){
		v.mark(2, AntColour.Red);
		assertTrue(v.isMarked(AntColour.Red, 2));
	}
	@Test
	public void test_isMarkedRed3(){
		v.mark(3, AntColour.Red);
		assertTrue(v.isMarked(AntColour.Red, 3));
	}
	@Test
	public void test_isMarkedRed4(){
		v.mark(4, AntColour.Red);
		assertTrue(v.isMarked(AntColour.Red, 4));
	}
	@Test
	public void test_isMarkedRed5(){
		v.mark(5, AntColour.Red);
		assertTrue(v.isMarked(AntColour.Red, 5));
	}
	@Test
	public void test_isMarkedRed6(){
		v.mark(6, AntColour.Red);
		assertTrue(v.isMarked(AntColour.Red, 6));
	}

}
