package roshambo.views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import roshambo.common.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A version of "Roshambo", the classic Rock, Paper, Scissors game.
 * Written for Module 4, Java Programming. Assignment 1B.
 * @author Steve Teece - Student ID: 804727850
 * @version 1.0
 */
public class RoshamboApp extends JFrame 
{
	
	// Program variables
	private int gameCounter = 0;
	private int gameWins = 0;
	private int gameLosses = 0;
	private int gameDraws = 0;
	
	
	
	
	// Swing variables
	private JPanel contentPane;
	private JButton btnClose;
	private JTextField txtName;
	private final ButtonGroup bgOpponent = new ButtonGroup();
	private JRadioButton rdbtnBart;
	private JRadioButton rdbtnLisa;
	private JLabel lblOpponent;
	private JTextField txtGamesWon;
	private JTextField txtGamesLost;
	private JTextField txtGamesDrawn;
	private JTextField txtGamesPlayed;
	private final ButtonGroup bgPlayersHand = new ButtonGroup();
	private JRadioButton rdbtnRock;
	private JRadioButton rdbtnPaper;
	private JRadioButton rdbtnScissors;
	private JButton btnPlay;
	private JLabel lblRock;
	private JLabel lblPaper;
	private JLabel lblScissors;
	private JLabel lblBart;
	private JLabel lblLisa;

	public static void main(String[] args) 
	{
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (Throwable e) 
		{
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					RoshamboApp frame = new RoshamboApp();
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Private subroutine to draw the Swing GUI for the Roshambo program
	 */
	private RoshamboApp() 
	{
		setFont(new Font("Dialog", Font.PLAIN, 14));
		setSize(new Dimension(800, 550));
		setTitle("Roshambo - A Rock, Paper, Scissors game");
		
		// Set default font and size for JOptionPane message boxes
		UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(  
		          "Tahoma", Font.PLAIN, 14)));
		
		initComponents();
		createEvents();
	}
	
	////////////////////////////////////////////////////////////
	// Game methods
	////////////////////////////////////////////////////////////
	
