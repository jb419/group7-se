
/**
 * The Conditions that the brain can want to check when sensing. These are relative
 * conditions and must be translated by the brain into absolute conditions. Friend becomes
 * Red for example.
 * 
 * @author Owen Cox
 * @version 18/05/2012 - 2
 */
public enum BrainCondition
{
	Friend,
	Foe,
	FriendWithFood,
	FoeWithFood,
	Marker,
	FoeMarker,
	Home,
	FoeHome,
	Food,
	Rock;
}
