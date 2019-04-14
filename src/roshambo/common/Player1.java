package roshambo.common;

/**
 * This class represents the player of the Roshambo game
 * @author Steve Teece
 * @version 1.0
 */
public class Player1 extends Player 
{

	@Override
	Roshambo generateRoshambo()
	{
		
		return null;
	}
	
	/**
	 * Creates a new player for the game.
	 * @param playerName The name of the new Player
	 */
	public Player1(String playerName)
	{
		this.setName(playerName);
		
	}
	
}