	/**
	 * Private subroutine that is called by the click event of the "Play" button.
	 * This subroutine controls the validation of user data and selections before
	 * calling the routine to evaluate the hand played and determine a winner.
	 */
	private void playHand()
	{
		if (!isEmpty(txtName.getText())) 
		{
			if (rdbtnRock.isSelected() || rdbtnPaper.isSelected() || rdbtnScissors.isSelected()) 
			{
				evaluateHand();
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Please select eitehr Rock, Paper or Scissors to play a hand.");	
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Please enter your name to play.");	
		}
	}
	
	/**
	 * Private subroutine that determines the selected opponent (Bart or Lisa),
	 * determines the player's chosen hand, and increments the total games played counter
	 * before calling the subroutine to determine the winner of the hand.
	 */
	private void evaluateHand()
	{
		// Initialise local variables
		Player opponent = null;
		Player player = new Player1(txtName.getText());
		
		// Increment total played games
		gameCounter++;
		txtGamesPlayed.setText(Integer.valueOf(gameCounter).toString());
		
		// Determine opponent
		if (rdbtnBart.isSelected())
			opponent = new Bart();
		if (rdbtnLisa.isSelected())
			opponent = new Lisa();
		// Determine player's hand
		if (rdbtnRock.isSelected())
			player.setRoshambo(Roshambo.Rock);
		else if (rdbtnPaper.isSelected())
			player.setRoshambo(Roshambo.Paper);
		else if (rdbtnScissors.isSelected())
			player.setRoshambo(Roshambo.Scissors);
		
		determineWinner(player, opponent);
	}
	
	/**
	 * Function to evaluate if a string is either empty or contains a null value.
	 * This code snippet was found at https://stackoverflow.com/questions/3598770/check-whether-a-string-is-not-null-and-not-empty
	 * @param s string to check
	 * @return boolean true for an empty string, otherwise false
	 */
	private static boolean isEmpty( final String s ) 
	{
		  // Null-safe, short-circuit evaluation.
		  return s == null || s.trim().isEmpty();
	}
	
	/**
	 * Private subroutine that determines the winner of the hand, calls the
	 * appropriate Scorecard update subroutine and displays the winner and reason for the win.
	 * @param player the player of the game
	 * @param opponent the selected opponent (Bart or Lisa)
	 */
	private void determineWinner(Player player, Player opponent)
	{
		Player winner = null;
		Roshambo playerHand = player.getRoshambo();
		Roshambo opponentHand = opponent.getRoshambo();
		String outcomeDescription = "";
		String displayWinner = "";
		
		// Player selects Rock
		if (playerHand == Roshambo.Rock)
		{
		    // Rock vs. Rock - Draw
			if (opponentHand == Roshambo.Rock)
		    {
				winner = null;
				outcomeDescription = "Game is Drawn.\nNo Winner!";
		    	drawGame();
		    }
			
			// Rock vs. Paper - Paper wins
			else if (opponentHand == Roshambo.Paper)
			{
				winner = opponent;
				outcomeDescription = "Paper wraps Rock.\n" + winner.getName() + " wins!";
				loseGame();				
			}
			
			// Rock vs. Scissors - Rock wins
			else if (opponentHand == Roshambo.Scissors)
			{
				winner = player;
				outcomeDescription = "Rock blunts Scissors.\n" + winner.getName() + " wins!";
				winGame();
			}
		}
		// Player selects Paper
		else if (playerHand == Roshambo.Paper)
		{
			// Paper vs Rock - Paper wins
			if (opponentHand == Roshambo.Rock)
			{
				winner = player;
				outcomeDescription = "Paper wraps Rock.\n" + winner.getName() + " wins!";
				winGame();
			}
						
			// Paper vs Paper - Draw
			else if (opponentHand == Roshambo.Paper)
			{
				winner = null;
				outcomeDescription = "Game is Drawn.\nNo Winner!";
				drawGame();
		    				
			}
			
			// Paper vs Scissors - Scissors wins
			else if (opponentHand == Roshambo.Scissors)
			{
				winner = opponent;
				outcomeDescription = "Scissors cuts paper.\n" + winner.getName() + " wins!";
				loseGame();
			}
		}
		// Player selects Scissors
		else if (playerHand == Roshambo.Scissors)
		{
			// Scissors vs Rock - Rock wins
			if (opponentHand == Roshambo.Rock)
			{
				winner = opponent;
				outcomeDescription = "Rock blunts Scissors.\n" + winner.getName() + " wins!";
				loseGame();
			}
			
			// Scissors vs Paper - Scissors wins
			else if (opponentHand == Roshambo.Paper)
			{
				winner = player;
				outcomeDescription = "Scissors cuts Paper.\n" + winner.getName() + " wins!";
				winGame();				
			}
			
			// Scissors vs Scissors - Draw
			else if (opponentHand == Roshambo.Scissors)
			{
				winner = null;
				outcomeDescription = "Game is Drawn.\nNo Winner!";
				drawGame();
		    				
			}
		}
		
		//Display Winner;
		displayWinner = "You played: " + player.getRoshambo() + "\n"
				+ opponent.getName() + " played: " + opponent.getRoshambo() + "\n\n"
				+ outcomeDescription;
		
		JOptionPane.showMessageDialog(null, displayWinner);
		
	}
	
	/**
	 * Private subroutine to update the total games won counter
	 */
	private void winGame()
	{
		gameWins++;
		txtGamesWon.setText(Integer.valueOf(gameWins).toString());
	}
	
	/**
	 * Private subroutine to update the total games lost counter
	 */
	private void loseGame()
	{
		gameLosses++;
		txtGamesLost.setText(Integer.valueOf(gameLosses).toString());
	}
	
	/**
	 * Private subroutine to update the total games drawn counter
	 */
	private void drawGame()
	{
		gameDraws++;
		txtGamesDrawn.setText(Integer.valueOf(gameDraws).toString());
	}
	
	private void setOpponent()
	{
		if (rdbtnBart.isSelected())
			lblOpponent.setText("Bart");
		else
			lblOpponent.setText("Lisa");
	}
	
	////////////////////////////////////////////////////////////
	// Contains code to create and initialise all components
	////////////////////////////////////////////////////////////
	
	/**
	 * Private method to create / define the components of the GUI.
	 */
	private void initComponents() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(RoshamboApp.class.getResource("/roshambo/resources/roshambo_32.png")));
		setBounds(100, 100, 800, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		btnClose = new JButton("Close");
		
		JLabel lblName = new JLabel("Please enter your name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblWelcomeToRoshambo = new JLabel("Welcome to Roshambo, a classic Rock, Paper Scissors game.");
		lblWelcomeToRoshambo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblWelcomeToRoshambo.setHorizontalAlignment(SwingConstants.CENTER);
		
		JSeparator separator = new JSeparator();
		
		txtName = new JTextField();
		txtName.setColumns(10);
		
		JPanel pnlScoreCard = new JPanel();
		pnlScoreCard.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlScoreCard.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Scorecard", TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 14), null));
		
		JLabel lblChooseYourOpponent = new JLabel("Choose your opponent:");
		lblChooseYourOpponent.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblBart = new JLabel("");
		lblBart.setIcon(new ImageIcon(RoshamboApp.class.getResource("/roshambo/resources/bart_100.png")));
		
		lblLisa = new JLabel("");
		lblLisa.setIcon(new ImageIcon(RoshamboApp.class.getResource("/roshambo/resources/lisa_100.png")));
		
		rdbtnBart = new JRadioButton("Bart");
		
		rdbtnBart.setSelected(true);
		bgOpponent.add(rdbtnBart);
		
		rdbtnLisa = new JRadioButton("Lisa");
		
		bgOpponent.add(rdbtnLisa);
		
		lblOpponent = new JLabel("Bart");
		lblOpponent.setForeground(Color.BLUE);
		lblOpponent.setHorizontalAlignment(SwingConstants.CENTER);
		lblOpponent.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JLabel lblPlayingAgainst = new JLabel("Playing against:");
		lblPlayingAgainst.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayingAgainst.setForeground(Color.BLUE);
		lblPlayingAgainst.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblChooseHand = new JLabel("Choose your hand:");
		lblChooseHand.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblRock = new JLabel("");
		
		lblRock.setIcon(new ImageIcon(RoshamboApp.class.getResource("/roshambo/resources/rock_100.png")));
		
		lblPaper = new JLabel("");
		lblPaper.setIcon(new ImageIcon(RoshamboApp.class.getResource("/roshambo/resources/Paper_100.png")));
		
		lblScissors = new JLabel("");
		lblScissors.setIcon(new ImageIcon(RoshamboApp.class.getResource("/roshambo/resources/scissors_100.png")));
		
		rdbtnRock = new JRadioButton("Rock");
		
		bgPlayersHand.add(rdbtnRock);
		
		rdbtnPaper = new JRadioButton("Paper");
		
		bgPlayersHand.add(rdbtnPaper);
		
		rdbtnScissors = new JRadioButton("Scissors");
		
		bgPlayersHand.add(rdbtnScissors);
		
		btnPlay = new JButton("Play");
		
		btnPlay.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(separator, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)
						.addComponent(lblWelcomeToRoshambo, GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblName)
										.addComponent(lblPlayingAgainst, GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
										.addComponent(lblOpponent, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
										.addComponent(lblChooseYourOpponent)
										.addComponent(lblChooseHand, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblRock, Alignment.TRAILING))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(10)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(lblBart)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addGap(35)
													.addComponent(rdbtnBart)))
											.addGap(35)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addGap(18)
													.addComponent(lblLisa))
												.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
													.addComponent(rdbtnLisa)
													.addComponent(lblPaper, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
												.addComponent(btnPlay, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
										.addComponent(txtName, GroupLayout.PREFERRED_SIZE, 259, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(77)
									.addComponent(rdbtnRock)
									.addPreferredGap(ComponentPlacement.RELATED, 204, Short.MAX_VALUE)
									.addComponent(rdbtnPaper)
									.addGap(41)))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(pnlScoreCard, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(10)
											.addComponent(rdbtnScissors))
										.addComponent(lblScissors, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
									.addGap(92))
								.addComponent(btnClose))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblWelcomeToRoshambo)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblName)
								.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(19)
									.addComponent(lblChooseYourOpponent)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(lblPlayingAgainst)
									.addGap(9)
									.addComponent(lblOpponent))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(10)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblLisa)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblBart)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
												.addComponent(rdbtnBart)
												.addComponent(rdbtnLisa)))))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(pnlScoreCard, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)))
					.addGap(30)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblChooseHand, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblRock))
								.addComponent(lblPaper, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblScissors, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnScissors)
								.addComponent(rdbtnPaper)
								.addComponent(rdbtnRock))
							.addGap(35)
							.addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnClose))
					.addContainerGap(58, Short.MAX_VALUE))
		);
		
		JLabel lblGamesWon = new JLabel("Games won");
		lblGamesWon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblGamesLost = new JLabel("Games lost");
		lblGamesLost.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblGamesPlayed = new JLabel("Games played");
		lblGamesPlayed.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblGamesDrawn = new JLabel("Games drawn");
		lblGamesDrawn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		txtGamesWon = new JTextField();
		txtGamesWon.setBackground(Color.WHITE);
		txtGamesWon.setEditable(false);
		txtGamesWon.setHorizontalAlignment(SwingConstants.CENTER);
		txtGamesWon.setText("0");
		txtGamesWon.setFont(new Font("Tahoma", Font.BOLD, 18));
		txtGamesWon.setColumns(10);
		
		txtGamesLost = new JTextField();
		txtGamesLost.setBackground(Color.WHITE);
		txtGamesLost.setEditable(false);
		txtGamesLost.setText("0");
		txtGamesLost.setHorizontalAlignment(SwingConstants.CENTER);
		txtGamesLost.setFont(new Font("Tahoma", Font.BOLD, 18));
		txtGamesLost.setColumns(10);
		
		txtGamesDrawn = new JTextField();
		txtGamesDrawn.setBackground(Color.WHITE);
		txtGamesDrawn.setEditable(false);
		txtGamesDrawn.setText("0");
		txtGamesDrawn.setHorizontalAlignment(SwingConstants.CENTER);
		txtGamesDrawn.setFont(new Font("Tahoma", Font.BOLD, 18));
		txtGamesDrawn.setColumns(10);
		
		txtGamesPlayed = new JTextField();
		txtGamesPlayed.setBackground(Color.WHITE);
		txtGamesPlayed.setEditable(false);
		txtGamesPlayed.setText("0");
		txtGamesPlayed.setHorizontalAlignment(SwingConstants.CENTER);
		txtGamesPlayed.setFont(new Font("Tahoma", Font.BOLD, 18));
		txtGamesPlayed.setColumns(10);
		GroupLayout gl_pnlScoreCard = new GroupLayout(pnlScoreCard);
		gl_pnlScoreCard.setHorizontalGroup(
			gl_pnlScoreCard.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlScoreCard.createSequentialGroup()
					.addGap(56)
					.addGroup(gl_pnlScoreCard.createParallelGroup(Alignment.LEADING)
						.addComponent(lblGamesWon)
						.addGroup(gl_pnlScoreCard.createSequentialGroup()
							.addGap(10)
							.addComponent(txtGamesWon, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblGamesDrawn)
						.addGroup(gl_pnlScoreCard.createSequentialGroup()
							.addGap(10)
							.addComponent(txtGamesDrawn, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
					.addGroup(gl_pnlScoreCard.createParallelGroup(Alignment.LEADING)
						.addComponent(lblGamesLost)
						.addComponent(lblGamesPlayed)
						.addGroup(gl_pnlScoreCard.createSequentialGroup()
							.addGap(10)
							.addComponent(txtGamesLost, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlScoreCard.createSequentialGroup()
							.addGap(10)
							.addComponent(txtGamesPlayed, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)))
					.addGap(62))
		);
		gl_pnlScoreCard.setVerticalGroup(
			gl_pnlScoreCard.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlScoreCard.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlScoreCard.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblGamesWon)
						.addComponent(lblGamesLost))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnlScoreCard.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtGamesWon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtGamesLost, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_pnlScoreCard.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblGamesDrawn)
						.addComponent(lblGamesPlayed))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlScoreCard.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtGamesDrawn, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtGamesPlayed, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(13, Short.MAX_VALUE))
		);
		pnlScoreCard.setLayout(gl_pnlScoreCard);
		contentPane.setLayout(gl_contentPane);
			
	}

	/////////////////////////////////////////////////////////////	
	// Contains the code to create all the events
	/////////////////////////////////////////////////////////////
	
	/**
	 * Private method to create the event handlers used by the game.
	 */
	private void createEvents() 
	{
		btnPlay.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				playHand();
			}
		});
		
		lblBart.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				rdbtnBart.setSelected(true);
				setOpponent();
			}
		});
		
		rdbtnBart.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				rdbtnBart.setSelected(true);
				setOpponent();
			}
		});
		
		lblLisa.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				rdbtnLisa.setSelected(true);
				setOpponent();
			}
		});
		
		rdbtnLisa.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				rdbtnLisa.setSelected(true);
				setOpponent();
			}
		});
		
		btnClose.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				System.exit(0);
			}
		});
		
		lblRock.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				rdbtnRock.setSelected(true);
			}
		});
		
		lblPaper.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				rdbtnPaper.setSelected(true);
			}
		});
		
		lblScissors.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				rdbtnScissors.setSelected(true);
			}
		});
		
	}
}
