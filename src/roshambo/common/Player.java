package roshambo.common;

/**
 * The Player class is an abstract class that contains code common to all players.
 * The Player class defines the player's name, and their Roshambo hand (Rock, Paper or Scissors)
 * An abstract method to generate a new Roshambo value is included, and must be implemented by any class deriving from Player.
 * @author Steve Teece
 * @version 1.0
 */
public abstract class Player 
{
	
	protected String _name;
	
	protected Roshambo _selection;
	
	/**
	 * Abstract method to generate a Roshambo hand.
	 * This method must be implemented in classes deriving from the Player class.
	  * @return Roshambo Returns a valid Roshambo value of either Rock, Paper or Scissors.
	 */
	abstract Roshambo generateRoshambo();

	/**
	 * Gets the player's name.
	 * @return String The name of the player
	 */
	public String getName() {
		return this._name;
	}

	/**
	 * Sets the name of the player.
	 * @param name The player's name
	 */
	public void setName(String name) {
		this._name = name;
	}

	/**
	 * Gets the Roshambo value of the player's hand.
	 * @return Roshambo The player's selection of Rock, Paper or Scissors
	 */
	public Roshambo getRoshambo() {
		return this._selection;
	}

	/**
	 * Sets the Roshambo value of the player.
	 * This is the player's choice of whether to play Rock, Paper or Scissors.
	 * 
	 * @param roshambo The player's selection of Rock, Paper or Scissors
	 */
	public void setRoshambo(Roshambo roshambo) {
		this._selection = roshambo;
	}
	

}
