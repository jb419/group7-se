
/**
 * The Condition enum stores the absolute conditions that can be checked. So while a
 * brain might want to check friend or foe it must convert into this type of condition
 * 
 * @author Owen Cox
 * @version 25/05/2012 - 1
 */
public enum Condition
{
	RedAnt, 
	BlackAnt, 
	RedAntWithFood, 
	BlackAntWithFood, 
	RedMarker, 
	BlackMarker,
	RedHill, 
	BlackHill, 
	Food, 
	Rock;
}
