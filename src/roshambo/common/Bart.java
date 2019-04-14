package roshambo.common;


/**
 * One of the opponents in the Roshambo game.
 * Bart always selects "Rock" as their Roshambo hand to play.
 * @author Steve Teece
 * @version 1.0
 */
public class Bart extends Player
{

	
	/**
	 * Creates a new instance of Bart, and assigns a Roshambo hand of
	 * Rock. This is the only hand available to Bart.
	 */
	public Bart()
	{
		this._name = "Bart";
		this._selection = generateRoshambo();
	}
	
	@Override
	Roshambo generateRoshambo() {
		
		// Bart always returns "Rock"
		return Roshambo.Rock;
	}
	
}
