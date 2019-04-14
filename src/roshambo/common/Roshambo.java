package roshambo.common;

/**
 * Enumeration of Roshambo values. Valid values are Rock, Paper or Scissors.
 * @author Steve Teece
 * @version 1.0
 */
public enum Roshambo 
{
	Rock
	{
		public String toString()
		{
			return "Rock";
		}
	},
	Paper
	{
		public String toString()
		{
			return "Paper";
		}
	},
	Scissors
	{
		public String toString()
		{
			return "Scissors";
		}
	};
}
